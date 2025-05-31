package app;

import servent.message.SKReplyMessage;
import servent.message.SKRequestMessage;
import servent.message.util.MessageUtil;

import java.util.*;

import static servent.message.util.MessageUtil.sendMessage;

public class SuzukiKasamiMutex {
    public static int sequenceNumber=0;
    public static int[] RN = new int[ChordState.CHORD_SIZE];
    public static boolean hasToken = (AppConfig.myServentInfo.getChordId() == 35);
    public static Queue<Integer> tokenQueue = new ArrayDeque<>();
    public static int[] LN = new int[ChordState.CHORD_SIZE];
    public static final Object objectLock=new Object();

    public static void updateValues(Queue<Integer> tQueue, int[] ln) {
        LN = ln.clone();
        tokenQueue = tQueue;
        hasToken = true;
        objectLock.notifyAll();
    }

    public void requestCriticalSection() {
        synchronized (objectLock) {
            AppConfig.timestampedStandardPrint("Requesting token");
            //Increment my requestNum and seqNum
            sequenceNumber++;
            RN[(AppConfig.myServentInfo.getChordId())] = sequenceNumber;
            //If I don't have the token notify others
            if(!hasToken){
                AppConfig.timestampedStandardPrint("Propagate token request");
                propagateRequest(AppConfig.myServentInfo.getChordId(), new HashSet<>());
            }
            //Wait for token
            while (!hasToken &&  (!tokenQueue.isEmpty() && tokenQueue.peek() != AppConfig.myServentInfo.getChordId())) {
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            //Pop myself from queue after I get the token
            AppConfig.timestampedStandardPrint("Token received");
            tokenQueue.remove((Integer) (AppConfig.myServentInfo.getChordId()));
        }
    }

    public void releaseCriticalSection() {
        synchronized (objectLock) {
            LN[AppConfig.myServentInfo.getChordId()] = RN[AppConfig.myServentInfo.getChordId()];
            AppConfig.timestampedStandardPrint("Q when starting" + tokenQueue.toString());
            for (int i = 0; i < ChordState.CHORD_SIZE; i++) {
                if (i != AppConfig.myServentInfo.getChordId() && RN[i] > LN[i]) {
                    tokenQueue.add(i);
                }

            }
            AppConfig.timestampedStandardPrint("Q after for loop " + tokenQueue.toString());
            if (!tokenQueue.isEmpty() && hasToken) {
                int nextNode = tokenQueue.poll();
                sendToken(nextNode);
                hasToken = false;
            }
            AppConfig.timestampedStandardPrint("Released critical section and hasToken: "+hasToken);
        }
    }

    public static void sendToken(int requestNodeChordId) {
        ServentInfo nextNodeInfo = AppConfig.chordState.getNextNodeForKey(requestNodeChordId);
        AppConfig.timestampedStandardPrint(AppConfig.myServentInfo.getIpAndPort() + " sending token to:"+ nextNodeInfo);

        sendMessage(new SKReplyMessage((AppConfig.myServentInfo.getListenerPort()),
                nextNodeInfo.getChordId()==AppConfig.myServentInfo.getChordId()? AppConfig.chordState.getNextNodePort():nextNodeInfo.getListenerPort(),
                requestNodeChordId, LN.clone(), new ArrayDeque<>(tokenQueue)));
    }

    public static void propagateRequest(int requestNodeChordId, Set<String> receivers){
        List<String> outgoing = new ArrayList<>();

        AppConfig.timestampedErrorPrint((Arrays.stream(AppConfig.chordState.getSuccessorTable()).toList().toString()));
        for(ServentInfo node : Arrays.stream(AppConfig.chordState.getSuccessorTable()).toList()) {
            if (node == null)
                continue;
            if (!receivers.contains(node.getIpAndPort()) && node.getChordId() != requestNodeChordId) {
                receivers.add(node.getIpAndPort());
                outgoing.add(node.getIpAndPort());
            }
        }
        for(String node : outgoing)
            MessageUtil.sendMessage(new SKRequestMessage(AppConfig.myServentInfo.getListenerPort(),
                    Integer.parseInt(node.split(":")[1]), receivers, requestNodeChordId, sequenceNumber));
    }

}

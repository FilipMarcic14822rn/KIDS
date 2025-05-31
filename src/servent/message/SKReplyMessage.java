package servent.message;

import java.util.Queue;

public class SKReplyMessage extends BasicMessage {
    private int requesterChordId;
    private int[] LN;
    private Queue<Integer> tokenQueue;

    public SKReplyMessage(int senderPort, int receiverPort, int requesterChordId, int[] LN, Queue<Integer> tokenQueue) {
        super(MessageType.SK_REPLY, senderPort, receiverPort);
        this.requesterChordId = requesterChordId;
        this.LN = LN;
        this.tokenQueue = tokenQueue;
    }

    public int getRequesterChordId() {return requesterChordId;}

    public int[] getLN() {return LN;}

    public Queue<Integer> getTokenQueue() {return tokenQueue;}
}

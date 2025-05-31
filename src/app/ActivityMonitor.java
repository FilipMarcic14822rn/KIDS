package app;

import servent.message.PingMessage;
import servent.message.WarningMessage;
import servent.message.util.MessageUtil;

public class ActivityMonitor implements Runnable {
    private volatile boolean working = true;
    public static final Object deleteLock = new Object();
    @Override
    public void run() {
        while(working){
            ServentInfo me = AppConfig.myServentInfo;
            ServentInfo successor = AppConfig.chordState.getSuccessorTable()[0];
            me.setSuccessorActivityFlag(false);

            if(successor != null){
                MessageUtil.sendMessage(new PingMessage(me.getListenerPort(),
                        successor.getListenerPort(), "ping"));

                try {Thread.sleep(AppConfig.myServentInfo.getSoftLimit());}
                catch (InterruptedException e) {throw new RuntimeException(e);}

                if(!working)
                    break;

                if(!me.isSuccessorActivityFlag()){
                    ServentInfo successorChecker = AppConfig.chordState.getPredecessor();

                    if(successorChecker != null) {
                        AppConfig.timestampedStandardPrint("Sending warning to " + successorChecker.getListenerPort() +
                                " because " + successor.getListenerPort() + " isn't responding");
                        MessageUtil.sendMessage(new WarningMessage(me.getListenerPort(),
                                successorChecker.getListenerPort(), successor.getListenerPort()));
                    }else
                        AppConfig.timestampedStandardPrint("Successor doesn't have a successor, waiting on original pong");
                }

                try {Thread.sleep(AppConfig.myServentInfo.getHardLimit());}
                catch (InterruptedException e) {throw new RuntimeException(e);}

                if(!working)
                    break;

                if(!me.isSuccessorActivityFlag()) {
                    AppConfig.timestampedStandardPrint("Monitor removing " + successor.getListenerPort());
                    AppConfig.chordState.removeNode(successor);
                }else
                    AppConfig.timestampedStandardPrint("Activity confirmed for " + successor.getListenerPort());
            }
        }
    }

    public void stop() {
        this.working = false;
    }
}

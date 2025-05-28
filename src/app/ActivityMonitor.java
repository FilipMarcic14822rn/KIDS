package app;

import servent.message.PingMessage;
import servent.message.WarningMessage;
import servent.message.util.MessageUtil;

public class ActivityMonitor implements Runnable {
    private volatile boolean working = true;
    @Override
    public void run() {
        while(working){
            ServentInfo me = AppConfig.myServentInfo;
            ServentInfo successor = AppConfig.chordState.getSuccessorTable()[0];

            if(successor != null){
                MessageUtil.sendMessage(new PingMessage(me.getListenerPort(),
                        successor.getListenerPort(), "ping"));
                System.out.println(Thread.currentThread().getName());
                try {Thread.sleep(AppConfig.myServentInfo.getSoftLimit());}
                catch (InterruptedException e) {throw new RuntimeException(e);}

                if(!working)
                    break;

                if(!me.isSuccessorActivityFlag()){
                    ServentInfo successorChecker = AppConfig.chordState.getSuccessorTable()[1];

                    if(successorChecker != null)
                        MessageUtil.sendMessage(new WarningMessage(me.getListenerPort(),
                                successorChecker.getListenerPort()));

                    try {Thread.sleep(AppConfig.myServentInfo.getHardLimit());}
                    catch (InterruptedException e) {throw new RuntimeException(e);}

                    if(!working)
                        break;

                    if(!me.isSuccessorActivityFlag())
                        AppConfig.chordState.removeNode(successor);
                }

                me.setSuccessorActivityFlag(false);
            }
        }
    }
}

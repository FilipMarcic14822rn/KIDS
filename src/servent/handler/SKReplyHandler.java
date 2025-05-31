package servent.handler;

import app.AppConfig;
import app.ServentInfo;
import app.SuzukiKasamiMutex;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.SKReplyMessage;
import servent.message.util.MessageUtil;

public class SKReplyHandler implements MessageHandler {
    private Message clientMessage;
    public SKReplyHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.SK_REPLY){
            synchronized (SuzukiKasamiMutex.objectLock) {
                SKReplyMessage skrepmsg = (SKReplyMessage) clientMessage;
                if(skrepmsg.getRequesterChordId()!=AppConfig.myServentInfo.getChordId()){
                    ServentInfo nextNode =  AppConfig.chordState.getNextNodeForKey(skrepmsg.getRequesterChordId());
                    MessageUtil.sendMessage(new SKReplyMessage(skrepmsg.getSenderPort(),
                            nextNode.getListenerPort()==AppConfig.myServentInfo.getListenerPort()?AppConfig.chordState.getNextNodePort() : nextNode.getListenerPort(),
                            skrepmsg.getRequesterChordId(), skrepmsg.getLN(), skrepmsg.getTokenQueue()));
                    AppConfig.timestampedStandardPrint("Propagating token to "+nextNode.getChordId() + "  " + nextNode.getListenerPort());
                    return;
                }
                AppConfig.timestampedStandardPrint("Using token");
                SuzukiKasamiMutex.updateValues(skrepmsg.getTokenQueue(), skrepmsg.getLN());
            }
        }else
            AppConfig.timestampedErrorPrint("SKReply handler got a message that is not SK_REPLY");
    }
}

package servent.handler;

import app.AppConfig;
import app.SuzukiKasamiMutex;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.SKRequestMessage;

import static app.SuzukiKasamiMutex.*;

public class SKRequestHandler implements MessageHandler {
    private Message clientMessage;
    public SKRequestHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.SK_REQUEST){
            SKRequestMessage skreqmsg= (SKRequestMessage) clientMessage;

            synchronized (objectLock){
                RN[skreqmsg.getRequestNodeChordId()] = Math.max(RN[skreqmsg.getRequestNodeChordId()], skreqmsg.getSequenceNumber());
                if (hasToken && tokenQueue.isEmpty() &&
                        (RN[skreqmsg.getRequestNodeChordId()] > LN[skreqmsg.getRequestNodeChordId()] ||
                        (RN[skreqmsg.getRequestNodeChordId()] == LN[skreqmsg.getRequestNodeChordId()] &&
                         skreqmsg.getRequestNodeChordId() < AppConfig.myServentInfo.getChordId()))){

                    hasToken = false;
                    sendToken(skreqmsg.getRequestNodeChordId());
                }
                else if(!hasToken)
                    SuzukiKasamiMutex.propagateRequest(skreqmsg.getRequestNodeChordId(), skreqmsg.getReceivers());
            }
        }else
            AppConfig.timestampedErrorPrint("SKRequest handler got a message that is not SK_REQUEST");
    }
}

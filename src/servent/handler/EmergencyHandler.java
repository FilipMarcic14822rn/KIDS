package servent.handler;

import app.AppConfig;
import servent.message.DefuseMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PingMessage;
import servent.message.util.MessageUtil;

public class EmergencyHandler implements MessageHandler {
    private final Message clientMessage;

    public EmergencyHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        //Send ping to origin to confirm activity if communication is possible
        //and send defuse to successor to intervene with origin if communication is impossible
        if (clientMessage.getMessageType() == MessageType.EMERGENCY){
            MessageUtil.sendMessage(new PingMessage(AppConfig.myServentInfo.getListenerPort(),
                    Integer.parseInt(clientMessage.getMessageText().split(":")[1]), "pong"));

            MessageUtil.sendMessage(new DefuseMessage(AppConfig.myServentInfo.getListenerPort(),
                    clientMessage.getSenderPort(), clientMessage.getMessageText()));
        }else
            AppConfig.timestampedErrorPrint("Emergency handler got a message that is not EMERGENCY");
    }
}

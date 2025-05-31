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
            if (!(AppConfig.myServentInfo.getChordId() == 57))
                MessageUtil.sendMessage(new PingMessage(AppConfig.myServentInfo.getListenerPort(),
                        Integer.parseInt(clientMessage.getMessageText()), "pong"));

            MessageUtil.sendMessage(new DefuseMessage(AppConfig.myServentInfo.getListenerPort(),
                    clientMessage.getSenderPort(), clientMessage.getMessageText()));

            AppConfig.timestampedStandardPrint("Emergency Message Received from " + clientMessage.getSenderPort() +
                    " because node " + clientMessage.getMessageText() + " couldn't reach me");
        }else
            AppConfig.timestampedErrorPrint("Emergency handler got a message that is not EMERGENCY");
    }
}

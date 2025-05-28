package servent.handler;

import app.AppConfig;
import servent.message.EmergencyMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.util.MessageUtil;

public class WarningHandler implements MessageHandler{
    private final Message clientMessage;

    public WarningHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if (clientMessage.getMessageType() != MessageType.WARNING) {
            //Send emergency to predecessor because unresponsiveness
            EmergencyMessage emergencyMessage = new EmergencyMessage(AppConfig.myServentInfo.getListenerPort(),
                    AppConfig.chordState.getPredecessor().getListenerPort(),
                    clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort());

            MessageUtil.sendMessage(emergencyMessage);
        }else
            AppConfig.timestampedErrorPrint("Warning handler got a message that is not WARNING");
    }
}

package servent.handler;

import app.AppConfig;
import servent.message.EmergencyMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.WarningMessage;
import servent.message.util.MessageUtil;

public class WarningHandler implements MessageHandler{
    private final Message clientMessage;

    public WarningHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.WARNING) {
            //Send emergency to risky node because unresponsiveness
            MessageUtil.sendMessage( new EmergencyMessage(AppConfig.myServentInfo.getListenerPort(),
                    ((WarningMessage)clientMessage).getRiskyNode(),
                    Integer.toString(clientMessage.getSenderPort())));

            AppConfig.timestampedErrorPrint("Sending emergency to " + AppConfig.chordState.getPredecessor().getListenerPort());
        }else
            AppConfig.timestampedErrorPrint("Warning handler got a message that is not WARNING");
    }
}

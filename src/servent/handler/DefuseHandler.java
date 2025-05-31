package servent.handler;

import app.AppConfig;
import servent.message.DefuseMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.util.MessageUtil;

public class DefuseHandler implements MessageHandler{
    private final Message clientMessage;

    public DefuseHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        //Check if defuse is meant for me or forward to ping origin
        if (clientMessage.getMessageType() == MessageType.DEFUSE)
            if (clientMessage.getMessageText().equals(Integer.toString(AppConfig.myServentInfo.getListenerPort()))) {
                AppConfig.myServentInfo.setSuccessorActivityFlag(true);
                AppConfig.timestampedStandardPrint("Defusing for node " + clientMessage.getSenderPort());
            }
            else {
                AppConfig.timestampedStandardPrint("Defuse forwarding activity of " + clientMessage.getSenderPort() + " to " +
                        clientMessage.getMessageText());
                MessageUtil.sendMessage(new DefuseMessage(AppConfig.chordState.getPredecessor().getListenerPort(),
                        Integer.parseInt(clientMessage.getMessageText()), clientMessage.getMessageText()));
            }
        else
            AppConfig.timestampedErrorPrint("Defuse handler got a message that is not DEFUSE");
    }
}

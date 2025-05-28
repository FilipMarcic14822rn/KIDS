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
            if (clientMessage.getMessageText().equals(AppConfig.myServentInfo.getIpAddress() + ":" + AppConfig.myServentInfo.getListenerPort()))
                AppConfig.myServentInfo.setSuccessorActivityFlag(true);
            else
                MessageUtil.sendMessage(new DefuseMessage(AppConfig.myServentInfo.getListenerPort(),
                        Integer.parseInt(clientMessage.getMessageText().split(":")[1]),clientMessage.getMessageText()));
        else
            AppConfig.timestampedErrorPrint("Defuse handler got a message that is not DEFUSE");
    }
}

package servent.handler;

import app.AppConfig;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.PingMessage;
import servent.message.util.MessageUtil;

public class PingHandler implements MessageHandler{
    private Message clientMessage;

    public PingHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.PING){
            //Return pong or confirm activity of node
            if (clientMessage.getMessageText().equals("ping")){
                PingMessage pong=new PingMessage(AppConfig.myServentInfo.getListenerPort(),
                        clientMessage.getSenderPort(), "pong");

                MessageUtil.sendMessage(pong);
            }
            else if (clientMessage.getMessageText().equals("pong")){
                AppConfig.myServentInfo.setSuccessorActivityFlag(true);
            }
        }else {
            AppConfig.timestampedErrorPrint("Ping handler got a message that is not PING");
        }
    }
}

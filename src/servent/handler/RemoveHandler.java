package servent.handler;

import app.AppConfig;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.RemoveMessage;

public class RemoveHandler implements MessageHandler{
    private Message clientMessage;

    public RemoveHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.REMOVE) {
            AppConfig.timestampedStandardPrint("Asked to remove: " + ((RemoveMessage) clientMessage).getNode());
            AppConfig.chordState.removeNode(((RemoveMessage) clientMessage).getNode());
        }
        else
            AppConfig.timestampedErrorPrint("Remove handler got a message that is not REMOVE");
    }
}

package servent.message;

import app.ServentInfo;

public class RemoveMessage extends BasicMessage{
    private ServentInfo node;
    public RemoveMessage(int senderPort, int receiverPort, ServentInfo node) {
        super(MessageType.REMOVE, senderPort, receiverPort);
        this.node = node;
    }

    public ServentInfo getNode() {return node;}
}

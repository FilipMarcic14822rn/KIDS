package servent.message;

public class DefuseMessage extends BasicMessage{
    public DefuseMessage(int senderPort, int receiverPort, String destination) {
        super(MessageType.DEFUSE, senderPort, receiverPort);
    }
}

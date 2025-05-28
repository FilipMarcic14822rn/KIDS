package servent.message;

public class WarningMessage extends BasicMessage{
    public WarningMessage(int senderPort, int receiverPort) {
        super(MessageType.WARNING, senderPort, receiverPort);
    }
}

package servent.message;

public class       WarningMessage extends BasicMessage{
    private final int riskyNode;
    public WarningMessage(int senderPort, int receiverPort, int riskyNode) {
        super(MessageType.WARNING, senderPort, receiverPort);
        this.riskyNode = riskyNode;
    }

    public int getRiskyNode() {return riskyNode;}
}

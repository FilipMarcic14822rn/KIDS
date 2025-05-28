package servent.message;

public class EmergencyMessage extends BasicMessage{
    public EmergencyMessage(int senderPort, int receiverPort, String origin) {
        super(MessageType.EMERGENCY, senderPort, receiverPort, origin);
    }
}

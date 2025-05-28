package servent.message;

public class PingMessage extends BasicMessage{
    public PingMessage(int senderPort, int receiverPort, String messageText) {
        super(MessageType.PING, senderPort, receiverPort, messageText);
    }
}

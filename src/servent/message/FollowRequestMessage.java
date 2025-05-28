package servent.message;

public class FollowRequestMessage extends BasicMessage {
    private String destination;
    private String origin;
    public FollowRequestMessage( int senderPort, int receiverPort, String destination , String origin) {
        super(MessageType.FOLLOW_REQUEST, senderPort, receiverPort);
        this.destination = destination;
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }
}

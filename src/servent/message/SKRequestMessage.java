package servent.message;

import java.util.Set;

public class SKRequestMessage extends BasicMessage {
    private int requestNodeChordId;
    private Set<String> receivers;
    private int sequenceNumber;

    public SKRequestMessage(int senderPort, int receiverPort, Set<String> receivers, int requestNodeChordId, int sequenceNumber) {
        super(MessageType.SK_REQUEST, senderPort, receiverPort);
        this.receivers = receivers;
        this.requestNodeChordId = requestNodeChordId;
        this.sequenceNumber = sequenceNumber;
    }

    public Set<String> getReceivers() {return receivers;}

    public int getRequestNodeChordId() {return requestNodeChordId;}

    public int getSequenceNumber() {return sequenceNumber;}
}

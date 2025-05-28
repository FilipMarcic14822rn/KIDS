package servent.message;

import app.ChordImage;

import java.util.List;

public class BackupMessage extends BasicMessage{
    private List<ChordImage> images;
    public BackupMessage(int senderPort, int receiverPort, List<ChordImage> values) {
        super(MessageType.BACKUP, senderPort, receiverPort);
        this.images = values;
    }

    public List<ChordImage> getImages() {
        return images;
    }
}

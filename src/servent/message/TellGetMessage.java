package servent.message;

import app.ChordImage;

import java.util.List;

public class TellGetMessage extends BasicMessage {

	private static final long serialVersionUID = -6213394344524749872L;
	private static List<ChordImage> images;

	public TellGetMessage(int senderPort, int receiverPort, int key, List<ChordImage> value) {
		super(MessageType.TELL_GET, senderPort, receiverPort, key + ":" + value);
		images = value;
	}

	public List<ChordImage> getImages() {
		return images;
	}
}

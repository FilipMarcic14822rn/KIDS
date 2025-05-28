package servent.message;

import app.ChordImage;

import java.util.List;
import java.util.Map;

public class WelcomeMessage extends BasicMessage {

	private static final long serialVersionUID = -8981406250652693908L;

	private Map<Integer, List<ChordImage>> values;
	
	public WelcomeMessage(int senderPort, int receiverPort, Map<Integer, List<ChordImage>> values) {
		super(MessageType.WELCOME, senderPort, receiverPort);
		
		this.values = values;
	}
	
	public Map<Integer, List<ChordImage>> getValues() {
		return values;
	}
}

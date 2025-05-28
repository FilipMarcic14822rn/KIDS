package servent.handler;

import app.AppConfig;
import app.ChordImage;
import servent.message.Message;
import servent.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class PutHandler implements MessageHandler {

	private Message clientMessage;
	
	public PutHandler(Message clientMessage) {
		this.clientMessage = clientMessage;
	}
	
	@Override
	public void run() {
		if (clientMessage.getMessageType() == MessageType.PUT) {
			String[] splitText = clientMessage.getMessageText().split(":");
			if (splitText.length == 2) {
				int key = 0;
				ChordImage value = null;
				
				try {
					key = Integer.parseInt(splitText[0]);
					value = new ChordImage("");


					AppConfig.chordState.putValue(key, value);
				} catch (NumberFormatException e) {
					AppConfig.timestampedErrorPrint("Got put message with bad text: " + clientMessage.getMessageText());
				}
			} else {
				AppConfig.timestampedErrorPrint("Got put message with bad text: " + clientMessage.getMessageText());
			}
			
			
		} else {
			AppConfig.timestampedErrorPrint("Put handler got a message that is not PUT");
		}

	}

}

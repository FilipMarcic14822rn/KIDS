package servent.handler;

import app.AppConfig;
import app.ChordImage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.TellGetMessage;

import java.util.List;

public class TellGetHandler implements MessageHandler {

	private Message clientMessage;
	
	public TellGetHandler(Message clientMessage) {
		this.clientMessage = clientMessage;
	}
	
	@Override
	public void run() {
		if (clientMessage.getMessageType() == MessageType.TELL_GET) {
			List<ChordImage> images = ((TellGetMessage)clientMessage).getImages();
			
			if (images == null)
				AppConfig.timestampedStandardPrint("User " + clientMessage.getSenderPort() + " has no images currently");
			else if(images.isEmpty())
				AppConfig.timestampedStandardPrint("User "+clientMessage.getSenderPort()+" images are private");
			else
				for (ChordImage img : images)
					System.out.println(img.toString());
		} else {
			AppConfig.timestampedErrorPrint("Tell get handler got a message that is not TELL_GET");
		}
	}

}

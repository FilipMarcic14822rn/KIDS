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
				AppConfig.timestampedErrorPrint("Got TELL_GET message with null value");
			else if(images.isEmpty())
				AppConfig.timestampedStandardPrint("Images are private");
			else
				for (ChordImage img : images)
					System.out.println(img.toString());
		} else {
			AppConfig.timestampedErrorPrint("Tell get handler got a message that is not TELL_GET");
		}
	}

}

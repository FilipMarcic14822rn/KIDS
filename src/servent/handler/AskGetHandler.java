package servent.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.AppConfig;
import app.ChordImage;
import app.ChordState;
import app.ServentInfo;
import servent.message.AskGetMessage;
import servent.message.Message;
import servent.message.MessageType;
import servent.message.TellGetMessage;
import servent.message.util.MessageUtil;

import static app.ChordState.chordHash;

public class AskGetHandler implements MessageHandler {

	private Message clientMessage;
	
	public AskGetHandler(Message clientMessage) {
		this.clientMessage = clientMessage;
	}
	
	@Override
	public void run() {
		if (clientMessage.getMessageType() == MessageType.ASK_GET) {
			try {
				int key = Integer.parseInt(clientMessage.getMessageText());
				if (AppConfig.chordState.isKeyMine(key)) {
					//Visibility check for non followers
					if (AppConfig.myServentInfo.getVisibility().equals("public")
					|| (AppConfig.myServentInfo.getVisibility().equals("private") &&
						AppConfig.chordState.getFollowerMap().get(AppConfig.myServentInfo.getChordId()).contains(
								chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort())))
					){
						Map<Integer, List<ChordImage>> valueMap = AppConfig.chordState.getValueMap();
						List<ChordImage> value = null;

						if (valueMap.containsKey(key)) {
							value = valueMap.get(key);
						}

						AppConfig.timestampedStandardPrint("Sending files to: " + clientMessage.getSenderPort());
						TellGetMessage tgm = new TellGetMessage(AppConfig.myServentInfo.getListenerPort(), clientMessage.getSenderPort(),
								key, value);
						MessageUtil.sendMessage(tgm);
					}
					else{
						AppConfig.timestampedStandardPrint("Not a follower " + clientMessage.getSenderPort());
						MessageUtil.sendMessage(new TellGetMessage(AppConfig.myServentInfo.getListenerPort(), clientMessage.getSenderPort(), key, new ArrayList<>()));
					}
				} else {
					ServentInfo nextNode = AppConfig.chordState.getNextNodeForKey(key);
					if (AppConfig.chordState.isKeyMine(nextNode.getChordId()))
						return;
					AppConfig.timestampedStandardPrint("AskGet forwarding from " + clientMessage.getSenderPort() + " to " + nextNode.getListenerPort());
					AskGetMessage agm = new AskGetMessage(clientMessage.getSenderPort(), nextNode.getListenerPort()==AppConfig.myServentInfo.getListenerPort()?AppConfig.chordState.getNextNodePort() : nextNode.getListenerPort(),
							clientMessage.getMessageText());
					MessageUtil.sendMessage(agm);
				}
			} catch (NumberFormatException e) {
				AppConfig.timestampedErrorPrint("Got ask get with bad text: " + clientMessage.getMessageText());
			}
			
		} else {
			AppConfig.timestampedErrorPrint("Ask get handler got a message that is not ASK_GET");
		}

	}

}
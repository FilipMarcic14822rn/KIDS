package servent.handler;

import app.AppConfig;
import app.ServentInfo;
import servent.message.FollowRequestMessage;
import servent.message.Message;
import servent.message.util.MessageUtil;

import java.util.ArrayList;

import static app.ChordState.chordHash;

public class FollowRequestHandler implements MessageHandler {
    private Message clientMessage;
    public FollowRequestHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        try {
            AppConfig.chordState.sendFollowRequest(((FollowRequestMessage)clientMessage).getDestination(), ((FollowRequestMessage)clientMessage).getOrigin());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

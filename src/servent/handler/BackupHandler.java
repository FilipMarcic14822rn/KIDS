package servent.handler;

import app.AppConfig;
import servent.message.BackupMessage;
import servent.message.Message;
import servent.message.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

import static app.ChordState.chordHash;

public class BackupHandler implements MessageHandler {
    private final Message clientMessage;

    public BackupHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }
    @Override
    public void run() {
        //??
        if (clientMessage.getMessageType() == MessageType.BACKUP){
            if (AppConfig.chordState.getBackupMap().containsKey(AppConfig.myServentInfo.getChordId()))
                if (AppConfig.chordState.getBackupMap().get(AppConfig.myServentInfo.getChordId()).containsKey(
                        chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort()))) {
                    AppConfig.chordState.getBackupMap().get(AppConfig.myServentInfo.getChordId()).get(
                                    chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort())).clear();

                    AppConfig.chordState.getBackupMap().get(AppConfig.myServentInfo.getChordId()).get(
                                    chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort())).
                            addAll(((BackupMessage) clientMessage).getImages());
                } else {
                    AppConfig.chordState.getBackupMap().get(AppConfig.myServentInfo.getChordId()).
                            put(chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort()),
                                    new ArrayList<>(((BackupMessage) clientMessage).getImages()));
                }
            else{
                AppConfig.chordState.getBackupMap().put(AppConfig.myServentInfo.getChordId(), new HashMap<>());
                AppConfig.chordState.getBackupMap().get(AppConfig.myServentInfo.getChordId()).
                        put(chordHash(clientMessage.getSenderIpAddress() + ":" + clientMessage.getSenderPort()),
                                new ArrayList<>(((BackupMessage) clientMessage).getImages()));
            }
        AppConfig.timestampedStandardPrint("Added backup " + ((BackupMessage) clientMessage).getImages() + " for " + clientMessage.getSenderPort() + " on " + AppConfig.myServentInfo.getListenerPort());
        }else
            AppConfig.timestampedStandardPrint("Backup handler got a message that is not BACKUP");
    }
}

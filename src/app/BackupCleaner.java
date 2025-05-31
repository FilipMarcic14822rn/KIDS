package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BackupCleaner implements Runnable{
    private volatile boolean working = true;
    @Override
    public void run() {
        while(working){
            ServentInfo me = AppConfig.myServentInfo;
            ServentInfo successor = AppConfig.chordState.getSuccessorTable()[0];
            ServentInfo predecessor = AppConfig.chordState.getPredecessor();
            List<ChordImage> successorList = new ArrayList<>();
            List<ChordImage> predecessorList = new ArrayList<>();

            try {
                if (successor != null)
                    successorList.addAll((AppConfig.chordState.getBackupMap().get(me.getChordId()).get(successor.getChordId())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                if (predecessor != null)
                    predecessorList.addAll((AppConfig.chordState.getBackupMap().get(me.getChordId()).get(predecessor.getChordId())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            AppConfig.chordState.getBackupMap().remove(me.getChordId());
            AppConfig.chordState.getBackupMap().put(me.getChordId(), new HashMap<>());

            if (successor != null)
                AppConfig.chordState.getBackupMap().get(me.getChordId()).put(successor.getChordId(), successorList);
            if (predecessor != null)
                AppConfig.chordState.getBackupMap().get(me.getChordId()).put(predecessor.getChordId(), predecessorList);

            AppConfig.timestampedStandardPrint("Backups cleaned");
            try {Thread.sleep(600000);}
            catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }

    public void stop() {
        this.working = false;
    }
}

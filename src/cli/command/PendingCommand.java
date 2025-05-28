package cli.command;

import app.AppConfig;
import app.ServentInfo;

import java.util.List;

public class PendingCommand implements CLICommand{

    @Override
    public String commandName() {return "pending";}

    @Override
    public void execute(String args) {
        try {
            List<String> requests = AppConfig.chordState.getPendingRequests();
            if (requests != null)
                AppConfig.timestampedStandardPrint("Pending follow requests:\n" + requests);
            else
                AppConfig.timestampedStandardPrint("No pending requests for: " + args);
        }catch (Exception e){
            //TODO exception i funkcija
            AppConfig.timestampedErrorPrint("Pending command exception: " + e.getMessage() + " for args: " + args);
        }
    }
}

package cli.command;

import app.AppConfig;

public class AcceptCommand implements CLICommand{

    @Override
    public String commandName() {return "accept";}

    @Override
    public void execute(String args) {
        try {
            if (AppConfig.chordState.acceptFollowRequest(args))
                AppConfig.timestampedStandardPrint("Follow request from " + args + " accepted.");
            else
                AppConfig.timestampedStandardPrint("Follow request from user " + args + " not found");
        }catch (Exception e){
            //TODO exception
            AppConfig.timestampedErrorPrint("Invalid ACCEPT: " + args + ". Should be key, which is an int.");
        }
    }
}

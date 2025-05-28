package cli.command;

import app.AppConfig;

public class RemoveFileCommand implements CLICommand{

    @Override
    public String commandName() {return "remove_file";}

    @Override
    public void execute(String args) {
        try {
            if (AppConfig.chordState.removeFile(args))
                AppConfig.timestampedStandardPrint("File " + args + " was removed.");
            else
                AppConfig.timestampedStandardPrint("File " + args + " not found.");
        }catch (Exception e){
            //TODO exception i funkcija
            AppConfig.timestampedErrorPrint("Invalid REMOVE FILE: " + args + ". Should be key, which is an int.");
        }
    }
}
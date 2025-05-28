package cli.command;

import app.AppConfig;

public class VisibilityCommand implements CLICommand{

    @Override
    public String commandName() {return "visibility";}

    @Override
    public void execute(String args) {
        try {
            if (AppConfig.chordState.toggleVisibility(args))
                AppConfig.timestampedStandardPrint("Your files are now " + args);
            else
                AppConfig.timestampedStandardPrint("Files are already " + args);
        }catch (Exception e){
            //TODO sredi
            AppConfig.timestampedErrorPrint("Invalid argument for view_files: " + args + ". Should be key, which is an int.");
        }

    }
}

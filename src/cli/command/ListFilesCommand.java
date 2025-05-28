package cli.command;

import app.AppConfig;
import app.ChordImage;

import java.util.List;

public class ListFilesCommand implements CLICommand {
	@Override
	public String commandName() {
		return "list_files";
	}

	@Override
	public void execute(String args) {
		try {
			List<ChordImage> val=AppConfig.chordState.getValue(args);
			if (val != null)
				AppConfig.timestampedStandardPrint("view_files execute  "+args + ": " + val);
			else
				AppConfig.timestampedStandardPrint("waiting...");
//		} catch (NumberFormatException e) {
//			AppConfig.timestampedErrorPrint("Invalid argument for view_files: " + args + ". Should be key, which is an int.");
		} catch (Exception e) {
			AppConfig.timestampedErrorPrint("List files command exception: " + e.getMessage() + " for args: " + args);
		}
	}

}

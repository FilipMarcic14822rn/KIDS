package cli.command;

import app.AppConfig;
import app.ChordImage;
import app.ChordState;

public class UploadCommand implements CLICommand {

	@Override
	public String commandName() {
		return "upload";
	}

	@Override
	public void execute(String args) {
		if (AppConfig.chordState.uploadFile(new ChordImage(args))) {
			AppConfig.timestampedStandardPrint("Successfully uploaded " + args);
		} else {
			AppConfig.timestampedErrorPrint("File " + args + " not found");
		}

	}

}

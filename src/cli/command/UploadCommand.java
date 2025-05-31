package cli.command;

import app.AppConfig;
import app.ChordImage;
import app.ChordState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UploadCommand implements CLICommand {

	@Override
	public String commandName() {
		return "upload";
	}

	@Override
	public void execute(String args){
		try {
			if (AppConfig.chordState.uploadFile(new ChordImage(ImageIO.read(new File(AppConfig.ROOT_DIR + args)), args,
					args.split("\\.")[1]))) {
				AppConfig.timestampedStandardPrint("Successfully uploaded " + args);
			} else {
				AppConfig.timestampedErrorPrint("File " + args + " not found");
			}
		}catch (IOException e){
			AppConfig.timestampedErrorPrint("Error while uploading " + e);
		}

	}

}

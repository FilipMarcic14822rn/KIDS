package cli.command.old;

import app.AppConfig;
import cli.command.CLICommand;

public class PauseCommand implements CLICommand {

	@Override
	public String commandName() {
		return "pause";
	}

	@Override
	public void execute(String args) {
		int timeToSleep = -1;
		
		try {
			timeToSleep = Integer.parseInt(args);
			
			if (timeToSleep < 0) {
				throw new NumberFormatException();
			}

			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (NumberFormatException e) {
			AppConfig.timestampedErrorPrint("Pause command should have one int argument, which is time in ms.");
		}
	}

}

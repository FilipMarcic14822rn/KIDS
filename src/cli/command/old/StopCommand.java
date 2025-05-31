package cli.command.old;

import app.ActivityMonitor;
import app.AppConfig;
import app.BackupCleaner;
import cli.CLIParser;
import cli.command.CLICommand;
import servent.SimpleServentListener;

public class StopCommand implements CLICommand {

	private CLIParser parser;
	private SimpleServentListener listener;
	private ActivityMonitor monitor;
	private BackupCleaner cleaner;
	
	public StopCommand(CLIParser parser, SimpleServentListener listener, ActivityMonitor monitor, BackupCleaner cleaner) {
		this.parser = parser;
		this.listener = listener;
		this.monitor = monitor;
		this.cleaner = cleaner;
	}
	
	@Override
	public String commandName() {
		return "stop";
	}

	@Override
	public void execute(String args) {
		AppConfig.timestampedStandardPrint("Stopping...");
		parser.stop();
		listener.stop();
		cleaner.stop();
		monitor.stop();
	}

}

package app;

import java.io.Serializable;

/**
 * This is an immutable class that holds all the information for a servent.
 *
 * @author bmilojkovic
 */
public class ServentInfo implements Serializable {

	private static final long serialVersionUID = 5304170042791281555L;
	private final String ipAddress;
	private final int listenerPort;
	private final int chordId;
	private String visibility;
	private boolean successorActivityFlag;
	private final int softLimit;
	private final int hardLimit;

	public int getSoftLimit() {
		return softLimit;
	}

	public int getHardLimit() {
		return hardLimit;
	}

	public ServentInfo(String ipAddress, int listenerPort, int softLimit, int hardLimit) {
		this.ipAddress = ipAddress;
		this.listenerPort = listenerPort;
		this.chordId = ChordState.chordHash(ipAddress + ":" + listenerPort);
		this.visibility = "public";
		this.softLimit = softLimit;
		this.hardLimit = hardLimit;
	}

	public String getIpAndPort(){return ipAddress + ":" + listenerPort;}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getListenerPort() {
		return listenerPort;
	}

	public int getChordId() {
		return chordId;
	}

	public boolean isSuccessorActivityFlag() {
		return successorActivityFlag;
	}

	public void setSuccessorActivityFlag(boolean activityFlag) {
		this.successorActivityFlag = activityFlag;
	}

	@Override
	public String toString() {
		return "[" + chordId + "|" + ipAddress + "|" + listenerPort + "]";
	}

	public boolean setVisibility(String args) {
		if (this.visibility.equals(args)) return false;
		this.visibility = args;
		return true;
	}

	public String getVisibility() {
		return visibility;
	}
}

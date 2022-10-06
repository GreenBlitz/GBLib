package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.debug;

import edu.wpi.first.networktables.NetworkTable;

import java.util.HashMap;
import java.util.Map;

public class RemoteCSVTarget {

	public static final NetworkTable CSV_LOGGER = DebugTables.DEBUG.getSubTable("csvlogger");
	private static final NetworkTable NAMES = CSV_LOGGER.getSubTable("names");
	private static final NetworkTable VALUES = CSV_LOGGER.getSubTable("values");
	private static final Map<String, RemoteCSVTarget> targets = new HashMap<>();
	protected final String[] ntNames;
	private final String remoteFileName;

	private RemoteCSVTarget(String remoteFileName, String[] ntNames) {
		this.remoteFileName = remoteFileName;
		this.ntNames = ntNames;

		NAMES.getEntry(remoteFileName).setStringArray(ntNames);
	}

	public static RemoteCSVTarget initTarget(String fileName, String... names) {
		if (!targets.containsKey(fileName)) {
			targets.put(fileName, new RemoteCSVTarget(fileName, names));
		}
		return targets.get(fileName);
	}

	public static RemoteCSVTarget getTarget(String fileName) {
		return targets.getOrDefault(fileName, null);
	}

	public void report(double... record) {
		if (record.length != ntNames.length) {
			System.err.println("Warning unexpected record length");
		}

		VALUES.getEntry(remoteFileName).setDoubleArray(record);
	}
}

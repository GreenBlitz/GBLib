package edu.greenblitz.gblib.motion.debug.logger;

import edu.greenblitz.gblib.motion.debug.DebugTables;
import edu.wpi.first.networktables.NetworkTable;

import java.util.HashMap;
import java.util.Map;

public class LoggerManager {

	public static final NetworkTable TXT_LOGGER = DebugTables.DEBUG.getSubTable("textlogger");
	private static final Map<String, LogObject> targets = new HashMap<>();

	public static LogObject getLog(String name) {
		if (!targets.containsKey(name)) {
			targets.put(name, new LogObject(name));
		}
		return targets.get(name);
	}


}

package edu.greenblitz.gblib.motion.debug;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DebugTables {
	public static final NetworkTable DEBUG = NetworkTableInstance.getDefault().getTable("debug");
}

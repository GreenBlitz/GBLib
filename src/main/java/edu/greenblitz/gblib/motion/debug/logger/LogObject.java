package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.debug.logger;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.NetworkTableEntry;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogObject {

	private final Object LOCK = new Object();
	private final String name;
	private final Queue<String> toSend;
	private boolean enabled;
	private int logNumber = 1;

	public LogObject(String name) {
		this.name = name;
		this.toSend = new ConcurrentLinkedQueue<>();
		enabled = true;
		NetworkTableEntry relevantEntry = LoggerManager.TXT_LOGGER.getInstance().getEntry(name);
		relevantEntry.addListener(this::updateValues, EntryListenerFlags.kUpdate);
	}

	public void disable() {
		synchronized (LOCK) {
			enabled = false;
		}
	}

	public void enable() {
		synchronized (LOCK) {
			enabled = true;
		}
	}

	public void addLog(String message) {
		synchronized (LOCK) {
			if (!enabled) return;
		}

		toSend.add(logNumber++ + " - " + new Date() + " - " + message);
	}

	private void updateValues(EntryNotification value) {
		String str = value.getEntry().getString("");
		if (str.equals("") && toSend.size() > 0) {
			value.getEntry().setString(toSend.remove());
		}
	}
}

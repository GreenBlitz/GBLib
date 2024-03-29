package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.debug;

import java.util.ArrayList;

public class RemoteCSVTargetBuffer {
	private final long delay;
	RemoteCSVTarget target;
	ArrayList<double[]> buffer;

	public RemoteCSVTargetBuffer(String fileName, String... names) {
		this(fileName, 50, names);
	}

	public RemoteCSVTargetBuffer(String fileName, long delay, String... names) {
		target = RemoteCSVTarget.initTarget(fileName, names);
		buffer = new ArrayList<double[]>();
		this.delay = delay;
	}

	public void report(double... record) {

		buffer.add(record);
	}

	public void passToCSV(boolean resolveDupes) {
		if (resolveDupes) resolveDupes();
		target.report(buffer.get(0).clone());
		for (int i = 0; i < buffer.size(); i++) {
			target.report(buffer.get(i));
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void resolveDupes() {
		int time = 0;
		for (int i = 0; i < buffer.get(0).length; i++) {
			if (target.ntNames[i].equals("time")) time = i;
		}

		for (int i = 0; i < buffer.size() - 2; i++) {
			for (int j = 0; j < buffer.get(i).length; j++) {
				if (buffer.get(i)[j] == buffer.get(i + 1)[j]) {
					int l = i + 2;
					while (l < buffer.size() - 1 && buffer.get(l)[j] == buffer.get(i)[j]) l++;
					double dV = buffer.get(l)[j] - buffer.get(i)[j];
					double dT = buffer.get(l)[time] - buffer.get(i)[time];
					for (int k = i + 1; k < l; k++) {
						double dt = buffer.get(k)[time] - buffer.get(i)[time];
						buffer.get(k)[j] = dV / dT * dt + buffer.get(i)[j];
					}
				}
			}
		}
	}
}

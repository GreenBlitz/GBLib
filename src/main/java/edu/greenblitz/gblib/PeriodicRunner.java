package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib;

import edu.wpi.first.wpilibj.Notifier;

public abstract class PeriodicRunner {
	private final Notifier periodicRunner;

	private final long period;
	private volatile boolean active = false;

	public PeriodicRunner(long period) {
		this.period = period;
		periodicRunner = new Notifier(this::_periodic);
		periodicRunner.startPeriodic(this.period / 1000.0);
	}

	public PeriodicRunner() {
		this(20);
	}

	public void start() {
		active = true;
	}

	public void stop() {
		active = false;
	}

	public void end() {
		periodicRunner.stop();
	}

	public boolean isActive() {
		return active;
	}

	public abstract boolean isFinished();

	protected abstract void whenActive();

	protected void whenInActive() {

	}

	private void _periodic() {
		if (isActive()) whenActive();
		else whenInActive();
	}

}

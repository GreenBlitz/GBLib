package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.debug.RemoteCSVTargetBuffer;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.tolerance.ITolerance;
import edu.greenblitz.pegasus.utils.PIDObject;

public class PIDController  {

	protected PIDObject pidObject;
	protected long previousTime;
	protected double goal;
	protected double previousError;
	protected double integral;
	protected long startTime;

	protected double minimumOutput;
	protected double maximumOutput;
	protected double absoluteMinimumOut;

	protected boolean configured;

	protected ITolerance tolerance;

	private RemoteCSVTargetBuffer PIDTarget;
	private int dataDelay = 0;
	private String targetName = "PIDTarget";

	public PIDController(PIDObject pidObject, ITolerance tolerance) {
		this.pidObject = pidObject;
		this.tolerance = tolerance;
		configured = false;
	}

	public PIDController(PIDObject pidObject) {
		this(pidObject, null);
	}

	public PIDController(double kP, double kI, double kD, double kF) {
		this(new PIDObject(kP, kI, kD, kF));
	}

	public PIDController(double kP, double kI, double kD) {
		this(kP, kI, kD, 0);
	}

	public void configureOutputLimits(double min, double max) {
		minimumOutput = min;
		maximumOutput = max;
	}

	public void configure(double curr, double goal, double limitLower, double limitUpper, double absoluteMinimumOut) {
		setGoal(goal);
		previousError = goal - curr;
		resetIntegralZone(0);
		configureOutputLimits(limitLower, limitUpper);
		previousTime = System.currentTimeMillis();
		this.absoluteMinimumOut = absoluteMinimumOut;
		startTime = System.currentTimeMillis();
		if (dataDelay != 0) {
			PIDTarget = new RemoteCSVTargetBuffer(targetName, dataDelay, "time", "P", "I", "D", "kf", "PID");
		}
		configured = true;
	}

	public double getAbsoluteMinimumOut() {
		return absoluteMinimumOut;
	}

	public void setAbsoluteMinimumOut(double absoluteMinimumOut) {
		this.absoluteMinimumOut = absoluteMinimumOut;
	}

	public double getGoal() {
		return goal;
	}

	public void setGoal(double goal) {
		this.goal = goal;
	}

	public double getLowerOutputLimit() {
		return minimumOutput;
	}

	public double getUpperOutputLimit() {
		return maximumOutput;
	}

	public double calculatePID(double current) {
		if (!configured)
			throw new UninitializedPIDException("PID - " + this + " - not configured");

		if (isFinished(current))
			return 0;

		var err = (goal - current) * pidObject.getInverted();
		var dt = updateTime();

		var p = pidObject.getKp() * err;
		if (pidObject.getIZone() == 0 || err <= pidObject.getIZone()) {
			integral += err * dt;
		}
		var i = pidObject.getKi() * integral;

		var d = 0.0;
		if (Math.abs(dt) >= 1)
			d = pidObject.getKd() * (err - previousError) / dt;

		previousError = err;
		if (dataDelay != 0)
			PIDTarget.report((System.currentTimeMillis() - startTime) / 1000.0, p, i, d, pidObject.getKf(), p + i + d + pidObject.getKf());
		return clampFully(p + i + d + pidObject.getKf());
	}

	public double clampFully(double value) {
		double calc = clamp(value);
		if (Double.isNaN(absoluteMinimumOut)) {
			return calc;
		}
		return Math.copySign(Math.max(Math.abs(calc), absoluteMinimumOut), calc);
	}

	public PIDObject getPidObject() {
		return pidObject;
	}

	public void setPidObject(PIDObject newPidObj) {
		this.pidObject = newPidObj;
	}

	public double getLastError() {
		return previousError;
	}

	public void resetIntegralZone(double iZone) {
		integral = iZone;
	}

	public ITolerance getTolerance() {
		return tolerance;
	}

	public void setTolerance(ITolerance tol) {
		tolerance = tol;
	}

	public boolean isFinished(double current) {
		return hasTolerance() && tolerance.onTarget(getGoal(), current);
	}

	public boolean hasTolerance() {
		return tolerance != null;
	}

	protected double updateTime() {
		var current = System.currentTimeMillis();
		double ms = current - previousTime;
		previousTime = current;
		return ms;
	}

	protected double clamp(double value) {
		if (Double.isNaN(maximumOutput + minimumOutput)) {
			return value;
		}
		return Math.min(Math.max(value, minimumOutput), maximumOutput);
	}

	public void atEnd() {
		if (dataDelay != 0) {
			PIDTarget.passToCSV(true);
		}
	}

	public void setSendData(boolean sendData) {
		dataDelay = sendData ? 50 : 0;
	}

	public void setDataDelay(int dataDelay, String name) {
		this.dataDelay = dataDelay;
		targetName = name;
	}
}

package edu.greenblitz.gblib.motors.brushless;

public abstract class AbstractMotor implements GBMotor {
	private double ticksToRadians;
	private double ticksToWheelRPM;

	public double getTicksToRadians() {
		return this.ticksToRadians;
	}


	public void setTicksToRadians(double ticksToRadians) {
		this.ticksToRadians = ticksToRadians;
	}

	public double getTicksToWheelRPM() {
		return ticksToWheelRPM;
	}

	public void setTicksToWheelRPM(double ticksToWheelRPM) {
		this.ticksToWheelRPM = ticksToWheelRPM;
	}

	@Override
	public double getNormalizedPosition() {
		return (getRawTicks() / ticksToRadians);
	}


	@Override
	public double getNormalizedVelocity() {
		return (getRawVelocity() / ticksToWheelRPM);
	}

	public enum IdleMode {
		Coast,
		Brake
	}

	public enum PIDTarget {
		Speed,
		Position,
		Current
	}
}

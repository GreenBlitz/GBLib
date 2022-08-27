package edu.greenblitz.gblib.motors.brushless;

public abstract class AbstractMotor implements GBMotor {
	private double ticksToWheelPosition;
	private double ticksToWheelRPM;

	public double getTicksToWheelPosition() {
		return this.ticksToWheelPosition;
	}

	public double getTicksToWheelRPM() {
		return ticksToWheelRPM;
	}

	public void setTicksToWheelPosition(double ticksToWheelPosition) {
		this.ticksToWheelPosition = ticksToWheelPosition;
	}

	public void setTicksToWheelRPM(double ticksToWheelRPM) {
		this.ticksToWheelRPM = ticksToWheelRPM;
	}

	@Override
	public double getNormalizedPosition() {
		return (getRawTicks() / ticksToWheelPosition);
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

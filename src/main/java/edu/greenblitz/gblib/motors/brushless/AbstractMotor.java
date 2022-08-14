package edu.greenblitz.gblib.motors.brushless;

public abstract class AbstractMotor implements GBMotor {
	private double ticksToRotations;
	
	public double getTicksToRotations() {
		return this.ticksToRotations;
	}
	
	@Override
	public void setTicksToRotations(double ticksToRotations) {
		this.ticksToRotations = ticksToRotations;
	}
	
	@Override
	public double getNormalizedPosition() {
		return (getRawTicks() / ticksToRotations);
	}
	
	
	@Override
	public double getNormalizedVelocity() {
		return (getRawVelocity() / ticksToRotations) * 60;
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

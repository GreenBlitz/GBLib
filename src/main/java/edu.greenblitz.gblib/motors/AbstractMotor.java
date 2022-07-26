package edu.greenblitz.gblib.motors;

public abstract class AbstractMotor implements GBMotor {
	private double gearRatio;
	
	public double getGearRatio() {
		return this.gearRatio;
	}
	
	@Override
	public void setGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
	}
	
	@Override
	public double getNormalizedPosition() {
		return (getRawTicks() / gearRatio);
	}
	
	
	@Override
	public double getNormalizedVelocity() {
		return (getRawVelocity() / gearRatio) * 60;
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

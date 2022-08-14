package edu.greenblitz.gblib.motion.tolerance;

public class AbsoluteTolerance implements ITolerance {
	private final double absoluteError;
	
	public AbsoluteTolerance(double absoluteError) {
		this.absoluteError = absoluteError;
	}
	
	@Override
	public boolean onTarget(double goal, double current) {
		return Math.abs(goal - current) < absoluteError;
	}
}
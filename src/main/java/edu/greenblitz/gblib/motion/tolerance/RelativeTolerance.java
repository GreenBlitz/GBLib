package edu.greenblitz.gblib.motion.tolerance;

public class RelativeTolerance implements ITolerance {
	private final double percentTolerance;
	
	public RelativeTolerance(double percentTolerance) {
		if (percentTolerance <= 0)
			throw new IllegalArgumentException("tolerance must be greater than 0");
		
		this.percentTolerance = percentTolerance;
	}
	
	@Override
	public boolean onTarget(double goal, double current) {
		return Math.abs(goal - current) < Math.abs(percentTolerance * goal);
	}
}

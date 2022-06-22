package edu.greenblitz.gblib.motion.tolerance;

public class AbsoluteTolerance implements ITolerance {
	private final double m_absoluteError;
	
	public AbsoluteTolerance(double absoluteError) {
		this.m_absoluteError = absoluteError;
	}
	
	@Override
	public boolean onTarget(double goal, double current) {
		return Math.abs(goal - current) < m_absoluteError;
	}
}
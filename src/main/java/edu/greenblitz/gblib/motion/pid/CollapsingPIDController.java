package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.tolerance.ITolerance;


public class CollapsingPIDController extends PIDController {

	protected double ICollapseThreshold = 0;

	public CollapsingPIDController(PIDObject obj, ITolerance tolerance) {
		super(obj, tolerance);
	}

	public CollapsingPIDController(PIDObject obj, double thresh) {
		super(obj);
		ICollapseThreshold = thresh;
	}

	public CollapsingPIDController(double kP, double kI, double kD, double kF) {
		super(kP, kI, kD, kF);
	}

	public CollapsingPIDController(double kP, double kI, double kD) {
		super(kP, kI, kD);
	}

	@Override
	public double calculatePID(double current) {
		if (Math.abs(goal - current) < ICollapseThreshold) {
			integral = 0;
		}

		return super.calculatePID(current);
	}

}

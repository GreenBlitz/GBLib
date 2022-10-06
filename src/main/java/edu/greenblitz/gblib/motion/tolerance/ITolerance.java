package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.tolerance;

@FunctionalInterface
public interface ITolerance {
	boolean onTarget(double goal, double current);
}

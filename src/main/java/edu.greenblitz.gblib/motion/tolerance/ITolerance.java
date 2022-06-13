package edu.greenblitz.gblib.motion.tolerance;

@FunctionalInterface
public interface ITolerance {
    boolean onTarget(double goal, double current);
}

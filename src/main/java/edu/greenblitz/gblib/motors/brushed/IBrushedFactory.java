package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed;

public interface IBrushedFactory {

	/** this is the factory for a brushed motors */
	GBBrushedMotor generate(int id);
}

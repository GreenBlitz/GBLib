package edu.greenblitz.gblib.motors.brushed;

public interface IBrushedFactory {

	/** this is the factory for a brushed motors */
	GBBrushedMotor generate(int id);
}

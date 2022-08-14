package edu.greenblitz.gblib.motors.brushed;

import edu.greenblitz.gblib.motors.brushless.GBMotor;

public interface IBrushedFactory {

	GBBrushedMotor generate(int id);
}

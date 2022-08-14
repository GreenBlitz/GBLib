package edu.greenblitz.gblib.motors.brushless;

import edu.greenblitz.gblib.motors.brushless.GBMotor;

public interface IMotorFactory {
	
	GBMotor generate(int id);
}

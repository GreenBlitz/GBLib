package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;

public interface GBBrushedMotor {

	void setPower(double power);

	/**
	 * @return true if the motors are inverted;
	 */
	boolean getInverted();

	void setInverted(boolean inverted);

	AbstractMotor.IdleMode getIdleMode();

	void setIdleMode(AbstractMotor.IdleMode idleMode);


}

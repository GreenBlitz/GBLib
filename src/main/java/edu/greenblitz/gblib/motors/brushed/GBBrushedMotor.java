package edu.greenblitz.gblib.motors.brushed;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;

public interface GBBrushedMotor {

	/**
	 * sets motor power from -1 to 1;
	 */
	void setPower(double power);

	/**
	 * @return true if the motors are inverted;
	 */
	boolean getInverted();

	void setInverted(boolean inverted);

	AbstractMotor.IdleMode getIdleMode();

	void setIdleMode(AbstractMotor.IdleMode idleMode);


}

package edu.greenblitz.gblib.motors;

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

	void setIdleMode(AbstractMotor.IdleMode idleMode);

	AbstractMotor.IdleMode getIdleMode();


}

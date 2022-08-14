/**
 * @author Ido
 * @author Tal
 */

package edu.greenblitz.gblib.motors;

import edu.greenblitz.gblib.motion.pid.PIDObject;

public interface GBMotor {

//	public Motor getInstance();
//
	
	/**
	 * sets motor power from -1 to 1;
	 */
	void setPower(double power);
	
	
	/**
	 * @return true if the motors are inverted;
	 */
	boolean getInverted();
	
	void setInverted(boolean inverted);
	
	/**
	 * @return raw encoder ticks;
	 */
	double getRawTicks();
	
	/**
	 * normalizes encoder ticks to rotations using the gear ratio;
	 *
	 * @return position in parts of a rotation as in 0.5 is a half rotation;
	 */
	
	double getNormalizedPosition();
	
	/**
	 * @return raw velocity in ticks per second
	 */
	double getRawVelocity();
	
	/**
	 * normalizes encoder ticks to rotations using the gear ratio
	 *
	 * @return velocity in RPM;
	 */
	double getNormalizedVelocity();
	
	void configurePID(PIDObject pidObject);
	
	void setTargetByPID(double target, AbstractMotor.PIDTarget targetType);
	
	void setTargetSpeedByPID(double target);
	
	void resetEncoder();
	
	void setIdleMode(AbstractMotor.IdleMode idleMode);
	
	AbstractMotor.IdleMode getIdleMode();
	
	void setTicksToRotations(double ticksToRotations);
}

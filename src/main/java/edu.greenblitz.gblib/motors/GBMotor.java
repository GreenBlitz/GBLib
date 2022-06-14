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
	 * if true, makes motors rotate in opposite direction;
	 */
	@Deprecated // should be done with factory builder
	void setInverted(boolean inverted);
	
	/**
	 * @return true if the motors are inverted;
	 */
	boolean getInverted();
	
	
	/**
	 * @return raw encoder ticks;
	 */
	double getRawTicks();
	
	/**
	 * normalizes encoder ticks to rotations using the gear ratio;
	 *
	 * @return position in meters;
	 */
	
	/**
	 * sets new gear ratio (gears per rotations)
	 */
	@Deprecated
	void setGearRatio(double gearRatio);
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
	
	@Deprecated
	void setCurrentLimit(int limit);
	
	void resetEncoder();
	
	void setIdleMode(AbstractMotor.IdleMode idleMode);
}

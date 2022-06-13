/**
 * @author Ido
 * @author Tal
 */

package edu.greenblitz.gblib.motors;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.greenblitz.gblib.motion.pid.PIDObject;

public interface Motor {

//	public Motor getInstance();
//
	/**
	 * sets motor power from -1 to 1;
	 */
	void setPower(double power);
	
	
	/**
	 * if true, makes motors rotate in opposite direction;
	 */
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
	
	void setCurrentLimit(int limit);
	
	void resetEncoder();
	
	void setIdleMode(AbstractMotor.IdleMode idleMode);
}

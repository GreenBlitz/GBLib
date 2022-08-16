/**
 * @author Ido
 * @author Tal
 */

package edu.greenblitz.gblib.motors.brushless;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;

public interface GBMotor extends GBBrushedMotor {
	/**
	 *
	 * */
	
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
	
	void setTicksToRotations(double ticksToRotations);
}
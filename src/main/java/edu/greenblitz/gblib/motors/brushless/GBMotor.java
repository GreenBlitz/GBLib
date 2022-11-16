/**
 * @author Ido
 * @author Tal
 */

package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless;

import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;

public interface GBMotor extends GBBrushedMotor {

	/**
	 * @return raw encoder ticks;
	 */
	double getRawTicks();

	/**
	 * normalizes encoder ticks to radians using the gear ratio;
	 *
	 * @return position in radians;
	 */

	double getNormalizedPosition();

	/**
	 * @return raw velocity in ticks per second
	 */
	double getRawVelocity();

	/**
	 * gets normalized velocity, ticks per second/gear ratio
	 *
	 * @return velocity in RPM;
	 */
	double getNormalizedVelocity();

	void configurePID(PIDObject pidObject);

	void setTargetByPID(double target, AbstractMotor.PIDTarget targetType);

	void setTargetSpeedByPID(double target);

	/**
	 * @param voltageFF - feed forward, used in swerve
	 */
	void setTargetSpeedByPID(double target, double voltageFF);

	/**
	 * @return setting the voltage
	 *
	 */

	void setVoltage(double voltage);

	void resetEncoder();

	void setEncoderAng(double angle);
}

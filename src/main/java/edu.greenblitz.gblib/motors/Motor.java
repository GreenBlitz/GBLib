/**
 * @author Ido
 * @author Tal
 */

package edu.greenblitz.gblib.motors;

public interface Motor {
	
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
	 * normalizes encoder ticks to rotations using the gear ratio
	 *
	 * @return velocity in RPM;
	 */
	double getNormalizedVelocity();
	
}

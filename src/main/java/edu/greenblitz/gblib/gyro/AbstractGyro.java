package edu.greenblitz.gblib.gyro;

public abstract class AbstractGyro {
	protected int inverted;

	private static double normalize(double angle) {
		angle %= 2 * Math.PI;
		if (angle > Math.PI)
			angle -= 2 * Math.PI;
		if (angle <= -Math.PI)
			angle += 2 * Math.PI;
		return angle;
	}

	/**
	 * @return total accumulated yaw in radians, left is positive
	 */
	protected abstract double getRawYaw();

	/**
	 * @return yaw normalized between -Pi and Pi in radians, left is positive
	 */
	double getNormalizedYaw() {
		return normalize(getRawYaw());
	}

	/**
	 * Change the direction of growth of the angle
	 */
	public void inverse() {
		inverted = inverted * -1;
	}

	/**
	 * @return The change in yaw in radians per second
	 */
	protected abstract double getYawRate();


	protected abstract void reset();
}

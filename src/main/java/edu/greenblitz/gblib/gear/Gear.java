package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.gear;

/**
 * This class represents the gear box on the robot's chassis
 *
 * @author Nitzan
 * @author Raz
 * @author Amir
 * @author Tal
 */

public class Gear {
	private static Gear instance;
	private boolean isPower;


	/**
	 * Constructor method
	 *
	 * @param isPower - the initial state of the robot gears (SPEED or POWER)
	 */
	private Gear(boolean isPower) {
		this.isPower = isPower;
	}

	public static Gear getInstance() {
		if (instance == null) {
			instance = new Gear(false);
		}
		return instance;
	}

	public boolean getState() {
		return this.isPower;
	}

	/**
	 * @param desiredState - the gear state we wish to change into
	 */
	public void setState(boolean desiredState) {
		this.isPower = desiredState;
	}

	/**
	 * Switches between the two gear states regardless of current state
	 */
	public void toggle() {
		this.isPower = !this.isPower;
	}
}

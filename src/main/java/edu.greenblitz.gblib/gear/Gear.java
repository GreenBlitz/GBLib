package edu.greenblitz.gblib.gear;

/**
 * This class represents the gear box on the robot's chassis
 *
 * @author Nitzan
 * @author Raz
 * @author Amir
 * @author Tal
 */

public class Gear {
	private boolean isPower;
	
	
	private static Gear instance;
	
	
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
	 * Switches between the two gear states regardless of current state
	 */
	public void toggle() {
		this.isPower = !this.isPower;
	}
	
	/**
	 * @param desiredState - the gear state we wish to change into
	 */
	public void setState(boolean desiredState) {
		this.isPower = desiredState;
	}
}

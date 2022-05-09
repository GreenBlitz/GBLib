package edu.greenblitz.gblib.gear;

/**
 * This class represents the gear box on the robot's chassis
 *
 * @author Nitzan
 * @author Raz
 */

public class Gear {
	private GearState state;

	private static Gear instance;


	/**
	 * Constructor method
	 *
	 * @param initState - the initial state of the robot gears (SPEED or POWER)
	 */
	private Gear(GearState initState) {
		this.state = initState;
	}

	public static Gear getInstance() {
		if (instance == null) {
			instance = new Gear(GearState.SPEED);
		}
		return instance;
	}


	public static Gear getInstance(GearState initState) {
		if (instance == null) {
			instance = new Gear(initState);
		}
		return instance;
	}

	public GearState getState(){
		return this.state;
	}

	public GearState getInverseState(){
		if(this.state == GearState.POWER) return GearState.SPEED;
		else return GearState.POWER;
	}

	/**
	 * Switches between the two gear states regardless of current state
	 */
	public void toggle() {
		this.state = this.getInverseState();
	}

	/**
	 * @param desiredState - the gear state we wish to change into
	 */
	public void setState(GearState desiredState) {
		this.state = desiredState;
	}
}

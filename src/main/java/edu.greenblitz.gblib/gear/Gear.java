package edu.greenblitz.gblib.gear;

/**
 * This class represents the gear box on the robot's chassis
 *
 * @Author Nitzan
 * @Author Raz
 */

public class Gear {
	private GearState state;
	private double kPower, kSpeed;

	private static Gear instance;

	private static double DEF_VALUE = 0.7;

	/**
	 * Constructor method
	 *
	 * @param kPower    - the coefficient of engine usage when the gears are in power drive
	 * @param kSpeed    - the coefficient of engine usage when the gears are in speed drive
	 * @param initState - the initial state of the robot gears (SPEED or POWER)
	 */
	private Gear(double kPower, double kSpeed, GearState initState) {
		this.kPower = kPower;
		this.kSpeed = kSpeed;
		this.state = initState;
	}

	public static Gear getInstance() {
		if (instance == null) {
			instance = new Gear(DEF_VALUE, DEF_VALUE, GearState.SPEED);
		}
		return instance;
	}

	public static Gear getInstance(double kPower, double kSpeed) {
		if (instance == null) {
			instance = new Gear(kPower, kSpeed, GearState.SPEED);
		}
		return instance;
	}

	public static Gear getInstance(double kPower, double kSpeed, GearState initState) {
		if (instance == null) {
			instance = new Gear(kPower, kSpeed, initState);
		}
		return instance;
	}

	public GearState getState(){
		return this.state;
	}

	private GearState getInverseState(){
		if(this.state == GearState.POWER) return GearState.SPEED;
		else return GearState.POWER;
	}

	/**
	 * @return the coefficient of engine usage depending on current gear state
	 */
	public double getValue() {
		if (this.state.isSpeed()) return kSpeed;
		else return kPower;
	}

	/**
	 * @param state - the gear state for which you wish to change the coefficient of engine usage
	 * @param k     - the coefficient of engine usage
	 */
	public void setValue(GearState state, double k) {
		if (this.state.isPower()) this.kPower = k;
		else this.kSpeed = k;
	}

	/**
	 * Switches between the two gear states regardless of current state
	 */
	public void toggle() {
		this.state = this.getInverseState();
	}

	public double getInverseValue(){
		if (this.state.isSpeed()) return kPower;
		else return kSpeed;
	}

	/**
	 * @param desiredState - the gear state we wish to change into
	 */
	public void changeGear(GearState desiredState) {
		this.state = desiredState;
	}
}

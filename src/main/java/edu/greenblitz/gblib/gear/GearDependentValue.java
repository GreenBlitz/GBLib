package edu.greenblitz.gblib.gear;


public class GearDependentValue {

	protected double whenPower;
	protected double whenSpeed;

	public GearDependentValue(double whenPower, double whenSpeed) {
		this.whenPower = whenPower;
		this.whenSpeed = whenSpeed;
	}

	public double getValue(boolean isPower) {
		return isPower ? whenPower : whenSpeed;
	}

	public double getValue() {
		return getValue(Gear.getInstance().getState());
	}

	public void setValue(boolean isPower, double val) {
		if (isPower)
			this.whenPower = val;
		else
			this.whenSpeed = val;
	}

}

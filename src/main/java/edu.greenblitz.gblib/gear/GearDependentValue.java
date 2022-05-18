package edu.greenblitz.gblib.gear;


import java.util.function.DoubleSupplier;

public class GearDependentValue<T>{

	protected T whenPower;
	protected T whenSpeed;

	public GearDependentValue(T whenPower, T whenSpeed){
		this.whenPower = whenPower;
		this.whenSpeed = whenSpeed;
	}

	public T getValue(boolean isPower){
		return isPower ? whenPower : whenSpeed;
	}

	public T getValue(){
		return getValue(Gear.getInstance().getState());
	}

	public void setValue(boolean isPower, T val){
		if (isPower)
			this.whenPower = val;
		else
			this.whenSpeed = val;
	}

}

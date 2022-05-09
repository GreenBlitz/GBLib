package edu.greenblitz.gblib.gear;


import java.util.function.DoubleSupplier;

public class GearDependentValue<T>{

	protected T whenPower;
	protected T whenSpeed;

	public GearDependentValue(T whenPower, T whenSpeed){
		this.whenPower = whenPower;
		this.whenSpeed = whenSpeed;
	}

	public T getValue(GearState state){
		return state.isSpeed() ? whenSpeed : whenPower;
	}

	public T getValue(){
		return getValue(Gear.getInstance().getState());
	}

	public void setValue(GearState state, T val){
		if (state.isSpeed())
			this.whenSpeed = val;
		else
			this.whenPower = val;
	}

}

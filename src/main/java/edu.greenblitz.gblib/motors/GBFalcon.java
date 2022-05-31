package edu.greenblitz.gblib.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class GBFalcon implements Motor{
	private TalonFX motor;
	
	/**
	 *sets default gear ratio, can be changed later depending on use.
	 */
	private double gearRatio = 2048;
	
	
	@Override
	public void setPower(double power) {
		motor.set(ControlMode.PercentOutput, power);
	}
	
	@Override
	public void setInverted(boolean inverted) {
		if(inverted){motor.setInverted(TalonFXInvertType.CounterClockwise);}
		else{motor.setInverted(TalonFXInvertType.Clockwise);}
	}
	
	@Override
	public boolean getInverted() {
		return  (motor.getInverted()); /*getInverted is also a talon method*/
	}
	
	@Override
	public double getRawTicks() {
		return motor.getSelectedSensorPosition();
	}
	
	@Override
	public void setGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
	}
	
	@Override
	public double getNormalizedPosition() {
		return (getRawTicks()/gearRatio)*360;
	}
	
	@Override
	public double getNormalizedVelocity() {
		return (motor.getSelectedSensorVelocity()/gearRatio)*60;
	}
	
	public void configurePID(){
	
	}
}

package edu.greenblitz.gblib.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;

public class GBFalcon extends AbstractMotor {
	private TalonFX motor;
	/**
	 * Constructor:
	 * Defines motor as the Falcon with the given id;
	 * Sets gear ratio as given;
	 */
	public GBFalcon(int id, double gearRatio) {
		motor = new TalonFX(id);
		setGearRatio(gearRatio);
	}
	
	/**
	 * sets default gear ratio as 2048, if not given otherwise
	 */
	public GBFalcon(int id) {
		this(id, 2048);
	}
	
	
	@Override
	public void setPower(double power) {
		motor.set(ControlMode.PercentOutput, power);
	}
	
	public void setAngleByPID(double angle) {
		motor.set(ControlMode.Position, angle);
	}
	
	@Override
	public void setInverted(boolean inverted) {
		if (inverted) {
			motor.setInverted(TalonFXInvertType.CounterClockwise);
		} else {
			motor.setInverted(TalonFXInvertType.Clockwise);
		}
	}
	
	@Override
	public boolean getInverted() {
		return (motor.getInverted()); /*getInverted is also a talon method*/
	}
	
	@Override
	public double getRawTicks() {
		return motor.getSelectedSensorPosition();
	}
	
	@Override
	public double getRawVelocity() {
		return motor.getSelectedSensorVelocity();
	}
	
	@Override
	public void configurePID(double p) {
		motor.config_kP(0, p);
	}
	
	@Override
	public void configurePID(double p, double i) {
		motor.config_kP(0, p);
		motor.config_kI(0, i);
	}
	
	@Override
	public void configurePID(double p, double i, double d) {
		motor.config_kP(0, p);
		motor.config_kI(0, i);
		motor.config_kD(0, d);
	}
	
	@Override
	public void configurePID(double p, double i, double d, double ff) {
		motor.config_kP(0, p);
		motor.config_kI(0, i);
		motor.config_kD(0, d);
		motor.config_kF(0, ff);
	}
	
	@Override
	public void setCurrentLimit(int limit) {
		motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40,35, 100));
	}
	
	@Override
	public void resetEncoder() {
		motor.setSelectedSensorPosition(0);
	}
	
	@Override
	public void setIdleMode(IdleMode idleMode) {
		if (idleMode == IdleMode.Brake){motor.setNeutralMode(NeutralMode.Brake);}
		else {motor.setNeutralMode(NeutralMode.Coast);}
		
	}
	
	
}

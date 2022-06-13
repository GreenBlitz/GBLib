package edu.greenblitz.gblib.motors;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import edu.greenblitz.gblib.motion.pid.PIDObject;

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
	public void configurePID(PIDObject pidObject) {
		motor.config_kP(0,pidObject.getKp());
		motor.config_kI(0,pidObject.getKi());
		motor.config_kD(0,pidObject.getKd());
		motor.config_kF(0,pidObject.getKf());
		motor.config_IntegralZone(0,pidObject.getIZone());
	}
	
	@Override
	public void setTargetByPID(double target, PIDTarget targetType) {
		switch (targetType){
			case Speed:
				motor.set(TalonFXControlMode.Velocity, target);
				break;
			case Current:
				motor.set(TalonFXControlMode.Current, target);
				break;
			case Position:
				motor.set(TalonFXControlMode.Position, target);
				break;
		}
		
	}
	
	@Override
	public void setTargetSpeedByPID(double target) {
		motor.set(TalonFXControlMode.Velocity, target);
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

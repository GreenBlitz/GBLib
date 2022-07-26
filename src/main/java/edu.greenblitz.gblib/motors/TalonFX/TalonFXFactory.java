package edu.greenblitz.gblib.motors.TalonFX;

import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.IMotorFactory;

public class TalonFXFactory implements IMotorFactory {
	
	private int currentLimit = 0;
	private double voltageCompensation = 0;
	private double rampRate = 0;
	private boolean inverted = false;
	private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
	private double gearRatio = 2048;
	
	@Override
	public GBMotor generate(int id) {
		GBTalonFX motor = new GBTalonFX(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setGearRatio(gearRatio);
		return motor;
	}
	
	public TalonFXFactory withCurrentLimit(int currentLimit) {
		this.currentLimit = currentLimit;
		return this;
	}
	
	public TalonFXFactory withVoltageCompensation(double voltageCompensation) {
		this.voltageCompensation = voltageCompensation;
		return this;
	}
	
	public TalonFXFactory withRampRate(double rampRate) {
		this.rampRate = rampRate;
		return this;
	}
	
	public TalonFXFactory withInverted(boolean inverted) {
		this.inverted = inverted;
		return this;
	}
	
	public TalonFXFactory withIdleMode(AbstractMotor.IdleMode idleMode) {
		this.idleMode = idleMode;
		return this;
	}
	
	public TalonFXFactory withGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
		return this;
	}
}

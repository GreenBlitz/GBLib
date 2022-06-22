package edu.greenblitz.gblib.motors.Falcon;

import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.IMotorFactory;

public class FalconFactory implements IMotorFactory {
	
	private int currentLimit = 0;
	private double voltageCompensation = 0;
	private double rampRate = 0;
	private boolean inverted = false;
	private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
	private double gearRatio = 2048;
	
	@Override
	public GBMotor generate(int id) {
		GBFalcon motor = new GBFalcon(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setGearRatio(gearRatio);
		return motor;
	}
	
	public FalconFactory withCurrentLimit(int currentLimit) {
		this.currentLimit = currentLimit;
		return this;
	}
	
	public FalconFactory withVoltageCompensation(double voltageCompensation) {
		this.voltageCompensation = voltageCompensation;
		return this;
	}
	
	public FalconFactory withRampRate(double rampRate) {
		this.rampRate = rampRate;
		return this;
	}
	
	public FalconFactory withInverted(boolean inverted) {
		this.inverted = inverted;
		return this;
	}
	
	public FalconFactory withIdleMode(AbstractMotor.IdleMode idleMode) {
		this.idleMode = idleMode;
		return this;
	}
	
	public FalconFactory withGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
		return this;
	}
}

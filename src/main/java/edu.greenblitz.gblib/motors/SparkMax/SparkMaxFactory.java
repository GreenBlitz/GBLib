package edu.greenblitz.gblib.motors.SparkMax;

import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.IMotorFactory;

public class SparkMaxFactory implements IMotorFactory {
	
	private int currentLimit = 0;
	private int voltageCompensation = 0;
	private double rampRate = 0;
	private boolean inverted = false;
	private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
	private double gearRatio = 42;
	
	@Override
	public GBMotor generate(int id) {
		GBSparkMax motor = new GBSparkMax(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setGearRatio(gearRatio);
		return motor;
	}
	
	public SparkMaxFactory withCurrentLimit(int currentLimit) {
		this.currentLimit = currentLimit;
		return this;
	}
	
	public SparkMaxFactory withVoltageCompensation(int voltageCompensation) {
		this.voltageCompensation = voltageCompensation;
		return this;
	}
	
	public SparkMaxFactory withRampRate(double rampRate) {
		this.rampRate = rampRate;
		return this;
	}
	
	public SparkMaxFactory withInverted(boolean inverted) {
		this.inverted = inverted;
		return this;
	}
	
	public SparkMaxFactory withIdleMode(AbstractMotor.IdleMode idleMode) {
		this.idleMode = idleMode;
		return this;
	}
	
	public SparkMaxFactory withGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
		return this;
	}
	
}

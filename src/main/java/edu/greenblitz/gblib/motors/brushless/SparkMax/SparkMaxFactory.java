package edu.greenblitz.gblib.motors.brushless.SparkMax;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;

public class SparkMaxFactory implements IMotorFactory {
	
	private int currentLimit = 0;
	private int voltageCompensation = 0;
	private double rampRate = 0;
	private boolean inverted = false;
	private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
	private double ticksToRotations = 1;
	
	@Override
	public GBMotor generate(int id) {
		GBSparkMax motor = new GBSparkMax(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setTicksToRotations(ticksToRotations);
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
	
	public SparkMaxFactory withTicksToRotations(double ticksToRotations) {
		this.ticksToRotations = ticksToRotations;
		return this;
	}
	
}

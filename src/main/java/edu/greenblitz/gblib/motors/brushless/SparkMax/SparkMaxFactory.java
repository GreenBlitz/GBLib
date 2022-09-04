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
	private double gearRatio = 1;
	private final static double SPARKMAX_TICKS_PER_RADIAN = 1 / (Math.PI * 2);
	private final static double SPARKMAX_VELOCITY_UNITS_TO_RPM = 1;


	@Override
	public GBMotor generate(int id) {
		GBSparkMax motor = new GBSparkMax(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setTicksToRadians(gearRatio * SPARKMAX_TICKS_PER_RADIAN);
		motor.setTicksToWheelRPM(gearRatio * SPARKMAX_VELOCITY_UNITS_TO_RPM);
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

package edu.greenblitz.gblib.motors.brushless.TalonFX;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;

public class TalonFXFactory implements IMotorFactory {
	private int currentLimit = 0;
	private double voltageCompensation = 0;
	private double rampRate = 0;
	private boolean inverted = false;
	private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
	private double gearRatio = 1;
	private final static double FALCON_TICKS_PER_RADIAN = 2048 /(Math.PI * 2) ;
	private final static double FALCON_VELOCITY_UNITS_TO_RPM  = FALCON_TICKS_PER_RADIAN /(600 * 2 * Math.PI);
	@Override
	public GBMotor generate(int id) {
		GBTalonFX motor = new GBTalonFX(id);
		motor.setCurrentLimit(currentLimit);
		motor.setVoltageCompensation(voltageCompensation);
		motor.setRampRate(rampRate);
		motor.setInverted(inverted);
		motor.setIdleMode(idleMode);
		motor.setTicksToRadians(gearRatio * FALCON_TICKS_PER_RADIAN);
		motor.setTicksToWheelRPM(gearRatio * FALCON_VELOCITY_UNITS_TO_RPM);
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

package edu.greenblitz.gblib.motors.SparkMax;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.AbstractMotor;

public class GBSparkMax extends AbstractMotor {
	private final CANSparkMax motor;
	private final RelativeEncoder encoder;
	
	/**
	 * Constructor:
	 * Defines motor as the edu.greenblitz.gblib.motors.SparkMax with the given id;
	 * Sets encoder as the motor's encoder;
	 * Sets gear ratio as given;
	 */
	public GBSparkMax(int id, double gearRatio) {
		this.motor = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.encoder = this.motor.getEncoder();
		setGearRatio(gearRatio);
	}
	
	
	/**
	 * sets gear ratio is 1 for default, if not given otherwise;
	 */
	public GBSparkMax(int id) {
		this(id, 1);
	}
	
	
	@Override
	public void setPower(double power) {
		motor.set(power);
	}
	
	@Override
	public boolean getInverted() {
		return motor.getInverted();
	}
	
	@Override
	public void setInverted(boolean inverted) {
		motor.setInverted(inverted);
	}
	
	@Override
	public double getRawTicks() {
		return encoder.getPosition();
	}
	
	@Override
	public double getRawVelocity() {
		return encoder.getVelocity();
	}
	
	
	@Override
	public void configurePID(PIDObject pidObject) {
		motor.getPIDController().setP(pidObject.getKp());
		motor.getPIDController().setI(pidObject.getKi());
		motor.getPIDController().setD(pidObject.getKd());
		motor.getPIDController().setFF(pidObject.getKf());
		motor.getPIDController().setIZone(pidObject.getIZone());
	}
	
	@Override
	public void setTargetByPID(double target, PIDTarget targetType) {
		switch (targetType) {
			case Speed:
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kVelocity);
				break;
			case Current:
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kCurrent);
				break;
			case Position:
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kPosition);
				break;
		}
	}
	
	@Override
	public void setTargetSpeedByPID(double target) {
		motor.getPIDController().setReference(target, CANSparkMax.ControlType.kVelocity);
	}
	
	
	public void setCurrentLimit(int limit) {
		motor.setSmartCurrentLimit(limit);
	}
	
	@Override
	public void resetEncoder() {
		motor.getEncoder().setPosition(0);
	}
	
	@Override
	public void setIdleMode(IdleMode idleMode) {
		if (idleMode == IdleMode.Brake) {
			motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		} else {
			motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
		}
	}
	
	@Override
	public IdleMode getIdleMode() {
		return motor.getIdleMode() == CANSparkMax.IdleMode.kBrake ? IdleMode.Brake : IdleMode.Coast;
	}
	
	public void setVoltageCompensation(int voltageCompensation) {
		if (voltageCompensation != 0) {
			motor.enableVoltageCompensation(voltageCompensation);
		} else {
			motor.disableVoltageCompensation();
		}
	}
	
	public void setRampRate(double rampRate) {
		motor.setOpenLoopRampRate(rampRate);
		motor.setClosedLoopRampRate(rampRate);
	}
	
}

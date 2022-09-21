package edu.greenblitz.gblib.motors.brushless.SparkMax;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.wpi.first.wpilibj.RobotController;

public class GBSparkMax extends AbstractMotor {
	private final CANSparkMax motor;
	private final RelativeEncoder encoder;


	protected GBSparkMax(int id) {
		this.motor = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.encoder = this.motor.getEncoder();
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
		motor.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
	}

	@Override
	public void setTargetByPID(double target, PIDTarget targetType) {
		switch (targetType) {
			case Speed:
				target *= getTicksToWheelRPM();
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kVelocity);
				break;
			case Current:
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kCurrent);
				break;
			case Position:
				target *= getTicksToRadians();
				motor.getPIDController().setReference(target, CANSparkMax.ControlType.kPosition);
				break;
		}
	}

	@Override
	public void setVoltage(double voltage) {
		motor.set(voltage / RobotController.getBatteryVoltage());
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
	public IdleMode getIdleMode() {
		return motor.getIdleMode() == CANSparkMax.IdleMode.kBrake ? IdleMode.Brake : IdleMode.Coast;
	}

	@Override
	public void setIdleMode(IdleMode idleMode) {
		if (idleMode == IdleMode.Brake) {
			motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		} else {
			motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
		}
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

	public void setEncoderAng(double angle){
		encoder.setPosition(angle*getTicksToRadians());
	}

}

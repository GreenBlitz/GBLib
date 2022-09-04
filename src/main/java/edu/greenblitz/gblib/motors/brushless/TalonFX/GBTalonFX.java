package edu.greenblitz.gblib.motors.brushless.TalonFX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;

public class GBTalonFX extends AbstractMotor {
	private final TalonFX motor;

	private final IdleMode idleMode = IdleMode.Brake;

	/**
	 * Constructor:
	 * Defines motor as the Falcon with the given id;
	 */
	protected GBTalonFX(int id) {
		motor = new TalonFX(id);
	}


	@Override
	public void setPower(double power) {
		motor.set(ControlMode.PercentOutput, power);
	}

	@Override
	public boolean getInverted() {
		return (motor.getInverted()); /*getInverted is also a talon method*/
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
	public double getRawTicks() {
		return motor.getSelectedSensorPosition();
	}

	@Override
	public double getRawVelocity() {
		return motor.getSelectedSensorVelocity();
	}

	@Override
	public void configurePID(PIDObject pidObject) {
		motor.config_kP(0, pidObject.getKp());
		motor.config_kI(0, pidObject.getKi());
		motor.config_kD(0, pidObject.getKd());
		motor.config_kF(0, pidObject.getKf());
		motor.config_IntegralZone(0, pidObject.getIZone());
		motor.configClosedLoopPeakOutput(0, pidObject.getMaxPower());
	}

	@Override
	public void setTargetByPID(double target, PIDTarget targetType) {
		switch (targetType) {
			case Speed:
				target *= getTicksToWheelRPM();
				motor.set(TalonFXControlMode.Velocity, target);
				break;
			case Current:
				motor.set(TalonFXControlMode.Current, target);
				break;
			case Position:
				target *= getTicksToRadians();
				motor.set(TalonFXControlMode.Position, target);
				break;
		}

	}

	@Override
	public void setTargetSpeedByPID(double target) {
		motor.set(TalonFXControlMode.Velocity, target);
	}

	@Override
	public void resetEncoder() {
		motor.setSelectedSensorPosition(0);
	}

	public void setCurrentLimit(int limit) {
		motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(limit != 0, limit, 0.875 * limit, 100));
	}

	public void setVoltageCompensation(double voltage) {
		motor.enableVoltageCompensation(voltage != 0);
		motor.configVoltageCompSaturation(voltage);
	}

	public void setRampRate(double rampRate) {
		motor.configOpenloopRamp(rampRate);
	}

	@Override
	public IdleMode getIdleMode() {
		return idleMode;
	}

	@Override
	public void setIdleMode(IdleMode idleMode) {

	}

	public void setEncoderAng(double angle){
		motor.setSelectedSensorPosition(angle*getTicksToRadians());
	}
}

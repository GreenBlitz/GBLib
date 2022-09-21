package edu.greenblitz.gblib.motors.brushless;

import edu.greenblitz.gblib.motion.pid.PIDObject;

public class DecoyMotor extends AbstractMotor{
	@Override
	public void setPower(double power) {

	}

	@Override
	public boolean getInverted() {
		return false;
	}

	@Override
	public void setInverted(boolean inverted) {

	}

	@Override
	public IdleMode getIdleMode() {
		return IdleMode.Coast;
	}

	@Override
	public void setIdleMode(IdleMode idleMode) {

	}

	@Override
	public double getRawTicks() {
		return 0;
	}

	@Override
	public double getRawVelocity() {
		return 0;
	}

	@Override
	public void configurePID(PIDObject pidObject) {

	}

	@Override
	public void setTargetByPID(double target, PIDTarget targetType) {

	}

	@Override
	public void setTargetSpeedByPID(double target) {

	}

	@Override
	public void setVoltage(double voltage) {

	}

	@Override
	public void resetEncoder() {

	}

	@Override
	public void setEncoderAng(double angle) {

	}
}

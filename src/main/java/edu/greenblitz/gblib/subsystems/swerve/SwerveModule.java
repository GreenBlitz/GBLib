package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class SwerveModule extends GBSubsystem {

	private final GBMotor angleMotor;
	private final GBMotor linMotor;
	private int isReversed = 1;
	public double targetAngle;
	public double targetVel;

	public SwerveModule(IMotorFactory motorFactoryA, IMotorFactory motorFactoryL, int portA, int portL) {
		angleMotor = motorFactoryA.generate(portA);
		linMotor = motorFactoryL.generate(portL);
	}

	public void setVelocity(double speed) {
		speed *= isReversed;
		linMotor.setTargetByPID(speed, AbstractMotor.PIDTarget.Speed);
		targetVel = speed;
	}

	public void rotateToAngle(double angle) {
		angleMotor.setTargetByPID(angle, AbstractMotor.PIDTarget.Position);
		targetAngle = angle;
	}

	public double getCurrentAngle() {
		return angleMotor.getNormalizedPosition();
	}

	public double getCurrentVel() {
		return linMotor.getNormalizedVelocity();
	}

	public void rotateByAngle(double angle) {
		targetAngle = getCurrentAngle() + angle;
		angleMotor.setTargetByPID(targetAngle, AbstractMotor.PIDTarget.Position);
	}

	public void resetAngle() {
		angleMotor.resetEncoder();
	}

	public void configLinPID(PIDObject pidObject) {
		linMotor.configurePID(pidObject);
	}

	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configurePID(pidObject);
	}

	public void setLinPower(double power) {
		linMotor.setPower(power);
	}

	public double getTargetAngle() {
		return targetAngle;
	}

	public double getTargetVel() {
		return targetVel;
	} //udi: *isReversed


}

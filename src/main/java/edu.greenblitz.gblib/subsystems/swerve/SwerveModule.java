package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class SwerveModule extends GBSubsystem {
	
	private GBMotor angleMotor;
	private GBMotor linMotor;
	private static int isReversed;
	public double targetAngle;
	public double targetVel;
	
	public SwerveModule(int isReversed, IMotorFactory motorFactoryA, IMotorFactory motorFactoryL, int portA, int portL) {
		angleMotor = motorFactoryA.generate(portA);
		linMotor = motorFactoryL.generate(portL);
		isReversed = isReversed;
		
	}
	
	public void setVelocity(double speed) {
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
	}
	
	
}

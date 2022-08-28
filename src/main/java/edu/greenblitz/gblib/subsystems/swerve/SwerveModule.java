package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;
import edu.greenblitz.gblib.motors.brushed.IBrushedFactory;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveModule {


	private static int isReversed;
	private final int lampreyTicksPerRotation = 4076; //not my fault it's an ugly number
	public double targetAngle;
	public double targetVel;
	private final GBMotor angleMotor;
	private final GBBrushedMotor linMotor;
	private final AnalogInput lamprey;

	public SwerveModule(int isReversed, IMotorFactory motorFactoryA, IBrushedFactory motorFactoryL, int portA, int portL, int lampreyID) {
		angleMotor = motorFactoryA.generate(portA);
		linMotor = motorFactoryL.generate(portL);
		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
		isReversed = isReversed;
	}


	public void setVelocity(double speed) {
		speed *= isReversed;
		linMotor.setPower(speed);
		targetVel = speed;
	}

	public double getAbsoluteAngle() { // in degrees;
		return (((double) lamprey.getAverageValue()) / lampreyTicksPerRotation) * 360;
	}

	public void rotateToAngle(double angle) {
		angleMotor.setTargetByPID(angle, AbstractMotor.PIDTarget.Position);
		targetAngle = angle;
	}

	public double getCurrentAngle() {
		return angleMotor.getNormalizedPosition();
	}

//	public double getCurrentVel() {
//		return linMotor.getNormalizedVelocity();
//	}  todo make work

	public void rotateByAngle(double angle) {
		targetAngle = getCurrentAngle() + angle;
		angleMotor.setTargetByPID(targetAngle, AbstractMotor.PIDTarget.Position);
	}

	public void resetAngle() {
		angleMotor.resetEncoder();
	}

//	public void configLinPID(PIDObject pidObject) {
//		linMotor.configurePID(pidObject);
//	} todo make work

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

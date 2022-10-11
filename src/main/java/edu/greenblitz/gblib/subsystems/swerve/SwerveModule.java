package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.angles.DualSidedAngTarget;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveModule {


	private int isReversed = 1;
	public double targetAngle;
	public double targetVel;
	private double maxLampreyVal;
	private double minLampreyVal;
	private final GBMotor angleMotor;
	private final GBMotor linearMotor;
	private final AnalogInput lamprey;
	private final SimpleMotorFeedforward feedforward;
	private final double wheelCirc;

	public SwerveModule(IMotorFactory motorFactoryA, IMotorFactory motorFactoryL, int portA, int portL,
	                    int lampreyID, double maxLampreyVal, double minLampreyVal,
	                    PIDObject pidAng, PIDObject pidLin, SimpleMotorFeedforward feedforward, double wheelCirc) {
		angleMotor = motorFactoryA.generate(portA);
		linearMotor = motorFactoryL.generate(portL);
		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
		this.maxLampreyVal = maxLampreyVal;
		this.minLampreyVal = minLampreyVal;
		configAnglePID(pidAng);
		configLinPID(pidLin);
		this.feedforward = feedforward;
		this.wheelCirc = wheelCirc;
	}

	public double getLampreyAngle() { // in radians;
		return (lamprey.getValue() - minLampreyVal) / (maxLampreyVal - minLampreyVal) * Math.PI * 2;
	}

	public void rotateToAngle(double angle) {
		//TODO: Make good PID.
		//DualSidedAngTarget dualSidedAngTarget = DualSidedAngTarget.generateTarget(angle, GBMath.modulo(getMotorAngle(), 2 * Math.PI));
		//angle = dualSidedAngTarget.getTarget();

		double diff = GBMath.modulo(angle - getMotorAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getMotorAngle() + diff;

		//isReversed = dualSidedAngTarget.getDirection();
		angleMotor.setTargetByPID(angle, AbstractMotor.PIDTarget.Position);
		targetAngle = angle;
	}

	public double getRawLampreyAngle() {
		return lamprey.getValue();
	}

	public double getMotorAngle() {
		return angleMotor.getNormalizedPosition();
	}

	public double getCurrentVel() {
		return linearMotor.getNormalizedVelocity() / 60 * wheelCirc;
	}

	public void rotateByAngle(double angle) {
		angleMotor.setTargetByPID(getMotorAngle() + angle, AbstractMotor.PIDTarget.Position);
	}

	public void resetAngle() {
		angleMotor.resetEncoder();
	}

	public void resetEncoderByLamprey() {
		angleMotor.setEncoderAng(getLampreyAngle());
	}
	
	public void resetEncoderByValue(double angle) {
		angleMotor.setEncoderAng(angle);
	}

	public void configLinPID(PIDObject pidObject) {
		linearMotor.configurePID(pidObject);
	}

	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configurePID(pidObject);
	}


	public void setLinSpeed(double speed) {
		speed *= isReversed;
		linearMotor.setTargetSpeedByPID(speed, feedforward.calculate(speed));
	}

	public void setRotPower(double power) {
		angleMotor.setPower(power);
	}
	//only for debugging

	public double getTargetAngle() {
		return targetAngle;
	}

	public double getTargetVel() {
		return targetVel * isReversed;
	}

	public void setLinPower(double power) {
		linearMotor.setPower(power * isReversed);
	}

	public int getIsReversed() {
		return isReversed;
	}
	
	public SwerveModuleState getModuleState (){
		return new SwerveModuleState(getCurrentVel(),new Rotation2d(this.getMotorAngle()));
	}


}

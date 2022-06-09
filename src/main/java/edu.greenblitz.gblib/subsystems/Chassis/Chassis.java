package edu.greenblitz.gblib.subsystems.Chassis;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.Motor;
import edu.greenblitz.gblib.motors.MotorType;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Chassis extends GBSubsystem {
	private static Chassis instance;
	private final PigeonGyro gyroscope;
	private final double wheelDistance;
	private final Motor[] motors;
	
	
	private Chassis(MotorType motorType, int[] ports, boolean[] isInverted, double wheelDistance) {
		motors = new Motor[ports.length];
		
		for (int i = 0; i < motors.length; i++) {
			motors[i] = motorType.generate(ports[i]);
			motors[i].setInverted(isInverted[i]);
			motors[i].setCurrentLimit(40);
		}
		
		gyroscope = new PigeonGyro(new PigeonIMU(12)); //Pigeon connects to talon/CAN bus
		gyroscope.reset();
		setIdleMode(AbstractMotor.IdleMode.Brake);
		this.wheelDistance = wheelDistance;
	}
	public static Chassis getInstance() {
		return instance;
	}
	
	public static void create(
			MotorType motorType,
			int[] ports,
			boolean[] isInverted,
			double wheelDistance) {
		instance = new Chassis(motorType, ports, isInverted, wheelDistance);
	}
	
	public void setIdleMode(AbstractMotor.IdleMode idleMode) {
		for (Motor motor : motors) {
			motor.setIdleMode(idleMode);
		}
	}
	
	public Motor[] getRightMotor() {
		Motor[] right = new Motor[motors.length / 2];
		for (int i = 0; i < motors.length / 2; i++) {
			right[i] = motors[i];
		}
		return right;
	}
	
	public Motor getRightMotor(int id) {
		Motor[] rightMotors = getRightMotor();
		return rightMotors[id];
	}
	
	public Motor[] getLeftMotor() {
		Motor[] left = new Motor[motors.length / 2];
		for (int i = 0; i < motors.length / 2; i++) {
			left[i] = motors[motors.length / 2 + i];
		}
		return left;
	}
	
	public Motor getLeftMotor(int id) {
		Motor[] leftMotors = getLeftMotor();
		return leftMotors[id];
	}
	
	public void moveMotors(double powerLeft, double powerRight) {
		for (Motor motor : getLeftMotor()) {
			motor.setPower(powerLeft);
		}
		for (Motor motor : getRightMotor()) {
			motor.setPower(powerRight);
		}
	}
	
	public void moveMotors(double power) {
		moveMotors(power, power);
	}
	
	
	public void arcadeDrive(double moveValue, double rotateValue) {
		moveMotors(moveValue - rotateValue, moveValue + rotateValue);
	}
	
	public double getLeftMeters() {
		return getLeftMotor(1).getNormalizedPosition();
	}
	
	public double getRightMeters() {
		return getRightMotor(1).getNormalizedPosition();
	}
	
	public double getMeters() {
		return (getLeftMeters() + getRightMeters()) * 0.5;
	}
	
	public double getLeftVelocity() {
		return getLeftMotor(1).getNormalizedVelocity();
	}
	
	public double getRightVelocity() {
		return getRightMotor(1).getNormalizedVelocity();
	}
	
	
	public double getLinearVelocity() {
		return (getRightVelocity() + getLeftVelocity()) * 0.5;
	}
	
		public double getAngularVelocityByWheels() {
		return (getRightVelocity() - getLeftVelocity())/getWheelDistance();
		//this is true, if u don't understand, go to your mommy and cry (ask asaf);
	}

	public double getAngle() {
		return gyroscope.getNormalizedYaw();
	}
	
	public double getRawAngle() {
		return gyroscope.getRawYaw();
	}
	
	public double getAngularVelocityByGyro() {
		return gyroscope.getYawRate();
	}
	
	public void resetGyro() {
		gyroscope.reset();
	}
	
	public double getWheelDistance() {
		return wheelDistance;
	}

//	public Position getLocation() { //todo todo todo fzxtdjyygvju
//		return Localizer.getInstance().getLocation();
//	}
	
	public void resetEncoders() {
		getLeftMotor(1).resetEncoder();
		getRightMotor(1).resetEncoder();
	}
	
	public void setMotorByID(int id, double power) {
		motors[id].setPower(power);
	}
	
	
}

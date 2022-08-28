package edu.greenblitz.gblib.subsystems.Chassis;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motion.Localizer;
import edu.greenblitz.gblib.motion.cords.Position;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class Chassis extends GBSubsystem {
	private final PigeonGyro gyroscope;
	private final double wheelDistance;
	private final GBMotor[] motors;


	public Chassis(IMotorFactory motorFactory, int[] ports, boolean[] isInverted, double wheelDistance) {
		motors = new GBMotor[ports.length];

		for (int i = 0; i < motors.length; i++) {
			motors[i] = motorFactory.generate(ports[i]);
			motors[i].setInverted(isInverted[i]);
		}

		gyroscope = new PigeonGyro(new PigeonIMU(12)); //Pigeon connects to talon/CAN bus
		gyroscope.reset();
		setIdleMode(AbstractMotor.IdleMode.Brake);
		this.wheelDistance = wheelDistance;
	}


	public void setIdleMode(AbstractMotor.IdleMode idleMode) {
		for (GBMotor motor : motors) {
			motor.setIdleMode(idleMode);
		}
	}

	public GBMotor[] getRightMotor() {
		GBMotor[] right = new GBMotor[motors.length / 2];
		System.arraycopy(motors, 0, right, 0, motors.length / 2);
		return right;
	}

	public GBMotor getRightMotor(int id) {
		GBMotor[] rightMotors = getRightMotor();
		return rightMotors[id];
	}

	public GBMotor[] getLeftMotor() {
		GBMotor[] left = new GBMotor[motors.length / 2];
		System.arraycopy(motors, motors.length / 2 + 0, left, 0, motors.length / 2);
		return left;
	}

	public GBMotor getLeftMotor(int id) {
		GBMotor[] leftMotors = getLeftMotor();
		return leftMotors[id];
	}

	public void moveMotors(double powerLeft, double powerRight) {
		for (GBMotor motor : getLeftMotor()) {
			motor.setPower(powerLeft);
		}
		for (GBMotor motor : getRightMotor()) {
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
		return (getRightVelocity() - getLeftVelocity()) / getWheelDistance();
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

	public Position getLocation() {
		return Localizer.getInstance().getLocation();
	}

	public void resetEncoders() {
		getLeftMotor(1).resetEncoder();
		getRightMotor(1).resetEncoder();
	}

	public void setMotorByID(int id, double power) {
		motors[id].setPower(power);
	}


}

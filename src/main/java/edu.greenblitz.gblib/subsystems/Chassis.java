package edu.greenblitz.gblib.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBFalcon;
import edu.greenblitz.gblib.motors.Motor;
import edu.greenblitz.gblib.motors.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import javax.swing.text.Position;

import static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putString;


public class Chassis extends GBSubsystem {
	private static Chassis instance;
	private final PigeonGyro gyroscope;
	private double wheelDistance;
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
	
	public void setIdleMode(AbstractMotor.IdleMode idleMode){
		for (Motor motor: motors) {
			motor.setIdleMode(idleMode);
		}
	}

	public static void create(
			MotorType motorType,
			int[] ports,
			boolean[] isInverted,
			double wheelDistance )
	{instance= new Chassis(motorType,ports,isInverted,wheelDistance);}
	
	public Motor[] getRightMotors(){
		Motor[] right= new Motor[motors.length/2];
		for (int i = 0; i <motors.length/2; i++) {
			right[i] =motors[i];
		}
		return right;
	}
	
	public Motor[] getLeftMotors(){
		Motor[] left= new Motor[motors.length/2];
		for (int i = 0; i <motors.length/2; i++) {
			left[i] =motors[motors.length/2+i];
		}
		return left;
	}
	
//
//	public void moveMotors(double left, double right) {
//		SmartDashboard.putNumber("Left Power", left);
//		SmartDashboard.putNumber("Right Power", right);
//		for (int i = 0; i < motors.length/2; i++) {
//			motors[i].setPower(right);
//		}
//		for (int i = motors.length/2 +1 ; i <motors.length ; i++) {
//			motors[i].setPower(left);
//		}
////
////		motors[0].setPower(right);
////		motors[1].setPower(right);
////		motors[2].setPower(right);
////		motors[3].setPower(left);
//	}
//
//	public void moveMotors(double power){
//		moveMotors(power, power);
//	}
//
//	public void toBrake() {
//		for(Motor motor : motors){
//			motor.setIdleMode(AbstractMotor.IdleMode.Brake);
//		}
//	}
//
//	public void toCoast() {
//		for(Motor motor : motors){
//			motor.setIdleMode(AbstractMotor.IdleMode.Coast);
//		}
//	}
//
//	public void semiToCoast() {
//		for(int i = 1; i < 5; i++){
//			motors[i].setIdleMode(AbstractMotor.IdleMode.Coast); //moves two thirds to coast
//		}
//	}
//
//	public void semiToBrake() {
//		for(int i = 1; i < 5; i++){
//			motors[i].setIdleMode(AbstractMotor.IdleMode.Brake); //moves two thirds to brake
//		}
//	}
//
//	public void arcadeDrive(double moveValue, double rotateValue) {
//		SmartDashboard.putNumber("rotate power", rotateValue);
//		moveMotors(moveValue - rotateValue, moveValue + rotateValue);
//	}
//
//	public double getLeftMeters() {
//		return motors[1].getNormalizedPosition();
//	}
//
//	public double getRightMeters() {
//		return motors[motors.length/2+1].getNormalizedPosition();
//	}
//
//	public double getMeters(){
//		return (getLeftMeters() + getRightMeters()) / 2.0;
//	}
//
//	public double getLeftRate() {
//		return motors[1].getNormalizedVelocity();
//	}
//
//	public double getRightRate() {
//		return motors[motors.length/2+1].getNormalizedVelocity();
//	}
//
//	public double getLinearVelocity() {
//		return 0.5 * (getRightRate() + getLeftRate());
//	}
//
//	public double getAngularVelocityByWheels() {
//		return (getRightRate() - getLeftRate())/getWheelDistance();
//		//this is true, if u don't understand, go to your mommy and cry (ask asaf);
//	}
//
//	public double getAngle() {
//		return gyroscope.getNormalizedYaw();
//	}
//
//	public double getRawAngle() {
//		return gyroscope.getRawYaw();
//	}
//
//	public double getAngularVelocityByGyro() {
//		return gyroscope.getYawRate();
//	}
//
//	public void resetGyro() {
//		gyroscope.reset();
//	}
//
//	public double getWheelDistance() {
//		return wheelDistance;
//	}
//
//	public Position getLocation() { //todo todo todo fzxtdjyygvju
//		return Localizer.getInstance().getLocation();
//	}
//
//
//
//
//	@Override
//	public void periodic() {
//		SmartDashboard.putNumber("Pigeon angle deg", Math.toDegrees(getAngle()));
//		putString("Location", Chassis.getInstance().getLocation().toString());
//
//	}
//
//	public void resetEncoders() {
//		.reset();
//		leftEncoder.reset();
//	}
//
//	public void setMotorByID(int id, double power){
//		motors[id].set(power);
//	}
//
//
//
//
}

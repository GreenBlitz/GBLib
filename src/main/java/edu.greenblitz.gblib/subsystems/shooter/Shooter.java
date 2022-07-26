package edu.greenblitz.gblib.subsystems.shooter;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class Shooter extends GBSubsystem {
	
	private final static double RPM = 3000;
	private static Shooter instance;
	private final GBMotor motor;
	private boolean preparedToShoot;
	private boolean isShooter;
	
	private Shooter(IMotorFactory motorFactory, int id) {
		this.motor = motorFactory.generate(id);
//		//leader.setClosedLoopRampRate(1);
//
		preparedToShoot = false;
	}
	
	public static void create(
			IMotorFactory motorFactory,
			int port) {
		instance = new Shooter(motorFactory, port);
		
	}
	
	
	public static Shooter getInstance() {
		return instance;
	}
	
	public void setPower(double power) {
		this.motor.setPower(power);
	}
	
	public void setIdleMode(AbstractMotor.IdleMode idleMode) {
		motor.setIdleMode(idleMode);
	}
	
	/**
	 * pid is handled by GBMotor either internal motor controller pid or external in GBMotor implementation
	 *
	 * @param target the target speed in rpm
	 */
	public void setSpeedByPID(double target) {
		motor.setTargetSpeedByPID(target);
	}
	
	public void setPIDConsts(PIDObject obj) {
		motor.configurePID(obj);
	}
	
	
	public double getShooterSpeed() {
		return motor.getNormalizedVelocity();
	}
	
	public void resetEncoder() {
		motor.resetEncoder();
	}
	
	public boolean isPreparedToShoot() {
		return preparedToShoot;
	}
	
	public void setPreparedToShoot(boolean preparedToShoot) {
		this.preparedToShoot = preparedToShoot;
	}
	
	
}

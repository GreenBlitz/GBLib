package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.shooter.ShooterCommand;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Shooter extends GBSubsystem {

	private final static double RPM = 3000;
	private final GBMotor motor;

	private static Shooter instance;

	private Shooter(IMotorFactory motorFactory, int id) {
		this.motor = motorFactory.generate(id);
		motor.configurePID(RobotMap.Pegasus.Shooter.ShooterMotor.pid);
	}

	public static void create(IMotorFactory motorFactory, int id){
		instance = new Shooter(motorFactory,id);
	}

	public static Shooter getInstance(){
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
//		System.out.println(target);
		motor.setTargetSpeedByPID(target,RobotMap.Pegasus.Shooter.ShooterMotor.feedforward.calculate(target));
	}

	public void setPIDConsts(PIDObject obj) {
		motor.configurePID(obj);
	}

	public double getShooterSpeed() {
		return motor.getRawVelocity();
	}

	public void resetEncoder() {
		motor.resetEncoder();
	}

	public boolean isPreparedToShoot(){
		return ((ShooterCommand) getCurrentCommand()).isPreparedToShoot();
	}



}

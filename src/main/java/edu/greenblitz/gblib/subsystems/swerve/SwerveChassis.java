package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;

public class SwerveChassis extends GBSubsystem {
	
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	private final PigeonGyro pigeonGyro;
	private double length;
	private double width;
	
	public enum Module {
		FRONT_RIGHT,
		FRONT_LEFT,
		BACK_RIGHT,
		BACK_LEFT
	}
	
	private SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro, double length, double width) {
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
		this.pigeonGyro = pigeonGyro;
		this.width = width;
		this.length = length;
		
	}

	private static SwerveChassis instance;


	public static void create(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro, double length, double width){
		instance = new SwerveChassis(frontRight, frontLeft, backRight, backLeft, pigeonGyro, length, width);
	}

	public static SwerveChassis getInstance(){
		return instance;
	}
	
	/**
	 * returns the swerve module based on its name
	 */
	private SwerveModule getModule(Module module) {
		switch (module) {
			case BACK_LEFT:
				return backLeft;
			case BACK_RIGHT:
				return backRight;
			case FRONT_LEFT:
				return frontLeft;
			case FRONT_RIGHT:
				return frontRight;
		}
		return null;
	}
	
	/**
	 * for calibration purposes
	 */
	public void rotateModuleByPower(Module module, double power) {
		getModule(module).setRotPower(power);
	}
	
	@Deprecated
	public void brakeModules(Module... modules) {
		for (Module module : modules) {
			getModule(module).setLinPower(0);
			getModule(module).setRotPower(0);
		}
	}
	
	public void stop(){
		frontRight.setLinPower(0);
		frontRight.setRotPower(0);
		frontLeft.setLinPower(0);
		frontLeft.setRotPower(0);
		backRight.setLinPower(0);
		backRight.setRotPower(0);
		backLeft.setLinPower(0);
		backLeft.setRotPower(0);
	}
	
	public void configPID(PIDObject pidObjectAng, PIDObject pidObjectLin) {
		getModule(Module.FRONT_LEFT).configAnglePID(pidObjectAng);
		getModule(Module.FRONT_LEFT).configLinPID(pidObjectLin);
		getModule(Module.FRONT_RIGHT).configAnglePID(pidObjectAng);
		getModule(Module.FRONT_RIGHT).configLinPID(pidObjectLin);
		getModule(Module.BACK_LEFT).configAnglePID(pidObjectAng);
		getModule(Module.BACK_LEFT).configLinPID(pidObjectLin);
		getModule(Module.BACK_RIGHT).configAnglePID(pidObjectAng);
		getModule(Module.BACK_RIGHT).configLinPID(pidObjectLin);
	}
	
	public void resetAllEncoders (){
		
		getModule(Module.FRONT_LEFT).resetEncoderByLamprey();
		getModule(Module.FRONT_RIGHT).resetEncoderByLamprey();
		getModule(Module.BACK_LEFT).resetEncoderByLamprey();
		getModule(Module.BACK_RIGHT).resetEncoderByLamprey();
		
	}
	
	/**
	 * all code below is self-explanatory
	 * <p>
	 * ALL IN RADIANS, NOT DEGREES
	 */
	public void moveSingleModule(Module module, double angle, double speed) {
		getModule(module).rotateToAngle(angle);
		getModule(module).moveLinPID(speed);
	}
	
	public void moveChassisLin(double angle, double speed) {
		moveSingleModule(Module.FRONT_LEFT, angle, speed);
		moveSingleModule(Module.FRONT_RIGHT, angle, speed);
		moveSingleModule(Module.BACK_LEFT, angle, speed);
		moveSingleModule(Module.BACK_RIGHT, angle, speed);
	}


	public double getRawLampreyAngle(Module module) {
		return getModule(module).getRawLampreyAngle();
	}

	public double getAngle(Module module){
		return getModule(module).getMotorAngle();
	}

	public double getTarget(Module module){
		return getModule(module).getTargetAngle();

	}

	
	
	
}

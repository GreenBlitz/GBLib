package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

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
	
	public SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro, double length, double width) {
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
		this.pigeonGyro = pigeonGyro;
		this.width = width;
		this.length = length;
		
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
	
	public void configPID(PIDObject pidObject) {
		getModule(Module.FRONT_LEFT).configAnglePID(pidObject);
		getModule(Module.FRONT_RIGHT).configAnglePID(pidObject);
		getModule(Module.BACK_LEFT).configAnglePID(pidObject);
		getModule(Module.BACK_RIGHT).configAnglePID(pidObject);
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
	public void moveSingleModule(Module module, double angle, double power) {
		getModule(module).rotateToAngle(angle);
		getModule(module).setLinPower(power);
	}
	
	public void moveChassisLin(double angle, double power) {
		moveSingleModule(Module.FRONT_LEFT, angle, power);
		moveSingleModule(Module.FRONT_RIGHT, angle, power);
		moveSingleModule(Module.BACK_LEFT, angle, power);
		moveSingleModule(Module.BACK_RIGHT, angle, power);
	}
	
	public double getRawAbsoluteAngle(Module module) {
		return getModule(module).getRawLampreyAngle();
	}
	
	public double getAbsoluteAngle(Module module) {
		return getModule(module).getLampreyAngle();
	}
	
	
	// that's the end of the self-explanatory code
	
	/**
	 * rotates the chassis based on given power.
	 * the function rotates each module to the tangent of the circle on which it rotates
	 */
	public void rotateChassis(double power) {
		double angle = Math.PI/4 + Math.atan(length / width) ;
		
		moveSingleModule(Module.FRONT_RIGHT, angle, power);
		moveSingleModule(Module.FRONT_LEFT, -angle, power);
		moveSingleModule(Module.BACK_RIGHT, -angle, -power);
		moveSingleModule(Module.BACK_LEFT, angle, -power);
	}
	
	
}

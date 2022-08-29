package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
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
	 * all code below is self-explanatory
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
	 
    // that's the end of the self-explanatory code
    
    /** rotates the chassis based on given power.
     * the function rotates each module to the tangent of the circle on which it rotates */
	public void rotateChassis(double power) {
		double angle = 90 + Math.atan(length / width);
  
		moveSingleModule(Module.FRONT_RIGHT, angle, power);
        moveSingleModule(Module.FRONT_LEFT, -angle, power);
        moveSingleModule(Module.BACK_RIGHT, -angle, -power);
        moveSingleModule(Module.BACK_LEFT, angle, -power);
	}
	
	
	

	
}

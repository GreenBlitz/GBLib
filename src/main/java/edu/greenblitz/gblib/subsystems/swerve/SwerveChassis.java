package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveChassis extends GBSubsystem {
	
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	private final PigeonGyro pigeonGyro;
	
	
	SwerveDriveKinematics kinematics;
	
	
	public enum Module {
		FRONT_RIGHT,
		FRONT_LEFT,
		BACK_RIGHT,
		BACK_LEFT
	}
	
	private SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro,Translation2d[] swerveLocations) {
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;
		this.backRight = backRight;
		this.backLeft = backLeft;
		this.pigeonGyro = pigeonGyro;
		SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
				swerveLocations
		);
		
	}

	private static SwerveChassis instance;


	public static void create(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro,Translation2d[] swerveLocations){
		instance = new SwerveChassis(frontRight, frontLeft, backRight, backLeft, pigeonGyro,swerveLocations);
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
		getModule(module).setLinSpeed(speed);
	}
	
	public void moveSingleModule(Module module, SwerveModuleState state){
		moveSingleModule(module,state.angle.getRadians(),state.speedMetersPerSecond);
	}
	
	
	
	public void moveChassisLin(double angle, double speed) {
		moveSingleModule(Module.FRONT_RIGHT, angle, speed);
		moveSingleModule(Module.FRONT_LEFT, angle, speed);
		moveSingleModule(Module.BACK_RIGHT, angle, speed);
		moveSingleModule(Module.BACK_LEFT, angle, speed);
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
	
	public void holonomicDrive(ChassisSpeeds speeds){
		
		

		SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);



		
		SwerveModuleState frontRight = moduleStates[0];
		moveSingleModule(Module.FRONT_RIGHT,frontRight);
		
		SwerveModuleState frontLeft = moduleStates[1];
		moveSingleModule(Module.FRONT_LEFT,frontLeft);
		
		SwerveModuleState backRight = moduleStates[2];
		moveSingleModule(Module.BACK_RIGHT,backRight);
		
		SwerveModuleState backLeft = moduleStates[3];
		moveSingleModule(Module.BACK_LEFT,backLeft);
		
		
		
	}

	
	
	
}

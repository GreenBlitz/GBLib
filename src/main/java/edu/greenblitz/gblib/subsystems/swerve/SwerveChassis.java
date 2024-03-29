package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import javax.swing.plaf.PanelUI;

public class SwerveChassis extends GBSubsystem {
	
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	//	private final PigeonGyro pigeonGyro;
	private PigeonIMU pigeonIMU; //todo decide on whether to use our pijen;
	private SwerveDriveOdometry localizer;
	
	public double pigeonAngleOffset = 0.0;
	static final double blueAllianceOffset = (DriverStation.getAlliance() == DriverStation.Alliance.Blue) ? Math.PI : 0;
	//todo make not exist
	



	private SwerveDriveKinematics kinematics;


	public enum Module {
		FRONT_RIGHT,
		FRONT_LEFT,
		BACK_RIGHT,
		BACK_LEFT
	}

	private SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonIMU pigeonIMU, Translation2d[] swerveLocationsInSwerveKinematicsCoordinates, Pose2d initialPose) {
		
		this.frontRight = frontRight;
		this.frontLeft = frontLeft;

		this.backRight = backRight;
		this.backLeft = backLeft;
//		this.pigeonGyro = pigeonGyro;
		this.pigeonIMU = pigeonIMU;
		this.kinematics = new SwerveDriveKinematics(
				swerveLocationsInSwerveKinematicsCoordinates
		);
		this.localizer = new SwerveDriveOdometry(
				this.kinematics,
				new Rotation2d(this.getChassisAngle()),
				initialPose
		);
	}


	private static SwerveChassis instance;


	public static void create(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonIMU pigeonIMU, Translation2d[] swerveLocationsInSwerveKinematicsCoordinates, Pose2d initialPose) {
		instance = new SwerveChassis(frontRight, frontLeft, backRight, backLeft, pigeonIMU, swerveLocationsInSwerveKinematicsCoordinates, initialPose);
	}

	public static SwerveChassis getInstance() {
		return instance;
	}
	
	@Override
	public void periodic() {
		localizer.update(new Rotation2d(getChassisAngle()),
				frontLeft.getModuleState(), frontRight.getModuleState(),
				backLeft.getModuleState(), backRight.getModuleState());
	}
	
	/**
	 * @return returns the swerve module based on its name
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

	public void stop() {
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
	
	
	public void resetAllEncodersByValues() {
		getModule(Module.FRONT_LEFT).resetEncoderByValue(Math.PI); //todo MAGIC numbers
		getModule(Module.FRONT_RIGHT).resetEncoderByValue(0);
		getModule(Module.BACK_LEFT).resetEncoderByValue(0);
		getModule(Module.BACK_RIGHT).resetEncoderByValue(Math.PI);
	}
	
	
	public void resetAllEncodersByLamprey() {
		getModule(Module.FRONT_LEFT).resetEncoderByLamprey();
		getModule(Module.FRONT_RIGHT).resetEncoderByLamprey();
		getModule(Module.BACK_LEFT).resetEncoderByLamprey();
		getModule(Module.BACK_RIGHT).resetEncoderByLamprey();

	}
	
	public void resetAllEncodersToZero() {
		getModule(Module.FRONT_LEFT).resetEncoderToZero();
		getModule(Module.FRONT_RIGHT).resetEncoderToZero();
		getModule(Module.BACK_LEFT).resetEncoderToZero();
		getModule(Module.BACK_RIGHT).resetEncoderToZero();
	}
	
	public void resetModuleToZero(Module module){
		getModule(module).resetEncoderToZero();
	}
	/**
	 * all code below is self-explanatory
	 * <p>
	 * ALL IN RADIANS, NOT DEGREES
	 */
	public void moveSingleModule(Module module, double radians, double speed) {
		if (getModule(module) != null) { //IntelliJ is being dumb here, this should fix it - nitzan.b todo ignore intellij
			getModule(module).rotateToAngle(radians);
			getModule(module).setLinSpeed(speed);
		}
	}

	public void moveSingleModule(Module module, SwerveModuleState state) {
		moveSingleModule(module, state.angle.getRadians(), state.speedMetersPerSecond);
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

	public double getLampreyAngle(Module module) {
		return getModule(module).getLampreyAngle();
	}
	
	public double getLampreyValue(Module module) {
		return getModule(module).getLampreyValue();
	}

	public double getModuleAngle(Module module) {
		return getModule(module).getMotorAngle();
	}

	public void resetChassisAngle(double angInDeegres) {//todo make with our reset

		pigeonIMU.setYaw(angInDeegres);
	}

	public void resetChassisAngle(){
		pigeonAngleOffset += getChassisAngle();
	}


	public double getChassisAngle() {
		return GBMath.modulo(Math.toRadians(pigeonIMU.getYaw()) - pigeonAngleOffset - blueAllianceOffset, 2 * Math.PI);
	}//todo delete blue offset;

	public double getTarget(Module module) {//todo make more informative name
		return getModule(module).getTargetAngle();

	}

	public void holonomicDrive(ChassisSpeeds speeds) {//todo deprecated?


		SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);


		SwerveModuleState frontRight = moduleStates[0];
		moveSingleModule(Module.FRONT_RIGHT, frontRight);

		SwerveModuleState frontLeft = moduleStates[1];
		moveSingleModule(Module.FRONT_LEFT, frontLeft);

		SwerveModuleState backRight = moduleStates[2];
		moveSingleModule(Module.BACK_RIGHT, backRight);

		SwerveModuleState backLeft = moduleStates[3];
		moveSingleModule(Module.BACK_LEFT, backLeft);
	}

	public void setModuleStates(SwerveModuleState[] states){
		moveSingleModule(Module.FRONT_RIGHT, states[0]);
		moveSingleModule(Module.FRONT_LEFT, states[1]);
		moveSingleModule(Module.BACK_RIGHT, states[2]);
		moveSingleModule(Module.BACK_LEFT, states[3]);
		
		SmartDashboard.putNumber("FR-lin-vel", states[0].speedMetersPerSecond);
		SmartDashboard.putNumber("FL-lin-vel", states[1].speedMetersPerSecond);
		SmartDashboard.putNumber("BR-lin-vel", states[2].speedMetersPerSecond);
		SmartDashboard.putNumber("BL-lin-vel", states[3].speedMetersPerSecond);
	}
	
	public void MoveByChassisSpeeds(double ForwardSpeed, double RightwardSpeed, double AngSpeed, double CurrentAng) {
		ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
				ForwardSpeed,
				-RightwardSpeed,
				AngSpeed,
				Rotation2d.fromDegrees(Math.toDegrees(CurrentAng)));
		SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
		setModuleStates(states);
	}
	
	public SwerveDriveKinematics getKinematics(){
		return this.kinematics;
	}
	public SwerveDriveOdometry getLocalizer (){
		return this.localizer;
	}
	public Pose2d getLocation(){
		return this.localizer.getPoseMeters();
	}
	public void resetLocalizer(){localizer.resetPosition(new Pose2d(),new Rotation2d());}
	
	public PigeonIMU getPigeonIMU() {
		return pigeonIMU;
	}
	public double getAngMotorTicks (Module m){
		return this.getModule(m).getAngMotorTicks();
	}
}

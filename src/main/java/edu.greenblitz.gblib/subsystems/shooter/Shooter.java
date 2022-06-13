package edu.greenblitz.gblib.subsystems.shooter;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.motors.Motor;
import edu.greenblitz.gblib.motors.MotorType;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends GBSubsystem {
	
	private static Shooter instance;
	
	private Motor motor;
	private boolean preparedToShoot;
	private boolean isShooter;
	private static double RPM = 3000;
	
	private Shooter(MotorType motorType, int id, boolean isInverted) {
		this.motor = motorType.generate(id);
		this.motor.setInverted(isInverted);
		this.motor.setCurrentLimit(40);
		this.motor.setIdleMode(AbstractMotor.IdleMode.Coast);
		
//		leader = new GBSparkMax(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);
//
//		leader.setInverted(RobotMap.Pegasus.Shooter.ShooterMotor.LEADER_INVERTED);
//
//		leader.setIdleMode(AbstractMotor.IdleMode.Coast);
//
//		leader.setCurrentLimit(40);
//
//		//leader.setClosedLoopRampRate(1);
//
		preparedToShoot = false;
	}
	
	public static void  create(
			MotorType motorType,
			int port,
			boolean isInverted){
		instance = new Shooter(motorType,port,isInverted);
		
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
	
	public void setRPM(double rpm){
		this.RPM = rpm;
	}
	
//	public void setSpeedByPID(double target) {
////		leader.getPIDController().setReference(target, CANSparkMax.ControlType.kVelocity); //todo fix
//		System.out.println("plz fix me- set speed by PID in shooter");
//	}

//	public void setPIDConsts(PIDObject obj, double iZone) {
//		SparkMaxPIDController controller = leader.getPIDController();
//		controller.setP(obj.getKp());
//		controller.setI(obj.getKi());
//		controller.setD(obj.getKd());
//		controller.setFF(obj.getKf());
//		controller.setIZone(iZone);
//	}

//	public void setPIDConsts(PIDObject obj) {
//		setPIDConsts(obj, 0);
//	}
	
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
	
//	public boolean toggleShooter() {
//		System.out.println(isShooter);
//		isShooter = !isShooter;
//		if (isShooter) {
//			(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM)).schedule();
//		} else {
//			(new StopShooter()).schedule();
//		}
//		return isShooter;
//	}
	
//	public SparkMaxPIDController getPIDController() {
//		return leader.getPIDController();
//	}

}

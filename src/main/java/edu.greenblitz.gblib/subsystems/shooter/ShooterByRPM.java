package edu.greenblitz.gblib.subsystems.shooter;

import edu.greenblitz.gblib.motion.pid.PIDObject;

public class ShooterByRPM extends ShooterCommand {
	private static final double EPSILON = 50;
	protected PIDObject obj;
	protected double iZone;
	//	protected RemoteCSVTarget logger;
	protected double target;
	protected double tStart;
	private int counter;
	
	public ShooterByRPM(PIDObject obj, double target) {
		this.obj = obj;
		this.target = target;
//		this.logger = RemoteCSVTarget.initTarget("FlyWheelVel", "time", "vel");
		this.counter = 0;
	}
	
	
	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
//		shooter.getPIDController().setIAccum(0.0);
		shooter.setPIDConsts(obj);
		tStart = System.currentTimeMillis() / 1000.0;
	}
	
	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		if (Math.abs(target - shooter.getShooterSpeed()) < EPSILON) {
			this.counter++;
			
		} else {
			this.counter = 0;
			shooter.setPreparedToShoot(false);
		}
		if (this.counter >= 5) {
			shooter.setPreparedToShoot(true);
		}
//		logger.report((System.currentTimeMillis()/1000.0 - tStart), shooter.getShooterSpeed());
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {
		
		shooter.setPreparedToShoot(false);
		this.counter = 0;
		super.end(interrupted);
	}
}

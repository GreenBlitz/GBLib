package edu.greenblitz.gblib.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

public class GBSparkMax extends AbstractMotor {
	private CANSparkMax motor;
	private RelativeEncoder encoder;
	
	/**
	 * Constructor:
	 * Defines motor as the SparkMax with the given id;
	 * Sets encoder as the motor's encoder;
	 * Sets gear ratio as given;
	 */
	public GBSparkMax(int id, double gearRatio) {
		this.motor = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.encoder = this.motor.getEncoder();
		setGearRatio(gearRatio);
	}
	
	
	/**
	 * sets gear ratio as 42 for default, if not given otherwise;
	 */
	public GBSparkMax(int id) {
		this(id, 42);
	}
	
	
	@Override
	public void setPower(double power) {
		motor.set(power);
	}
	
	@Override
	public void setInverted(boolean inverted) {
		motor.setInverted(inverted);
	}
	
	@Override
	public boolean getInverted() {
		return motor.getInverted();
	}
	
	@Override
	public double getRawTicks() {
		return encoder.getPosition();
	}
	
	@Override
	public double getRawVelocity() {
		return encoder.getVelocity();
	}
	
	
	@Override
	public void configurePID(double p) {
		motor.getPIDController().setP(p);
	}
	
	@Override
	public void configurePID(double p, double i) {
		motor.getPIDController().setP(p);
		motor.getPIDController().setI(i);
	}
	
	@Override
	public void configurePID(double p, double i, double d) {
		motor.getPIDController().setP(p);
		motor.getPIDController().setI(i);
		motor.getPIDController().setD(d);
	}
	
	@Override
	public void configurePID(double p, double i, double d, double ff) {
		motor.getPIDController().setP(p);
		motor.getPIDController().setI(i);
		motor.getPIDController().setD(d);
		motor.getPIDController().setFF(ff);
	}
	
	@Override
	public void setCurrentLimit(int limit) {
		motor.setSmartCurrentLimit(limit);
	}
	
	@Override
	public void resetEncoder() {
		motor.getEncoder().setPosition(0);
	}
	
	@Override
	public void setIdleMode(IdleMode idleMode) {
		if (idleMode == IdleMode.Brake){motor.setIdleMode(CANSparkMax.IdleMode.kBrake);}
		else {motor.setIdleMode(CANSparkMax.IdleMode.kCoast);}
	}
}

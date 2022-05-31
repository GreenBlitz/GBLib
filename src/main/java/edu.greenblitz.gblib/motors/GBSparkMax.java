package edu.greenblitz.gblib.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

public class GBSparkMax implements Motor {
	private CANSparkMax motor;
	private RelativeEncoder encoder;
	
	/**
	 * sets gear ratio as 42 for default, but can be changed
	 */
	private double gearRatio = 42;
	
	
	/**
	 * Constructor:
	 * Defines motor as the SparkMax with the given id;
	 * Sets encoder as the motor's encoder;
	 */
	public GBSparkMax(int id) {
		this.motor = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.encoder = this.motor.getEncoder();
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
	
	
	/**
	 * Sets gear ratio in ticks per rotation;
	 */
	@Override
	public void setGearRatio(double gearRatio) {
		this.gearRatio = gearRatio;
	}
	
	
	/**
	 * @return position in degrees;
	 */
	@Override
	public double getNormalizedPosition() {
		return (this.getRawTicks()/gearRatio)*360;
	}
	
	
	/**
	 * @return velocity in RPM
	 */
	@Override
	public double getNormalizedVelocity() {
		return (encoder.getVelocity()/gearRatio)*60;
	}
}

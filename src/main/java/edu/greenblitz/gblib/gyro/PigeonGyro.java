package edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.gyro;

import com.ctre.phoenix.sensors.PigeonIMU;

public class PigeonGyro extends AbstractGyro {

	private final PigeonIMU bird;

	public PigeonGyro(PigeonIMU bird) {
		this.bird = bird;
	}

	@Override
	public double getRawYaw() {
//		double[] vals = new double[3];
//		bird.getYawPitchRoll(vals);
//		return Math.toRadians(vals[0]) * inverted;
		return Math.toRadians(bird.getYaw()) * inverted;
	}


	@Override
	public double getYawRate() {
		double[] vals = new double[3];
		bird.getRawGyro(vals);
		return Math.toRadians(vals[0]);
	}

	@Override
	public double getNormalizedYaw() {
		return super.getNormalizedYaw();
	}

	@Override
	public void reset() {
		bird.setYaw(0);
	}

	public PigeonIMU getPigeon() {
		return bird;
	}
}

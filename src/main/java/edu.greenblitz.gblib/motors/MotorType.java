package edu.greenblitz.gblib.motors;

/**
 * t'is but a factory pattern to make a motor using an enum
 */
public enum MotorType implements IMotorFactory {
	GBFalcon {
		@Override
		public GBMotor generate(int id) {
			return new GBFalcon(id);
		}
	},
	GBSparkMax {
		@Override
		public GBMotor generate(int id) {
			return new GBSparkMax(id);
		}
	}
}


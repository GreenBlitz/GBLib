package edu.greenblitz.gblib.gear;

/**
 * This class represents the available gear states
 *
 * @author Nitzan
 * @author Raz
 */

public enum GearState {
	POWER,
	SPEED;

	public boolean isSpeed() {
		return this == SPEED;
	}

	public boolean isPower() {
		return this == POWER;
	}
}

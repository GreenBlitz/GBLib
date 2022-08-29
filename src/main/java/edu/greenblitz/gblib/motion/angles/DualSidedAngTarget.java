package edu.greenblitz.gblib.motion.angles;

import edu.greenblitz.gblib.utils.GBMath;

public class DualSidedAngTarget {
	private double target;
	private double start;
	private int direction;

	private DualSidedAngTarget(double target, double start, int direction) {
		this.target = target;
		this.start = start;
		this.direction = direction;
	}

	public static DualSidedAngTarget getTarget(double target, double head) {
		double tail = head + 0.5;

		DualSidedAngTarget head_target = chooseAngTarget(head, target);
		DualSidedAngTarget tail_target = chooseAngTarget(tail, target);
		tail_target.flip();
		if (head_target.getError() < tail_target.getError()) {
			return (head_target);
		} else {
			return (tail_target);
		}
	}

	private static double chooseAngTargetBin(double curr, double target1, double target2) {
		if (Math.abs(target1 - curr) > Math.abs(target2 - curr)) {
			return target2;
		} else {
			return target1;
		}
	}

	private static DualSidedAngTarget chooseAngTarget(double curr, double target) {
		return new DualSidedAngTarget(chooseAngTargetBin(curr, chooseAngTargetBin(curr, target, target + 1),
				target - 1), curr, 1);
	}

	//test
	public static void main(String[] args) {
		System.out.println(DualSidedAngTarget.getTarget(Double.parseDouble(args[0]), Double.parseDouble(args[1])));
	}

	public double getTarget() {
		return target;
	}

	private double getStart() {
		return start;
	}

	public int getDirection() {
		return direction;
	}

	public void flip() {
		direction = -direction;
		start = GBMath.modulo((start + 0.5), 1);
		target = GBMath.modulo((target + 0.5), 1);
	}

	public double getError() {
		return Math.max(GBMath.modulo((target - start), 1), GBMath.modulo((start - target), 1));
	}

	@Override
	public String toString() {
		return "{" +
				"\"target\":" + target +
				", \"start\":" + start +
				", \"direction\":" + direction +
				'}';
	}
}

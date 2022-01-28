package edu.greenblitz.gblib.hid.virtualHid;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.hal.FRCNetComm;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import java.util.HashMap;

public class VirtualJoystick extends SmartJoystick {

	private HashMap<Double, HashMap<Integer, Double>> myFollowDriverData;
	private GenericHID ourHID;
	private double startTime;
	private static final HashMap<Integer, Double> zeroHashMap = new HashMap<>();

	//we need the zero map for cases that we had problems finding buttons data in the follow driver record;
	//in such cases we don't want the robot to do any thing at all;
	static {
		zeroHashMap.put(1, 0.0);
		zeroHashMap.put(2, 0.0);
		zeroHashMap.put(3, 0.0);
		zeroHashMap.put(4, 0.0);
		zeroHashMap.put(5, 0.0);
		zeroHashMap.put(6, 0.0);
		zeroHashMap.put(7, 0.0);
		zeroHashMap.put(8, 0.0);
		zeroHashMap.put(9, 0.0);
		zeroHashMap.put(10, 0.0);
		zeroHashMap.put(11, 0.0);
		zeroHashMap.put(12, 0.0);
		zeroHashMap.put(13, 0.0);
		zeroHashMap.put(14, 0.0);
		zeroHashMap.put(15, 0.0);
		zeroHashMap.put(16, 0.0);
		zeroHashMap.put(17, 0.0);
		zeroHashMap.put(18, 0.0);
		zeroHashMap.put(19, 0.0);
		zeroHashMap.put(20, 0.0);
	}


	public VirtualJoystick(HashMap<Double, HashMap<Integer, Double>> myFollowDriverData) {
		super(-1); //no need for port
		if (myFollowDriverData == null) this.myFollowDriverData = new HashMap<>();
		this.myFollowDriverData = myFollowDriverData;
		startTime = System.currentTimeMillis() / 1000.0;
	}

	static final byte kDefaultXChannel = 0;
	static final byte kDefaultYChannel = 1;
	static final byte kDefaultZChannel = 2;
	static final byte kDefaultTwistChannel = 2;
	static final byte kDefaultThrottleChannel = 3;

	public enum AxisType {
		kX(0), kY(1), kZ(2), kTwist(3), kThrottle(4);

		@SuppressWarnings("MemberName")
		public final int value;

		AxisType(int value) {
			this.value = value;
		}
	}

	public enum ButtonType {
		kTrigger(1), kTop(2);

		@SuppressWarnings("MemberName")
		public final int value;

		ButtonType(int value) {
			this.value = value;
		}
	}

	private enum Button {
		kTrigger(1), kTop(2);

		@SuppressWarnings("MemberName")
		public final int value;

		Button(int value) {
			this.value = value;
		}
	}

	private enum Axis {
		kX(0), kY(1), kZ(2), kTwist(3), kThrottle(4), kNumAxes(5);

		@SuppressWarnings("MemberName")
		public final int value;

		Axis(int value) {
			this.value = value;
		}
	}

	private final byte[] m_axes = new byte[Axis.kNumAxes.value];

	public VirtualJoystick(final int port) {
		super(-1);//no port needed!!!

		m_axes[Axis.kX.value] = kDefaultXChannel;
		m_axes[Axis.kY.value] = kDefaultYChannel;
		m_axes[Axis.kZ.value] = kDefaultZChannel;
		m_axes[Axis.kTwist.value] = kDefaultTwistChannel;
		m_axes[Axis.kThrottle.value] = kDefaultThrottleChannel;

		HAL.report(FRCNetComm.tResourceType.kResourceType_Joystick, port + 1);
	}

	public void setXChannel(int channel) {
		m_axes[Axis.kX.value] = (byte) channel;
	}

	public void setYChannel(int channel) {
		m_axes[Axis.kY.value] = (byte) channel;
	}

	public void setZChannel(int channel) {
		m_axes[Axis.kZ.value] = (byte) channel;
	}

	public void setThrottleChannel(int channel) {
		m_axes[Axis.kThrottle.value] = (byte) channel;
	}

	public void setTwistChannel(int channel) {
		m_axes[Axis.kTwist.value] = (byte) channel;
	}

	public int getXChannel() {
		return m_axes[Axis.kX.value];
	}

	public int getYChannel() {
		return m_axes[Axis.kY.value];
	}

	public int getZChannel() {
		return m_axes[Axis.kZ.value];
	}

	public int getTwistChannel() {
		return m_axes[Axis.kTwist.value];
	}

	public int getThrottleChannel() {
		return m_axes[Axis.kThrottle.value];
	}

    /*public final double getX(Hand hand) {
        return getRawAxis(m_axes[Axis.kX.value]);
    }

    public final double getY(Hand hand) {
        return getRawAxis(m_axes[Axis.kY.value]);
    }

    public double getZ() {
        return getRawAxis(m_axes[Axis.kZ.value]);
    }*/

	public double getTwist() {
		return getRawAxis(m_axes[Axis.kTwist.value]);
	}

	public double getThrottle() {
		return getRawAxis(m_axes[Axis.kThrottle.value]);
	}

	public boolean getTrigger() {
		return getRawButton(Button.kTrigger.value);
	}

	public boolean getTriggerPressed() {
		return m_joystick.getRawButtonPressed(Button.kTrigger.value);
	}

	public boolean getTriggerReleased() {
		return m_joystick.getRawButtonReleased(Button.kTrigger.value);
	}

	public boolean getTop() {
		return getRawButton(Button.kTop.value);
	}

	public boolean getTopPressed() {
		return m_joystick.getRawButtonPressed(Button.kTop.value);
	}

	public boolean getTopReleased() {
		return m_joystick.getRawButtonReleased(Button.kTop.value);
	}

	public double getMagnitude() {
		return Math.sqrt(Math.pow(m_joystick.getX(), 2) + Math.pow(m_joystick.getY(), 2));
	}

	public double getDirectionRadians() {
		return Math.atan2(m_joystick.getX(), -m_joystick.getY());
	}

	public double getDirectionDegrees() {
		return Math.toDegrees(getDirectionRadians());
	}

	/**
	 * find the closest time in the record
	 *
	 * @return
	 */
	private HashMap<Integer, Double> getCurButtons() {
		double thisTime = System.currentTimeMillis() / 1000.0;
		double timeFromStart = thisTime - this.startTime;
		double closestTime = Double.POSITIVE_INFINITY;
		double biggestTime = 0.0;
		for (Double d : myFollowDriverData.keySet()) {
			if (Math.abs(timeFromStart - d) < Math.abs(timeFromStart - closestTime)) {
				closestTime = d;
			}
		}
		if (Math.abs(timeFromStart - closestTime) < 0.02) {
			System.out.println("for some reason the diff in times is too big, we ask the robot to do nothing");
			System.out.println("(returns - zeroRecord - no button is pressed) from getCurButtons");
			return zeroHashMap;
		} else if (closestTime == Double.POSITIVE_INFINITY) {
			System.out.println("bro, something went wrong, probably no recording (returns - null) from getCurButtons");
			return zeroHashMap;
		} else {
			return myFollowDriverData.get(closestTime);
		}
	}

	public boolean getRawButton(int button) {
		return (getCurButtons().get(button) == 1.0);
	}

	public int getPOV(int pov) {
        /*  11, POV_UP
            12, POV_RIGHT
            13, POV_DOWN
            14, POV_LEFT    */
		int sumX = 0;
		int sumY = 0;
		if (getCurButtons().get(11) == 1.0) sumY++;
		if (getCurButtons().get(12) == 1.0) sumX++;
		if (getCurButtons().get(13) == 1.0) sumX--;
		if (getCurButtons().get(12) == 1.0) sumY--;

		if (sumX == 1 && sumY == 0) return 0;
		if (sumX == 1 && sumY == 1) return 45;
		if (sumX == 0 && sumY == 1) return 90;
		if (sumX == -1 && sumY == 1) return 135;
		if (sumX == -1 && sumY == 0) return 180;
		if (sumX == -1 && sumY == -1) return 225;
		if (sumX == 0 && sumY == -1) return 270;
		if (sumX == 1 && sumY == -1) return 315;
		else /*(sumX == 0 && sumY == 0)*/ return -1;
	}

    /*
    @Override
    public double getX(Hand hand) {
        if (hand == Hand.kRight) {
            return getCurButtons().get(19);
        } else {
            return getCurButtons().get(15);
        }
    }

    @Override
    public double getY(Hand hand) {
        if (hand == Hand.kRight) {
            return getCurButtons().get(20);
        } else {
            return getCurButtons().get(16);
        }
    }*/

	@Override
	public double getRawAxis(int axis) {
		return getCurButtons().get(15 + axis);
	}
}


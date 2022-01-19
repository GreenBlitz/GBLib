package edu.greenblitz.gblib.hid.virtualHid;

import edu.wpi.first.wpilibj.GenericHID;

import java.util.HashMap;

public class VirtualHID extends GenericHID {
    public VirtualHID(int port) {
        super(port);
    }

    private double startTime;//todo: use this!!!!
    private HashMap<Double, HashMap<Integer, Double>> followDriverData;
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

    public VirtualHID(HashMap<Double, HashMap<Integer, Double>> followDriverData) {
        super(-1);//i think we should use a port that does not exist
        this.followDriverData = followDriverData;
        startTime = System.currentTimeMillis() / 1000.0;
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
        for (Double d : this.followDriverData.keySet()) {
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
            return this.followDriverData.get(closestTime);
        }
    }

    @Override
    public boolean getRawButton(int button) {
        return (getCurButtons().get(button) == 1.0);
    }

    @Override
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
    }

    @Override
    public double getRawAxis(int axis) {
        return getCurButtons().get(15+axis);
    }
}

package edu.greenblitz.gblib.encoder;

import edu.greenblitz.gblib.gear.Gear;

import java.util.function.DoubleSupplier;

public abstract class AbstractEncoder implements IEncoder{
    private boolean inverted;
    private int accumulatedTicks;
    private double accumulatedDistance;
    private DoubleSupplier normalizeConst;

    /**
     *
     * @return velocity after conversion in m/s
     */
    double getNormalizedVelocity() {
        return getTickRate() * invert() / normalizeConst.getAsDouble();
    }

    int invert() {
        return isInverted() ? -1 : 1;
    }

    public AbstractEncoder() {
        accumulatedTicks = 0;
        accumulatedDistance = 0;
    }

    /**
     * This constructor receives the normalize constant of the motor controller.
     */
    public AbstractEncoder(DoubleSupplier normalizeConst){
        this();
        this.normalizeConst = normalizeConst;
    }

    public void switchGear(){
        accumulatedDistance = getNormalizedTicks();
        accumulatedTicks = getRawTicks();
    }

    public void setNormalizeConst(DoubleSupplier normalizeConst) {
        this.normalizeConst = normalizeConst;
    }

    public double getNormalizedTicks() {
        return ((getRawTicks() - accumulatedTicks) * invert() / normalizeConst.getAsDouble()) + accumulatedDistance;
    }

    public void invert(boolean inverted) {
        inverted = inverted;
    }

    public boolean isInverted() {
        return inverted;
    }
}

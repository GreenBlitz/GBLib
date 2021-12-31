package edu.greenblitz.gblib.encoder;

import edu.greenblitz.gblib.gear.Gear;

public abstract class AbstractEncoder implements IEncoder{
    private boolean inverted;
    private int accumulatedTicks;
    private double accumulatedDistance;
    private Gear gear;

    /**
     *
     * @return velocity after conversion in m/s
     */
    double getNormalizedVelocity() {
        return getTickRate() * invert() / Gear.getInstance().getValue();
    }

    int invert() {
        return isInverted() ? -1 : 1;
    }

    /**
     * This constructor receives the normalize constant of the motor controller.
     * It also checks to see if the constant valid as well.
     */
    public AbstractEncoder() {
        this.gear = Gear.getInstance();
        if (gear.getValue() == 0.0 || !Double.isFinite(gear.getValue()))
            throw new IllegalArgumentException("invalid ticks per meter value '" + gear.getValue() + "'");
        if (gear.getInverseValue() == 0.0 || !Double.isFinite(gear.getInverseValue()))
            throw new IllegalArgumentException("invalid ticks per meter value '" + gear.getInverseValue() + "'");

        accumulatedTicks = 0;
        accumulatedDistance = 0;
    }

    public void switchGear(){
        accumulatedDistance = getNormalizedTicks();
        accumulatedTicks = getRawTicks();
    }

    public void setNormalizeConst(double value) {
        gear.setValue(gear.getState(), value);
    }

    public double getNormalizedTicks() {
        return ((getRawTicks() - accumulatedTicks) * invert() / gear.getValue()) + accumulatedDistance;
    }

    public void invert(boolean inverted) {
        inverted = inverted;
    }

    public boolean isInverted() {
        return inverted;
    }
}

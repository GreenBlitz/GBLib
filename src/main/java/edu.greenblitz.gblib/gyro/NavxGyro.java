package edu.greenblitz.gblib.gyro;

import com.kauailabs.navx.frc.AHRS;

public class NavxGyro extends AbstractGyro {

    private AHRS navx;

    public NavxGyro(AHRS navx){
        this.navx = navx;
    }

    @Override
    public double getRawYaw() {
        return inverted * Math.toRadians(navx.getAngle());
    }


    @Override
    public double getYawRate() {
        return Math.toRadians(navx.getRate());
    }

    @Override
    public void reset() {
        navx.reset();
    }

    public AHRS getNavx(){
        return navx;
    }
}

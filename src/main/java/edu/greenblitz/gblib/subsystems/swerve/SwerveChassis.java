package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class SwerveChassis extends GBSubsystem {

    private final SwerveModule frontRight, frontLeft, backRight, backLeft;
    private final PigeonGyro pigeonGyro;

    private enum Module {
        FRONT_RIGHT,
        FRONT_LEFT,
        BACK_RIGHT,
        BACK_LEFT
    }

    public SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro pigeonGyro) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.pigeonGyro = pigeonGyro;
    }
}

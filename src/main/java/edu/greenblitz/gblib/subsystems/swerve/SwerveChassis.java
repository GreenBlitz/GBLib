package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;

public class SwerveChassis extends GBSubsystem {

    private final SwerveModule frontRight, frontLeft, backRight, backLeft;
    private final PigeonGyro gyro;

    private enum Module{
        FRONTRIGHT,
        FRONTLEFT,
        BACKRIGHT,
        BACKLEFT
    }

    public SwerveChassis(SwerveModule frontRight, SwerveModule frontLeft, SwerveModule backRight, SwerveModule backLeft, PigeonGyro gyro){
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.gyro = gyro;
    }
}

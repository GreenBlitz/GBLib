package edu.greenblitz.gblib.subsystems.swerve;

import edu.greenblitz.gblib.motion.angles.DualSidedAngTarget;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;
import edu.greenblitz.gblib.motors.brushed.IBrushedFactory;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveModule {


    private int isReversed = 1;
    private static final int lampreyTicksPerRotation = 4076; //not my fault it's an ugly number
    public double targetAngle;
    public double targetVel;
	private  double maxLampreyVal;
	private double minLampreyVal;
    private final GBMotor angleMotor;
    private final GBBrushedMotor linearMotor;
    private final AnalogInput lamprey;

    public SwerveModule(IMotorFactory motorFactoryA, IBrushedFactory motorFactoryL, int portA, int portL, int lampreyID, double maxLampreyVal, double minLampreyVal) {
        angleMotor = motorFactoryA.generate(portA);
        linearMotor = motorFactoryL.generate(portL);
        lamprey = new AnalogInput(lampreyID);
        lamprey.setAverageBits(2);
		this.maxLampreyVal = maxLampreyVal;
		this.minLampreyVal = minLampreyVal;
    }


    public void setVelocity(double speed) {
        speed *= isReversed;
        linearMotor.setPower(speed);
        targetVel = speed;
    }

    public double getLampreyAngle() { // in rotations;
        return (lamprey.getValue()- minLampreyVal)/(maxLampreyVal-minLampreyVal);
    }

    public void rotateToAngle(double angle) {
		if (Double.isNaN(angle)) return;
        DualSidedAngTarget dualSidedAngTarget = DualSidedAngTarget.generateTarget(angle, getMotorAngle());
        angle = dualSidedAngTarget.getTarget();
        isReversed = dualSidedAngTarget.getDirection();
        angleMotor.setTargetByPID(angle, AbstractMotor.PIDTarget.Position);
        targetAngle = angle;
    }

	public double getRawLampreyAngle(){
		return lamprey.getValue();
	}



	public double getMotorAngle() {
		return angleMotor.getNormalizedPosition();
	}
//	public double getCurrentVel() {
//		return linMotor.getNormalizedVelocity();
//	}  todo make work

    public void rotateByAngle(double angle) {
		if (Double.isNaN(angle)) return;
        targetAngle = getMotorAngle() + angle;
        angleMotor.setTargetByPID(targetAngle, AbstractMotor.PIDTarget.Position);
    }

    public void resetAngle() {
        angleMotor.resetEncoder();
    }

	public void restEncoderByLamprey(){
		angleMotor.setEncoderAng(getLampreyAngle());
	}

//	public void configLinPID(PIDObject pidObject) {
//		linMotor.configurePID(pidObject);
//	} todo make work

    public void configAnglePID(PIDObject pidObject) {
        angleMotor.configurePID(pidObject);
    }

    public void setLinPower(double power) {
        linearMotor.setPower(power);
    }

	public void setRotpower(double power){
		angleMotor.setPower(power);
	}


    public double getTargetAngle() {
        return targetAngle;
    }

    public double getTargetVel() {
        return targetVel;
    } //udi: *isReversed

    public int getIsReversed() {
        return isReversed;
    }

}

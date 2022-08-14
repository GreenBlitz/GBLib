package edu.greenblitz.gblib.motors.TalonSRX;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.AbstractMotor.*;
import edu.greenblitz.gblib.motors.GBBrushedMotor;

public class GBTalonSRX implements GBBrushedMotor {

    private final TalonSRX motor;
    private IdleMode idleMode = IdleMode.Brake;
    private int isInverted = 1;

    public GBTalonSRX(int id) {
        this.motor = new TalonSRX(id);
    }

    /**
     * sets motor power from -1 to 1;
     *
     * @param power
     */
    @Override
    public void setPower(double power) {
        motor.set(ControlMode.PercentOutput, power * isInverted);
    }

    /**
     * @return true if the motors are inverted;
     */
    @Override
    public boolean getInverted() {
        return (motor.getInverted()); /*getInverted is also a talon method*/
    }

    @Override
    public void setInverted(boolean inverted) {
        if (inverted) {
            isInverted = -1;
        } else {
            isInverted = 1;
        }
    }

    @Override
    public IdleMode getIdleMode() {
        return idleMode;
    }

    @Override
    public void setIdleMode(IdleMode idleMode) {
        this.idleMode = idleMode;
        if (idleMode == IdleMode.Brake) {
            motor.setNeutralMode(NeutralMode.Brake);
        } else {
            motor.setNeutralMode(NeutralMode.Coast);
        }

    }

    public void setCurrentLimit(int limit) {
        motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(limit != 0, limit, 0.875 * limit, 100));
    }

    public void setVoltageCompensation(double voltage) {
        motor.enableVoltageCompensation(voltage != 0);
        motor.configVoltageCompSaturation(voltage);
    }

    public void setRampRate(double rampRate) {
        motor.configOpenloopRamp(rampRate);
    }
}

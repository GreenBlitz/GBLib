package edu.greenblitz.gblib.motors.TalonSRX;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.AbstractMotor;

public class GBTalonSRX extends AbstractMotor {

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

    /**
     * @return raw encoder ticks;
     */
    @Override
    public double getRawTicks() {
        return motor.getSelectedSensorPosition();
    }

    /**
     * @return raw velocity in ticks per second
     */
    @Override
    public double getRawVelocity() {
        return motor.getSelectedSensorVelocity();
    }

    @Override
    public void configurePID(PIDObject pidObject) {
        motor.config_kP(0, pidObject.getKp());
        motor.config_kI(0, pidObject.getKi());
        motor.config_kD(0, pidObject.getKd());
        motor.config_kF(0, pidObject.getKf());
        motor.config_IntegralZone(0, pidObject.getIZone());
    }

    @Override
    public void setTargetByPID(double target, PIDTarget targetType) {
        switch (targetType) {
            case Speed:
                motor.set(TalonSRXControlMode.Velocity, target);
                break;
            case Current:
                motor.set(TalonSRXControlMode.Current, target);
                break;
            case Position:
                motor.set(TalonSRXControlMode.Position, target);
                break;
        }
    }

    @Override
    public void setTargetSpeedByPID(double target) {
        motor.set(TalonSRXControlMode.Velocity, target);
    }

    @Override
    public void resetEncoder() {
        motor.setSelectedSensorPosition(0);
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

    @Override
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

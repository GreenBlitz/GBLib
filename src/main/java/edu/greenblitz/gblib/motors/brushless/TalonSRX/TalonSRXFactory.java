package edu.greenblitz.gblib.motors.brushless.TalonSRX;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.gblib.motors.brushless.TalonFX.GBTalonFX;
import edu.greenblitz.gblib.motors.brushless.TalonFX.TalonFXFactory;

public class TalonSRXFactory implements IMotorFactory {

    private int currentLimit = 0;
    private double voltageCompensation = 0;
    private double rampRate = 0;
    private boolean inverted = false;
    private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;
    private double gearRatio = -1; //todo find real ratio asaf

    @Override
    public GBMotor generate(int id) {
        GBTalonFX motor = new GBTalonFX(id);
        motor.setCurrentLimit(currentLimit);
        motor.setVoltageCompensation(voltageCompensation);
        motor.setRampRate(rampRate);
        motor.setInverted(inverted);
        motor.setIdleMode(idleMode);
        return motor;
    }

    public TalonSRXFactory withCurrentLimit(int currentLimit) {
        this.currentLimit = currentLimit;
        return this;
    }

    public TalonSRXFactory withVoltageCompensation(double voltageCompensation) {
        this.voltageCompensation = voltageCompensation;
        return this;
    }

    public TalonSRXFactory withRampRate(double rampRate) {
        this.rampRate = rampRate;
        return this;
    }

    public TalonSRXFactory withInverted(boolean inverted) {
        this.inverted = inverted;
        return this;
    }

    public TalonSRXFactory withIdleMode(AbstractMotor.IdleMode idleMode) {
        this.idleMode = idleMode;
        return this;
    }

    public TalonSRXFactory withGearRatio(double gearRatio) {
        this.gearRatio = gearRatio;
        return this;
    }
}



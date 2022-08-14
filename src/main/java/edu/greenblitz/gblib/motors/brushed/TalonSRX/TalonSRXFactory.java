package edu.greenblitz.gblib.motors.brushed.TalonSRX;

import edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;
import edu.greenblitz.gblib.motors.brushed.IBrushedFactory;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.IMotorFactory;

public class TalonSRXFactory implements IBrushedFactory {

    private int currentLimit = 0;
    private double voltageCompensation = 0;
    private double rampRate = 0;
    private boolean inverted = false;
    private AbstractMotor.IdleMode idleMode = AbstractMotor.IdleMode.Brake;

    @Override
    public GBBrushedMotor generate(int id) {
        GBTalonSRX motor = new GBTalonSRX(id);
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

}




//package edu.greenblitz.gblib.encoder;
//
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//
//import java.util.function.DoubleSupplier;
//
//public class TalonEncoder extends AbstractEncoder {
//    private TalonSRX m_talon;
//
//    public TalonEncoder(DoubleSupplier normConst, TalonSRX talon) {
//		super(normConst);
//        m_talon = talon;
//    }
//
//    @Override
//    public void reset() {
//        m_talon.setSelectedSensorPosition(0);
//    }
//
//    @Override
//    public double getRawTicks() {
//        return m_talon.getSelectedSensorPosition();
//    }
//
//    @Override
//    public double getTickRate() {
//        return m_talon.getSelectedSensorVelocity() * 10;
//    }
//
//}

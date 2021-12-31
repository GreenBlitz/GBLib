package edu.greenblitz.gblib.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class SparkMaxEncoder extends AbstractEncoder {
    private static final int SPARK_COUNT_RATIO = 42;
    private CANEncoder m_sparkEncoder;
    private int m_nullPosition;

    public SparkMaxEncoder(CANSparkMax sparkEncoder) {
        m_sparkEncoder = sparkEncoder.getEncoder();
        m_nullPosition = 0;
    }

    @Override
    public void reset() {
        m_nullPosition = get();
    }

    /**
     * OK here me out on this, the spark returns the ticks in multiples of 0.023809523809523808 (1/42),
     * which is obviously a double, and to match AbstractEncoder documentation, we have to multiply :(
     * @return SparkMax encoder's raw tick count
     */
    private int get() {
        return (int) Math.round(m_sparkEncoder.getPosition() * SPARK_COUNT_RATIO);
    }

    @Override
    public int getRawTicks() {
        return get() - m_nullPosition;
    }

    @Override
    public double getTickRate() {
        return m_sparkEncoder.getVelocity() * SPARK_COUNT_RATIO / 60.0;
    }
}
package edu.greenblitz.gblib.encoder;

import edu.wpi.first.wpilibj.Encoder;

public class RoborioEncoder extends AbstractEncoder {
    private Encoder m_encoder;

    public RoborioEncoder(Encoder encoder) {
        super();
        m_encoder = encoder;
    }

    public RoborioEncoder(int channelA, int channelB) {
        this(new Encoder(channelA, channelB));
    }

    @Override
    public void reset() {
        m_encoder.reset();
    }

    @Override
    public double getRawTicks() {
        return m_encoder.getRaw();
    }

    @Override
    public double getTickRate() {
        return m_encoder.getRate();
    }
}

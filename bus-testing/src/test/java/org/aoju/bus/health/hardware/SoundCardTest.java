package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.SoundCard;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test SoundCard
 */
public class SoundCardTest {

    /**
     * Testing sound cards , each attribute.
     */
    @Test
    public void testSoundCards() {
        for (SoundCard soundCard : Builder.getHardware().getSoundCards()) {
            assertNotNull(soundCard.getCodec());
            assertNotNull(soundCard.getDriverVersion());
            assertNotNull(soundCard.getName());
        }
    }

}

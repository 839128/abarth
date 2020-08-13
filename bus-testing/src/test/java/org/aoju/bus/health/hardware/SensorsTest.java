package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.Sensors;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test Sensors
 */
public class SensorsTest {

    private final Sensors s = Builder.getHardware().getSensors();

    /**
     * Test sensors
     */
    @Test
    public void testSensors() {
        assertTrue(s.getCpuTemperature() >= 0d && s.getCpuTemperature() <= 100d);
        assertTrue(s.getCpuVoltage() >= 0);
    }

    @Test
    public void testFanSpeeds() {
        int[] speeds = s.getFanSpeeds();
        for (int speed : speeds) {
            assertTrue(speed >= 0);
        }
    }

}

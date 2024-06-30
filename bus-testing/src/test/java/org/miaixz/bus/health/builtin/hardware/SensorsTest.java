package org.miaixz.bus.health.builtin.hardware;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Sensors
 */
class SensorsTest {
    private Platform si = new Platform();
    private Sensors s = si.getHardware().getSensors();

    /**
     * Test sensors
     */
    @Test
    void testSensors() {
        assertThat("CPU Temperature should be NaN or between 0 and 100", s.getCpuTemperature(),
                either(notANumber()).or(both(greaterThanOrEqualTo(0d)).and(lessThanOrEqualTo(100d))));
        assertThat("CPU voltage shouldn't be negative", s.getCpuVoltage(), is(greaterThanOrEqualTo(0d)));
    }

    @Test
    void testFanSpeeds() {
        int[] speeds = s.getFanSpeeds();
        for (int speed : speeds) {
            assertThat("Fan Speed shouldn't be negative", speed, is(greaterThanOrEqualTo(0)));
        }
    }

}

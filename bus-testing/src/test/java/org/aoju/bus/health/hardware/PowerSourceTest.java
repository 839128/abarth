package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.PowerSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test Power Source
 */
public class PowerSourceTest {

    /**
     * Test power source.
     */
    @Test
    public void testPowerSource() {
        List<PowerSource> psArr = Builder.getHardware().getPowerSources();
        for (PowerSource ps : psArr) {
            assertTrue(ps.getRemainingCapacityPercent() >= 0);
            double epsilon = 1E-6;
            assertTrue(ps.getTimeRemainingEstimated() > 0 || Math.abs(ps.getTimeRemainingEstimated() - -1) < epsilon
                    || Math.abs(ps.getTimeRemainingEstimated() - -2) < epsilon);
        }
    }

}

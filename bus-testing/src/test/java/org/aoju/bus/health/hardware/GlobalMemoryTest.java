package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.GlobalMemory;
import org.aoju.bus.health.builtin.hardware.HardwareAbstractionLayer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test GlobalMemory
 */
public class GlobalMemoryTest {
    /**
     * Test GlobalMemory.
     */
    @Test
    public void testGlobalMemory() {
        HardwareAbstractionLayer hal = Builder.getHardware();
        GlobalMemory memory = hal.getMemory();
        assertNotNull(memory);

        assertTrue(memory.getTotal() > 0);
        assertTrue(memory.getAvailable() >= 0);
        assertTrue(memory.getAvailable() <= memory.getTotal());
        assertTrue(memory.getPageSize() > 0);
        assertTrue(memory.toString().contains("Available"));
    }

}

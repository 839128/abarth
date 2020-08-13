package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.GlobalMemory;
import org.aoju.bus.health.builtin.hardware.HardwareAbstractionLayer;
import org.aoju.bus.health.builtin.hardware.VirtualMemory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test GlobalMemory
 */
public class VirtualMemoryTest {

    /**
     * Test VirtualMemory.
     */
    @Test
    public void testGlobalMemory() {
        HardwareAbstractionLayer hal = Builder.getHardware();
        GlobalMemory memory = hal.getMemory();
        VirtualMemory vm = memory.getVirtualMemory();
        assertNotNull(vm);

        // Swap tests
        assertTrue(vm.getSwapPagesIn() >= 0);
        assertTrue(vm.getSwapPagesOut() >= 0);
        assertTrue(vm.getSwapTotal() >= 0);
        assertTrue(vm.getSwapUsed() >= 0);
        assertTrue(vm.getSwapUsed() <= vm.getSwapTotal());
        assertTrue(vm.getVirtualMax() >= vm.getSwapTotal());
        assertTrue(vm.getVirtualInUse() >= 0);
        assertTrue(vm.toString().contains("Used"));
    }

}

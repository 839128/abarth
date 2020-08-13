package org.aoju.bus.health.hardware;

import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.CentralProcessor;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test CPU
 */
public class CentralProcessorTest {

    /**
     * Test central processor.
     */
    @Test
    public void testCentralProcessor() {
        CentralProcessor p = Builder.getHardware().getProcessor();

        CentralProcessor.ProcessorIdentifier pi = p.getProcessorIdentifier();
        assertNotNull(pi.getVendor());
        assertTrue(pi.getVendorFreq() == -1 || pi.getVendorFreq() > 0);
        assertNotNull(pi.getName());
        assertNotNull(pi.getIdentifier());
        assertNotNull(pi.getProcessorID());
        assertNotNull(pi.getStepping());
        assertNotNull(pi.getModel());
        assertNotNull(pi.getFamily());
        assertFalse(StringKit.isBlank(pi.getMicroarchitecture()));

        long[] ticks = p.getSystemCpuLoadTicks();
        long[][] procTicks = p.getProcessorCpuLoadTicks();
        assertEquals(ticks.length, CentralProcessor.TickType.values().length);

        Builder.sleep(500);

        assertTrue(p.getSystemCpuLoadBetweenTicks(ticks) >= 0 && p.getSystemCpuLoadBetweenTicks(ticks) <= 1);
        assertEquals(3, p.getSystemLoadAverage(3).length);

        assertEquals(p.getProcessorCpuLoadBetweenTicks(procTicks).length, p.getLogicalProcessorCount());
        for (int cpu = 0; cpu < p.getLogicalProcessorCount(); cpu++) {
            assertTrue(p.getProcessorCpuLoadBetweenTicks(procTicks)[cpu] >= 0
                    && p.getProcessorCpuLoadBetweenTicks(procTicks)[cpu] <= 1);
            assertEquals(p.getProcessorCpuLoadTicks()[cpu].length, CentralProcessor.TickType.values().length);
        }

        assertTrue(p.getLogicalProcessorCount() >= p.getPhysicalProcessorCount());
        assertTrue(p.getPhysicalProcessorCount() > 0);
        assertTrue(p.getPhysicalProcessorCount() >= p.getPhysicalPackageCount());
        assertTrue(p.getPhysicalPackageCount() > 0);
        assertTrue(p.getContextSwitches() >= 0);
        assertTrue(p.getInterrupts() >= 0);

        long max = p.getMaxFreq();
        long[] curr = p.getCurrentFreq();
        assertEquals(curr.length, p.getLogicalProcessorCount());
        if (max >= 0) {
            for (int i = 0; i < curr.length; i++) {
                assertTrue(curr[i] <= max);
            }
        }
    }

}

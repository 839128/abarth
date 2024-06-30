package org.miaixz.bus.health.linux.driver.proc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.Platform;
import org.miaixz.bus.health.builtin.hardware.CentralProcessor;
import org.miaixz.bus.health.builtin.hardware.HardwareAbstractionLayer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@EnabledOnOs(OS.LINUX)
class CpuStatTest {

    @Test
    void testSystemCpuLoadTicks() {
        long[] systemCpuLoadTicks = CpuStat.getSystemCpuLoadTicks();
        for (long systemCpuTick : systemCpuLoadTicks) {
            assertThat("CPU tick should be greater than or equal to 0", systemCpuTick, greaterThanOrEqualTo(0L));
        }
    }

    @Test
    void testGetProcessorCpuLoadTicks() {
        Platform si = new Platform();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        int logicalProcessorCount = processor.getLogicalProcessorCount();
        long[][] processorCpuLoadTicks = CpuStat.getProcessorCpuLoadTicks(logicalProcessorCount);
        for (long[] cpuTicks : processorCpuLoadTicks) {
            for (long cpuTick : cpuTicks) {
                assertThat("CPU tick should be greater than or equal to 0", cpuTick, greaterThanOrEqualTo(0L));
            }
        }
    }

    @Test
    void testGetContextSwitches() {
        assertThat("Context switches should be greater than or equal to -1", CpuStat.getContextSwitches(),
                greaterThanOrEqualTo(-1L));
    }

    @Test
    void testGetInterrupts() {
        assertThat("Interrupts should be greater than or equal to -1", CpuStat.getInterrupts(),
                greaterThanOrEqualTo(-1L));
    }

    @Test
    void testGetBootTime() {
        assertThat("Boot time should be greater than or equal to 0", CpuStat.getBootTime(), greaterThanOrEqualTo(0L));
    }

}

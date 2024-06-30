package org.miaixz.bus.health.windows.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Triplet;
import org.miaixz.bus.health.builtin.hardware.CentralProcessor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@EnabledOnOs(OS.WINDOWS)
class LogicalProcessorInformationTest {
    @Test
    void testGetLogicalProcessorInformation() {
        Triplet<List<CentralProcessor.LogicalProcessor>, List<CentralProcessor.PhysicalProcessor>, List<CentralProcessor.ProcessorCache>> info = LogicalProcessorInformation
                .getLogicalProcessorInformation();
        assertThat("Logical Processor list must not be empty", info.getLeft(), is(not(empty())));
        assertThat("Physical Processor list is null", info.getMiddle(), is(nullValue()));
        assertThat("Cache list is null", info.getRight(), is(nullValue()));
    }

    @Test
    void testGetLogicalProcessorInformationEx() {
        Triplet<List<CentralProcessor.LogicalProcessor>, List<CentralProcessor.PhysicalProcessor>, List<CentralProcessor.ProcessorCache>> info = LogicalProcessorInformation
                .getLogicalProcessorInformationEx();
        assertThat("Must be more Logical Processors than Physical Ones", info.getLeft().size(),
                greaterThanOrEqualTo(info.getMiddle().size()));
        assertThat("Must be more Physical Processors than L3 Caches", info.getMiddle().size(),
                greaterThanOrEqualTo((int) info.getRight().stream().filter(c -> c.getLevel() == 3).count()));
        assertThat("Must be more Physical Processors than L2 Caches", info.getMiddle().size(),
                greaterThanOrEqualTo((int) info.getRight().stream().filter(c -> c.getLevel() == 2).count()));
    }

}

package org.miaixz.bus.health.mac.driver.disk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnabledOnOs(OS.MAC)
class FsstatTest {

    @Test
    void testQueryPartitionToMountMap() {
        assertThat("Partition to mount map should not be empty.", Fsstat.queryPartitionToMountMap(),
                is(not(anEmptyMap())));
    }

}

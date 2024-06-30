package org.miaixz.bus.health.unix.platform.solaris.driver.disk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Tuple;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@EnabledOnOs(OS.SOLARIS)
class DiskDriversTest {

    @Test
    void testDiskQueries() {
        Map<String, String> deviceMap = Iostat.queryPartitionToMountMap();
        assertThat("Partition to mount map should not be empty", deviceMap, is(not(anEmptyMap())));

        Map<String, Tuple> deviceStringMap = Iostat.queryDeviceStrings(deviceMap.keySet());
        assertThat("Device string map should not be empty", deviceStringMap, is(not(anEmptyMap())));
    }

}

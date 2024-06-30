package org.miaixz.bus.health.unix.platform.freebsd.driver.disk;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.tuple.Triplet;
import org.miaixz.bus.health.builtin.hardware.HWPartition;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiskDriversTest {

    @Test
    void testDiskDrivers() {
        Map<String, Triplet<String, String, Long>> diskStringMap = GeomDiskList.queryDisks();
        assertThat("Disk string map should not be empty", diskStringMap, not(anEmptyMap()));

        Map<String, List<HWPartition>> partMap = GeomPartList.queryPartitions();
        assertThat("Partition map should not be empty", partMap, not(anEmptyMap()));

        Map<String, String> deviceMap = Mount.queryPartitionToMountMap();
        Set<String> diskNames = diskStringMap.keySet();
        Set<String> mountedPartitions = deviceMap.keySet();
        for (Entry<String, List<HWPartition>> entry : partMap.entrySet()) {
            assertTrue(diskNames.contains(entry.getKey()), "Disk name should be in disk string map");
            for (HWPartition part : entry.getValue()) {
                if (!part.getMountPoint().isEmpty()) {
                    assertTrue(mountedPartitions.contains(entry.getKey()), "Partition name should be in mount map");
                }
            }
        }
    }

}

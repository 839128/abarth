package org.miaixz.bus.health.linux.driver.proc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnabledOnOs(OS.LINUX)
class DiskStatsTest {

    @Test
    void testGetDiskStats() {
        Map<String, Map<DiskStats.IoStat, Long>> map = DiskStats.getDiskStats();
        assertNotNull(map, "DiskStats should not be null");

        map.forEach((key, value) -> {
            assertNotNull(value, "Entry should not have a null map!");
            assertInstanceOf(EnumMap.class, value, "Value should be enum map!");
        });
    }

}

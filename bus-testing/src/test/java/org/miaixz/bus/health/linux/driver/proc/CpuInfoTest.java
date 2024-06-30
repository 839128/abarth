package org.miaixz.bus.health.linux.driver.proc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@EnabledOnOs(OS.LINUX)
class CpuInfoTest {

    @Test
    void testQueries() {
        assertDoesNotThrow(CpuInfo::queryCpuManufacturer);
        assertDoesNotThrow(CpuInfo::queryBoardInfo);
    }

}

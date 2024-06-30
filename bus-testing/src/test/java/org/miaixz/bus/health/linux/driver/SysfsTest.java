package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@EnabledOnOs(OS.LINUX)
class SysfsTest {

    @Test
    void testQueries() {
        assertDoesNotThrow(Sysfs::querySystemVendor);
        assertDoesNotThrow(Sysfs::queryProductModel);
        assertDoesNotThrow(Sysfs::queryProductSerial);
        assertDoesNotThrow(Sysfs::queryUUID);
        assertDoesNotThrow(Sysfs::queryBoardVendor);
        assertDoesNotThrow(Sysfs::queryBoardModel);
        assertDoesNotThrow(Sysfs::queryBoardVersion);
        assertDoesNotThrow(Sysfs::queryBoardSerial);
        assertDoesNotThrow(Sysfs::queryBiosVendor);
        assertDoesNotThrow(Sysfs::queryBiosDescription);
        assertDoesNotThrow(Sysfs::queryBiosReleaseDate);
    }

    @Test
    void testQueryBiosVersion() {
        Sysfs.queryBiosVersion("");
        final String biosRevisionSuffix = "biosRevision";
        final String biosRevision = Sysfs.queryBiosVersion(biosRevisionSuffix);
        if (biosRevision != null) {
            assertThat("Test Sysfs queryBiosVersion with biosRevision", biosRevision,
                    containsString(biosRevisionSuffix));
        }
    }

}

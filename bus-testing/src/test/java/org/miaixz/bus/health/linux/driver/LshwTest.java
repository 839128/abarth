package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.BuilderTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@EnabledOnOs(OS.LINUX)
class LshwTest {

    @Test
    void testQueries() {
        assertDoesNotThrow(Lshw::queryModel);
        assertDoesNotThrow(Lshw::querySerialNumber);
        String uuid = Lshw.queryUUID();
        if (uuid != null) {
            assertThat("Test Lshw queryUUID", uuid, matchesRegex(BuilderTest.UUID_REGEX));
        }
        assertThat("Test Lshw queryCpuCapacity", Lshw.queryCpuCapacity(), anyOf(greaterThan(0L), equalTo(-1L)));
    }

}

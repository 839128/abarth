package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.BuilderTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LshalTest {

    @Test
    void testQueries() {
        assertDoesNotThrow(Lshal::querySerialNumber);

        final String uuid = Lshal.queryUUID();
        if (uuid != null) {
            assertThat("Test Lshal queryUUID format", uuid, matchesRegex(BuilderTest.UUID_REGEX));
        }
    }

}

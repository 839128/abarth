package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.BuilderTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DmidecodeTest {

    @Test
    void testQueries() {
        assertDoesNotThrow(Dmidecode::querySerialNumber);
        assertDoesNotThrow(Dmidecode::queryBiosNameRev);

        final String uuid = Dmidecode.queryUUID();
        if (uuid != null) {
            assertThat("Test Lshal queryUUID format", uuid, matchesRegex(BuilderTest.UUID_REGEX));
        }
    }

}

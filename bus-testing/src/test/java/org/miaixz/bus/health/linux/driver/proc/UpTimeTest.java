package org.miaixz.bus.health.linux.driver.proc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@EnabledOnOs(OS.LINUX)
class UpTimeTest {

    @Test
    void testGetSystemUptimeSeconds() {
        double uptime = UpTime.getSystemUptimeSeconds();
        assertThat("Uptime should be nonnegative", uptime, greaterThanOrEqualTo(0d));
    }

}

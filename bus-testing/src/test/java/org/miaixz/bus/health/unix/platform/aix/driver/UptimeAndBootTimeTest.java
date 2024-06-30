package org.miaixz.bus.health.unix.platform.aix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnabledOnOs(OS.AIX)
class UptimeAndBootTimeTest {

    @Test
    void testQueryBootTime() {
        long msSinceEpoch = Who.queryBootTime();
        // Possible to return 0 if there is no year information in the command
        assertThat("Boot time should be after the epoch", msSinceEpoch, greaterThanOrEqualTo(0L));
        assertThat("Boot time should be before now", msSinceEpoch, lessThan(System.currentTimeMillis()));

        long msSinceBoot = Uptime.queryUpTime();
        assertThat("Up time should be positive", msSinceBoot, greaterThan(0L));
        assertThat("Boot time plus uptime should be just before now", msSinceBoot + msSinceEpoch,
                lessThanOrEqualTo(System.currentTimeMillis()));
    }

}

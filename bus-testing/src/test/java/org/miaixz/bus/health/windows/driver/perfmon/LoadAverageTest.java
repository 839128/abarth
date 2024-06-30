package org.miaixz.bus.health.windows.driver.perfmon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.xyz.ThreadKit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@EnabledOnOs(OS.WINDOWS)
class LoadAverageTest {

    @Test
    void testQueryLoadAverage() {
        double[] loadAverage = LoadAverage.queryLoadAverage(3);
        assertThat("1m load average should be negative", loadAverage[0], lessThan(0d));
        assertThat("5m load average should be negative", loadAverage[1], lessThan(0d));
        assertThat("15m load average should be negative", loadAverage[2], lessThan(0d));
        LoadAverage.startDaemon();
        ThreadKit.sleep(11000L);
        loadAverage = LoadAverage.queryLoadAverage(3);
        assertThat("1m load average should be positive", loadAverage[0], greaterThan(0d));
        assertThat("5m load average should be positive", loadAverage[1], greaterThan(0d));
        assertThat("15m load average should be positive", loadAverage[2], greaterThan(0d));
        LoadAverage.stopDaemon();
    }

}

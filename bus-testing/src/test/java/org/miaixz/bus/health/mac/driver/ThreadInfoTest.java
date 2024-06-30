package org.miaixz.bus.health.mac.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.Platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@EnabledOnOs(OS.MAC)
class ThreadInfoTest {

    @Test
    void testQueryTaskThreads() {
        int pid = new Platform().getOperatingSystem().getProcessId();
        assertThat("Processes should have at least one thread.", ThreadInfo.queryTaskThreads(pid).size(),
                greaterThan(0));
    }

}

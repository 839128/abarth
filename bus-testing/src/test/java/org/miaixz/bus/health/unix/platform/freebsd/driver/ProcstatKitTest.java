package org.miaixz.bus.health.unix.platform.freebsd.driver;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;
import org.miaixz.bus.health.unix.platform.freebsd.ProcstatKit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test general utility methods
 */
class ProcstatKitTest {

    @Test
    void testProcstat() {
        if (Platform.getCurrentPlatform().equals(Platform.OS.FREEBSD)) {
            int pid = new Platform().getOperatingSystem().getProcessId();

            assertThat("Open files must be nonnegative", ProcstatKit.getOpenFiles(pid), is(greaterThanOrEqualTo(0L)));

            assertThat("CwdMap should have at least one element", ProcstatKit.getCwdMap(-1), is(not(anEmptyMap())));

            assertThat("CwdMap with pid should have at least one element", ProcstatKit.getCwdMap(pid),
                    is(not(anEmptyMap())));

            assertThat("Cwd should be nonempty", ProcstatKit.getCwd(pid), is(not(emptyString())));
        }
    }

}

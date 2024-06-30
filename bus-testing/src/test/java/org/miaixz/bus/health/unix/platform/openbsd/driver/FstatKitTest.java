package org.miaixz.bus.health.unix.platform.openbsd.driver;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;
import org.miaixz.bus.health.unix.platform.openbsd.FstatKit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.miaixz.bus.health.Platform.OS.OPENBSD;


/**
 * Test general utility methods for {@link FstatKit}.
 */
class FstatKitTest {

    @Test
    void testFstat() {
        if (Platform.getCurrentPlatform().equals(OPENBSD)) {
            int pid = new Platform().getOperatingSystem().getProcessId();

            assertThat("Number of open files must be nonnegative", FstatKit.getOpenFiles(pid),
                    is(greaterThanOrEqualTo(0L)));

            assertThat("Cwd should not be empty", FstatKit.getCwd(pid), is(not(emptyString())));
        }
    }

}

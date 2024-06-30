package org.miaixz.bus.health.unix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.builtin.software.OSDesktopWindow;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DisabledOnOs({OS.WINDOWS, OS.MAC})
class XwinInfoTest {

    @Test
    void testQueryXWindows() {
        List<OSDesktopWindow> allWindows = Xwininfo.queryXWindows(false);
        List<OSDesktopWindow> visibleWindows = Xwininfo.queryXWindows(true);
        assertThat("All windows should be no smaller than visible windows", allWindows.size(),
                greaterThanOrEqualTo(visibleWindows.size()));
    }

}

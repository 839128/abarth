package org.miaixz.bus.health.windows.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.builtin.software.OSDesktopWindow;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@EnabledOnOs(OS.WINDOWS)
class EnumWindowsTest {

    @Test
    void testQueryDesktopWindows() {
        List<OSDesktopWindow> allWindows = EnumWindows.queryDesktopWindows(false);
        List<OSDesktopWindow> visibleWindows = EnumWindows.queryDesktopWindows(true);
        assertThat("All windows should be no smaller than visible windows", allWindows.size(),
                greaterThanOrEqualTo(visibleWindows.size()));
    }

}

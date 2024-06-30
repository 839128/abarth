package org.miaixz.bus.health.builtin.hardware;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

/**
 * Tests Displays
 */
class DisplayTest {

    /**
     * Test displays
     */
    @Test
    void testDisplay() {
        Platform si = new Platform();
        List<Display> displays = si.getHardware().getDisplays();
        for (Display d : displays) {
            assertThat("EDID Byte length should be at least 128", d.getEdid().length, is(greaterThanOrEqualTo(128)));
        }
    }

}

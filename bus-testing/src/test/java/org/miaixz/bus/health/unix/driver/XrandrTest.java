package org.miaixz.bus.health.unix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DisabledOnOs({OS.WINDOWS, OS.MAC})
class XrandrTest {

    @Test
    void testGetEdidArrays() {
        for (byte[] edid : Xrandr.getEdidArrays()) {
            assertThat("Edid length must be at least 128", edid.length, greaterThanOrEqualTo(128));
        }
    }

}

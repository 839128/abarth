package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.Display;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests Displays
 */
public class DisplayTest {

    /**
     * Test displays
     */
    @Test
    public void testDisplay() {
        List<Display> displays = Builder.getHardware().getDisplays();
        for (Display d : displays) {
            assertTrue(d.getEdid().length >= 128);
        }
    }

}

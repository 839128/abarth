package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.GraphicsCard;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test GraphicsCard
 */
public class GraphicsCardTest {

    /**
     * Testing sound cards , each attribute.
     */
    @Test
    public void testGraphicsCards() {
        for (GraphicsCard graphicsCard : Builder.getHardware().getGraphicsCards()) {
            assertNotNull(graphicsCard.getName());
            assertNotNull(graphicsCard.getVendor());
            assertTrue(graphicsCard.getVRam() >= 0);
            assertEquals(0, graphicsCard.getVRam() % 1024);
            assertTrue(graphicsCard.getDeviceId().length() >= 6);
            assertTrue(graphicsCard.getVersionInfo().length() >= 2);
        }
    }

}

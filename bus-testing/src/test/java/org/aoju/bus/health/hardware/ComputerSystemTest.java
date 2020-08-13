package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.Baseboard;
import org.aoju.bus.health.builtin.hardware.ComputerSystem;
import org.aoju.bus.health.builtin.hardware.Firmware;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests Computer System
 */
public class ComputerSystemTest {

    /**
     * Test Computer System
     */
    @Test
    public void testComputerSystem() {
        ComputerSystem cs = Builder.getHardware().getComputerSystem();
        assertNotNull(cs.getManufacturer());
        assertNotNull(cs.getModel());
        assertNotNull(cs.getSerialNumber());

        Firmware fw = cs.getFirmware();
        assertNotNull(fw);
        assertNotNull(fw.getManufacturer());
        assertNotNull(fw.getName());
        assertNotNull(fw.getDescription());
        assertNotNull(fw.getVersion());
        assertNotNull(fw.getReleaseDate());
        assertTrue(fw.toString().contains(fw.getManufacturer()));

        Baseboard bb = cs.getBaseboard();
        assertNotNull(bb);
        assertNotNull(bb.getManufacturer());
        assertNotNull(bb.getModel());
        assertNotNull(bb.getVersion());
        assertNotNull(bb.getSerialNumber());
    }

}

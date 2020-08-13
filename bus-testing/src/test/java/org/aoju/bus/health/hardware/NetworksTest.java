package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.NetworkIF;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Networks
 */
public class NetworksTest {
    /**
     * Test network interfaces extraction.
     */
    @Test
    public void testNetworkInterfaces() {

        for (NetworkIF net : Builder.getHardware().getNetworkIFs()) {
            assertNotNull(net.queryNetworkInterface());
            assertNotNull(net.getName());
            assertNotNull(net.getDisplayName());
            assertNotNull(net.getMacaddr());
            assertNotNull(net.getIPv4addr());
            assertNotNull(net.getSubnetMasks());
            assertNotNull(net.getIPv6addr());
            assertNotNull(net.getPrefixLengths());
            assertTrue(net.getIfType() >= 0);
            assertTrue(net.getNdisPhysicalMediumType() >= 0);
            assertTrue(net.getBytesRecv() >= 0);
            assertTrue(net.getBytesSent() >= 0);
            assertTrue(net.getPacketsRecv() >= 0);
            assertTrue(net.getPacketsSent() >= 0);
            assertTrue(net.getInErrors() >= 0);
            assertTrue(net.getOutErrors() >= 0);
            assertTrue(net.getInDrops() >= 0);
            assertTrue(net.getCollisions() >= 0);
            assertTrue(net.getSpeed() >= 0);
            assertTrue(net.getMTU() >= 0);
            assertTrue(net.getTimeStamp() > 0);

            net.updateAttributes();
            assertTrue(net.getBytesRecv() >= 0);
            assertTrue(net.getBytesSent() >= 0);
            assertTrue(net.getPacketsRecv() >= 0);
            assertTrue(net.getPacketsSent() >= 0);
            assertTrue(net.getInErrors() >= 0);
            assertTrue(net.getOutErrors() >= 0);
            assertTrue(net.getInDrops() >= 0);
            assertTrue(net.getCollisions() >= 0);
            assertTrue(net.getSpeed() >= 0);
            assertTrue(net.getTimeStamp() > 0);

            if (net.getMacaddr().startsWith("00:00:00") || net.getMacaddr().length() < 8) {
                assertFalse(net.isKnownVmMacAddr());
            }
        }
    }

}

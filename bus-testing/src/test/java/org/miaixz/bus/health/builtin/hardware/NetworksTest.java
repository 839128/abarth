package org.miaixz.bus.health.builtin.hardware;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Networks
 */
class NetworksTest {

    /**
     * Test inet network interfaces extraction.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    void testAllNetworkInterfaces() throws IOException {
        Platform si = new Platform();

        for (NetworkIF net : si.getHardware().getNetworkIFs(true)) {
            assertThat("NetworkIF should not be null", net.queryNetworkInterface(), is(notNullValue()));
            assertThat("NetworkIF name should not be null", net.getName(), is(notNullValue()));
            assertThat("NetworkIF display name should not be null", net.getDisplayName(), is(notNullValue()));
            assertThat("NetworkIF ifAlias should not be null", net.getIfAlias(), is(notNullValue()));
            assertThat("NetworkIF ifOperStatus should not be null", net.getIfOperStatus(), is(notNullValue()));
            assertThat("NetworkIF IPv4 address should not be null", net.getIPv4addr(), is(notNullValue()));
            assertThat("NetworkIF SubnetMasks should not be null", net.getSubnetMasks(), is(notNullValue()));
            assertThat("NetworkIF IPv6 should not be null", net.getIPv6addr(), is(notNullValue()));
            assertThat("NetworkIF prefix lengths should not be null", net.getPrefixLengths(), is(notNullValue()));
            assertThat("NetworkIF type should not be negative", net.getIfType(), is(greaterThanOrEqualTo(0)));
            assertThat("NetworkIF NDisPhysicalMediumType should not be negative", net.getNdisPhysicalMediumType(),
                    is(greaterThanOrEqualTo(0)));
            assertThat("NetworkIF bytes received should not be negative", net.getBytesRecv(),
                    is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF bytes sent should not be negative", net.getBytesSent(), is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF packets received should not be negative", net.getPacketsRecv(),
                    is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF packets sent should not be negative", net.getPacketsSent(),
                    is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF InErrors should not be negative", net.getInErrors(), is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF out errors should not be negative", net.getOutErrors(), is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF in drops should not be negative", net.getInDrops(), is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF collisions should not be negative", net.getCollisions(),
                    is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF speed should not be negative", net.getSpeed(), is(greaterThanOrEqualTo(0L)));
            assertThat("NetworkIF time stamp should be positive", net.getTimeStamp(), is(greaterThan(0L)));
            // MTU can be negative for non-local so test separately
            testUpdateAttributes(net);

            if (net.getMacaddr().startsWith("00:00:00") || net.getMacaddr().length() < 8) {
                assertThat("Invalid MAC address corresponds to a known virtual machine", net.isKnownVmMacAddr(),
                        is(false));
            }

            assertThat("NetworkIF.toString() should not be null", net.toString(), is(notNullValue()));
        }
    }

    private void testUpdateAttributes(NetworkIF net) {
        net.updateAttributes();
        assertThat("NetworkIF bytes received after update attr should not be negative", net.getBytesRecv(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF bytes sent after update attr should not be negative", net.getBytesSent(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF packets received after update attr should not be negative", net.getPacketsRecv(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF packets sent after update attr should not be negative", net.getPacketsSent(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF in errors after update attr should not be negative", net.getInErrors(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF out errors after update attr should not be negative", net.getOutErrors(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF in drops after update attr should not be negative", net.getInDrops(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF collisions after update attr should not be negative", net.getCollisions(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF MTU should not be negative", net.getMTU(), is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF speed after update attr should not be negative", net.getSpeed(),
                is(greaterThanOrEqualTo(0L)));
        assertThat("NetworkIF time stamp after update attr should not be negative", net.getTimeStamp(),
                is(greaterThan(0L)));
    }

    /**
     * Test all network interfaces extraction.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    void testNonLocalNetworkInterfaces() throws IOException {
        Platform si = new Platform();

        for (NetworkIF net : si.getHardware().getNetworkIFs(false)) {
            assertThat("NetworkIF should not be null", net.queryNetworkInterface(), is(notNullValue()));

            assertThat("Network interface is not localhost " + net.getDisplayName(),
                    net.queryNetworkInterface().isLoopback(), is(false));
            assertThat("Network interface has a hardware address", net.queryNetworkInterface().getHardwareAddress(),
                    is(notNullValue()));

            assertThat("NetworkIF MacAddress should not be null", net.getMacaddr(), is(notNullValue()));
        }
    }

}

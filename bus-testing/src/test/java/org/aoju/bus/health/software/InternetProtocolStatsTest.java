package org.aoju.bus.health.software;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.software.InternetProtocolStats;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Test network parameters
 */
public class InternetProtocolStatsTest {

    /**
     * Test network parameters
     */
    @Test
    public void testIPStats() {
        InternetProtocolStats ipStats = Builder.getOs().getInternetProtocolStats();
        InternetProtocolStats.TcpStats tcp4 = ipStats.getTCPv4Stats();
        assertTrue(tcp4.getConnectionsEstablished() >= 0);
        assertTrue(tcp4.getConnectionsActive() >= 0);
        assertTrue(tcp4.getConnectionsPassive() >= 0);
        assertTrue(tcp4.getConnectionFailures() >= 0);
        assertTrue(tcp4.getConnectionsReset() >= 0);
        assertTrue(tcp4.getSegmentsReceived() >= 0);
        assertTrue(tcp4.getSegmentsSent() >= 0);
        assertTrue(tcp4.getSegmentsRetransmitted() >= 0);
        assertTrue(tcp4.getInErrors() >= 0);
        assertTrue(tcp4.getOutResets() >= 0);

        InternetProtocolStats.TcpStats tcp6 = ipStats.getTCPv6Stats();
        assertTrue(tcp6.getConnectionsEstablished() >= 0);
        assertTrue(tcp6.getConnectionsActive() >= 0);
        assertTrue(tcp6.getConnectionsPassive() >= 0);
        assertTrue(tcp6.getConnectionFailures() >= 0);
        assertTrue(tcp6.getConnectionsReset() >= 0);
        assertTrue(tcp6.getSegmentsReceived() >= 0);
        assertTrue(tcp6.getSegmentsSent() >= 0);
        assertTrue(tcp6.getSegmentsRetransmitted() >= 0);
        assertTrue(tcp6.getInErrors() >= 0);
        assertTrue(tcp6.getOutResets() >= 0);

        InternetProtocolStats.UdpStats udp4 = ipStats.getUDPv4Stats();
        assertTrue(udp4.getDatagramsReceived() >= 0);
        assertTrue(udp4.getDatagramsSent() >= 0);
        assertTrue(udp4.getDatagramsNoPort() >= 0);
        assertTrue(udp4.getDatagramsReceivedErrors() >= 0);

        InternetProtocolStats.UdpStats udp6 = ipStats.getUDPv6Stats();
        assertTrue(udp6.getDatagramsReceived() >= 0);
        assertTrue(udp6.getDatagramsSent() >= 0);
        assertTrue(udp6.getDatagramsNoPort() >= 0);
        assertTrue(udp6.getDatagramsReceivedErrors() >= 0);

        assertNotNull(tcp4.toString());
        assertNotNull(tcp6.toString());
        assertNotNull(udp4.toString());
        assertNotNull(udp6.toString());
    }

}

package org.miaixz.bus.health.unix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.health.builtin.software.InternetProtocolStats;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisabledOnOs(OS.WINDOWS)
class NetStatTest {

    @DisabledOnOs({OS.WINDOWS, OS.MAC, OS.LINUX})
    @Test
    void testQueryNetstat() {
        for (InternetProtocolStats.IPConnection conn : NetStat.queryNetstat()) {
            assertThat("Connection is tcp or udp", conn.getType(), anyOf(startsWith("tcp"), startsWith("udp")));
        }
    }

    @Test
    void testQueryTcpNetstat() {
        Pair<Long, Long> tcpConns = NetStat.queryTcpnetstat();
        assertThat("ipv4 connections must be nonnegative", tcpConns.getLeft().intValue(), greaterThanOrEqualTo(0));
        assertThat("ipv6 connections must be nonnegative", tcpConns.getRight().intValue(), greaterThanOrEqualTo(0));
    }

    @EnabledOnOs(OS.LINUX)
    @Test
    void testQueryStatsLinux() {
        InternetProtocolStats.TcpStats tcpStats = NetStat.queryTcpStats("netstat -st4");
        assertThat("tcp connections must be nonnegative", tcpStats.getConnectionsEstablished(),
                greaterThanOrEqualTo(0L));
        InternetProtocolStats.UdpStats udp4Stats = NetStat.queryUdpStats("netstat -su4");
        assertThat("udp4 datagrams sent must be nonnegative", udp4Stats.getDatagramsSent(), greaterThanOrEqualTo(0L));
        InternetProtocolStats.UdpStats udp6Stats = NetStat.queryUdpStats("netstat -su6");
        assertThat("udp4 datagrams sent must be nonnegative", udp6Stats.getDatagramsSent(), greaterThanOrEqualTo(0L));
    }

    @Test
    void testQueryStatsOpenBSD() {
        InternetProtocolStats.TcpStats tcpStats = NetStat.queryTcpStats("netstat -s -p tcp");
        assertThat("tcp connections must be nonnegative", tcpStats.getConnectionsEstablished(),
                greaterThanOrEqualTo(0L));
        InternetProtocolStats.UdpStats udpStats = NetStat.queryUdpStats("netstat -s -p udp");
        assertThat("udp  datagrams sent must be nonnegative", udpStats.getDatagramsSent(), greaterThanOrEqualTo(0L));
    }

}

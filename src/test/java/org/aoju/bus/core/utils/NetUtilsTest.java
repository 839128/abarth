package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.RegEx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

/**
 * NetUtils单元测试
 */
public class NetUtilsTest {

    @Test
    public void getLocalhostStrTest() {
        String localhost = NetUtils.getLocalhostStr();
        Assertions.assertNotNull(localhost);
    }

    @Test
    public void getLocalhostTest() {
        InetAddress localhost = NetUtils.getLocalhost();
        Assertions.assertNotNull(localhost);
    }

    @Test
    public void getLocalMacAddressTest() {
        String macAddress = NetUtils.getLocalMacAddress();
        Assertions.assertNotNull(macAddress);

        // 验证MAC地址正确
        boolean match = PatternUtils.isMatch(RegEx.MAC_ADDRESS, macAddress);
        Assertions.assertTrue(match);
    }

    @Test
    public void longToIpTest() {
        String ipv4 = NetUtils.longToIpv4(2130706433L);
        Assertions.assertEquals("127.0.0.1", ipv4);
    }

    @Test
    public void ipToLongTest() {
        long ipLong = NetUtils.ipv4ToLong("127.0.0.1");
        Assertions.assertEquals(2130706433L, ipLong);
    }

}

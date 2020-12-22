package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.RegEx;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;

/**
 * NetKit单元测试
 */
public class NetKitTest {

    @Test
    public void getLocalhostStrTest() {
        String localhost = NetKit.getLocalhostStr();
        Assert.assertNotNull(localhost);
    }

    @Test
    public void getLocalhostTest() {
        InetAddress localhost = NetKit.getLocalhost();
        Assert.assertNotNull(localhost);
    }

    @Test
    public void getLocalMacAddressTest() {
        String macAddress = NetKit.getLocalMacAddress();
        Assert.assertNotNull(macAddress);

        // 验证MAC地址正确
        boolean match = PatternKit.isMatch(RegEx.MAC_ADDRESS, macAddress);
        Assert.assertTrue(match);
    }

    @Test
    public void longToIpTest() {
        String ipv4 = NetKit.longToIpv4(2130706433L);
        Assert.assertEquals("127.0.0.1", ipv4);
    }

    @Test
    public void ipToLongTest() {
        long ipLong = NetKit.ipv4ToLong("127.0.0.1");
        Assert.assertEquals(2130706433L, ipLong);
    }

}

package org.miaixz.bus.core.net.ip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.regex.Pattern;
import org.miaixz.bus.core.net.MaskBit;
import org.miaixz.bus.core.net.Protocol;
import org.miaixz.bus.core.xyz.PatternKit;

import java.net.InetAddress;

public class IPv4Test {

    private static void testLongToIp(final String ip) {
        final long ipLong = IPv4.ipv4ToLong(ip);
        final String ipv4 = IPv4.longToIpv4(ipLong);
        Assertions.assertEquals(ip, ipv4);
    }

    @Test
    @Disabled
    public void getLocalHostNameTest() {
        // 注意此方法会触发反向DNS解析，导致阻塞，阻塞时间取决于网络！
        Assertions.assertNotNull(IPv4.getLocalHostName());
    }

    @Test
    public void formatIpBlockTest() {
        for (int i = IPv4.IPV4_MASK_BIT_VALID_MIN; i < IPv4.IPV4_MASK_BIT_MAX; i++) {
            Assertions.assertEquals("192.168.1.101/" + i, IPv4.formatIpBlock("192.168.1.101", IPv4.getMaskByMaskBit(i)));
        }
    }

    @Test
    public void getMaskBitByMaskTest() {
        final int maskBitByMask = IPv4.getMaskBitByMask("255.255.255.0");
        Assertions.assertEquals(24, maskBitByMask);
    }

    @Test
    public void getMaskBitByIllegalMaskTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> IPv4.getMaskBitByMask("255.255.0.255"), "非法掩码测试");
    }

    @Test
    public void getMaskByMaskBitTest() {
        final String mask = IPv4.getMaskByMaskBit(24);
        Assertions.assertEquals("255.255.255.0", mask);
    }

    @Test
    public void longToIpTest() {
        testLongToIp("192.168.1.255");
        testLongToIp("0.0.0.0");
        testLongToIp("0.0.0.255");
        testLongToIp("0.0.255.255");
        testLongToIp("0.255.255.255");
        testLongToIp("255.255.255.255");
        testLongToIp("255.255.255.0");
        testLongToIp("255.255.0.0");
        testLongToIp("255.0.0.0");
        testLongToIp("0.255.255.0");
    }

    @Test
    public void getEndIpStrTest() {
        final String ip = "192.168.1.1";
        final int maskBitByMask = IPv4.getMaskBitByMask("255.255.255.0");
        final String endIpStr = IPv4.getEndIpString(ip, maskBitByMask);
        Assertions.assertEquals("192.168.1.255", endIpStr);
    }

    @Test
    public void listTest() {
        final String ip = "192.168.100.2";
        testGenerateIpList(ip, 22, false);
        testGenerateIpList(ip, 22, true);

        testGenerateIpList(ip, 24, false);
        testGenerateIpList(ip, 24, true);

        testGenerateIpList(ip, 26, false);
        testGenerateIpList(ip, 26, true);

        testGenerateIpList(ip, 30, false);
        testGenerateIpList(ip, 30, true);

        testGenerateIpList(ip, 31, false);
        testGenerateIpList(ip, 31, true);

        testGenerateIpList(ip, 32, false);
        testGenerateIpList(ip, 32, true);
    }

    @Test
    public void listTest2() {
        testGenerateIpList("10.1.0.1", "10.2.1.2");

        testGenerateIpList("10.2.1.1", "10.2.1.2");
        testGenerateIpList("10.2.0.1", "10.2.1.2");
        testGenerateIpList("10.1.0.1", "10.2.1.2");
        testGenerateIpList("10.1.2.1", "10.2.1.2");

        testGenerateIpList("10.2.1.2", "10.2.1.2");
        testGenerateIpList("10.2.0.2", "10.2.1.2");
        testGenerateIpList("10.1.1.2", "10.2.1.2");
        testGenerateIpList("10.1.2.2", "10.2.1.2");

        testGenerateIpList("10.2.0.3", "10.2.1.2");
        testGenerateIpList("10.1.0.3", "10.2.1.2");
        testGenerateIpList("10.1.1.3", "10.2.1.2");
        testGenerateIpList("10.1.2.3", "10.2.1.2");

        testGenerateIpList("9.255.2.1", "10.2.1.2");
        testGenerateIpList("9.255.2.2", "10.2.1.2");
        testGenerateIpList("9.255.2.3", "10.2.1.2");

        testGenerateIpList("9.255.1.2", "10.2.1.2");
        testGenerateIpList("9.255.0.2", "10.2.1.2");
        testGenerateIpList("9.255.3.2", "10.2.1.2");
    }

    @Test
    public void isMaskValidTest() {
        final boolean maskValid = IPv4.isMaskValid("255.255.255.0");
        Assertions.assertTrue(maskValid, "掩码合法检验");
    }

    @Test
    public void isMaskInvalidTest() {
        Assertions.assertFalse(IPv4.isMaskValid("255.255.0.255"), "掩码非法检验 - 255.255.0.255");
        Assertions.assertFalse(IPv4.isMaskValid(null), "掩码非法检验 - null值");
        Assertions.assertFalse(IPv4.isMaskValid(""), "掩码非法检验 - 空字符串");
        Assertions.assertFalse(IPv4.isMaskValid(" "), "掩码非法检验 - 空白字符串");
    }

    @Test
    public void isMaskBitValidTest() {
        for (int i = 1; i < 32; i++) {
            Assertions.assertTrue(IPv4.isMaskBitValid(i), "掩码位非法：" + i);
        }
    }

    @Test
    public void isMaskBitInvalidTest() {
        final boolean maskBitValid = IPv4.isMaskBitValid(33);
        Assertions.assertFalse(maskBitValid, "掩码位非法检验");
    }

    @Test
    public void ipv4ToLongTest() {
        long l = IPv4.ipv4ToLong("127.0.0.1");
        Assertions.assertEquals(2130706433L, l);
        l = IPv4.ipv4ToLong("114.114.114.114");
        Assertions.assertEquals(1920103026L, l);
        l = IPv4.ipv4ToLong("0.0.0.0");
        Assertions.assertEquals(0L, l);
        l = IPv4.ipv4ToLong("255.255.255.255");
        Assertions.assertEquals(4294967295L, l);
    }

    @Test
    public void getMaskIpLongTest() {
        for (int i = 1; i <= 32; i++) {
            Assertions.assertEquals(IPv4.ipv4ToLong(MaskBit.get(i)), MaskBit.getMaskIpLong(i));
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void testGenerateIpList(final String fromIp, final String toIp) {
        Assertions.assertEquals(
                IPv4.countByIpRange(fromIp, toIp),
                IPv4.list(fromIp, toIp).size()
        );
    }

    @SuppressWarnings("SameParameterValue")
    private void testGenerateIpList(final String ip, final int maskBit, final boolean isAll) {
        Assertions.assertEquals(
                IPv4.countByMaskBit(maskBit, isAll),
                IPv4.list(ip, maskBit, isAll).size()
        );
    }

    @Test
    public void isInnerTest() {
        Assertions.assertTrue(IPv4.isInnerIP(Protocol.HOST_IPV4));
        Assertions.assertTrue(IPv4.isInnerIP("192.168.5.12"));
        Assertions.assertTrue(IPv4.isInnerIP("172.20.10.1"));

        Assertions.assertFalse(IPv4.isInnerIP("180.10.2.5"));
        Assertions.assertFalse(IPv4.isInnerIP("192.160.10.3"));
    }

    @Test
    public void isPublicTest() {
        Assertions.assertTrue(IPv4.isPublicIP("180.10.2.5"));
        Assertions.assertTrue(IPv4.isPublicIP("192.160.10.3"));

        Assertions.assertFalse(IPv4.isPublicIP(Protocol.HOST_IPV4));
        Assertions.assertFalse(IPv4.isPublicIP("192.168.5.12"));
        Assertions.assertFalse(IPv4.isPublicIP("127.0.0.1"));
        Assertions.assertFalse(IPv4.isPublicIP("172.20.10.1"));
    }

    @Test
    public void getMaskBitByIpRange() {
        final String ip = "192.168.100.2";
        for (int i = 1; i <= 32; i++) {
            final String beginIpStr = IPv4.getBeginIpString(ip, i);
            final String endIpStr = IPv4.getEndIpString(ip, i);
            Assertions.assertEquals(IPv4.getMaskByMaskBit(i), IPv4.getMaskByIpRange(beginIpStr, endIpStr));
        }
    }

    @Test
    @Disabled
    public void getLocalhostTest() {
        final InetAddress localhost = IPv4.getLocalhost();
        Assertions.assertNotNull(localhost);
    }

    @Test
    @Disabled
    public void getLocalMacAddressTest() {
        final String macAddress = IPv4.getLocalMacAddress();
        Assertions.assertNotNull(macAddress);

        // 验证MAC地址正确
        final boolean match =
                PatternKit.isMatch(Pattern.MAC_ADDRESS_PATTERN, macAddress);
        Assertions.assertTrue(match);
    }

    @Test
    public void matchesTest() {
        final boolean matches1 = IPv4.matches("127.*.*.1", "127.0.0.1");
        Assertions.assertTrue(matches1);
        final boolean matches2 = IPv4.matches("192.168.*.1", "127.0.0.1");
        Assertions.assertFalse(matches2);
    }

}

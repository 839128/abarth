package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;

import java.net.HttpCookie;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * 单元测试
 */
public class NetKitTest {

    @Test
    @Disabled
    public void getLocalhostStrTest() {
        final String localhost = NetKit.getLocalhostStringV4();
        Assertions.assertNotNull(localhost);
    }

    @Test
    public void longToIpTest() {
        final String ipv4 = NetKit.longToIpv4(2130706433L);
        Assertions.assertEquals("127.0.0.1", ipv4);
    }

    @Test
    public void ipToLongTest() {
        final long ipLong = NetKit.ipv4ToLong("127.0.0.1");
        Assertions.assertEquals(2130706433L, ipLong);
    }

    @Test
    @Disabled
    public void isUsableLocalPortTest() {
        Assertions.assertTrue(NetKit.isUsableLocalPort(80));
    }

    @Test
    public void parseCookiesTest() {
        final String cookieStr = "cookieName=\"cookieValue\";Path=\"/\";Domain=\"cookiedomain.com\"";
        final List<HttpCookie> httpCookies = NetKit.parseCookies(cookieStr);
        Assertions.assertEquals(1, httpCookies.size());

        final HttpCookie httpCookie = httpCookies.get(0);
        Assertions.assertEquals(0, httpCookie.getVersion());
        Assertions.assertEquals("cookieName", httpCookie.getName());
        Assertions.assertEquals("cookieValue", httpCookie.getValue());
        Assertions.assertEquals("/", httpCookie.getPath());
        Assertions.assertEquals("cookiedomain.com", httpCookie.getDomain());
    }

    @Test
    public void getLocalHostTest() {
        Assertions.assertNotNull(NetKit.getLocalhostV4());
    }

    @Test
    public void pingTest() {
        Assertions.assertTrue(NetKit.ping("127.0.0.1"));
    }

    @Test
    @Disabled
    public void isOpenTest() {
        final InetSocketAddress address = new InetSocketAddress("www.miaixz.org", 443);
        Assertions.assertTrue(NetKit.isOpen(address, 200));
    }

    @Test
    @Disabled
    public void getDnsInfoTest() {
        final List<String> txt = NetKit.getDnsInfo("miaixz.org", "TXT");
        Console.log(txt);
    }

    @Test
    public void isInRangeTest() {
        Assertions.assertTrue(NetKit.isInRange("114.114.114.114", "0.0.0.0/0"));
        Assertions.assertTrue(NetKit.isInRange("192.168.3.4", "192.0.0.0/8"));
        Assertions.assertTrue(NetKit.isInRange("192.168.3.4", "192.168.0.0/16"));
        Assertions.assertTrue(NetKit.isInRange("192.168.3.4", "192.168.3.0/24"));
        Assertions.assertTrue(NetKit.isInRange("192.168.3.4", "192.168.3.4/32"));
        Assertions.assertFalse(NetKit.isInRange("8.8.8.8", "192.0.0.0/8"));
        Assertions.assertFalse(NetKit.isInRange("114.114.114.114", "192.168.3.4/32"));
    }

    @Test
    public void issueTest() {
        // 获取结果应该去掉空格
        final String ips = "unknown, 12.34.56.78, 23.45.67.89";
        final String ip = NetKit.getMultistageReverseProxyIp(ips);
        Assertions.assertEquals("12.34.56.78", ip);
    }

}

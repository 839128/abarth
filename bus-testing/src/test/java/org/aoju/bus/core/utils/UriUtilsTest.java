package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * UriUtils单元测试
 */
public class UriUtilsTest {

    @Test
    public void normalizeTest() {
        String url = "http://www.aoju.org//aaa/bbb";
        String normalize = UriUtils.normalize(url);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb", normalize);

        url = "www.aoju.org//aaa/bbb";
        normalize = UriUtils.normalize(url);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb", normalize);
    }

    @Test
    public void normalizeTest2() {
        String url = "http://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriUtils.normalize(url);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriUtils.normalize(url);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void normalizeTest3() {
        String url = "http://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriUtils.normalize(url, false);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriUtils.normalize(url, false);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);

        url = "\\/www.aoju.org//aaa/bbb?a=1&b=2";
        normalize = UriUtils.normalize(url, false);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void normalizeIpv6Test() {
        String url = "http://[fe80::8f8:2022:a603:d180]:9439";
        String normalize = UriUtils.normalize("http://[fe80::8f8:2022:a603:d180]:9439", true);
        Assertions.assertEquals(url, normalize);
    }

    @Test
    public void formatTest() {
        String url = "//www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriUtils.normalize(url);
        Assertions.assertEquals("http://www.aoju.org/aaa/bbb?a=1&b=2", normalize);
    }

    @Test
    public void getHostTest() throws MalformedURLException {
        String url = "https://www.aoju.org//aaa/\\bbb?a=1&b=2";
        String normalize = UriUtils.normalize(url);
        URI host = UriUtils.getHost(new URL(normalize));
        Assertions.assertEquals("https://www.aoju.org", host.toString());
    }

    @Test
    public void encodeTest() {
        String body = "366466 - 副本.jpg";
        String encode = UriUtils.encode(body);
        Assertions.assertEquals("366466%20-%20%E5%89%AF%E6%9C%AC.jpg", encode);
        Assertions.assertEquals(body, UriUtils.decode(encode));

        String encode2 = UriUtils.encodeQuery(body);
        Assertions.assertEquals("366466%20-%20%E5%89%AF%E6%9C%AC.jpg", encode2);
    }

}

package org.miaixz.bus.core.net.url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

class UrlDecoderTest {

    @Test
    void decodeForPathTest() {
        Assertions.assertEquals("+", UrlDecoder.decodeForPath("+", Charset.UTF_8));
    }

    @Test
    public void decodePlusTest() {
        final String decode = UrlDecoder.decode("+", Charset.UTF_8);
        Assertions.assertEquals(" ", decode);
    }

    @Test
    void issueTest() throws UnsupportedEncodingException {
        final String s = "测试";
        final String expectedDecode = "%FE%FF%6D%4B%8B%D5";

        final String s1 = UrlEncoder.encodeAll(s, StandardCharsets.UTF_16);
        Assertions.assertEquals(expectedDecode, s1);
        final String s2 = java.net.URLEncoder.encode(s, "UTF-16");
        Assertions.assertEquals(expectedDecode, s2);

        final String decode = UrlDecoder.decode(s1, StandardCharsets.UTF_16);
        Assertions.assertEquals(s, decode);

        // 测试编码字符串和非编码字符串混合
        final String mixDecoded = expectedDecode + "你好";
        final String decode2 = UrlDecoder.decode(mixDecoded, StandardCharsets.UTF_16);
        Assertions.assertEquals("测试你好", decode2);

        Assertions.assertEquals(
                java.net.URLDecoder.decode(mixDecoded, "UTF-16"),
                UrlDecoder.decode(mixDecoded, StandardCharsets.UTF_16)
        );
    }

    @Test
    void decodeCharSetIsNullToStrTest() {
        final String hello = "你好";
        final String decode = UrlDecoder.decode(hello, null, true);
        Assertions.assertEquals(hello, decode);
    }

    @Test
    void decodeStrIsEmptyToStrTest() {
        final String strEmpty = "";
        final String decode = UrlDecoder.decode(strEmpty, StandardCharsets.UTF_8, true);
        Assertions.assertEquals(strEmpty, decode);
    }

    @Test
    void decodeStrWithUTF8ToStrTest() {
        final String exceptedDecode = "你好";
        final String encode = "%E4%BD%A0%E5%A5%BD";
        final String s1 = UrlDecoder.decode(encode);
        Assertions.assertEquals(exceptedDecode, s1);

        final String s2 = UrlDecoder.decode(encode, StandardCharsets.UTF_8);
        Assertions.assertEquals(exceptedDecode, s2);

        final String s3 = UrlDecoder.decode(encode, true);
        Assertions.assertEquals(exceptedDecode, s3);

        final String s4 = UrlDecoder.decode(encode + "+", false);
        Assertions.assertEquals(exceptedDecode + "+", s4);

        final String s5 = UrlDecoder.decode(encode, StandardCharsets.UTF_8, false);
        Assertions.assertEquals(exceptedDecode, s5);
    }

    @Test
    void decodeStrWithUTF8ToByteTest() {
        final String exceptedDecode = "你好";
        final String encode = "%E4%BD%A0%E5%A5%BD";
        final byte[] decode = UrlDecoder.decode(encode.getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(exceptedDecode, new String(decode, StandardCharsets.UTF_8));

        final byte[] decode1 = UrlDecoder.decode((encode + "+").getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(exceptedDecode + " ", new String(decode1, StandardCharsets.UTF_8));
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

import java.nio.charset.StandardCharsets;

/**
 * HexKit 单元测试
 */
public class HexKitTest {

    @Test
    public void hexStrTest() {
        final String str = "我是一个字符串";

        final String hex = HexKit.encodeString(str, Charset.UTF_8);
        final String decodedStr = HexKit.decodeString(hex);

        Assertions.assertEquals(str, decodedStr);
    }

    @Test
    public void issueTest() {
        final String s = HexKit.encodeString("烟".getBytes(StandardCharsets.UTF_16BE));
        Assertions.assertEquals("70df", s);
    }

    @Test
    public void toUnicodeHexTest() {
        String unicodeHex = HexKit.toUnicodeHex('\u2001');
        Assertions.assertEquals("\\u2001", unicodeHex);

        unicodeHex = HexKit.toUnicodeHex('你');
        Assertions.assertEquals("\\u4f60", unicodeHex);
    }

    @Test
    public void isHexNumberTest() {
        Assertions.assertTrue(HexKit.isHexNumber("0"));
        Assertions.assertTrue(HexKit.isHexNumber("002c"));

        String a = "0x3544534F444";
        Assertions.assertTrue(HexKit.isHexNumber(a));

        a = "0x0000000000000001158e460913d00000";
        Assertions.assertTrue(HexKit.isHexNumber(a));

        // 错误的
        a = "0x0000001000T00001158e460913d00000";
        Assertions.assertFalse(HexKit.isHexNumber(a));

        a = "-1";
        Assertions.assertFalse(HexKit.isHexNumber(a));
    }

    @Test
    public void decodeTest() {
        final String str = "e8c670380cb220095268f40221fc748fa6ac39d6e930e63c30da68bad97f885d";
        Assertions.assertArrayEquals(HexKit.decode(str),
                HexKit.decode(str.toUpperCase()));
    }

    @Test
    public void formatHexTest() {
        final String hex = "e8c670380cb220095268f40221fc748fa6ac39d6e930e63c30da68bad97f885d";
        final String formatHex = HexKit.format(hex);
        Assertions.assertEquals("e8 c6 70 38 0c b2 20 09 52 68 f4 02 21 fc 74 8f a6 ac 39 d6 e9 30 e6 3c 30 da 68 ba d9 7f 88 5d", formatHex);
    }

    @Test
    public void formatHexTest2() {
        final String hex = "e8c670380cb220095268f40221fc748fa6";
        final String formatHex = HexKit.format(hex, "0x");
        Assertions.assertEquals("0xe8 0xc6 0x70 0x38 0x0c 0xb2 0x20 0x09 0x52 0x68 0xf4 0x02 0x21 0xfc 0x74 0x8f 0xa6", formatHex);
    }

    @Test
    public void decodeHexTest() {
        final String s = HexKit.encodeString("6");
        final String s1 = HexKit.decodeString(s);
        Assertions.assertEquals("6", s1);
    }

}

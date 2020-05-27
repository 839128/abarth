package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * HexKit单元测试
 */
public class HexKitTest {

    @Test
    public void hexStrTest() {
        String str = "我是一个字符串";

        String hex = HexKit.encodeHexStr(str, Charset.UTF_8);
        String decodedStr = HexKit.decodeHexStr(hex);

        Assertions.assertEquals(str, decodedStr);
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
        String a = "0x3544534F444";
        boolean isHex = HexKit.isHexNumber(a);
        Assertions.assertTrue(isHex);
    }

}

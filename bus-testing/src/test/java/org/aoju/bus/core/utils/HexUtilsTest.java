package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * HexUtils单元测试
 */
public class HexUtilsTest {

    @Test
    public void hexStrTest() {
        String str = "我是一个字符串";

        String hex = HexUtils.encodeHexStr(str, Charset.UTF_8);
        String decodedStr = HexUtils.decodeHexStr(hex);

        Assertions.assertEquals(str, decodedStr);
    }

    @Test
    public void toUnicodeHexTest() {
        String unicodeHex = HexUtils.toUnicodeHex('\u2001');
        Assertions.assertEquals("\\u2001", unicodeHex);

        unicodeHex = HexUtils.toUnicodeHex('你');
        Assertions.assertEquals("\\u4f60", unicodeHex);
    }

    @Test
    public void isHexNumberTest() {
        String a = "0x3544534F444";
        boolean isHex = HexUtils.isHexNumber(a);
        Assertions.assertTrue(isHex);
    }

}

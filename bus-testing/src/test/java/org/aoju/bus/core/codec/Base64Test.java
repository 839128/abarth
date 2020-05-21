package org.aoju.bus.core.codec;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Base64单元测试
 */
public class Base64Test {

    @Test
    public void encodeAndDecodeTest() {
        String a = "伦家是一个非常长的字符串66";
        String encode = Base64.encode(a);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeTest2() {
        String a = "a61a5db5a67c01445ca2-HZ20181120172058/pdf/中国电信影像云单体网关Docker版-V1.2.pdf";
        String encode = Base64.encode(a, Charset.UTF_8);

        String decodeStr = Base64.decodeStr(encode, Charset.UTF_8);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeTest3() {
        String a = ":";
        String encode = Base64.encode(a);
        Assertions.assertEquals("Og==", encode);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void urlSafeEncodeAndDecodeTest() {
        String a = "需要安全感123";
        String encode = StringUtils.toString(Base64.encodeUrlSafe(StringUtils.bytes(a), false));
        Assertions.assertEquals("6ZyA6KaB5a6J5YWo5oSfMTIz", encode);

        String decodeStr = Base64.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

}

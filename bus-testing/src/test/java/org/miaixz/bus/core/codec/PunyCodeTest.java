package org.miaixz.bus.core.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PunyCodeTest {

    @Test
    public void encodeDecodeTest() {
        final String text = "bus编码器";
        final String strPunyCode = PunyCode.encode(text);
        Assertions.assertEquals("bus-ux9js33tgln", strPunyCode);
        String decode = PunyCode.decode("bus-ux9js33tgln");
        Assertions.assertEquals(text, decode);
        decode = PunyCode.decode("xn--bus-ux9js33tgln");
        Assertions.assertEquals(text, decode);
    }

    @Test
    public void encodeDecodeTest2() {
        // 无需编码和解码
        final String text = "Bus";
        final String strPunyCode = PunyCode.encode(text);
        Assertions.assertEquals("Bus", strPunyCode);
    }

    @Test
    public void encodeDecodeDomainTest() {
        // 全中文
        final String text = "百度.中国";
        final String strPunyCode = PunyCode.encodeDomain(text);
        Assertions.assertEquals("xn--wxtr44c.xn--fiqs8s", strPunyCode);

        final String decode = PunyCode.decodeDomain(strPunyCode);
        Assertions.assertEquals(text, decode);
    }

    @Test
    public void encodeDecodeDomainTest2() {
        // 中英文分段
        final String text = "bus.中国";
        final String strPunyCode = PunyCode.encodeDomain(text);
        Assertions.assertEquals("bus.xn--fiqs8s", strPunyCode);

        final String decode = PunyCode.decodeDomain(strPunyCode);
        Assertions.assertEquals(text, decode);
    }

    @Test
    public void encodeDecodeDomainTest3() {
        // 中英文混合
        final String text = "bus工具.中国";
        final String strPunyCode = PunyCode.encodeDomain(text);
        Assertions.assertEquals("xn--bus-up2j943f.xn--fiqs8s", strPunyCode);

        final String decode = PunyCode.decodeDomain(strPunyCode);
        Assertions.assertEquals(text, decode);
    }

    @Test
    public void encodeEncodeDomainTest2() {
        final String domain = "赵新虎.com";
        final String strPunyCode = PunyCode.encodeDomain(domain);
        Assertions.assertEquals("xn--efvz93e52e.com", strPunyCode);
        final String decode = PunyCode.decodeDomain(strPunyCode);
        Assertions.assertEquals(domain, decode);
    }

}

package org.aoju.bus.crypto;

import org.aoju.bus.core.lang.Algorithm;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.IoUtils;
import org.aoju.bus.crypto.algorithm.digest.HMac;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Hmac单元测试
 */
public class HmacTest {

    @Test
    public void hmacTest() {
        String testStr = "test中文";

        byte[] key = "password".getBytes();
        HMac mac = new HMac(Algorithm.HmacMD5, key);

        String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex1);

        String macHex2 = mac.digestHex(IoUtils.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex2);
    }

    @Test
    public void hmacMd5Test() {
        String testStr = "test中文";

        HMac mac = Builder.hmacMd5("password");

        String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex1);

        String macHex2 = mac.digestHex(IoUtils.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex2);
    }

    @Test
    public void hmacSha1Test() {
        String testStr = "test中文";

        HMac mac = Builder.hmacSha1("password");

        String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("1dd68d2f119d5640f0d416e99d3f42408b88d511", macHex1);

        String macHex2 = mac.digestHex(IoUtils.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("1dd68d2f119d5640f0d416e99d3f42408b88d511", macHex2);
    }

}

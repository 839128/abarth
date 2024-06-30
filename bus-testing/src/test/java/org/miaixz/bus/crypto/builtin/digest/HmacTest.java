package org.miaixz.bus.crypto.builtin.digest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.center.HMac;

import javax.crypto.spec.IvParameterSpec;

/**
 * Hmac单元测试
 */
public class HmacTest {

    @Test
    public void hmacTest() {
        final String testStr = "test中文";

        final byte[] key = "password".getBytes();
        final HMac mac = new HMac(Algorithm.HMACMD5, key);

        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex1);

        final String macHex2 = mac.digestHex(IoKit.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex2);
    }

    @Test
    public void hmacMd5Test() {
        final String testStr = "test中文";

        final HMac mac = Builder.hmacMd5("password");

        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex1);

        final String macHex2 = mac.digestHex(IoKit.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("b977f4b13f93f549e06140971bded384", macHex2);
    }

    @Test
    public void hmacSha1Test() {
        final HMac mac = Builder.hmacSha1("password");

        final String testStr = "test中文";
        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("1dd68d2f119d5640f0d416e99d3f42408b88d511", macHex1);

        final String macHex2 = mac.digestHex(IoKit.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("1dd68d2f119d5640f0d416e99d3f42408b88d511", macHex2);
    }

    @Test
    public void zuc128MacTest() {
        final byte[] iv = new byte[16];
        final byte[] key = new byte[16];
        final HMac mac = new HMac("ZUC-128",
                Keeper.generateKey(Algorithm.ZUC_128.getValue(), key),
                new IvParameterSpec(iv));

        final String testStr = "test中文";
        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("1e0b9455", macHex1);
    }

    @Test
    public void zuc256MacTest() {
        final byte[] key = new byte[32];
        final byte[] iv = new byte[25];
        final HMac mac = new HMac("ZUC-256",
                Keeper.generateKey(Algorithm.ZUC_128.getValue(), key),
                new IvParameterSpec(iv));

        final String testStr = "test中文";
        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("d9ad618357c1bfb1d9d1200a763d5eaa", macHex1);
    }

    @Test
    public void sm4CMACTest() {
        final byte[] key = new byte[16];
        final HMac mac = new HMac(Algorithm.SM4CMAC,
                Keeper.generateKey("SM4", key));

        // 原文
        final String testStr = "test中文";

        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("58a0d231315664af51b858a174eabc21", macHex1);
    }

}

package org.miaixz.bus.crypto.builtin.digest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.crypto.Builder;

/**
 * 摘要算法单元测试
 */
public class DigestTest {

    @Test
    public void digesterTest() {
        final String testStr = "test中文";

        final Digester md5 = new Digester(Algorithm.MD5);
        final String digestHex = md5.digestHex(testStr);
        Assertions.assertEquals("5393554e94bf0eb6436f240a4fd71282", digestHex);
    }

    @Test
    public void md5Test() {
        final String testStr = "test中文";

        final String md5Hex1 = Builder.md5Hex(testStr);
        Assertions.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Hex1);

        final String md5Hex2 = Builder.md5Hex(IoKit.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Hex2);
    }

    @Test
    public void md5WithSaltTest() {
        final String testStr = "test中文";

        final Digester md5 = new Digester(Algorithm.MD5);

        //加盐
        md5.setSalt("saltTest".getBytes());
        final String md5Hex1 = md5.digestHex(testStr);
        Assertions.assertEquals("762f7335200299dfa09bebbb601a5bc6", md5Hex1);
        final String md5Hex2 = md5.digestHex(IoKit.toUtf8Stream(testStr));
        Assertions.assertEquals("762f7335200299dfa09bebbb601a5bc6", md5Hex2);

        //重复2次
        md5.setDigestCount(2);
        final String md5Hex3 = md5.digestHex(testStr);
        Assertions.assertEquals("2b0616296f6755d25efc07f90afe9684", md5Hex3);
        final String md5Hex4 = md5.digestHex(IoKit.toUtf8Stream(testStr));
        Assertions.assertEquals("2b0616296f6755d25efc07f90afe9684", md5Hex4);
    }

    @Test
    public void sha1Test() {
        final String testStr = "test中文";

        final String sha1Hex1 = Builder.sha1Hex(testStr);
        Assertions.assertEquals("ecabf586cef0d3b11c56549433ad50b81110a836", sha1Hex1);

        final String sha1Hex2 = Builder.sha1Hex(IoKit.toStream(testStr, Charset.UTF_8));
        Assertions.assertEquals("ecabf586cef0d3b11c56549433ad50b81110a836", sha1Hex2);
    }

    @Test
    public void hash256Test() {
        final String testStr = "Test中文";
        final String hex = Builder.sha256Hex(testStr);
        Assertions.assertEquals(64, hex.length());
    }

    @Test
    public void hash512Test() {
        final String testStr = "Test中文";
        final String hex = Builder.sha512Hex(testStr);
        Assertions.assertEquals(128, hex.length());
    }

}

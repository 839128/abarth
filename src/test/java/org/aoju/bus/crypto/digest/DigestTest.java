package org.aoju.bus.crypto.digest;

import org.aoju.bus.core.lang.Algorithm;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.IoUtils;
import org.aoju.bus.crypto.Builder;
import org.aoju.bus.crypto.algorithm.digest.Digester;
import org.junit.Assert;
import org.junit.Test;

/**
 * 摘要算法单元测试
 */
public class DigestTest {

    @Test
    public void digesterTest() {
        String testStr = "test中文";

        Digester md5 = new Digester(Algorithm.MD5);
        String digestHex = md5.digestHex(testStr);
        Assert.assertEquals("5393554e94bf0eb6436f240a4fd71282", digestHex);
    }

    @Test
    public void md5Test() {
        String testStr = "test中文";

        String md5Hex1 = Builder.md5Hex(testStr);
        Assert.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Hex1);

        String md5Hex2 = Builder.md5Hex(IoUtils.toStream(testStr, Charset.UTF_8));
        Assert.assertEquals("5393554e94bf0eb6436f240a4fd71282", md5Hex2);
    }

    @Test
    public void sha1Test() {
        String testStr = "test中文";

        String sha1Hex1 = Builder.sha1Hex(testStr);
        Assert.assertEquals("ecabf586cef0d3b11c56549433ad50b81110a836", sha1Hex1);

        String sha1Hex2 = Builder.sha1Hex(IoUtils.toStream(testStr, Charset.UTF_8));
        Assert.assertEquals("ecabf586cef0d3b11c56549433ad50b81110a836", sha1Hex2);
    }

    @Test
    public void hash256Test() {
        String testStr = "Test中文";
        String hex = Builder.sha256Hex(testStr);
        Assert.assertEquals(64, hex.length());
    }

}

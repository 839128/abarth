package org.aoju.bus.crypto.digest;

import org.aoju.bus.crypto.algorithm.digest.MD5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MD5 单元测试
 */
public class Md5Test {

    @Test
    public void md5To16Test() {
        String hex16 = new MD5().digestHex16("中国");
        Assertions.assertEquals(16, hex16.length());
        Assertions.assertEquals("cb143acd6c929826", hex16);
    }

}
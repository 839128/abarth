package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.crypto.builtin.symmetric.Crypto;

/**
 * TEA（Tiny Encryption Algorithm）和 XTEA算法单元测试
 */
public class TEATest {

    @Test
    public void teaTest() {
        final String data = "测试的加密数据";
        // 密钥必须为128bit
        final Crypto tea = new Crypto("TEA", "MyPassword123456".getBytes());
        final byte[] encrypt = tea.encrypt(data);
        // 解密
        final String decryptStr = tea.decryptString(encrypt);

        Assertions.assertEquals(data, decryptStr);
    }

    @Test
    public void xteaTest() {
        final String data = "测试的加密数据";
        // 密钥必须为128bit
        final Crypto tea = new Crypto("XTEA", "MyPassword123456".getBytes());
        final byte[] encrypt = tea.encrypt(data);
        // 解密
        final String decryptStr = tea.decryptString(encrypt);

        Assertions.assertEquals(data, decryptStr);
    }

    @Test
    public void xxteaTest() {
        final String data = "测试的加密数据";

        // 密钥必须为128bit
        final TEA tea = new TEA("MyPassword123456".getBytes());
        final byte[] encrypt = tea.encrypt(data);

        // 解密
        final String decryptStr = tea.decryptString(encrypt);

        Assertions.assertEquals(data, decryptStr);
    }

}

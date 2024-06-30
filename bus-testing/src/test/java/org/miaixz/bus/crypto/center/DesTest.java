package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.Padding;
import org.miaixz.bus.crypto.builtin.symmetric.Crypto;

/**
 * DES加密解密单元测试
 */
public class DesTest {

    @Test
    public void desTest() {
        final String content = "test中文";

        final byte[] key = Keeper.generateKey(Algorithm.DES.getValue()).getEncoded();

        final Crypto des = new Crypto(Algorithm.DES, key);
        final byte[] encrypt = des.encrypt(content);
        final byte[] decrypt = des.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        final String encryptHex = des.encryptHex(content);
        final String decryptStr = des.decryptString(encryptHex);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void desTest2() {
        final String content = "test中文";

        final byte[] key = Keeper.generateKey(Algorithm.DES.getValue()).getEncoded();

        final DES des = Builder.des(key);
        final byte[] encrypt = des.encrypt(content);
        final byte[] decrypt = des.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        final String encryptHex = des.encryptHex(content);
        final String decryptStr = des.decryptString(encryptHex);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void desTest3() {
        final String content = "test中文";

        final DES des = new DES(Algorithm.Mode.CTS, Padding.PKCS5Padding, "0CoJUm6Qyw8W8jud".getBytes(), "01020304".getBytes());

        final byte[] encrypt = des.encrypt(content);
        final byte[] decrypt = des.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        final String encryptHex = des.encryptHex(content);
        final String decryptStr = des.decryptString(encryptHex);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void encryptDecryptTest() {
        final String content = "我是一个测试的test字符串123";
        final DES des = Builder.des();

        final String encryptHex = des.encryptHex(content);
        final String result = des.decryptString(encryptHex);

        Assertions.assertEquals(content, result);
    }

    @Test
    public void encryptDecryptWithCustomTest() {
        final String content = "我是一个测试的test字符串123";
        final DES des = new DES(
                Algorithm.Mode.CTS,
                Padding.PKCS5Padding,
                ByteKit.toBytes("12345678"),
                ByteKit.toBytes("11223344")
        );

        final String encryptHex = des.encryptHex(content);
        final String result = des.decryptString(encryptHex);

        Assertions.assertEquals(content, result);
    }

    @Test
    public void issuesTest() {
        final DES des = new DES(Algorithm.Mode.CTS, Padding.PKCS5Padding, "1234567890".getBytes(), "12345678".getBytes());
        des.encryptHex("root");
    }

}

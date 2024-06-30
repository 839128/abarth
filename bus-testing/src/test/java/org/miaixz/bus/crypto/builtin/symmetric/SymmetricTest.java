package org.miaixz.bus.crypto.builtin.symmetric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.core.xyz.RandomKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.Padding;
import org.miaixz.bus.crypto.center.AES;
import org.miaixz.bus.crypto.center.Vigenere;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 对称加密算法单元测试
 */
public class SymmetricTest {

    @Test
    public void aesTest() {
        final String content = "test中文";

        // 随机生成密钥
        final byte[] key = Keeper.generateKey(Algorithm.AES.getValue()).getEncoded();

        // 构建
        final Crypto aes = new Crypto(Algorithm.AES, key);

        // 加密
        final byte[] encrypt = aes.encrypt(content);
        // 解密
        final byte[] decrypt = aes.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt, Charset.UTF_8));

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);
        // 解密为字符串
        final String decryptStr = aes.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void aesTest2() {
        final String content = "test中文";

        // 随机生成密钥
        final byte[] key = Keeper.generateKey(Algorithm.AES.getValue()).getEncoded();

        // 构建
        final AES aes = Builder.aes(key);

        // 加密
        final byte[] encrypt = aes.encrypt(content);
        // 解密
        final byte[] decrypt = aes.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);
        // 解密为字符串
        final String decryptStr = aes.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void aesTest3() {
        final String content = "test中文aaaaaaaaaaaaaaaaaaaaa";

        final AES aes = new AES(Algorithm.Mode.CTS, Padding.PKCS5Padding, "0CoJUm6Qyw8W8jud".getBytes(), "0102030405060708".getBytes());

        // 加密
        final byte[] encrypt = aes.encrypt(content);
        // 解密
        final byte[] decrypt = aes.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);
        // 解密为字符串
        final String decryptStr = aes.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void aesTest4() {
        final String content = "4321c9a2db2e6b08987c3b903d8d11ff";
        final AES aes = new AES(Algorithm.Mode.CBC, Padding.PKCS5Padding, "0123456789ABHAEQ".getBytes(), "DYgjCEIMVrj2W9xN".getBytes());

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);

        Assertions.assertEquals("cd0e3a249eaf0ed80c330338508898c4bddcfd665a1b414622164a273ca5daf7b4ebd2c00aaa66b84dd0a237708dac8e", encryptHex);
    }

    @Test
    public void pbeWithoutIvTest() {
        final String content = "4321c9a2db2e6b08987c3b903d8d11ff";
        final Crypto crypto = new Crypto(Algorithm.PBEWITHMD5ANDDES,
                "0123456789ABHAEQ".getBytes());

        // 加密为16进制表示
        final String encryptHex = crypto.encryptHex(content);
        final String data = crypto.decryptString(encryptHex);

        Assertions.assertEquals(content, data);
    }

    @Test
    public void aesUpdateTest() {
        final String content = "4321c9a2db2e6b08987c3b903d8d11ff";
        final AES aes = new AES(Algorithm.Mode.CBC, Padding.PKCS5Padding, "0123456789ABHAEQ".getBytes(), "DYgjCEIMVrj2W9xN".getBytes());

        // 加密为16进制表示
        aes.setMode(Algorithm.Type.ENCRYPT);
        final String randomData = aes.updateHex(content.getBytes(StandardCharsets.UTF_8));
        aes.setMode(Algorithm.Type.ENCRYPT);
        final String randomData2 = aes.updateHex(content.getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(randomData2, randomData);
        Assertions.assertEquals(randomData, "cd0e3a249eaf0ed80c330338508898c4");
    }


    @Test
    public void aesZeroPaddingTest() {
        final String content = RandomKit.randomStringLower(RandomKit.randomInt(200));
        final AES aes = new AES(Algorithm.Mode.CBC, Padding.ZeroPadding, "0123456789ABHAEQ".getBytes(), "DYgjCEIMVrj2W9xN".getBytes());

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);
        // 解密
        final String decryptStr = aes.decryptString(encryptHex);
        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void aesZeroPaddingTest2() {
        final String content = "RandomKit.randomString(RandomKit.randomInt(2000))";
        final AES aes = new AES(Algorithm.Mode.CBC, Padding.ZeroPadding, "0123456789ABHAEQ".getBytes(), "DYgjCEIMVrj2W9xN".getBytes());

        final ByteArrayOutputStream encryptStream = new ByteArrayOutputStream();
        aes.encrypt(IoKit.toUtf8Stream(content), encryptStream, true);

        final ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
        aes.decrypt(IoKit.toStream(encryptStream), contentStream, true);

        Assertions.assertEquals(content, StringKit.toString(contentStream.toByteArray()));
    }

    @Test
    public void aesPkcs7PaddingTest() {
        final String content = RandomKit.randomStringLower(RandomKit.randomInt(200));
        final AES aes = new AES("CBC", "PKCS7Padding",
                RandomKit.randomBytes(32),
                "DYgjCEIMVrj2W9xN".getBytes());

        // 加密为16进制表示
        final String encryptHex = aes.encryptHex(content);
        // 解密
        final String decryptStr = aes.decryptString(encryptHex);
        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void vigenereTest() {
        final String content = "Wherethereisawillthereisaway";
        final String key = "CompleteVictory";

        final String encrypt = Vigenere.encrypt(content, key);
        Assertions.assertEquals("zXScRZ]KIOMhQjc0\\bYRXZOJK[Vi", encrypt);
        final String decrypt = Vigenere.decrypt(encrypt, key);
        Assertions.assertEquals(content, decrypt);
    }

}

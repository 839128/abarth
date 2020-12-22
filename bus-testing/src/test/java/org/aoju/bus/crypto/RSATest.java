package org.aoju.bus.crypto;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.crypto.asymmetric.KeyType;
import org.aoju.bus.crypto.asymmetric.RSA;
import org.junit.Assert;
import org.junit.Test;

import java.security.KeyPair;

/**
 * RSA算法单元测试
 */
public class RSATest {

    @Test
    public void generateKeyPairTest() {
        KeyPair pair = Builder.generateKeyPair("RSA");
        Assert.assertNotNull(pair.getPrivate());
        Assert.assertNotNull(pair.getPublic());
    }

    @Test
    public void rsaCustomKeyTest() {
        KeyPair pair = Builder.generateKeyPair("RSA");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        RSA rsa = Builder.rsa(privateKey, publicKey);

        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt, Charset.UTF_8));

        // 私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt2, Charset.UTF_8));
    }

    @Test
    public void rsaTest() {
        final RSA rsa = new RSA();

        // 获取私钥和公钥
        Assert.assertNotNull(rsa.getPrivateKey());
        Assert.assertNotNull(rsa.getPrivateKeyBase64());
        Assert.assertNotNull(rsa.getPublicKey());
        Assert.assertNotNull(rsa.getPrivateKeyBase64());

        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PublicKey);

        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt, Charset.UTF_8));

        // 私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt2, Charset.UTF_8));
    }

    @Test
    public void rsaWithBlockTest2() {
        final RSA rsa = new RSA();
        rsa.setEncryptBlockSize(3);

        // 获取私钥和公钥
        Assert.assertNotNull(rsa.getPrivateKey());
        Assert.assertNotNull(rsa.getPrivateKeyBase64());
        Assert.assertNotNull(rsa.getPublicKey());
        Assert.assertNotNull(rsa.getPrivateKeyBase64());

        // 公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt, Charset.UTF_8));

        // 私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt2, Charset.UTF_8));
    }

    @Test
    public void rsaBcdTest() {
        String text = "我是一段测试aaaa";

        final RSA rsa = new RSA();

        // 公钥加密，私钥解密
        String encryptStr = rsa.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StringKit.toString(rsa.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        Assert.assertEquals(text, decryptStr);

        // 私钥加密，公钥解密
        String encrypt2 = rsa.encryptBcd(text, KeyType.PrivateKey);
        String decrypt2 = StringKit.toString(rsa.decryptFromBcd(encrypt2, KeyType.PublicKey));
        Assert.assertEquals(text, decrypt2);
    }

    @Test
    public void rsaBase64Test() {
        String textBase = "我是一段特别长的测试";
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            text.append(textBase);
        }

        final RSA rsa = new RSA();

        // 公钥加密，私钥解密
        String encryptStr = rsa.encryptBase64(text.toString(), KeyType.PublicKey);
        String decryptStr = StringKit.toString(rsa.decrypt(encryptStr, KeyType.PrivateKey));
        Assert.assertEquals(text.toString(), decryptStr);

        // 私钥加密，公钥解密
        String encrypt2 = rsa.encryptBase64(text.toString(), KeyType.PrivateKey);
        String decrypt2 = StringKit.toString(rsa.decrypt(encrypt2, KeyType.PublicKey));
        Assert.assertEquals(text.toString(), decrypt2);
    }


}

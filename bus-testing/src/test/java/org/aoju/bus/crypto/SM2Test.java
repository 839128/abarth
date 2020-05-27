package org.aoju.bus.crypto;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.toolkit.HexKit;
import org.aoju.bus.core.toolkit.StringKit;
import org.aoju.bus.crypto.asymmetric.KeyType;
import org.aoju.bus.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * SM2算法单元测试
 */
public class SM2Test {

    @Test
    public void generateKeyPairTest() {
        KeyPair pair = Builder.generateKeyPair("SM2");
        Assertions.assertNotNull(pair.getPrivate());
        Assertions.assertNotNull(pair.getPublic());
    }

    @Test
    public void KeyPairOIDTest() {
        // OBJECT IDENTIFIER 1.2.156.10197.1.301
        String OID = "06082A811CCF5501822D";
        KeyPair pair = Builder.generateKeyPair("SM2");
        Assertions.assertTrue(HexKit.encodeHexStr(pair.getPrivate().getEncoded()).toUpperCase().contains(OID));
        Assertions.assertTrue(HexKit.encodeHexStr(pair.getPublic().getEncoded()).toUpperCase().contains(OID));
    }

    @Test
    public void sm2CustomKeyTest() {
        KeyPair pair = Builder.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm2 = Builder.sm2(privateKey, publicKey);
        sm2.setMode(SM2Engine.Mode.C1C3C2);

        // 公钥加密，私钥解密
        byte[] encrypt = sm2.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PublicKey);
        byte[] decrypt = sm2.decrypt(encrypt, KeyType.PrivateKey);
        Assertions.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt, Charset.UTF_8));
    }

    @Test
    public void sm2Test() {
        final SM2 sm2 = Builder.sm2();

        // 获取私钥和公钥
        Assertions.assertNotNull(sm2.getPrivateKey());
        Assertions.assertNotNull(sm2.getPrivateKeyBase64());
        Assertions.assertNotNull(sm2.getPublicKey());
        Assertions.assertNotNull(sm2.getPrivateKeyBase64());

        // 公钥加密，私钥解密
        byte[] encrypt = sm2.encrypt(StringKit.bytes("我是一段测试aaaa", Charset.UTF_8), KeyType.PublicKey);
        byte[] decrypt = sm2.decrypt(encrypt, KeyType.PrivateKey);
        Assertions.assertEquals("我是一段测试aaaa", StringKit.toString(decrypt, Charset.UTF_8));
    }

    @Test
    public void sm2BcdTest() {
        String text = "我是一段测试aaaa";

        final SM2 sm2 = Builder.sm2();

        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StringKit.toString(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        Assertions.assertEquals(text, decryptStr);
    }

    @Test
    public void sm2Base64Test() {
        String textBase = "我是一段特别长的测试";
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            text.append(textBase);
        }

        SM2 sm2 = new SM2();

        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBase64(text.toString(), KeyType.PublicKey);
        String decryptStr = StringKit.toString(sm2.decrypt(encryptStr, KeyType.PrivateKey));
        Assertions.assertEquals(text.toString(), decryptStr);

        // 测试自定义密钥后是否生效
        PrivateKey privateKey = sm2.getPrivateKey();
        PublicKey publicKey = sm2.getPublicKey();

        sm2 = Builder.sm2();
        sm2.setPrivateKey(privateKey);
        sm2.setPublicKey(publicKey);
        String decryptStr2 = StringKit.toString(sm2.decrypt(encryptStr, KeyType.PrivateKey));
        Assertions.assertEquals(text.toString(), decryptStr2);
    }

    @Test
    public void sm2SignAndVerifyTest() {
        String content = "我是kk.";

        final SM2 sm2 = Builder.sm2();

        byte[] sign = sm2.sign(content.getBytes());
        boolean verify = sm2.verify(content.getBytes(), sign);
        Assertions.assertTrue(verify);
    }

    @Test
    public void sm2SignAndVerifyUseKeyTest() {
        String content = "我是kk.";

        KeyPair pair = Builder.generateKeyPair("SM2");

        final SM2 sm2 = new SM2(
                HexKit.encodeHexStr(pair.getPrivate().getEncoded()),
                HexKit.encodeHexStr(pair.getPublic().getEncoded())
        );

        byte[] sign = sm2.sign(content.getBytes());
        boolean verify = sm2.verify(content.getBytes(), sign);
        Assertions.assertTrue(verify);
    }

}

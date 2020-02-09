package org.aoju.bus.crypto;

import org.aoju.bus.core.codec.Base64Encoder;
import org.aoju.bus.core.key.UUID;
import org.aoju.bus.core.lang.Algorithm;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.ResourceUtils;
import org.aoju.bus.crypto.algorithm.asymmetric.KeyType;
import org.aoju.bus.crypto.algorithm.asymmetric.RSA;
import org.aoju.bus.logger.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class BuilderTest {

    @Test
    public void readPrivateKeyTest() {
        PrivateKey privateKey = Builder.readPrivateKey(ResourceUtils.getStream("test_private_key.pem"));
        Assertions.assertNotNull(privateKey);
    }

    @Test
    public void readPublicKeyTest() {
        PublicKey publicKey = Builder.readPublicKey(ResourceUtils.getStream("test_public_key.csr"));
        Assertions.assertNotNull(publicKey);
    }

    @Test
    public void validateKey() {
        PrivateKey privateKey = Builder.readPrivateKey(ResourceUtils.getStream("test_private_key.pem"));
        PublicKey publicKey = Builder.readPublicKey(ResourceUtils.getStream("test_public_key.csr"));

        RSA rsa = new RSA(privateKey, publicKey);
        String str = "你好";//测试字符串

        String encryptStr = rsa.encryptBase64(str, KeyType.PublicKey);
        String decryptStr = rsa.decryptStr(encryptStr, KeyType.PrivateKey);
        Assertions.assertEquals(str, decryptStr);
    }

    @Test
    public void testAll() {
        String value = "iPhone";
        /** AES 加解密 */
        String key = "5c3ec809eaa7ecc5287dcff9";
        Logger.error("AES-K:" + key);
        value = Builder.encrypt(Algorithm.AES, key, value, Charset.UTF_8);
        Logger.error("AES-E:" + value);
        value = Builder.decrypt(Algorithm.AES, key, value, Charset.UTF_8);
        Logger.error("AES-D:" + value);

        /** DES 加解密 */
        key = Base64Encoder.encode(Builder.generateKey(Algorithm.DES).getEncoded());
        Logger.error("DES-K:" + key);
        value = Builder.encrypt(Algorithm.RC4, key, value, Charset.UTF_8);
        Logger.error("DES-E:" + value);
        value = Builder.decrypt(Algorithm.RC4, key, value, Charset.UTF_8);
        Logger.error("DES-D:" + value);

        /** RC4 加解密 */
        Logger.error("RC4-K:" + key);
        value = Builder.encrypt(Algorithm.RC4, key, value, Charset.UTF_8);
        Logger.error("RC4-E:" + value);
        value = Builder.decrypt(Algorithm.RC4, key, value, Charset.UTF_8);
        Logger.error("RC4-D:" + value);

        /** RSA 加解密 */
        KeyPair pair = Builder.generateKeyPair("RSA");
        key = Base64Encoder.encode(pair.getPrivate().getEncoded()) + ","
                + Base64Encoder.encode(pair.getPublic().getEncoded());
        Logger.error("RSA-K:" + key);
        value = Builder.encrypt(Algorithm.RSA, key + "," + Algorithm.TYPE_PRIVATE_KEY, value, Charset.UTF_8);
        Logger.error("RSA-E:" + value);
        value = Builder.decrypt(Algorithm.RSA, key + "," + Algorithm.TYPE_PUBLIC_KEY, value, Charset.UTF_8);
        Logger.error("RSA-D:" + value);

        /** SM2 加解密 */
        pair = Builder.generateKeyPair("SM2");
        key = Base64Encoder.encode(pair.getPrivate().getEncoded()) + ","
                + Base64Encoder.encode(pair.getPublic().getEncoded());
        Logger.error("SM2-K:" + key);
        value = Builder.encrypt(Algorithm.SM2, key + ",PublicKey", value, Charset.UTF_8);
        Logger.error("SM2-E:" + value);
        value = Builder.decrypt(Algorithm.SM2, key + ",PrivateKey", value, Charset.UTF_8);
        Logger.error("SM2-D:" + value);

        /** SM4 加解密 */
        key = UUID.randomUUID().toString().replace("-", "");
        Logger.error("SM4-K:" + key);
        value = Builder.encrypt(Algorithm.SM4, key, value, Charset.UTF_8);
        Logger.error("SM4-E:" + value);
        value = Builder.decrypt(Algorithm.SM4, key, value, Charset.UTF_8);
        Logger.error("SM4-D:" + value);
    }

}

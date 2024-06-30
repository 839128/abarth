package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.crypto.builtin.symmetric.Crypto;

import java.nio.charset.StandardCharsets;

public class RC4Test {

    @Test
    public void testCryptMessage() {
        final byte[] key = "This is pretty long key".getBytes();
        final Crypto SymmetricCrypto = new Crypto("RC4", key);
        final String message = "Hello, World!";
        final byte[] crypt = SymmetricCrypto.encrypt(message);
        final String msg = SymmetricCrypto.decryptString(crypt);
        Assertions.assertEquals(message, msg);

        final String message2 = "Hello, World， this is megssage 2";
        final byte[] crypt2 = SymmetricCrypto.encrypt(message2);
        final String msg2 = SymmetricCrypto.decryptString(crypt2);
        Assertions.assertEquals(message2, msg2);
    }

    @Test
    public void testCryptWithChineseCharacters() {
        final String message = "这是一个中文消息！";
        final byte[] key = "我是一个文件密钥".getBytes(StandardCharsets.UTF_8);
        final Crypto SymmetricCrypto = new Crypto("RC4", key);
        final byte[] crypt = SymmetricCrypto.encrypt(message);
        final String msg = SymmetricCrypto.decryptString(crypt);
        Assertions.assertEquals(message, msg);

        final String message2 = "这是第二个中文消息！";
        final byte[] crypt2 = SymmetricCrypto.encrypt(message2);
        final String msg2 = SymmetricCrypto.decryptString(crypt2);
        Assertions.assertEquals(message2, msg2);
    }

    @Test
    public void testDecryptWithHexMessage() {
        final String message = "这是第一个用来测试密文为十六进制字符串的消息！";
        final String key = "生成一个密钥";
        final Crypto SymmetricCrypto = new Crypto("RC4", key.getBytes(StandardCharsets.UTF_8));
        final String encryptHex = SymmetricCrypto.encryptHex(message, Charset.UTF_8);
        final String msg = SymmetricCrypto.decryptString(encryptHex);
        Assertions.assertEquals(message, msg);

        final String message2 = "这是第二个用来测试密文为十六进制字符串的消息！";
        final String encryptHex2 = SymmetricCrypto.encryptHex(message2);
        final String msg2 = SymmetricCrypto.decryptString(encryptHex2);
        Assertions.assertEquals(message2, msg2);
    }

    @Test
    public void testDecryptWithBase64Message() {
        final String message = "这是第一个用来测试密文为Base64编码的消息！";
        final String key = "生成一个密钥";
        final Crypto SymmetricCrypto = new Crypto("RC4", key.getBytes(StandardCharsets.UTF_8));
        final String encryptHex = SymmetricCrypto.encryptBase64(message, Charset.UTF_8);
        final String msg = SymmetricCrypto.decryptString(encryptHex);
        Assertions.assertEquals(message, msg);

        final String message2 = "这是第一个用来测试密文为Base64编码的消息！";
        final String encryptHex2 = SymmetricCrypto.encryptBase64(message2);
        final String msg2 = SymmetricCrypto.decryptString(encryptHex2);
        Assertions.assertEquals(message2, msg2);
    }

}

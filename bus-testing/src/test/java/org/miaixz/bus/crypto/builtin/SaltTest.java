package org.miaixz.bus.crypto.builtin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.builtin.symmetric.Crypto;

import javax.crypto.SecretKey;

public class SaltTest {

    /**
     * 测试：
     * https://www.bejson.com/enc/aesdes/
     */
    @Test
    void rc4Test() {
        final String encrypted = "U2FsdGVkX19DSROPe0+Ejkw84osqWw==";

        final byte[] salt = SaltMagic.getSalt(Builder.decode(encrypted));
        Assertions.assertNotNull(salt);

        final byte[][] keyAndIV = SaltParser.ofMd5(32, "RC4")
                .getKeyAndIV("1234567890123456".getBytes(), salt);
        Assertions.assertNotNull(keyAndIV);
        Assertions.assertNotNull(keyAndIV[0]);

        final SecretKey rc4Key = Keeper.generateKey("RC4", keyAndIV[0]);
        Assertions.assertNotNull(rc4Key);

        final byte[] data = SaltMagic.getData(Builder.decode(encrypted));

        final Crypto rc4 = new Crypto("RC4", rc4Key);
        final String decrypt = rc4.decryptString(data);
        Assertions.assertEquals("bus", decrypt);
    }

    /**
     * 测试：
     * https://www.bejson.com/enc/aesdes/
     */
    @Test
    void rc4Test2() {
        final String encrypted = "U2FsdGVkX19DSROPe0+Ejkw84osqWw==";
        final Crypto rc4 = new Crypto("RC4", "1234567890123456".getBytes());
        final String decrypt = rc4.decryptString(encrypted);
        Assertions.assertEquals("bus", decrypt);
    }

    /**
     * 测试：
     * https://www.bejson.com/enc/aesdes/
     */
    @Test
    void aesTest() {
        final String encrypted = "U2FsdGVkX1+lqsuKAR+OdOeNduvx5wgXf6yEUdDIh3g=";
        final Crypto des = new Crypto("AES/CBC/PKCS5Padding", "1234567890123456".getBytes());
        final String decrypt = des.decryptString(encrypted);
        Assertions.assertEquals("bus", decrypt);
    }

}

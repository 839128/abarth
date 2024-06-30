package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.RandomKit;

/**
 * 见：https://stackoverflow.com/questions/32672241/using-bouncycastles-chacha-for-file-encryption
 */
public class ChaCha20Test {

    @Test
    public void encryptAndDecryptTest() {
        // 32 for 256-bit key or 16 for 128 bit
        final byte[] key = RandomKit.randomBytes(32);
        // 64 bit IV required by ChaCha20
        final byte[] iv = RandomKit.randomBytes(12);

        final ChaCha20 chacha = new ChaCha20(key, iv);

        final String content = "test中文";
        // 加密为16进制表示
        final String encryptHex = chacha.encryptHex(content);
        // 解密为字符串
        final String decryptStr = chacha.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

}

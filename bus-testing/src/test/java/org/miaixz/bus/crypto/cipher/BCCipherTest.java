package org.miaixz.bus.crypto.cipher;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DefaultBufferedBlockCipher;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.xyz.ByteKit;

public class BCCipherTest {

    @Test
    void sm4Test() {
        final byte[] data = ByteKit.toBytes("我是测试字符串00");

        final BufferedBlockCipher blockCipher = new DefaultBufferedBlockCipher(CBCBlockCipher.newInstance(new SM4Engine()));
        final BCCipher bcCipher = new BCCipher(blockCipher);
        bcCipher.init(Algorithm.Type.ENCRYPT, new BCCipher.BCParameters(new KeyParameter(ByteKit.toBytes("1234567890000000"))));
        final byte[] encryptData = bcCipher.processFinal(data);

        bcCipher.init(Algorithm.Type.DECRYPT, new BCCipher.BCParameters(new KeyParameter(ByteKit.toBytes("1234567890000000"))));
        final byte[] decryptData = bcCipher.processFinal(encryptData);

        Assertions.assertArrayEquals(data, decryptData);
    }

}

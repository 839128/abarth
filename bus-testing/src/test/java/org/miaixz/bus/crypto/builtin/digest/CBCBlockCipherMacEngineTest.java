package org.miaixz.bus.crypto.builtin.digest;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.builtin.digest.mac.SM4Mac;
import org.miaixz.bus.crypto.center.Mac;

public class CBCBlockCipherMacEngineTest {

    @Test
    public void SM4CMACTest() {
        final byte[] key = new byte[16];
        final CipherParameters parameter = new KeyParameter(Keeper.generateKey("SM4", key).getEncoded());
        final Mac mac = new Mac(new SM4Mac(parameter));

        // 原文
        final String testStr = "test中文";

        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("3212e848db7f816a4bd591ad9948debf", macHex1);
    }

    @Test
    public void SM4CMACWithIVTest() {
        final byte[] key = new byte[16];
        final byte[] iv = new byte[16];
        CipherParameters parameter = new KeyParameter(Keeper.generateKey("SM4", key).getEncoded());
        parameter = new ParametersWithIV(parameter, iv);
        final Mac mac = new Mac(new SM4Mac(parameter));

        // 原文
        final String testStr = "test中文";

        final String macHex1 = mac.digestHex(testStr);
        Assertions.assertEquals("3212e848db7f816a4bd591ad9948debf", macHex1);
    }

}

package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.RandomKit;

public class ZUCTest {

    @Test
    public void zuc128Test() {
        final byte[] secretKey = ZUC.generateKey(ZUC.ZUCAlgorithm.ZUC_128);
        final byte[] iv = RandomKit.randomBytes(16);
        final ZUC zuc = new ZUC(ZUC.ZUCAlgorithm.ZUC_128, secretKey, iv);

        final String msg = RandomKit.randomStringLower(500);
        final byte[] crypt2 = zuc.encrypt(msg);
        final String msg2 = zuc.decryptString(crypt2, Charset.UTF_8);
        Assertions.assertEquals(msg, msg2);
    }

    @Test
    public void zuc256Test() {
        final byte[] secretKey = ZUC.generateKey(ZUC.ZUCAlgorithm.ZUC_256);
        final byte[] iv = RandomKit.randomBytes(25);
        final ZUC zuc = new ZUC(ZUC.ZUCAlgorithm.ZUC_256, secretKey, iv);

        final String msg = RandomKit.randomStringLower(500);
        final byte[] crypt2 = zuc.encrypt(msg);
        final String msg2 = zuc.decryptString(crypt2, Charset.UTF_8);
        Assertions.assertEquals(msg, msg2);
    }

}

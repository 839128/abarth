package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.Padding;

public class TDEATest {

    @Test
    public void tdeaTest2() {
        final String content = "test中文";
        final byte[] key = Keeper.generateKey(Algorithm.DESEDE.getValue()).getEncoded();
        final TDEA des = new TDEA(Algorithm.Mode.CBC, Padding.PKCS5Padding, key, "12345678".getBytes());

        final byte[] encrypt = des.encrypt(content);
        final byte[] decrypt = des.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        final String encryptHex = des.encryptHex(content);
        final String decryptStr = des.decryptString(encryptHex);

        Assertions.assertEquals(content, decryptStr);
    }


    @Test
    public void tdeaTest() {
        final String content = "test中文";
        final byte[] key = Keeper.generateKey(Algorithm.DESEDE.getValue()).getEncoded();
        final TDEA des = Builder.tdea(key);

        final byte[] encrypt = des.encrypt(content);
        final byte[] decrypt = des.decrypt(encrypt);

        Assertions.assertEquals(content, StringKit.toString(decrypt));

        final String encryptHex = des.encryptHex(content);
        final String decryptStr = des.decryptString(encryptHex);

        Assertions.assertEquals(content, decryptStr);
    }

}

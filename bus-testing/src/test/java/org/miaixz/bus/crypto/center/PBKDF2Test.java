package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.RandomKit;
import org.miaixz.bus.crypto.Builder;

public class PBKDF2Test {

    @Test
    public void encryptTest() {
        final String s = Builder.pbkdf2("123456".toCharArray(), RandomKit.randomBytes(16));
        Assertions.assertEquals(128, s.length());
    }

}

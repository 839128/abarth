package org.miaixz.bus.crypto.builtin.digest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.crypto.center.BCrypt;

public class BCryptTest {

    @Test
    public void checkpwTest() {
        Assertions.assertFalse(BCrypt.checkpw("xxx",
                "$2a$2a$10$e4lBTlZ019KhuAFyqAlgB.Jxc6cM66GwkSR/5/xXNQuHUItPLyhzy"));
    }

}

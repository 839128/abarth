package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.crypto.Builder;

/**
 * SM3单元测试
 */
public class SM3Test {

    @Test
    public void sm3Test() {
        final String digestHex = Builder.sm3("aaaaa");
        Assertions.assertEquals("136ce3c86e4ed909b76082055a61586af20b4dab674732ebd4b599eef080c9be", digestHex);
    }

    @Test
    public void hmacSm3Test() {
        final String content = "test中文";
        final HMac hMac = Builder.hmacSm3("password".getBytes());
        final String digest = hMac.digestHex(content);
        Assertions.assertEquals("493e3f9a1896b43075fbe54658076727960d69632ac6b6ed932195857a6840c6", digest);
    }

}

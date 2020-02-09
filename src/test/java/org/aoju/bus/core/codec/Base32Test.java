package org.aoju.bus.core.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base32Test {

    @Test
    public void encodeAndDecodeTest() {
        String a = "非常长的字符串";
        String encode = Base32.encode(a);

        String decodeStr = Base32.decodeStr(encode);
        Assertions.assertEquals(a, decodeStr);
    }

}

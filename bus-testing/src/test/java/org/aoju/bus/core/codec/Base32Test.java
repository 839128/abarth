package org.aoju.bus.core.codec;

import org.junit.Assert;
import org.junit.Test;

public class Base32Test {

    @Test
    public void encodeAndDecodeTest() {
        String a = "非常长的字符串";
        String encode = Base32.encode(a);

        String decodeStr = Base32.decodeStr(encode);
        Assert.assertEquals(a, decodeStr);
    }

}

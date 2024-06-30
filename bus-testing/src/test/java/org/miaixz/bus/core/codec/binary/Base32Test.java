package org.miaixz.bus.core.codec.binary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.RandomKit;

public class Base32Test {

    @Test
    public void encodeAndDecodeTest() {
        final String a = "伦家是一个非常长的字符串";
        final String encode = Base32.encode(a);
        Assertions.assertEquals("4S6KNZNOW3TJRL7EXCAOJOFK5GOZ5ZNYXDUZLP7HTKCOLLMX46WKNZFYWI======", encode);

        String decodeString = Base32.decodeString(encode);
        Assertions.assertEquals(a, decodeString);

        // 支持小写模式解码
        decodeString = Base32.decodeString(encode.toLowerCase());
        Assertions.assertEquals(a, decodeString);
    }

    @Test
    public void hexEncodeAndDecodeTest() {
        final String a = "伦家是一个非常长的字符串";
        final String encode = Base32.encodeHex(ByteKit.toBytes(a));
        Assertions.assertEquals("SIUADPDEMRJ9HBV4N20E9E5AT6EPTPDON3KPBFV7JA2EBBCNSUMADP5OM8======", encode);

        String decodeString = Base32.decodeStrHex(encode);
        Assertions.assertEquals(a, decodeString);

        // 支持小写模式解码
        decodeString = Base32.decodeStrHex(encode.toLowerCase());
        Assertions.assertEquals(a, decodeString);
    }

    @Test
    public void encodeAndDecodeRandomTest() {
        final String a = RandomKit.randomStringLower(RandomKit.randomInt(1000));
        final String encode = Base32.encode(a);
        final String decodeString = Base32.decodeString(encode);
        Assertions.assertEquals(a, decodeString);
    }

    @Test
    public void decodeTest() {
        final String a = "伦家是一个非常长的字符串";
        final String decodeString = Base32.decodeString("4S6KNZNOW3TJRL7EXCAOJOFK5GOZ5ZNYXDUZLP7HTKCOLLMX46WKNZFYWI");
        Assertions.assertEquals(a, decodeString);
    }

}

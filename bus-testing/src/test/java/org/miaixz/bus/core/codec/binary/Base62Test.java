package org.miaixz.bus.core.codec.binary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.RandomKit;

/**
 * Base62单元测试
 */
public class Base62Test {

    @Test
    public void encodeAndDecodeTest() {
        final String a = "伦家是一个非常长的字符串66";
        final String encode = Base62.encode(a);
        Assertions.assertEquals("17vKU8W4JMG8dQF8lk9VNnkdMOeWn4rJMva6F0XsLrrT53iKBnqo", encode);

        final String decodeStr = Base62.decodeString(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeInvertedTest() {
        final String a = "伦家是一个非常长的字符串66";
        final String encode = Base62.encodeInverted(a);
        Assertions.assertEquals("17Vku8w4jmg8Dqf8LK9vnNKDmoEwN4RjmVA6f0xSlRRt53IkbNQO", encode);

        final String decodeStr = Base62.decodeStrInverted(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeRandomTest() {
        final String a = RandomKit.randomStringLower(RandomKit.randomInt(1000));
        final String encode = Base62.encode(a);
        final String decodeStr = Base62.decodeString(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    public void encodeAndDecodeInvertedRandomTest() {
        final String a = RandomKit.randomStringLower(RandomKit.randomInt(1000));
        final String encode = Base62.encodeInverted(a);
        final String decodeStr = Base62.decodeStrInverted(encode);
        Assertions.assertEquals(a, decodeStr);
    }

    @Test
    void encodeNumbersTest() {
        final String encode = Base62.encode("181338494");
        Assertions.assertEquals("HRmWh8NiFvYi", encode);
    }

}

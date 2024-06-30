package org.miaixz.bus.core.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringRepeaterTest {

    @Test
    public void repeatByLengthTest() {
        // 如果指定长度非指定字符串的整数倍，截断到固定长度
        final String ab = StringRepeater.of(5).repeatByLength("ab");
        Assertions.assertEquals("ababa", ab);
    }

    @Test
    public void repeatByLengthTest2() {
        // 如果指定长度小于字符串本身的长度，截断之
        final String ab = StringRepeater.of(2).repeatByLength("abcde");
        Assertions.assertEquals("ab", ab);
    }

}

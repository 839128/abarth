package org.miaixz.bus.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberParserTest {

    @Test
    void parseLongTest() {
        final long value = NumberParser.INSTANCE.parseLong("0.a");
        Assertions.assertEquals(0L, value);
    }

}

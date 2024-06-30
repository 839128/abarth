package org.miaixz.bus.core.center.date.culture.en;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VariousTest {

    @Test
    public void ofTest() {
        Various field = Various.of(11);
        Assertions.assertEquals(Various.HOUR_OF_DAY, field);
        field = Various.of(12);
        Assertions.assertEquals(Various.MINUTE, field);
    }

}

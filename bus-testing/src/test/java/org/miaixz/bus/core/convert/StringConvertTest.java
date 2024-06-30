package org.miaixz.bus.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

public class StringConvertTest {

    @Test
    public void timezoneToStrTest() {
        final String s = Convert.toString(TimeZone.getTimeZone("Asia/Shanghai"));
        Assertions.assertEquals("Asia/Shanghai", s);
    }

}

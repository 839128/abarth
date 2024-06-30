package org.miaixz.bus.core.center.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Objects;

public class CalendarTest {

    @Test
    public void formatChineseDate() {
        final java.util.Calendar calendar = Objects.requireNonNull(DateKit.parse("2018-02-24 12:13:14")).toCalendar();
        final String chineseDate = Calendar.formatChineseDate(calendar, false);
        Assertions.assertEquals("二〇一八年二月二十四日", chineseDate);
        final String chineseDateTime = Calendar.formatChineseDate(calendar, true);
        Assertions.assertEquals("二〇一八年二月二十四日十二时十三分十四秒", chineseDateTime);
    }

    @Test
    public void parseTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final java.util.Calendar calendar = Calendar.parse("2021-09-27 00:00:112323", false,
                    Formatter.NORM_DATETIME_FORMAT);

            // 在使用严格模式时，秒不正确，抛出异常
            DateKit.date(calendar);
        });
    }

}

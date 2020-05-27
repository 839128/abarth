package org.aoju.bus.core.date;

import org.aoju.bus.core.lang.Fields;
import org.aoju.bus.core.toolkit.DateKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class BetweenTest {

    @Test
    public void betweenYearTest() {
        Date start = DateKit.parse("2017-02-01 12:23:46");
        Date end = DateKit.parse("2018-02-01 12:23:46");
        long betweenYear = new Between(start, end).betweenYear(false);
        Assertions.assertEquals(1, betweenYear);

        Date start1 = DateKit.parse("2017-02-01 12:23:46");
        Date end1 = DateKit.parse("2018-03-01 12:23:46");
        long betweenYear1 = new Between(start1, end1).betweenYear(false);
        Assertions.assertEquals(1, betweenYear1);

        // 不足1年
        Date start2 = DateKit.parse("2017-02-01 12:23:46");
        Date end2 = DateKit.parse("2018-02-01 11:23:46");
        long betweenYear2 = new Between(start2, end2).betweenYear(false);
        Assertions.assertEquals(0, betweenYear2);
    }

    @Test
    public void betweenYearTest2() {
        Date start = DateKit.parse("2000-02-29");
        Date end = DateKit.parse("2018-02-28");
        long betweenYear = new Between(start, end).betweenYear(false);
        Assertions.assertEquals(18, betweenYear);
    }

    @Test
    public void betweenMonthTest() {
        Date start = DateKit.parse("2017-02-01 12:23:46");
        Date end = DateKit.parse("2018-02-01 12:23:46");
        long betweenMonth = new Between(start, end).betweenMonth(false);
        Assertions.assertEquals(12, betweenMonth);

        Date start1 = DateKit.parse("2017-02-01 12:23:46");
        Date end1 = DateKit.parse("2018-03-01 12:23:46");
        long betweenMonth1 = new Between(start1, end1).betweenMonth(false);
        Assertions.assertEquals(13, betweenMonth1);

        // 不足
        Date start2 = DateKit.parse("2017-02-01 12:23:46");
        Date end2 = DateKit.parse("2018-02-01 11:23:46");
        long betweenMonth2 = new Between(start2, end2).betweenMonth(false);
        Assertions.assertEquals(11, betweenMonth2);
    }

    @Test
    public void betweenMinuteTest() {
        Date date1 = DateKit.parse("2017-03-01 20:33:23");
        Date date2 = DateKit.parse("2017-03-01 23:33:23");
        String formatBetween = DateKit.formatBetween(date1, date2, Fields.Level.SECOND);
        Assertions.assertEquals("3小时", formatBetween);
    }

}

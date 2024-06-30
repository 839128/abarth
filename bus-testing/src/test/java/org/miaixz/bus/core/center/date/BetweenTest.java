package org.miaixz.bus.core.center.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.en.Units;
import org.miaixz.bus.core.center.date.format.FormatPeriod;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Date;

public class BetweenTest {

    @Test
    public void betweenYearTest() {
        final Date start = DateKit.parse("2017-02-01 12:23:46");
        final Date end = DateKit.parse("2018-02-01 12:23:46");
        final long betweenYear = new Between(start, end).betweenYear(false);
        Assertions.assertEquals(1, betweenYear);

        final Date start1 = DateKit.parse("2017-02-01 12:23:46");
        final Date end1 = DateKit.parse("2018-03-01 12:23:46");
        final long betweenYear1 = new Between(start1, end1).betweenYear(false);
        Assertions.assertEquals(1, betweenYear1);

        // 不足1年
        final Date start2 = DateKit.parse("2017-02-01 12:23:46");
        final Date end2 = DateKit.parse("2018-02-01 11:23:46");
        final long betweenYear2 = new Between(start2, end2).betweenYear(false);
        Assertions.assertEquals(0, betweenYear2);
    }

    @Test
    public void betweenYearTest2() {
        final Date start = DateKit.parse("2000-02-29");
        final Date end = DateKit.parse("2018-02-28");
        final long betweenYear = new Between(start, end).betweenYear(false);
        Assertions.assertEquals(18, betweenYear);
    }

    @Test
    public void betweenYearTest3() {
        final String dateStr1 = "2023-02-28 00:00:01";
        final Date sdate = DateKit.parse(dateStr1);
        final String dateStr2 = "2024-02-29 00:00:00";
        final Date edate = DateKit.parse(dateStr2);

        final long result = DateKit.betweenYear(sdate, edate, false);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void betweenYearTest4() {
        final String dateStr1 = "2024-02-29 00:00:00";
        final Date sdate = DateKit.parse(dateStr1);
        final String dateStr2 = "2025-02-28 00:00:00";
        final Date edate = DateKit.parse(dateStr2);

        final long result = DateKit.betweenYear(sdate, edate, false);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void issueTest() {
        final String dateStr1 = "2024-02-29 23:59:59";
        final Date sdate = DateKit.parse(dateStr1);

        final String dateStr2 = "2023-03-01 00:00:00";
        final Date edate = DateKit.parse(dateStr2);

        final long result = DateKit.betweenYear(sdate, edate, false);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void betweenMonthTest() {
        final Date start = DateKit.parse("2017-02-01 12:23:46");
        final Date end = DateKit.parse("2018-02-01 12:23:46");
        final long betweenMonth = new Between(start, end).betweenMonth(false);
        Assertions.assertEquals(12, betweenMonth);

        final Date start1 = DateKit.parse("2017-02-01 12:23:46");
        final Date end1 = DateKit.parse("2018-03-01 12:23:46");
        final long betweenMonth1 = new Between(start1, end1).betweenMonth(false);
        Assertions.assertEquals(13, betweenMonth1);

        // 不足
        final Date start2 = DateKit.parse("2017-02-01 12:23:46");
        final Date end2 = DateKit.parse("2018-02-01 11:23:46");
        final long betweenMonth2 = new Between(start2, end2).betweenMonth(false);
        Assertions.assertEquals(11, betweenMonth2);
    }

    @Test
    public void betweenMinuteTest() {
        final Date date1 = DateKit.parse("2017-03-01 20:33:23");
        final Date date2 = DateKit.parse("2017-03-01 23:33:23");
        final String formatBetween = DateKit.formatBetween(date1, date2, FormatPeriod.Level.SECOND);
        Assertions.assertEquals("3小时", formatBetween);
    }

    @Test
    public void betweenWeeksTest() {
        final long betweenWeek = DateKit.betweenWeek(
                DateKit.parse("2020-11-21"),
                DateKit.parse("2020-11-23"), false);

        final long betweenWeek2 = DateKit.between(
                DateKit.parse("2020-11-21", "yyy-MM-dd"),
                DateKit.parse("2020-11-23", "yyy-MM-dd"), Units.WEEK);
        Assertions.assertEquals(betweenWeek, betweenWeek2);
    }

}

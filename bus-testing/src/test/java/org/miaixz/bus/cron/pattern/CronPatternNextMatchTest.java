package org.miaixz.bus.cron.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.DateTime;
import org.miaixz.bus.core.center.date.culture.en.Various;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Calendar;

public class CronPatternNextMatchTest {

    @Test
    public void nextMatchAllAfterTest() {
        // 匹配所有，返回下一秒的时间
        CronPattern pattern = new CronPattern("* * * * * * *");
        DateTime date = DateKit.truncate(DateKit.now(), Various.SECOND);
        Calendar calendar = pattern.nextMatchAfter(date.toCalendar());
        Assertions.assertEquals(date.getTime() + 1000, DateKit.date(calendar).getTime());

        // 匹配所有分，返回下一分钟
        pattern = new CronPattern("0 * * * * * *");
        date = DateKit.parse("2022-04-08 07:44:16");
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(date.toCalendar());
        Assertions.assertEquals(DateKit.parse("2022-04-08 07:45:00"), DateKit.date(calendar));

        // 匹配所有时，返回下一小时
        pattern = new CronPattern("0 0 * * * * *");
        date = DateKit.parse("2022-04-08 07:44:16");
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(date.toCalendar());
        Assertions.assertEquals(DateKit.parse("2022-04-08 08:00:00"), DateKit.date(calendar));

        // 匹配所有天，返回明日
        pattern = new CronPattern("0 0 0 * * * *");
        date = DateKit.parse("2022-04-08 07:44:16");
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(date.toCalendar());
        Assertions.assertEquals(DateKit.parse("2022-04-09 00:00:00"), DateKit.date(calendar));

        // 匹配所有月，返回下一月
        pattern = new CronPattern("0 0 0 1 * * *");
        date = DateKit.parse("2022-04-08 07:44:16");
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(date.toCalendar());
        Assertions.assertEquals(DateKit.parse("2022-05-01 00:00:00"), DateKit.date(calendar));
    }

    @Test
    public void nextMatchAfterTest() {
        final CronPattern pattern = new CronPattern("23 12 * 12 * * *");

        // 时间正常递增
        //noinspection ConstantConditions
        Calendar calendar = pattern.nextMatchAfter(
                DateKit.parse("2022-04-12 09:12:12").toCalendar());

        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-04-12 09:12:23", DateKit.date(calendar).toString());

        // 秒超出规定值的最大值，分+1，秒取最小值
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(
                DateKit.parse("2022-04-12 09:09:24").toCalendar());
        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-04-12 09:12:23", DateKit.date(calendar).toString());

        // 秒超出规定值的最大值，分不变，小时+1，秒和分使用最小值
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(
                DateKit.parse("2022-04-12 09:12:24").toCalendar());
        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-04-12 10:12:23", DateKit.date(calendar).toString());

        // 天超出规定值的最大值，月+1，天、时、分、秒取最小值
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(
                DateKit.parse("2022-04-13 09:12:24").toCalendar());
        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-05-12 00:12:23", DateKit.date(calendar).toString());

        // 跨年
        //noinspection ConstantConditions
        calendar = pattern.nextMatchAfter(
                DateKit.parse("2021-12-22 00:00:00").toCalendar());
        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-01-12 00:12:23", DateKit.date(calendar).toString());
    }

    @Test
    public void nextMatchAfterTest2() {
        final CronPattern pattern = new CronPattern("23 12 * 12 * * *");

        // 秒超出规定值的最大值，分不变，小时+1，秒和分使用最小值
        //noinspection ConstantConditions
        final Calendar calendar = pattern.nextMatchAfter(
                DateKit.parse("2022-04-12 09:12:24").toCalendar());
        Assertions.assertTrue(pattern.match(calendar, true));
        Assertions.assertEquals("2022-04-12 10:12:23", DateKit.date(calendar).toString());
    }

    @Test
    public void nextMatchAfterByWeekTest() {
        final CronPattern pattern = new CronPattern("1 1 1 * * Sat *");
        // 周日，下个周六在4月9日
        final DateTime time = DateKit.parse("2022-04-03");
        assert time != null;
        final Calendar calendar = pattern.nextMatchAfter(time.toCalendar());
        Assertions.assertEquals("2022-04-09 01:01:01", DateKit.date(calendar).toString());
    }

}

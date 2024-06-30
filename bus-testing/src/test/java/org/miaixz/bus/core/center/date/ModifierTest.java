package org.miaixz.bus.core.center.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.en.Various;
import org.miaixz.bus.core.lang.Fields;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Date;

public class ModifierTest {

    @Test
    public void truncateTest() {
        final String dateStr = "2017-03-01 22:33:23.123";
        final Date date = DateKit.parse(dateStr);

        // 毫秒
        DateTime begin = DateKit.truncate(date, Various.MILLISECOND);
        Assertions.assertEquals(dateStr, begin.toString(Fields.NORM_DATETIME_MS));

        // 秒
        begin = DateKit.truncate(date, Various.SECOND);
        Assertions.assertEquals("2017-03-01 22:33:23.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 分
        begin = DateKit.truncate(date, Various.MINUTE);
        Assertions.assertEquals("2017-03-01 22:33:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 小时
        begin = DateKit.truncate(date, Various.HOUR);
        Assertions.assertEquals("2017-03-01 22:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.truncate(date, Various.HOUR_OF_DAY);
        Assertions.assertEquals("2017-03-01 22:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 上下午，原始日期是22点，上下午的起始就是12点
        begin = DateKit.truncate(date, Various.AM_PM);
        Assertions.assertEquals("2017-03-01 12:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 天，day of xxx按照day处理
        begin = DateKit.truncate(date, Various.DAY_OF_WEEK_IN_MONTH);
        Assertions.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.truncate(date, Various.DAY_OF_WEEK);
        Assertions.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.truncate(date, Various.DAY_OF_MONTH);
        Assertions.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 星期
        begin = DateKit.truncate(date, Various.WEEK_OF_MONTH);
        Assertions.assertEquals("2017-02-27 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.truncate(date, Various.WEEK_OF_YEAR);
        Assertions.assertEquals("2017-02-27 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 月
        begin = DateKit.truncate(date, Various.MONTH);
        Assertions.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));

        // 年
        begin = DateKit.truncate(date, Various.YEAR);
        Assertions.assertEquals("2017-01-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
    }

    @Test
    public void truncateDayOfWeekInMonthTest() {
        final String dateStr = "2017-03-01 22:33:23.123";
        final Date date = DateKit.parse(dateStr);

        // 天，day of xxx按照day处理
        final DateTime begin = DateKit.truncate(date, Various.DAY_OF_WEEK_IN_MONTH);
        Assertions.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS));
    }

    @Test
    public void ceilingTest() {
        final String dateStr = "2017-03-01 22:33:23.123";
        final Date date = DateKit.parse(dateStr);

        // 毫秒
        DateTime begin = DateKit.ceiling(date, Various.MILLISECOND);
        Assertions.assertEquals(dateStr, begin.toString(Fields.NORM_DATETIME_MS));

        // 秒
        begin = DateKit.ceiling(date, Various.SECOND);
        Assertions.assertEquals("2017-03-01 22:33:23.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 分
        begin = DateKit.ceiling(date, Various.MINUTE);
        Assertions.assertEquals("2017-03-01 22:33:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 小时
        begin = DateKit.ceiling(date, Various.HOUR);
        Assertions.assertEquals("2017-03-01 22:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.ceiling(date, Various.HOUR_OF_DAY);
        Assertions.assertEquals("2017-03-01 22:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 上下午，原始日期是22点，上下午的结束就是23点
        begin = DateKit.ceiling(date, Various.AM_PM);
        Assertions.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 天，day of xxx按照day处理
        begin = DateKit.ceiling(date, Various.DAY_OF_WEEK_IN_MONTH);
        Assertions.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.ceiling(date, Various.DAY_OF_WEEK);
        Assertions.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.ceiling(date, Various.DAY_OF_MONTH);
        Assertions.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 星期
        begin = DateKit.ceiling(date, Various.WEEK_OF_MONTH);
        Assertions.assertEquals("2017-03-05 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));
        begin = DateKit.ceiling(date, Various.WEEK_OF_YEAR);
        Assertions.assertEquals("2017-03-05 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 月
        begin = DateKit.ceiling(date, Various.MONTH);
        Assertions.assertEquals("2017-03-31 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));

        // 年
        begin = DateKit.ceiling(date, Various.YEAR);
        Assertions.assertEquals("2017-12-31 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS));
    }

    @Test
    public void roundTest() {
        final String dateStr = "2022-08-12 14:59:21.500";
        final Date date = DateKit.parse(dateStr);

        final DateTime dateTime = DateKit.round(date, Various.SECOND);
        Assertions.assertEquals("2022-08-12 14:59:21.999", dateTime.toString(Fields.NORM_DATETIME_MS));
    }

}

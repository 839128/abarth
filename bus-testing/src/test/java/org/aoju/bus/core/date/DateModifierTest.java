package org.aoju.bus.core.date;

import org.aoju.bus.core.lang.Fields;
import org.aoju.bus.core.toolkit.DateKit;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class DateModifierTest {

    @Test
    public void truncateTest() {
        String dateStr = "2017-03-01 22:33:23.123";
        Date date = DateKit.parse(dateStr);

        // 毫秒
        DateTime begin = DateKit.truncate(date, Fields.Type.MILLISECOND);
        Assert.assertEquals(dateStr, begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 秒
        begin = DateKit.truncate(date, Fields.Type.SECOND);
        Assert.assertEquals("2017-03-01 22:33:23.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 分
        begin = DateKit.truncate(date, Fields.Type.MINUTE);
        Assert.assertEquals("2017-03-01 22:33:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 小时
        begin = DateKit.truncate(date, Fields.Type.HOUR);
        Assert.assertEquals("2017-03-01 22:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.truncate(date, Fields.Type.HOUR_OF_DAY);
        Assert.assertEquals("2017-03-01 22:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 上下午，原始日期是22点，上下午的起始就是12点
        begin = DateKit.truncate(date, Fields.Type.AM_PM);
        Assert.assertEquals("2017-03-01 12:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 天，day of xxx按照day处理
        begin = DateKit.truncate(date, Fields.Type.DAY_OF_WEEK_IN_MONTH);
        Assert.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.truncate(date, Fields.Type.DAY_OF_WEEK);
        Assert.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.truncate(date, Fields.Type.DAY_OF_MONTH);
        Assert.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 星期
        begin = DateKit.truncate(date, Fields.Type.WEEK_OF_MONTH);
        Assert.assertEquals("2017-02-27 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.truncate(date, Fields.Type.WEEK_OF_YEAR);
        Assert.assertEquals("2017-02-27 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 月
        begin = DateKit.truncate(date, Fields.Type.MONTH);
        Assert.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 年
        begin = DateKit.truncate(date, Fields.Type.YEAR);
        Assert.assertEquals("2017-01-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
    }

    @Test
    public void truncateDayOfWeekInMonthTest() {
        String dateStr = "2017-03-01 22:33:23.123";
        Date date = DateKit.parse(dateStr);

        // 天，day of xxx按照day处理
        DateTime begin = DateKit.truncate(date, Fields.Type.DAY_OF_WEEK_IN_MONTH);
        Assert.assertEquals("2017-03-01 00:00:00.000", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
    }

    @Test
    public void ceilingTest() {
        String dateStr = "2017-03-01 22:33:23.123";
        Date date = DateKit.parse(dateStr);

        // 毫秒
        DateTime begin = DateKit.ceiling(date, Fields.Type.MILLISECOND);
        Assert.assertEquals(dateStr, begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 秒
        begin = DateKit.ceiling(date, Fields.Type.SECOND);
        Assert.assertEquals("2017-03-01 22:33:23.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 分
        begin = DateKit.ceiling(date, Fields.Type.MINUTE);
        Assert.assertEquals("2017-03-01 22:33:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 小时
        begin = DateKit.ceiling(date, Fields.Type.HOUR);
        Assert.assertEquals("2017-03-01 22:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.ceiling(date, Fields.Type.HOUR_OF_DAY);
        Assert.assertEquals("2017-03-01 22:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 上下午，原始日期是22点，上下午的结束就是23点
        begin = DateKit.ceiling(date, Fields.Type.AM_PM);
        Assert.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 天，day of xxx按照day处理
        begin = DateKit.ceiling(date, Fields.Type.DAY_OF_WEEK_IN_MONTH);
        Assert.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.ceiling(date, Fields.Type.DAY_OF_WEEK);
        Assert.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.ceiling(date, Fields.Type.DAY_OF_MONTH);
        Assert.assertEquals("2017-03-01 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 星期
        begin = DateKit.ceiling(date, Fields.Type.WEEK_OF_MONTH);
        Assert.assertEquals("2017-03-05 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
        begin = DateKit.ceiling(date, Fields.Type.WEEK_OF_YEAR);
        Assert.assertEquals("2017-03-05 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 月
        begin = DateKit.ceiling(date, Fields.Type.MONTH);
        Assert.assertEquals("2017-03-31 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));

        // 年
        begin = DateKit.ceiling(date, Fields.Type.YEAR);
        Assert.assertEquals("2017-12-31 23:59:59.999", begin.toString(Fields.NORM_DATETIME_MS_PATTERN));
    }
}

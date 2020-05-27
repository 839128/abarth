package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.date.Boundary;
import org.aoju.bus.core.date.DateTime;
import org.aoju.bus.core.date.TimeInterval;
import org.aoju.bus.core.date.format.FormatBuilder;
import org.aoju.bus.core.lang.Fields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间工具单元测试
 * 此单元测试依赖时区为中国+08:00
 *
 * <pre>
 * export TZ=Asia/Shanghai
 * </pre>
 */
public class DateKitTest {

    @Test
    public void nowTest() {
        // 当前时间
        Date date = DateKit.date();
        Assertions.assertNotNull(date);
        // 当前时间
        Date date2 = DateKit.date(Calendar.getInstance());
        Assertions.assertNotNull(date2);
        // 当前时间
        Date date3 = DateKit.date(System.currentTimeMillis());
        Assertions.assertNotNull(date3);

        // 当前日期字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateKit.now();
        Assertions.assertNotNull(now);
        // 当前日期字符串，格式：yyyy-MM-dd
        String today = DateKit.format(DateKit.date(), Fields.NORM_DATE_PATTERN);
        Assertions.assertNotNull(today);
    }

    @Test
    public void formatAndParseTest() {
        String dateStr = "2017-03-01";
        Date date = DateKit.parse(dateStr);

        String format = DateKit.format(date, "yyyy/MM/dd");
        Assertions.assertEquals("2017/03/01", format);

        // 常用格式的格式化
        String formatDate = DateKit.formatDate(date);
        Assertions.assertEquals("2017-03-01", formatDate);
        String formatDateTime = DateKit.formatDateTime(date);
        Assertions.assertEquals("2017-03-01 00:00:00", formatDateTime);
        String formatTime = DateKit.formatTime(date);
        Assertions.assertEquals("00:00:00", formatTime);
    }

    @Test
    public void beginAndEndTest() {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateKit.parse(dateStr);

        // 一天的开始
        Date beginOfDay = DateKit.beginOfDay(date);
        Assertions.assertEquals("2017-03-01 00:00:00", beginOfDay.toString());
        // 一天的结束
        Date endOfDay = DateKit.endOfDay(date);
        Assertions.assertEquals("2017-03-01 23:59:59", endOfDay.toString());
    }

    @Test
    public void beginAndWeedTest() {
        String dateStr = "2017-03-01 22:33:23";
        DateTime date = DateKit.parse(dateStr);
        Objects.requireNonNull(date).setFirstDayOfWeek(Fields.Week.MONDAY);

        // 一周的开始
        Date beginOfWeek = DateKit.beginOfWeek(date);
        Assertions.assertEquals("2017-02-27 00:00:00", beginOfWeek.toString());
        // 一周的结束
        Date endOfWeek = DateKit.endOfWeek(date);
        Assertions.assertEquals("2017-03-05 23:59:59", endOfWeek.toString());

        Calendar calendar = DateKit.calendar(date);
        // 一周的开始
        Calendar begin = DateKit.beginOfWeek(calendar);
        Assertions.assertEquals("2017-02-27 00:00:00", DateKit.date(begin).toString());
        // 一周的结束
        Calendar end = DateKit.endOfWeek(calendar);
        Assertions.assertEquals("2017-03-05 23:59:59", DateKit.date(end).toString());
    }

    @Test
    public void offsetDateTest() {
        String dateStr = "2017-03-01 22:33:23";
        Date date = DateKit.parse(dateStr);

        Date newDate = DateKit.offset(date, Fields.DateField.DAY_OF_MONTH, 2);
        Assertions.assertEquals("2017-03-03 22:33:23", newDate.toString());

        // 偏移天
        DateTime newDate2 = DateKit.offsetDay(date, 3);
        Assertions.assertEquals("2017-03-04 22:33:23", newDate2.toString());

        // 偏移小时
        DateTime newDate3 = DateKit.offsetHour(date, -3);
        Assertions.assertEquals("2017-03-01 19:33:23", newDate3.toString());

        // 偏移月
        DateTime offsetMonth = DateKit.offsetMonth(date, -1);
        Assertions.assertEquals("2017-02-01 22:33:23", offsetMonth.toString());
    }

    @Test
    public void offsetMonthTest() {
        DateTime st = DateKit.parseDate("2018-05-31");
        List<DateTime> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(DateKit.offsetMonth(st, i));
        }
        Assertions.assertEquals("2018-05-31 00:00:00", list.get(0).toString());
        Assertions.assertEquals("2018-06-30 00:00:00", list.get(1).toString());
        Assertions.assertEquals("2018-07-31 00:00:00", list.get(2).toString());
        Assertions.assertEquals("2018-08-31 00:00:00", list.get(3).toString());
    }

    @Test
    public void betweenTest() {
        String dateStr1 = "2019-03-01 22:34:23";
        Date date1 = DateKit.parse(dateStr1);

        String dateStr2 = "2019-04-01 23:56:14";
        Date date2 = DateKit.parse(dateStr2);

        // 相差月
        long betweenMonth = DateKit.betweenMonth(date1, date2, false);
        Assertions.assertEquals(1, betweenMonth);// 相差一个月
        // 反向
        betweenMonth = DateKit.betweenMonth(date2, date1, false);
        Assertions.assertEquals(1, betweenMonth);// 相差一个月

        // 相差天
        long betweenDay = DateKit.between(date1, date2, Fields.Unit.DAY);
        Assertions.assertEquals(31, betweenDay);// 相差一个月，31天
        // 反向
        betweenDay = DateKit.between(date2, date1, Fields.Unit.DAY);
        Assertions.assertEquals(31, betweenDay);// 相差一个月，31天

        // 相差小时
        long betweenHour = DateKit.between(date1, date2, Fields.Unit.HOUR);
        Assertions.assertEquals(745, betweenHour);
        // 反向
        betweenHour = DateKit.between(date2, date1, Fields.Unit.HOUR);
        Assertions.assertEquals(745, betweenHour);

        // 相差分
        long betweenMinute = DateKit.between(date1, date2, Fields.Unit.MINUTE);
        Assertions.assertEquals(44721, betweenMinute);
        // 反向
        betweenMinute = DateKit.between(date2, date1, Fields.Unit.MINUTE);
        Assertions.assertEquals(44721, betweenMinute);

        // 相差秒
        long betweenSecond = DateKit.between(date1, date2, Fields.Unit.SECOND);
        Assertions.assertEquals(2683311, betweenSecond);
        // 反向
        betweenSecond = DateKit.between(date2, date1, Fields.Unit.SECOND);
        Assertions.assertEquals(2683311, betweenSecond);

        // 相差秒
        long betweenMS = DateKit.between(date1, date2, Fields.Unit.MS);
        Assertions.assertEquals(2683311000L, betweenMS);
        // 反向
        betweenMS = DateKit.between(date2, date1, Fields.Unit.MS);
        Assertions.assertEquals(2683311000L, betweenMS);
    }

    @Test
    public void betweenTest2() {
        long between = DateKit.between(DateKit.parse("2019-05-06 02:15:00"), DateKit.parse("2019-05-06 02:20:00"), Fields.Unit.HOUR);
        Assertions.assertEquals(0, between);
    }

    @Test
    public void formatBetweenTest() {
        String dateStr1 = "2020-01-01 22:34:23";
        Date date1 = DateKit.parse(dateStr1);

        String dateStr2 = "2020-02-01 23:56:14";
        Date date2 = DateKit.parse(dateStr2);

        long between = DateKit.between(date1, date2, Fields.Unit.MS);
        String formatBetween = DateKit.formatBetween(between, Fields.Level.MINUTE);
        Assertions.assertEquals("31天1小时21分", formatBetween);
    }

    @Test
    public void timerTest() {
        TimeInterval timer = DateKit.timer();
        timer.interval();// 花费毫秒数
        timer.intervalRestart();// 返回花费时间，并重置开始时间
        timer.intervalMinute();// 花费分钟数
    }

    @Test
    public void currentTest() {
        long current = DateKit.timestamp(false);
        String currentStr = String.valueOf(current);
        Assertions.assertEquals(13, currentStr.length());

        long currentNano = DateKit.timestamp(true);
        String currentNanoStr = String.valueOf(currentNano);
        Assertions.assertNotNull(currentNanoStr);
    }

    @Test
    public void weekOfYearTest() {
        // 第一周周日
        int weekOfYear1 = DateKit.weekOfYear(DateKit.parse("2016-01-03"));
        Assertions.assertEquals(1, weekOfYear1);

        // 第二周周四
        int weekOfYear2 = DateKit.weekOfYear(DateKit.parse("2016-01-07"));
        Assertions.assertEquals(2, weekOfYear2);
    }

    @Test
    public void timeToSecondTest() {
        int second = DateKit.timeToSecond("00:01:40");
        Assertions.assertEquals(100, second);
        second = DateKit.timeToSecond("00:00:40");
        Assertions.assertEquals(40, second);
        second = DateKit.timeToSecond("01:00:00");
        Assertions.assertEquals(3600, second);
        second = DateKit.timeToSecond("00:00:00");
        Assertions.assertEquals(0, second);
    }

    @Test
    public void secondToTimeTest() {
        String time = DateKit.secondToTime(3600);
        Assertions.assertEquals("01:00:00", time);
        time = DateKit.secondToTime(3800);
        Assertions.assertEquals("01:03:20", time);
        time = DateKit.secondToTime(0);
        Assertions.assertEquals("00:00:00", time);
        time = DateKit.secondToTime(30);
        Assertions.assertEquals("00:00:30", time);
    }

    @Test
    public void secondToTimeTest2() {
        String s1 = "55:02:18";
        String s2 = "55:00:50";
        int i = DateKit.timeToSecond(s1) + DateKit.timeToSecond(s2);
        String s = DateKit.secondToTime(i);
        Assertions.assertEquals("110:03:08", s);
    }

    @Test
    public void parseTest2() {
        // 转换时间与SimpleDateFormat结果保持一致即可
        String birthday = "700403";
        Date birthDate = DateKit.parse(birthday, "yyMMdd");
        // 获取出生年(完全表现形式,如：2010)
        int sYear = DateKit.year(birthDate);
        Assertions.assertEquals(1970, sYear);
    }

    @Test
    public void parseTest3() {
        String dateStr = "2018-10-10 12:11:11";
        Date date = DateKit.parse(dateStr);
        String format = DateKit.format(date, Fields.NORM_DATETIME_PATTERN);
        Assertions.assertEquals(dateStr, format);
    }

    @Test
    public void parseTest4() {
        String ymd = DateKit.parse("2019-3-21 12:20:15", "yyyy-MM-dd").toString(Fields.PURE_DATE_PATTERN);
        Assertions.assertEquals("20190321", ymd);
    }


    @Test
    public void parseTest5() {
        // 测试时间解析
        String time = DateKit.parse("22:12:12").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("22:12:12", time);
        time = DateKit.parse("2:12:12").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:12:12", time);
        time = DateKit.parse("2:2:12").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:02:12", time);
        time = DateKit.parse("2:2:1").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:02:01", time);
        time = DateKit.parse("22:2:1").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("22:02:01", time);
        time = DateKit.parse("2:22:1").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:22:01", time);

        // 测试两位时间解析
        time = DateKit.parse("2:22").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("02:22:00", time);
        time = DateKit.parse("12:22").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("12:22:00", time);
        time = DateKit.parse("12:2").toString(Fields.NORM_TIME_FORMAT);
        Assertions.assertEquals("12:02:00", time);

    }


    @Test
    public void parseTest6() {
        String str = "Tue Jun 4 16:25:15 +0800 2019";
        DateTime dateTime = DateKit.parse(str);
        Assertions.assertEquals("2019-06-04 16:25:15", dateTime.toString());
    }

    @Test
    public void parseTest7() {
        String str = "2019-06-01T19:45:43.000 +0800";
        DateTime dateTime = DateKit.parse(str, "yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Assertions.assertEquals("2019-06-01 19:45:43", dateTime.toString());
    }


    @Test
    public void parseAndOffsetTest() {
        // 检查UTC时间偏移是否准确
        String str = "2019-09-17T13:26:17.948Z";
        DateTime dateTime = DateKit.parse(str);
        Assertions.assertEquals("2019-09-17 13:26:17", dateTime.toString());

        DateTime offset = DateKit.offsetHour(dateTime, 8);
        Assertions.assertEquals("2019-09-17 21:26:17", offset.toString());
    }

    @Test
    public void parseDateTest() {
        String dateStr = "2018-4-10";
        Date date = DateKit.parseDate(dateStr);
        String format = DateKit.format(date, Fields.NORM_DATE_PATTERN);
        Assertions.assertEquals("2018-04-10", format);
    }

    @Test
    public void parseToDateTimeTest1() {
        String dateStr1 = "2017-02-01";
        String dateStr2 = "2017/02/01";
        String dateStr3 = "2017.02.01";
        String dateStr4 = "2017年02月01日";

        DateTime dt1 = DateKit.parse(dateStr1);
        DateTime dt2 = DateKit.parse(dateStr2);
        DateTime dt3 = DateKit.parse(dateStr3);
        DateTime dt4 = DateKit.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest2() {
        String dateStr1 = "2017-02-01 12:23";
        String dateStr2 = "2017/02/01 12:23";
        String dateStr3 = "2017.02.01 12:23";
        String dateStr4 = "2017年02月01日 12:23";

        DateTime dt1 = DateKit.parse(dateStr1);
        DateTime dt2 = DateKit.parse(dateStr2);
        DateTime dt3 = DateKit.parse(dateStr3);
        DateTime dt4 = DateKit.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest3() {
        String dateStr1 = "2017-02-01 12:23:45";
        String dateStr2 = "2017/02/01 12:23:45";
        String dateStr3 = "2017.02.01 12:23:45";
        String dateStr4 = "2017年02月01日 12时23分45秒";

        DateTime dt1 = DateKit.parse(dateStr1);
        DateTime dt2 = DateKit.parse(dateStr2);
        DateTime dt3 = DateKit.parse(dateStr3);
        DateTime dt4 = DateKit.parse(dateStr4);
        Assertions.assertEquals(dt1, dt2);
        Assertions.assertEquals(dt2, dt3);
        Assertions.assertEquals(dt3, dt4);
    }

    @Test
    public void parseToDateTimeTest4() {
        String dateStr1 = "2017-02-01 12:23:45";
        String dateStr2 = "20170201122345";

        DateTime dt1 = DateKit.parse(dateStr1);
        DateTime dt2 = DateKit.parse(dateStr2);
        Assertions.assertEquals(dt1, dt2);
    }

    @Test
    public void parseToDateTimeTest5() {
        String dateStr1 = "2017-02-01";
        String dateStr2 = "20170201";

        DateTime dt1 = DateKit.parse(dateStr1);
        DateTime dt2 = DateKit.parse(dateStr2);
        Assertions.assertEquals(dt1, dt2);
    }

    @Test
    public void parseUTCTest() {
        String dateStr1 = "2018-09-13T05:34:31Z";
        DateTime dt = DateKit.parseUTC(dateStr1);

        // parse方法支持UTC格式测试
        DateTime dt2 = DateKit.parse(dateStr1);
        Assertions.assertEquals(dt, dt2);

        // 默认使用Pattern对应的时区，即UTC时区
        String dateStr = dt.toString();
        Assertions.assertEquals("2018-09-13 05:34:31", dateStr);

        // 使用当前（上海）时区
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:31", dateStr);

        dateStr1 = "2018-09-13T13:34:32+0800";
        dt = DateKit.parseUTC(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:32", dateStr);

        dateStr1 = "2018-09-13T13:34:33+08:00";
        dt = DateKit.parseUTC(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:33", dateStr);

        dateStr1 = "2018-09-13T13:34:34+0800";
        dt = DateKit.parse(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:34", dateStr);

        dateStr1 = "2018-09-13T13:34:35+08:00";
        dt = DateKit.parse(dateStr1);
        dateStr = dt.toString(TimeZone.getTimeZone("GMT+8:00"));
        Assertions.assertEquals("2018-09-13 13:34:35", dateStr);

        dateStr1 = "2018-09-13T13:34:36.999+0800";
        dt = DateKit.parseUTC(dateStr1);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Fields.NORM_DATETIME_MS_PATTERN);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:36.999", dateStr);

        dateStr1 = "2018-09-13T13:34:37.999+08:00";
        dt = DateKit.parseUTC(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:37.999", dateStr);

        dateStr1 = "2018-09-13T13:34:38.999+0800";
        dt = DateKit.parse(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:38.999", dateStr);

        dateStr1 = "2018-09-13T13:34:39.999+08:00";
        dt = DateKit.parse(dateStr1);
        dateStr = dt.toString(simpleDateFormat);
        Assertions.assertEquals("2018-09-13 13:34:39.999", dateStr);
    }


    @Test
    public void parseCSTTest() {
        String dateStr = "Wed Sep 16 11:26:23 CST 2009";
        DateTime dateTime = DateKit.parseCST(dateStr);
        Assertions.assertEquals("2009-09-17 01:26:23", dateTime.toString());

        dateTime = DateKit.parse(dateStr);
        Assertions.assertEquals("2009-09-17 01:26:23", dateTime.toString());
    }


    @Test
    public void parseJDkTest() {
        String dateStr = "Thu May 16 17:57:18 GMT+08:00 2019";
        DateTime time = DateKit.parse(dateStr);
        Assertions.assertEquals("2019-05-16 17:57:18", time.toString());
    }

    @Test
    public void endOfYearTest() {
        DateTime date = DateKit.date();
        date.setField(Fields.DateField.YEAR, 2019);
        DateTime endOfYear = DateKit.endOfYear(date);
        Assertions.assertEquals("2019-12-31 23:59:59", endOfYear.toString());
    }

    @Test
    public void endOfWeekTest() {
        // 周日
        DateTime now = DateKit.parse("2019-09-15 13:00");

        DateTime startOfWeek = DateKit.beginOfWeek(now);
        Assertions.assertEquals("2019-09-09 00:00:00", startOfWeek.toString());
        DateTime endOfWeek = DateKit.endOfWeek(now);
        Assertions.assertEquals("2019-09-15 23:59:59", endOfWeek.toString());

        long between = DateKit.between(endOfWeek, startOfWeek, Fields.Unit.DAY);
        // 周一和周日相距6天
        Assertions.assertEquals(6, between);
    }

    @Test
    public void dayOfWeekTest() {
        int dayOfWeek = DateKit.dayOfWeek(DateKit.parse("2018-03-07"));
        Assertions.assertEquals(Calendar.WEDNESDAY, dayOfWeek);
        Fields.Week week = DateTime.of(DateKit.parse("2018-03-07")).dayOfWeekEnum();
        Assertions.assertEquals(Fields.Week.WEDNESDAY, week);
    }

    @Test
    public void rangeTest() {
        DateTime start = DateKit.parse("2017-01-01");
        DateTime end = DateKit.parse("2017-01-03");

        // 测试包含开始和结束情况下步进为1的情况
        Boundary range = DateKit.range(start, end, Fields.DateField.DAY_OF_YEAR);
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-03"));
        try {
            range.next();
            Assertions.fail("已超过边界，下一个元素不应该存在！");
        } catch (NoSuchElementException ignored) {
        }

        // 测试多步进的情况
        range = new Boundary(start, end, Fields.DateField.DAY_OF_YEAR, 2);
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-01"));
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-03"));

        // 测试不包含开始结束时间的情况
        range = new Boundary(start, end, Fields.DateField.DAY_OF_YEAR, 1, false, false);
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-02"));
        try {
            range.next();
            Assertions.fail("不包含结束时间情况下，下一个元素不应该存在！");
        } catch (NoSuchElementException ignored) {
        }
    }

    @Test
    public void rangeToListTest() {
        DateTime start = DateKit.parse("2017-01-01");
        DateTime end = DateKit.parse("2017-01-31");

        List<DateTime> rangeToList = DateKit.rangeToList(start, end, Fields.DateField.DAY_OF_YEAR);
        Assertions.assertEquals(rangeToList.get(0), DateKit.parse("2017-02-01"));
    }

    @Test
    public void yearAndQTest() {
        String yearAndQuarter = DateKit.yearAndQuarter(DateKit.parse("2018-12-01"));
        Assertions.assertEquals("20184", yearAndQuarter);

        LinkedHashSet<String> yearAndQuarters = DateKit.yearAndQuarter(DateKit.parse("2018-09-10"), DateKit.parse("2018-12-20"));
        List<String> list = CollKit.list(false, yearAndQuarters);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("20183", list.get(0));
        Assertions.assertEquals("20184", list.get(1));

        LinkedHashSet<String> yearAndQuarters2 = DateKit.yearAndQuarter(DateKit.parse("2018-10-10"), DateKit.parse("2018-12-10"));
        List<String> list2 = CollKit.list(false, yearAndQuarters2);
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals("20184", list2.get(0));
    }

    @Test
    public void formatHttpDateTest() {
        DateTime dateTime = DateKit.parse("2019-01-02 22:32:01");
        String formatHttpDate = DateKit.formatHttpDate(dateTime);
        Assertions.assertEquals("Wed, 02 Jan 2019 14:32:01 GMT", formatHttpDate);
    }

    @Test
    public void toInstantTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Instant instant = DateKit.toInstant(localDateTime);
        Assertions.assertEquals("2017-05-06T00:30:00Z", instant.toString());

        LocalDate localDate = localDateTime.toLocalDate();
        instant = DateKit.toInstant(localDate);
        Assertions.assertNotNull(instant);

        LocalTime localTime = localDateTime.toLocalTime();
        instant = DateKit.toInstant(localTime);
        Assertions.assertNotNull(instant);
    }

    @Test
    public void dateTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        DateTime date = DateKit.date(localDateTime);
        Assertions.assertEquals("2017-05-06 08:30:00", date.toString());
    }

    @Test
    public void dateTest2() {
        // 测试负数日期
        long dateLong = -1497600000;
        final DateTime date = DateKit.date(dateLong);
        Assertions.assertEquals("1969-12-15 00:00:00", date.toString());
    }

    @Test
    public void ageTest() {
        String d1 = "2000-02-29";
        String d2 = "2018-02-28";
        final int age = DateKit.getAge(DateKit.parseDate(d1), DateKit.parseDate(d2));
        Assertions.assertEquals(18, age);
    }


    @Test
    public void getZodiacTest() {
        Assertions.assertEquals("摩羯座", DateKit.getZodiac(Fields.Month.JANUARY, 19));
        Assertions.assertEquals("水瓶座", DateKit.getZodiac(Fields.Month.JANUARY, 20));
        Assertions.assertEquals("巨蟹座", DateKit.getZodiac(6, 17));
    }

    @Test
    public void getChineseZodiacTest() {
        Assertions.assertEquals("狗", DateKit.getChineseZodiac(1994));
        Assertions.assertEquals("狗", DateKit.getChineseZodiac(2018));
        Assertions.assertEquals("猪", DateKit.getChineseZodiac(2019));
    }

    @Test
    public void timeZoneConvertTest() {
        DateTime dt = DateKit.parse("2018-07-10 21:44:32",
                FormatBuilder.getInstance(Fields.NORM_DATETIME_PATTERN, TimeZone.getTimeZone("GMT+8:00")));
        Assertions.assertEquals("2018-07-10 21:44:32", dt.toString());

        dt.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        int hour = dt.getField(Fields.DateField.HOUR_OF_DAY);
        Assertions.assertEquals(14, hour);
        Assertions.assertEquals("2018-07-10 14:44:32", dt.toString());
    }

    /**
     * 打印MINI_YEAR-01-31到MAX_YEAR-12-31所有的农历,并输出到txt中
     */
    @Test
    public void localTest1() {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        // start
        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, DateKit.MIN_YEAR);
        start.set(Calendar.MONTH, 1);
        start.set(Calendar.DATE, 12);
        // end
        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, DateKit.MAX_YEAR);
        end.set(Calendar.MONTH, 11);
        end.set(Calendar.DATE, 31);
        FileOutputStream out = null;
        PrintStream p = null;
        try {
            File testFile = new File("./resources/solar2lunar.txt");
            if (!testFile.exists()) {
                testFile.createNewFile();
            }
            out = new FileOutputStream(testFile);
            p = new PrintStream(out);
            Calendar t = start;
            while (t.before(end) || t.equals(end)) {
                DateKit lunar = DateKit.solar2Lunar(t);
                System.out.println(df.format(t.getTime()) + " <====> " + lunar.getFullLunarName());
                p.println(df.format(t.getTime()) + " <====> " + lunar.getFullLunarName());
                t.add(Calendar.DATE, 1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到solar2lunar.txt文件，或者文件创建失败.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("未找到solar2lunar.txt文件，或者文件创建失败.");
            e.printStackTrace();
        } finally {
            try {
                out.close();
                p.close();
            } catch (IOException e) {
                System.out.println("关闭流出错。");
                e.printStackTrace();
            }
        }
    }

    @Test
    public void localTest2() {
        final java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");

        int lunarDay = 9;
        int lunarMonth = 12;
        int lunarYear = 1048;
        Assertions.assertEquals("初九", DateKit.getDayName(lunarDay));
        Assertions.assertEquals('腊', DateKit.getMonthName(lunarMonth));
        Assertions.assertEquals("一〇四八", DateKit.getYearName(lunarYear));

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, 2);
        c.set(Calendar.DATE, 31);
        DateKit lunar = new DateKit(c);
        System.out.println(df.format(c.getTime()) + " -> " + lunar);
        Assertions.assertEquals("二〇一九年二月廿五", lunar.toString());

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        System.out.println(df.format(c1.getTime()));
        c1.add(Calendar.MONTH, 10);
        System.out.println(df.format(c1.getTime()));
        // int[] arr1900 = { 8, 131, 301, 331, 429, 528, 627, 726, 825, 924, 1023, 1122, 1222, 1320 };
        // System.out.println(LunarCalendar.binSearch(arr1900, 1121));

        c1.set(Calendar.YEAR, 1991);
        c1.set(Calendar.MONTH, 3);
        c1.set(Calendar.DATE, 1);


        c2.set(Calendar.YEAR, 1991);
        c2.set(Calendar.MONTH, 2);
        c2.set(Calendar.DATE, 1);

        System.out.println(DateKit.solarDiff(c1, c2, Calendar.DATE));
        System.out.println(df.format(c1.getTime()));
        System.out.println(df.format(c2.getTime()));
    }

    @Test
    public void localTest3() {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();

        c1.set(Calendar.YEAR, 2046);
        c1.set(Calendar.MONTH, 01);
        c1.set(Calendar.DATE, 06);
        DateKit luanr = DateKit.solar2Lunar(c1);
        System.out.println();
        System.out.println("Solar：" + df.format(c1.getTime()) + "Lunar：" + luanr);
    }

}
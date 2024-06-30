package org.miaixz.bus.core.lang.range;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.Boundary;
import org.miaixz.bus.core.center.date.DateTime;
import org.miaixz.bus.core.center.date.culture.en.Various;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link Range} 单元测试
 */
public class RangeTest {

    @Test
    public void dateRangeTest() {
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-02");

        final Range<DateTime> range = new Range<>(start, end, (current, end1, index) -> {
            if (current.isAfterOrEquals(end1)) {
                return null;
            }
            return current.offsetNew(Various.DAY_OF_YEAR, 1);
        });

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(DateKit.parse("2017-01-01"), range.next());
        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(DateKit.parse("2017-01-02"), range.next());
        Assertions.assertFalse(range.hasNext());
    }

    @Test
    public void dateRangeFuncTest() {
        final DateTime start = DateKit.parse("2021-01-01");
        final DateTime end = DateKit.parse("2021-01-03");

        final List<Integer> dayOfMonthList = DateKit.rangeFunc(start, end, Various.DAY_OF_YEAR, a -> DateTime.of(a).dayOfMonth());
        Assertions.assertArrayEquals(dayOfMonthList.toArray(new Integer[]{}), new Integer[]{1, 2, 3});

        final List<Integer> dayOfMonthList2 = DateKit.rangeFunc(null, null, Various.DAY_OF_YEAR, a -> DateTime.of(a).dayOfMonth());
        Assertions.assertArrayEquals(dayOfMonthList2.toArray(new Integer[]{}), new Integer[]{});
    }

    @Test
    public void dateRangeConsumeTest() {
        final DateTime start = DateKit.parse("2021-01-01");
        final DateTime end = DateKit.parse("2021-01-03");

        final StringBuilder sb = new StringBuilder();
        DateKit.rangeConsume(start, end, Various.DAY_OF_YEAR, a -> sb.append(DateTime.of(a).dayOfMonth()).append("#"));
        Assertions.assertEquals(sb.toString(), "1#2#3#");

        final StringBuilder sb2 = new StringBuilder();
        DateKit.rangeConsume(null, null, Various.DAY_OF_YEAR, a -> sb2.append(DateTime.of(a).dayOfMonth()).append("#"));
        Assertions.assertEquals(sb2.toString(), Normal.EMPTY);
    }

    @Test
    public void dateRangeTest2() {
        final DateTime start = DateKit.parse("2021-01-31");
        final DateTime end = DateKit.parse("2021-03-31");

        final Boundary range = DateKit.range(start, end, Various.MONTH);

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(DateKit.parse("2021-01-31"), range.next());
        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(DateKit.parse("2021-02-28"), range.next());
        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(DateKit.parse("2021-03-31"), range.next());
        Assertions.assertFalse(range.hasNext());
    }

    @Test
    public void intRangeTest() {
        final Range<Integer> range = new Range<>(1, 1, (current, end, index) -> current >= end ? null : current + 10);

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(Integer.valueOf(1), range.next());
        Assertions.assertFalse(range.hasNext());
    }

    @Test
    public void rangeByStepTest() {
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-03");

        // 测试包含开始和结束情况下步进为1的情况
        Boundary range = DateKit.range(start, end, Various.DAY_OF_YEAR);
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-01"));
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-02"));
        Assertions.assertEquals(range.next(), DateKit.parse("2017-01-03"));
        try {
            range.next();
            Assertions.fail("已超过边界，下一个元素不应该存在！");
        } catch (final NoSuchElementException ignored) {
        }

        // 测试多步进的情况
        range = new Boundary(start, end, Various.DAY_OF_YEAR, 2);
        Assertions.assertEquals(DateKit.parse("2017-01-01"), range.next());
        Assertions.assertEquals(DateKit.parse("2017-01-03"), range.next());
    }

    @Test
    public void rangeDayOfYearTest() {
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-05");

        // 测试不包含开始结束时间的情况
        final Boundary range = new Boundary(start, end, Various.DAY_OF_YEAR, 1, false, false);
        Assertions.assertEquals(DateKit.parse("2017-01-02"), range.next());
        Assertions.assertEquals(DateKit.parse("2017-01-03"), range.next());
        Assertions.assertEquals(DateKit.parse("2017-01-04"), range.next());
        try {
            range.next();
            Assertions.fail("不包含结束时间情况下，下一个元素不应该存在！");
        } catch (final NoSuchElementException ignored) {
        }
    }

    @Test
    public void rangeToListTest() {
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-31");

        final List<DateTime> rangeToList = DateKit.rangeToList(start, end, Various.DAY_OF_YEAR);
        Assertions.assertEquals(DateKit.parse("2017-01-01"), rangeToList.get(0));
        Assertions.assertEquals(DateKit.parse("2017-01-02"), rangeToList.get(1));
    }


    @Test
    public void rangeContains() {
        // 开始区间
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-31");
        final Boundary startRange = DateKit.range(start, end, Various.DAY_OF_YEAR);
        // 结束区间
        final DateTime start1 = DateKit.parse("2017-01-31");
        final DateTime end1 = DateKit.parse("2017-02-02");
        final Boundary endRange = DateKit.range(start1, end1, Various.DAY_OF_YEAR);
        // 交集
        final List<DateTime> dateTimes = DateKit.rangeContains(startRange, endRange);
        Assertions.assertEquals(1, dateTimes.size());
        Assertions.assertEquals(DateKit.parse("2017-01-31"), dateTimes.get(0));
    }

    @Test
    public void rangeNotContains() {
        // 开始区间
        final DateTime start = DateKit.parse("2017-01-01");
        final DateTime end = DateKit.parse("2017-01-30");
        final Boundary startRange = DateKit.range(start, end, Various.DAY_OF_YEAR);
        // 结束区间
        final DateTime start1 = DateKit.parse("2017-01-01");
        final DateTime end1 = DateKit.parse("2017-01-31");
        final Boundary endRange = DateKit.range(start1, end1, Various.DAY_OF_YEAR);
        // 差集
        final List<DateTime> dateTimes1 = DateKit.rangeNotContains(startRange, endRange);

        Assertions.assertEquals(1, dateTimes1.size());
        Assertions.assertEquals(DateKit.parse("2017-01-31"), dateTimes1.get(0));
    }

}

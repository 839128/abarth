package org.aoju.bus.core.lang;

import org.aoju.bus.core.date.DateTime;
import org.aoju.bus.core.toolkit.DateKit;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link Range} 单元测试
 */
public class RangeTest {

    @Test
    public void dateRangeTest() {
        DateTime start = DateKit.parse("2017-01-01");
        DateTime end = DateKit.parse("2017-01-02");

        final Range<DateTime> range = new Range<>(start, end, (current, end1, index) -> {
            if (current.isAfterOrEquals(end1)) {
                return null;
            }
            return current.offset(Fields.Type.DAY_OF_YEAR, 1);
        });

        Assert.assertTrue(range.hasNext());
        Assert.assertEquals(range.next(), DateKit.parse("2017-01-02"));
    }

    @Test
    public void intRangeTest() {
        final Range<Integer> range = new Range<Integer>(1, 1, (current, end, index) -> current >= end ? null : current + 10);

        Assert.assertTrue(range.hasNext());
        Assert.assertEquals(Integer.valueOf(1), range.next());
        Assert.assertFalse(range.hasNext());
    }

}

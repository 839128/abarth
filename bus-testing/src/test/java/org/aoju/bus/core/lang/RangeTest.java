package org.aoju.bus.core.lang;

import org.aoju.bus.core.date.DateTime;
import org.aoju.bus.core.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link Range} 单元测试
 */
public class RangeTest {

    @Test
    public void dateRangeTest() {
        DateTime start = DateUtils.parse("2017-01-01");
        DateTime end = DateUtils.parse("2017-01-02");

        final Range<DateTime> range = new Range<>(start, end, (current, end1, index) -> {
            if (current.isAfterOrEquals(end1)) {
                return null;
            }
            return current.offset(Fields.DateField.DAY_OF_YEAR, 1);
        });

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(range.next(), DateUtils.parse("2017-01-02"));
    }

    @Test
    public void intRangeTest() {
        final Range<Integer> range = new Range<Integer>(1, 1, (current, end, index) -> current >= end ? null : current + 10);

        Assertions.assertTrue(range.hasNext());
        Assertions.assertEquals(Integer.valueOf(1), range.next());
        Assertions.assertFalse(range.hasNext());
    }

}

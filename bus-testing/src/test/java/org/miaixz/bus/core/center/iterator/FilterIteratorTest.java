package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIteratorTest {

    @Test
    public void iterNullTest() {
        final Iterator<String> it = ListKit.of("1", "2").iterator();
        final FilterIterator<String> filterIter = new FilterIterator<>(it, null);
        int count = 0;
        while (filterIter.hasNext()) {
            if (null != filterIter.next()) {
                count++;
            }
        }
        Assertions.assertEquals(2, count);
    }

    @Test
    public void hasNext() {
        Iterator<Integer> iter = new FilterIterator<>(Arrays.asList(1, 2, 3).iterator(), i -> true);
        Assertions.assertTrue(iter.hasNext());
        iter = new FilterIterator<>(Collections.emptyIterator(), i -> true);
        Assertions.assertFalse(iter.hasNext());
    }

    @Test
    public void next() {
        // 只保留奇数
        Iterator<Integer> iter = new FilterIterator<>(Arrays.asList(1, 2, 3).iterator(), i -> (i & 1) == 1);
        Assertions.assertEquals((Integer) 1, iter.next());
        Assertions.assertEquals((Integer) 3, iter.next());
    }

    @Test
    public void remove() {
        Iterator<Integer> iter = new FilterIterator<>(Collections.emptyIterator(), i -> true);
        Assertions.assertThrows(IllegalStateException.class, iter::remove);
    }

    @Test
    public void getIterator() {
        FilterIterator<Integer> iter = new FilterIterator<>(Collections.emptyIterator(), i -> true);
        Assertions.assertSame(Collections.emptyIterator(), iter.getIterator());
    }

    @Test
    public void getFilter() {
        Predicate<Integer> predicate = i -> true;
        FilterIterator<Integer> iter = new FilterIterator<>(Collections.emptyIterator(), predicate);
        Assertions.assertSame(predicate, iter.getFilter());
    }

}

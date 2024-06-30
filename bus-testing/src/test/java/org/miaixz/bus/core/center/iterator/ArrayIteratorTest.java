package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test for {@link ArrayIterator}
 */
public class ArrayIteratorTest {

    @Test
    public void testHasNext() {
        final Integer[] arr = new Integer[]{1, 2, 3};
        final ArrayIterator<Integer> iter = new ArrayIterator<>(arr);
        Assertions.assertTrue(iter.hasNext());
    }

    @Test
    public void testNext() {
        final Integer[] arr = new Integer[]{1, 2, 3};
        final ArrayIterator<Integer> iter = new ArrayIterator<>(arr);
        Assertions.assertEquals((Integer) 1, iter.next());
        Assertions.assertEquals((Integer) 2, iter.next());
        Assertions.assertEquals((Integer) 3, iter.next());
    }

    @Test
    public void testRemove() {
        final Integer[] arr = new Integer[]{1, 2, 3};
        final ArrayIterator<Integer> iter = new ArrayIterator<>(arr);
        Assertions.assertThrows(UnsupportedOperationException.class, iter::remove);
    }

    @Test
    public void testGetArray() {
        final Integer[] arr = new Integer[]{1, 2, 3};
        final ArrayIterator<Integer> iter = new ArrayIterator<>(arr);
        Assertions.assertEquals(arr, iter.getArray());
    }

    @Test
    public void testReset() {
        final Integer[] arr = new Integer[]{1, 2, 3};
        final ArrayIterator<Integer> iter = new ArrayIterator<>(arr);
        Assertions.assertEquals((Integer) 1, iter.next());
        iter.reset();
        Assertions.assertEquals((Integer) 1, iter.next());
    }

}

package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 *
 */
public class EnumerationIteratorTest {

    @Test
    public void testHasNext() {
        Enumeration<Integer> enumeration = new IteratorEnumeration<>(Arrays.asList(1, 2, 3).iterator());
        EnumerationIterator<Integer> iter = new EnumerationIterator<>(enumeration);
        Assertions.assertTrue(iter.hasNext());
        Assertions.assertFalse(new EnumerationIterator<>(new IteratorEnumeration<>(Collections.emptyIterator())).hasNext());
    }

    @Test
    public void testNext() {
        Enumeration<Integer> enumeration = new IteratorEnumeration<>(Arrays.asList(1, 2, 3).iterator());
        EnumerationIterator<Integer> iter = new EnumerationIterator<>(enumeration);
        Assertions.assertEquals((Integer) 1, iter.next());
        Assertions.assertEquals((Integer) 2, iter.next());
        Assertions.assertEquals((Integer) 3, iter.next());
    }

    @Test
    public void testRemove() {
        Enumeration<Integer> enumeration = new IteratorEnumeration<>(Arrays.asList(1, 2, 3).iterator());
        EnumerationIterator<Integer> iter = new EnumerationIterator<>(enumeration);
        Assertions.assertThrows(UnsupportedOperationException.class, iter::remove);
    }

    @Test
    public void testIterator() {
        Enumeration<Integer> enumeration = new IteratorEnumeration<>(Arrays.asList(1, 2, 3).iterator());
        EnumerationIterator<Integer> iter = new EnumerationIterator<>(enumeration);
        Assertions.assertSame(iter, iter.iterator());
    }

}

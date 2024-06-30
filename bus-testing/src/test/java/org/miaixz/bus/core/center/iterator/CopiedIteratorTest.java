package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * test for {@link CopiedIterator}
 */
public class CopiedIteratorTest {

    @Test
    public void copyOf() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<Integer> iter = list.iterator();
        Assertions.assertEquals((Integer) 1, iter.next());

        Assertions.assertEquals((Integer) 2, CopiedIterator.copyOf(iter).next());
    }

    @Test
    public void hasNext() {
        Assertions.assertTrue(CopiedIterator.copyOf(Arrays.asList(1, 2, 3).iterator()).hasNext());
        Assertions.assertFalse(CopiedIterator.copyOf(Collections.emptyIterator()).hasNext());
    }

    @Test
    public void next() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<Integer> iter = CopiedIterator.copyOf(list.iterator());
        Assertions.assertEquals((Integer) 1, iter.next());
        Assertions.assertEquals((Integer) 2, iter.next());
        Assertions.assertEquals((Integer) 3, iter.next());
    }

    @Test
    public void remove() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Iterator<Integer> iter = CopiedIterator.copyOf(list.iterator());
        Assertions.assertThrows(UnsupportedOperationException.class, iter::remove);
    }

}

package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 *
 */
public class TransIteratorTest {

    @Test
    public void testHasNext() {
        TransIterator<Integer, String> iter = new TransIterator<>(Arrays.asList(1, 2, 3).iterator(), String::valueOf);
        Assertions.assertTrue(iter.hasNext());
        Assertions.assertFalse(new TransIterator<>(Collections.emptyIterator(), Function.identity()).hasNext());
    }

    @Test
    public void testNext() {
        TransIterator<Integer, String> iter = new TransIterator<>(Arrays.asList(1, 2, 3).iterator(), String::valueOf);
        Assertions.assertEquals("1", iter.next());
        Assertions.assertEquals("2", iter.next());
        Assertions.assertEquals("3", iter.next());
    }

    @Test
    public void testRemove() {
        List<Integer> list = ListKit.of(1, 2, 3);
        TransIterator<Integer, String> iter = new TransIterator<>(list.iterator(), String::valueOf);
        iter.next();
        iter.remove();
        iter.next();
        iter.remove();
        iter.next();
        iter.remove();
        Assertions.assertTrue(list.isEmpty());
    }

}

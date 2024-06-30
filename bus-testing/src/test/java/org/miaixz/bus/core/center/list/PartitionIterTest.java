package org.miaixz.bus.core.center.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.iterator.LineIterator;
import org.miaixz.bus.core.center.iterator.PartitionIterator;
import org.miaixz.bus.core.xyz.ArrayKit;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.ResourceKit;

import java.util.List;

public class PartitionIterTest {

    @Test
    public void iterTest() {
        final LineIterator lineIter = new LineIterator(ResourceKit.getReader("test_lines.csv"));
        final PartitionIterator<String> iter = new PartitionIterator<>(lineIter, 3);
        for (final List<String> lines : iter) {
            Assertions.assertTrue(lines.size() > 0);
        }
    }

    @Test
    public void iterMaxTest() {
        final List<Integer> list = ListKit.view(1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 0, 12, 45, 12);
        final PartitionIterator<Integer> iter = new PartitionIterator<>(list.iterator(), 3);
        int max = 0;
        for (final List<Integer> lines : iter) {
            max = ArrayKit.max(max, ArrayKit.max(lines.toArray(new Integer[0])));
        }
        Assertions.assertEquals(45, max);
    }

}

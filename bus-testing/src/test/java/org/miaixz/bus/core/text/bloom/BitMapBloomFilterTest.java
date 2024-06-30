package org.miaixz.bus.core.text.bloom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.HashKit;

public class BitMapBloomFilterTest {

    @Test
    public void filterTest() {
        final int size = 2 * 1024 * 1024 * 8;

        final CombinedBloomFilter filter = new CombinedBloomFilter(FunctionFilter.of(size, HashKit::rsHash));
        filter.add("123");
        filter.add("abc");
        filter.add("ddd");

        Assertions.assertTrue(filter.contains("abc"));
        Assertions.assertTrue(filter.contains("ddd"));
        Assertions.assertTrue(filter.contains("123"));
    }

}

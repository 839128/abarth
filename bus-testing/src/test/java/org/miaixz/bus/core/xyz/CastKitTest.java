package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.*;

public class CastKitTest {
    @Test
    public void testCastToSuper() {
        final Collection<Integer> collection = ListKit.of(1, 2, 3);
        final List<Integer> list = ListKit.of(1, 2, 3);
        final Set<Integer> set = SetKit.of(1, 2, 3);
        final Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);

        final Collection<Number> collection2 = CastKit.castUp(collection);
        collection2.add(Double.valueOf("123.1"));
        Assertions.assertSame(collection, collection2);

        final Collection<Integer> collection3 = CastKit.castDown(collection2);
        Assertions.assertSame(collection2, collection3);

        final List<Number> list2 = CastKit.castUp(list);
        Assertions.assertSame(list, list2);
        final List<Integer> list3 = CastKit.castDown(list2);
        Assertions.assertSame(list2, list3);

        final Set<Number> set2 = CastKit.castUp(set);
        Assertions.assertSame(set, set2);
        final Set<Integer> set3 = CastKit.castDown(set2);
        Assertions.assertSame(set2, set3);

        final Map<Number, Serializable> map2 = CastKit.castUp(map);
        Assertions.assertSame(map, map2);
        final Map<Integer, Number> map3 = CastKit.castDown(map2);
        Assertions.assertSame(map2, map3);
    }

}

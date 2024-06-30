package org.miaixz.bus.core.lang.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.CollKit;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmoothWeightSelectorTest {
    @Test
    public void selectTest() {
        final SmoothWeightSelector<String> selector = SmoothWeightSelector.of();
        selector.add("A", 10);
        selector.add("B", 50);
        selector.add("C", 100);

        final String result = selector.select();
        Assertions.assertTrue(ListKit.of("A", "B", "C").contains(result));
    }

    @Test
    public void selectCountTest() {
        final SmoothWeightSelector<String> selector = SmoothWeightSelector.of();
        selector.add("A", 10);
        selector.add("B", 50);
        selector.add("C", 100);

        final List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            resultList.add(selector.select());
        }

        final Map<String, Integer> countMap = CollKit.countMap(resultList);
        Assertions.assertEquals(63, countMap.get("A"));
        Assertions.assertEquals(312, countMap.get("B"));
        Assertions.assertEquals(625, countMap.get("C"));
    }

}

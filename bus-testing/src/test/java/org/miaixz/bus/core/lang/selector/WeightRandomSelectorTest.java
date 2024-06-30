package org.miaixz.bus.core.lang.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.CollKit;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.List;

public class WeightRandomSelectorTest {

    @Test
    public void selectTest() {
        final WeightRandomSelector<String> random = WeightRandomSelector.of();
        random.add("A", 10);
        random.add("B", 50);
        random.add("C", 100);

        final String result = random.select();
        Assertions.assertTrue(ListKit.of("A", "B", "C").contains(result));
    }

    @Test
    @Disabled
    public void selectCountTest() {
        final WeightRandomSelector<String> random = WeightRandomSelector.of();
        random.add("A", 10);
        random.add("B", 50);
        random.add("C", 100);

        final List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            resultList.add(random.select());
        }

        Console.log(CollKit.countMap(resultList));
    }

}

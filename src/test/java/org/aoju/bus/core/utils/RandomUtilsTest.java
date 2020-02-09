package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

public class RandomUtilsTest {

    @Test
    public void randomEleSetTest() {
        Set<Integer> set = RandomUtils.randomEleSet(CollUtils.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(set.size(), 2);
    }

    @Test
    public void randomElesTest() {
        List<Integer> result = RandomUtils.randomEles(CollUtils.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    public void randomDoubleTest() {
        double randomDouble = RandomUtils.randomDouble(0, 1, 0, RoundingMode.HALF_UP);
        Assertions.assertTrue(randomDouble <= 1);
    }

    @Test
    public void randomBooleanTest() {
        Console.log(RandomUtils.randomBoolean());
    }

}

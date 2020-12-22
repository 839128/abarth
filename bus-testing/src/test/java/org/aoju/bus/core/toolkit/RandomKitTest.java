package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Console;
import org.junit.Assert;
import org.junit.Test;

import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

public class RandomKitTest {

    @Test
    public void randomEleSetTest() {
        Set<Integer> set = RandomKit.randomEleSet(CollKit.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assert.assertEquals(set.size(), 2);
    }

    @Test
    public void randomElesTest() {
        List<Integer> result = RandomKit.randomEles(CollKit.newArrayList(1, 2, 3, 4, 5, 6), 2);
        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public void randomDoubleTest() {
        double randomDouble = RandomKit.randomDouble(0, 1, 0, RoundingMode.HALF_UP);
        Assert.assertTrue(randomDouble <= 1);
    }

    @Test
    public void randomBooleanTest() {
        Console.log(RandomKit.randomBoolean());
    }

}

package org.miaixz.bus.core.lang.tuple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.mutable.MutablePair;

/**
 * {@link Pair} 二元组单元测试
 * {@link MutablePair} 二元组单元测试
 */
public class PairTest {

    @Test
    public void mutablePairTest() {
        final MutablePair<String, String> pair = MutablePair
                .of("1", "1");
        Assertions.assertEquals("Pair{left=1, right=1}", pair.toString());

        pair.setLeft("2");
        pair.setRight("2");
        Assertions.assertEquals("Pair{left=2, right=2}", pair.toString());
    }

    @Test
    public void pairTest() {
        final Pair<String, String> triple = Pair
                .of("3", "3");
        Assertions.assertEquals("Pair{left=3, right=3}", triple.toString());
    }

}

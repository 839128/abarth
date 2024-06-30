package org.miaixz.bus.core.lang.tuple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.mutable.MutableTriplet;

/**
 * {@link Triplet} 三元组单元测试
 * {@link MutableTriplet} 三元组单元测试
 */
public class TripletTest {

    @Test
    public void mutableTripleTest() {
        final MutableTriplet<String, String, String> mutableTriple = MutableTriplet
                .of("1", "1", "1");
        Assertions.assertEquals("Triple{left=1, middle=1, right=1}", mutableTriple.toString());

        mutableTriple.setLeft("2");
        mutableTriple.setMiddle("2");
        mutableTriple.setRight("2");
        Assertions.assertEquals("Triple{left=2, middle=2, right=2}", mutableTriple.toString());
    }

    @Test
    public void tripleTest() {
        final Triplet<String, String, String> triple = Triplet
                .of("3", "3", "3");
        Assertions.assertEquals("Triple{left=3, middle=3, right=3}", triple.toString());
    }

}

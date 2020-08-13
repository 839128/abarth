package org.aoju.bus.health;

import org.junit.Test;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Quintet;
import oshi.util.tuples.Triplet;

import java.math.BigInteger;

/**
 * Test object pair.
 */
public class TupleTest {

    @Test
    public void testTuples() {
        Pair<String, Integer> pair = new Pair<>("A", 1);
        Triplet<String, Integer, Long> triplet = new Triplet<>("B", 2, Long.MAX_VALUE);
        Quartet<String, Integer, Long, Character> quartet = new Quartet<>("C", 3, Long.MIN_VALUE, 'c');
        Quintet<String, Integer, Long, Character, BigInteger> quintet = new Quintet<>("D", 4, Long.valueOf("0"), 'd',
                BigInteger.ZERO);

        assertEquals("A", pair.getA());
        assertEquals("B", triplet.getA());
        assertEquals("C", quartet.getA());
        assertEquals("D", quintet.getA());

        assertEquals(1, pair.getB().intValue());
        assertEquals(2, triplet.getB().intValue());
        assertEquals(3, quartet.getB().intValue());
        assertEquals(4, quintet.getB().intValue());

        assertEquals(Long.MAX_VALUE, triplet.getC().longValue());
        assertEquals(Long.MIN_VALUE, quartet.getC().longValue());
        assertEquals(0L, quintet.getC().longValue());

        assertEquals('c', quartet.getD().charValue());
        assertEquals('d', quintet.getD().charValue());

        assertEquals(BigInteger.ZERO, quintet.getE());
    }
}

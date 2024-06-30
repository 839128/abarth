package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.convert.Convert;
import org.miaixz.bus.core.lang.Console;

import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RandomKitTest {

    @Test
    public void randomEleSetTest() {
        final Set<Integer> set = RandomKit.randomEleSet(ListKit.of(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(set.size(), 2);
    }

    @Test
    public void randomElesTest() {
        final List<Integer> result = RandomKit.randomEles(ListKit.of(1, 2, 3, 4, 5, 6), 2);
        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    public void randomDoubleTest() {
        final double randomDouble = RandomKit.randomDouble(0, 1, 0, RoundingMode.HALF_UP);
        Assertions.assertTrue(randomDouble <= 1);
    }

    @Test
    @Disabled
    public void randomBooleanTest() {
        Console.log(RandomKit.randomBoolean());
    }

    @Test
    public void randomNumberTest() {
        final char c = RandomKit.randomNumber();
        Assertions.assertTrue(c <= '9');
    }

    @Test
    public void randomIntTest() {
        final int c = RandomKit.randomInt(10, 100);
        Assertions.assertTrue(c >= 10 && c < 100);
    }

    @Test
    public void randomBytesTest() {
        final byte[] c = RandomKit.randomBytes(10);
        Assertions.assertNotNull(c);
    }

    @Test
    public void randomChineseTest() {
        final char c = RandomKit.randomChinese();
        Assertions.assertTrue(c > 0);
    }

    @Test
    public void randomStringLowerWithoutStrTest() {
        for (int i = 0; i < 100; i++) {
            final String s = RandomKit.randomStringLowerWithoutString(8, "0IPOL");
            for (final char c : "0IPOL".toCharArray()) {
                Assertions.assertFalse(s.contains((String.valueOf(c).toLowerCase(Locale.ROOT))));
            }
        }
    }

    @Test
    public void randomStringOfLengthTest() {
        final String s = RandomKit.randomString("123", -1);
        Assertions.assertNotNull(s);
    }

    @Test
    public void generateRandomNumberTest() {
        final int[] ints = RandomKit.randomPickInts(5, MathKit.range(5, 20));
        Assertions.assertEquals(5, ints.length);
        final Set<?> set = Convert.convert(Set.class, ints);
        Assertions.assertEquals(5, set.size());
    }

}

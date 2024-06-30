package org.miaixz.bus.core.codec.binary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Z85Test {

    @Test
    public void testZ85Basic() {
        final byte[] bytes = new byte[]{-122, 79, -46, 111, -75, 89, -9, 91};
        final String string = new Z85().encode(bytes);
        Assertions.assertEquals("HelloWorld", string);
        final byte[] result = new Z85().decode(string);
        Assertions.assertArrayEquals(bytes, result);
    }

    @Test
    public void testZ85Unpadded() {
        final byte[] bytes = new byte[]{0xC, 0x0, 0xF, 0xF, 0xE, 0xE};
        final byte[] result = new Z85().decode(new Z85().encode(bytes));
        Assertions.assertArrayEquals(bytes, result);
    }

    @Test
    public void testZ85UnpaddedShort() {
        final byte[] bytes = new byte[]{0xA, 0xB, 0xC};
        final byte[] result = new Z85().decode(new Z85().encode(bytes));
        Assertions.assertArrayEquals(bytes, result);
    }

}

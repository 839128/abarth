package org.miaixz.bus.core.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuhnTest {
    @Test
    void checkTest() {
        assertTrue(Luhn.check("6225760008219524"));

        assertFalse(Luhn.check("123456"));
        assertFalse(Luhn.check(""));
        assertFalse(Luhn.check("abc"));
        assertFalse(Luhn.check("622576000821952a"));
    }

    @Test
    void getCheckDigitTest() {
        int checkDigit = Luhn.getCheckDigit("6225760008219524", true);
        assertEquals(4, checkDigit);

        checkDigit = Luhn.getCheckDigit("622576000821952", false);
        assertEquals(4, checkDigit);
    }
}
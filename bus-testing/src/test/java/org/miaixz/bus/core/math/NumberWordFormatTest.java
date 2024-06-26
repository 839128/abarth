package org.miaixz.bus.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberWordFormatTest {

    @Test
    public void formatTest() {
        final String format = EnglishNumberFormatter.format(100.23);
        Assertions.assertEquals("ONE HUNDRED AND CENTS TWENTY THREE ONLY", format);

        final String format2 = EnglishNumberFormatter.format("2100.00");
        Assertions.assertEquals("TWO THOUSAND ONE HUNDRED AND CENTS  ONLY", format2);

        final String format3 = EnglishNumberFormatter.format("1234567890123.12");
        Assertions.assertEquals("ONE TRILLION TWO HUNDRED AND THIRTY FOUR BILLION FIVE HUNDRED AND SIXTY SEVEN MILLION EIGHT HUNDRED AND NINETY THOUSAND ONE HUNDRED AND TWENTY THREE AND CENTS TWELVE ONLY", format3);
    }

    @Test
    public void formatSimpleTest() {
        final String format1 = EnglishNumberFormatter.formatSimple(1200, false);
        Assertions.assertEquals("1.2k", format1);

        final String format2 = EnglishNumberFormatter.formatSimple(4384324, false);
        Assertions.assertEquals("4.38m", format2);

        final String format3 = EnglishNumberFormatter.formatSimple(4384324, true);
        Assertions.assertEquals("438.43w", format3);

        final String format4 = EnglishNumberFormatter.formatSimple(4384324);
        Assertions.assertEquals("438.43w", format4);

        final String format5 = EnglishNumberFormatter.formatSimple(438);
        Assertions.assertEquals("438", format5);
    }

    @Test
    public void formatSimpleTest2() {
        final String s = EnglishNumberFormatter.formatSimple(1000);
        Assertions.assertEquals("1k", s);
    }
}

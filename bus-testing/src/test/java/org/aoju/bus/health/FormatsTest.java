package org.aoju.bus.health;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DecimalFormatSymbols;

import static org.junit.Assert.assertEquals;

/**
 * The Class FormatsTest.
 */
public class FormatsTest {

    /**
     * The decimal separator.
     */
    private static char DECIMAL_SEPARATOR;

    /**
     * Sets the up class.
     */
    @BeforeClass
    public static void setUpClass() {
        // use decimal separator according to current locale
        DecimalFormatSymbols syms = new DecimalFormatSymbols();
        DECIMAL_SEPARATOR = syms.getDecimalSeparator();
    }

    /**
     * Test format bytes.
     */
    @Test
    public void testFormatBytes() {
        assertEquals("0 bytes", Formats.formatBytes(0));
        assertEquals("1 byte", Formats.formatBytes(1));
        assertEquals("532 bytes", Formats.formatBytes(532));
        assertEquals("1 KiB", Formats.formatBytes(1024));
        assertEquals("1 GiB", Formats.formatBytes(1024 * 1024 * 1024));
        assertEquals("1 TiB", Formats.formatBytes(1099511627776L));
    }

    /**
     * Test format bytes with decimal separator.
     */
    @Test
    public void testFormatBytesWithDecimalSeparator() {
        String expected1 = "1" + DECIMAL_SEPARATOR + "3 KiB";
        String expected2 = "2" + DECIMAL_SEPARATOR + "3 MiB";
        String expected3 = "2" + DECIMAL_SEPARATOR + "2 GiB";
        String expected4 = "1" + DECIMAL_SEPARATOR + "1 TiB";
        String expected5 = "1" + DECIMAL_SEPARATOR + "1 PiB";
        String expected6 = "1" + DECIMAL_SEPARATOR + "1 EiB";
        assertEquals(expected1, Formats.formatBytes(1340));
        assertEquals(expected2, Formats.formatBytes(2400016));
        assertEquals(expected3, Formats.formatBytes(2400000000L));
        assertEquals(expected4, Formats.formatBytes(1099511627776L + 109951162777L));
        assertEquals(expected5, Formats.formatBytes(1125899906842624L + 112589990684262L));
        assertEquals(expected6, Formats.formatBytes(1152921504606846976L + 115292150460684698L));
    }

    /**
     * Test format decimal bytes.
     */
    @Test
    public void testFormatBytesDecimal() {
        assertEquals("0 bytes", Formats.formatBytesDecimal(0));
        assertEquals("1 byte", Formats.formatBytesDecimal(1));
        assertEquals("532 bytes", Formats.formatBytesDecimal(532));
        assertEquals("1 KB", Formats.formatBytesDecimal(1000));
        assertEquals("1 GB", Formats.formatBytesDecimal(1000 * 1000 * 1000));
        assertEquals("1 TB", Formats.formatBytesDecimal(1000000000000L));
    }

    /**
     * Test format decimal bytes with decimal separator.
     */
    @Test
    public void testFormatBytesDecimalWithDecimalSeparator() {
        String expected1 = "1" + DECIMAL_SEPARATOR + "3 KB";
        String expected2 = "2" + DECIMAL_SEPARATOR + "3 MB";
        String expected3 = "2" + DECIMAL_SEPARATOR + "2 GB";
        String expected4 = "1" + DECIMAL_SEPARATOR + "1 TB";
        String expected5 = "3" + DECIMAL_SEPARATOR + "4 PB";
        String expected6 = "5" + DECIMAL_SEPARATOR + "6 EB";
        assertEquals(expected1, Formats.formatBytesDecimal(1300));
        assertEquals(expected2, Formats.formatBytesDecimal(2300000));
        assertEquals(expected3, Formats.formatBytesDecimal(2200000000L));
        assertEquals(expected4, Formats.formatBytesDecimal(1100000000000L));
        assertEquals(expected5, Formats.formatBytesDecimal(3400000000000000L));
        assertEquals(expected6, Formats.formatBytesDecimal(5600000000000000000L));
    }

    /**
     * Test format hertz.
     */
    @Test
    public void testFormatHertz() {
        assertEquals("0 Hz", Formats.formatHertz(0));
        assertEquals("1 Hz", Formats.formatHertz(1));
        assertEquals("999 Hz", Formats.formatHertz(999));
        assertEquals("1 KHz", Formats.formatHertz(1000));
        assertEquals("1 MHz", Formats.formatHertz(1000 * 1000));
        assertEquals("1 GHz", Formats.formatHertz(1000 * 1000 * 1000));
        assertEquals("1 THz", Formats.formatHertz(1000L * 1000L * 1000L * 1000L));
    }

    /**
     * Test format elapsed secs
     */
    @Test
    public void testFormatElapsedSecs() {
        assertEquals("0 days, 00:00:00", Formats.formatElapsedSecs(0));
        assertEquals("0 days, 03:25:45", Formats.formatElapsedSecs(12345));
        assertEquals("1 days, 10:17:36", Formats.formatElapsedSecs(123456));
        assertEquals("14 days, 06:56:07", Formats.formatElapsedSecs(1234567));
    }

    /**
     * Test round.
     */
    @Test
    public void testRound() {
        assertEquals(42.42, Formats.round(42.423f, 2), 0.00001f);
        assertEquals(42.43, Formats.round(42.425f, 2), 0.00001f);
        assertEquals(42.5, Formats.round(42.499f, 2), 0.00001f);
        assertEquals(42, Formats.round(42, 2), 0.00001f);
    }

    /**
     * Test unsigned int to long.
     */
    @Test
    public void testGetUnsignedInt() {
        assertEquals(4294967295L, Formats.getUnsignedInt(-1));
    }

    /**
     * Test unsigned string
     */
    @Test
    public void testToUnsignedString() {
        assertEquals("1", Formats.toUnsignedString(0x00000001));
        assertEquals("2147483648", Formats.toUnsignedString(0x80000000));
        assertEquals("4294967295", Formats.toUnsignedString(0xffffffff));

        assertEquals("1", Formats.toUnsignedString(0x0000000000000001L));
        assertEquals("9223372036854775808", Formats.toUnsignedString(0x8000000000000000L));
        assertEquals("18446744073709551615", Formats.toUnsignedString(0xffffffffffffffffL));
    }

    /**
     * Test format error
     */
    @Test
    public void testFormatError() {
        assertEquals("0xB66A00A8", Formats.formatError(-1234567000));
    }

}

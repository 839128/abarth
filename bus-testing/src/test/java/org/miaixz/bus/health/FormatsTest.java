package org.miaixz.bus.health;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * The Class FormatsTest.
 */
class FormatsTest {

    private static final char DECIMAL_SEPARATOR = DecimalFormatSymbols.getInstance(Locale.ROOT).getDecimalSeparator();

    /**
     * Test format bytes.
     */
    @Test
    void testFormatBytes() {
        assertThat("format 0 bytes", Formats.formatBytes(0), is("0 bytes"));
        assertThat("format byte", Formats.formatBytes(1), is("1 byte"));
        assertThat("format bytes", Formats.formatBytes(532), is("532 bytes"));
        assertThat("format KiByte", Formats.formatBytes(1024), is("1 KiB"));
        assertThat("format GiByte", Formats.formatBytes(1024 * 1024 * 1024), is("1 GiB"));
        assertThat("format TiByte", Formats.formatBytes(1099511627776L), is("1 TiB"));
    }

    /**
     * Test format bytes with decimal separator.
     */
    @Test
    void testFormatBytesWithDecimalSeparator() {
        String expected1 = "1" + DECIMAL_SEPARATOR + "3 KiB";
        String expected2 = "2" + DECIMAL_SEPARATOR + "3 MiB";
        String expected3 = "2" + DECIMAL_SEPARATOR + "2 GiB";
        String expected4 = "1" + DECIMAL_SEPARATOR + "1 TiB";
        String expected5 = "1" + DECIMAL_SEPARATOR + "1 PiB";
        String expected6 = "1" + DECIMAL_SEPARATOR + "1 EiB";
        assertThat("format KiBytes with decimal separator", Formats.formatBytes(1340), is(expected1));
        assertThat("format MiBytes with decimal separator", Formats.formatBytes(2400016), is(expected2));
        assertThat("format GiBytes with decimal separator", Formats.formatBytes(2400000000L), is(expected3));
        assertThat("format TiBytes with decimal separator", Formats.formatBytes(1099511627776L + 109951162777L),
                is(expected4));
        assertThat("format PiBytes with decimal separator",
                Formats.formatBytes(1125899906842624L + 112589990684262L), is(expected5));
        assertThat("format EiBytes with decimal separator",
                Formats.formatBytes(1152921504606846976L + 115292150460684698L), is(expected6));
    }

    /**
     * Test format decimal bytes.
     */
    @Test
    void testFormatBytesDecimal() {
        assertThat("format 0 bytesDecimal", Formats.formatBytesDecimal(0), is("0 bytes"));
        assertThat("format byteDecimal", Formats.formatBytesDecimal(1), is("1 byte"));
        assertThat("format bytesDecimal", Formats.formatBytesDecimal(532), is("532 bytes"));
        assertThat("format KbytesDecimal", Formats.formatBytesDecimal(1000), is("1 KB"));
        assertThat("format GbytesDecimal", Formats.formatBytesDecimal(1000 * 1000 * 1000), is("1 GB"));
        assertThat("format TbytesDecimal", Formats.formatBytesDecimal(1000000000000L), is("1 TB"));
    }

    /**
     * Test format decimal bytes with decimal separator.
     */
    @Test
    void testFormatBytesDecimalWithDecimalSeparator() {
        String expected1 = "1" + DECIMAL_SEPARATOR + "3 KB";
        String expected2 = "2" + DECIMAL_SEPARATOR + "3 MB";
        String expected3 = "2" + DECIMAL_SEPARATOR + "2 GB";
        String expected4 = "1" + DECIMAL_SEPARATOR + "1 TB";
        String expected5 = "3" + DECIMAL_SEPARATOR + "4 PB";
        String expected6 = "5" + DECIMAL_SEPARATOR + "6 EB";
        assertThat("format KBytes with decimal separator", Formats.formatBytesDecimal(1300), is(expected1));
        assertThat("format MBytes with decimal separator", Formats.formatBytesDecimal(2300000), is(expected2));
        assertThat("format GBytes with decimal separator", Formats.formatBytesDecimal(2200000000L), is(expected3));
        assertThat("format TBytes with decimal separator", Formats.formatBytesDecimal(1100000000000L),
                is(expected4));
        assertThat("format PBytes with decimal separator", Formats.formatBytesDecimal(3400000000000000L),
                is(expected5));
        assertThat("format EBytes with decimal separator", Formats.formatBytesDecimal(5600000000000000000L),
                is(expected6));
    }

    /**
     * Test format hertz.
     */
    @Test
    void testFormatHertz() {
        assertThat("format zero Hertz", Formats.formatHertz(0), is("0 Hz"));
        assertThat("format one Hertz", Formats.formatHertz(1), is("1 Hz"));
        assertThat("format many Hertz", Formats.formatHertz(999), is("999 Hz"));
        assertThat("format KHertz", Formats.formatHertz(1000), is("1 KHz"));
        assertThat("format MHertz", Formats.formatHertz(1000 * 1000), is("1 MHz"));
        assertThat("format GHertz", Formats.formatHertz(1000 * 1000 * 1000), is("1 GHz"));
        assertThat("format THertz", Formats.formatHertz(1000L * 1000L * 1000L * 1000L), is("1 THz"));
    }

    /**
     * Test format elapsed secs
     */
    @Test
    void testFormatElapsedSecs() {
        assertThat("format 0 elapsed seconds", Formats.formatElapsedSecs(0), is("0 days, 00:00:00"));
        assertThat("format many elapsed seconds", Formats.formatElapsedSecs(12345), is("0 days, 03:25:45"));
        assertThat("format elapsed day in seconds", Formats.formatElapsedSecs(123456), is("1 days, 10:17:36"));
        assertThat("format elapsed days in seconds", Formats.formatElapsedSecs(1234567), is("14 days, 06:56:07"));
    }

    /**
     * Test unsigned int to long.
     */
    @Test
    void testGetUnsignedInt() {
        assertThat("unsigned int", Formats.getUnsignedInt(-1), is(4294967295L));
    }

    /**
     * Test unsigned string
     */
    @Test
    void testToUnsignedString() {
        assertThat("Integer to unsigned string", Formats.toUnsignedString(0x00000001), is("1"));
        assertThat("Big Integer to unsigned string", Formats.toUnsignedString(0x80000000), is("2147483648"));
        assertThat("INT_MAX to unsigned string", Formats.toUnsignedString(0xffffffff), is("4294967295"));

        assertThat("Long to unsigned string", Formats.toUnsignedString(0x0000000000000001L), is("1"));
        assertThat("Big Long to unsigned string", Formats.toUnsignedString(0x8000000000000000L),
                is("9223372036854775808"));
        assertThat("LONG_MAX to unsigned string", Formats.toUnsignedString(0xffffffffffffffffL),
                is("18446744073709551615"));
    }

    /**
     * Test format error
     */
    @Test
    void testFormatError() {
        assertThat("Format error code", Formats.formatError(-1234567000), is("0xB66A00A8"));
    }

    /**
     * Test round to int
     */
    @Test
    void testRoundToInt() {
        assertThat("Improper rounding pi", Formats.roundToInt(Math.PI), is(3));
        assertThat("Improper rounding e", Formats.roundToInt(Math.E), is(3));
        assertThat("Improper rounding 0", Formats.roundToInt(0d), is(0));
        assertThat("Improper rounding 1", Formats.roundToInt(1d), is(1));
    }

}

package org.miaixz.bus.health;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.core.lang.tuple.Triplet;
import org.miaixz.bus.core.xyz.ByteKit;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The Class ParsingTest.
 */
class ParsingTest {

    private static final double EPSILON = Double.MIN_VALUE;

    /**
     * Test parse hertz.
     */
    @Test
    void testParseHertz() {
        assertThat("parse OneHz", Parsing.parseHertz("OneHz"), is(-1L));
        assertThat("parse NotEvenAHertz", Parsing.parseHertz("NotEvenAHertz"), is(-1L));
        assertThat("parse 10000000000000000000 Hz", Parsing.parseHertz("10000000000000000000 Hz"),
                is(Long.MAX_VALUE));
        assertThat("parse 1Hz", Parsing.parseHertz("1Hz"), is(1L));
        assertThat("parse 500 Hz", Parsing.parseHertz("500 Hz"), is(500L));
        assertThat("parse 1kHz", Parsing.parseHertz("1kHz"), is(1_000L));
        assertThat("parse 1MHz", Parsing.parseHertz("1MHz"), is(1_000_000L));
        assertThat("parse 1GHz", Parsing.parseHertz("1GHz"), is(1_000_000_000L));
        assertThat("parse 1.5GHz", Parsing.parseHertz("1.5GHz"), is(1_500_000_000L));
        assertThat("parse 1THz", Parsing.parseHertz("1THz"), is(1_000_000_000_000L));
        // GHz exceeds max double
    }

    /**
     * Test parse string.
     */
    @Test
    void testParseLastInt() {
        assertThat("parse def -1", Parsing.parseLastInt("foo : bar", -1), is(-1));
        assertThat("parse 1", Parsing.parseLastInt("foo : 1", 0), is(1));
        assertThat("parse def 2", Parsing.parseLastInt("foo", 2), is(2));
        assertThat("parse maxInt+1", Parsing.parseLastInt("max_int plus one is 2147483648", 3), is(3));
        assertThat("parse 0xff", Parsing.parseLastInt("0xff", 4), is(255));

        assertThat("parse def -1 as long", Parsing.parseLastLong("foo : bar", -1L), is(-1L));
        assertThat("parse 1 as long", Parsing.parseLastLong("foo : 1", 0L), is(1L));
        assertThat("parse def 2 as long", Parsing.parseLastLong("foo", 2L), is(2L));
        assertThat("parse maxInt+1 as long", Parsing.parseLastLong("max_int plus one is" + " 2147483648", 3L),
                is(2147483648L));
        assertThat("parse 0xff as long", Parsing.parseLastLong("0xff", 0L), is(255L));

        assertThat("parse def -1 as double", Parsing.parseLastDouble("foo : bar", -1d), is(closeTo(-1d, EPSILON)));
        assertThat("parse 1.0 as double", Parsing.parseLastDouble("foo : 1.0", 0d), is(closeTo(1d, EPSILON)));
        assertThat("parse def 2 as double", Parsing.parseLastDouble("foo", 2d), is(closeTo(2d, EPSILON)));
    }

    /**
     * Test parse string.
     */
    @Test
    void testParseLastString() {
        assertThat("parse bar", Parsing.parseLastString("foo : bar"), is("bar"));
        assertThat("parse foo", Parsing.parseLastString("foo"), is("foo"));
        assertThat("parse \"\"", Parsing.parseLastString(""), is(emptyString()));
    }

    /**
     * Test hex string to byte array (and back).
     */
    @Test
    void testHexStringToByteArray() {
        byte[] temp = {(byte) 0x12, (byte) 0xaf};
        assertThat(Arrays.equals(temp, ByteKit.hexStringToByteArray("12af")), is(true));
        assertThat("parse 12AF", ByteKit.byteArrayToHexString(temp), is("12AF"));
        temp = new byte[0];
        assertThat(Arrays.equals(temp, ByteKit.hexStringToByteArray("expected error abcde")), is(true));
        assertThat(Arrays.equals(temp, ByteKit.hexStringToByteArray("abcde")), is(true));
    }

    /**
     * Test string to byte array.
     */
    @Test
    void testStringToByteArray() {
        byte[] temp = {(byte) '1', (byte) '2', (byte) 'a', (byte) 'f', (byte) 0};
        assertThat(Arrays.equals(temp, Parsing.asciiStringToByteArray("12af", 5)), is(true));
    }

    /**
     * Test long to byte array.
     */
    @Test
    void testLongToByteArray() {
        byte[] temp = {(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0};
        assertThat(Arrays.equals(temp, Parsing.longToByteArray(0x12345678, 4, 5)), is(true));
    }

    /**
     * Test string and byte array to long.
     */
    @Test
    void testStringAndByteArrayToLong() {
        byte[] temp = {(byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e'};
        long abcde = (long) temp[0] << 32 | temp[1] << 24 | temp[2] << 16 | temp[3] << 8 | temp[4];
        long edcba = (long) temp[4] << 32 | temp[3] << 24 | temp[2] << 16 | temp[1] << 8 | temp[0];
        // Test string
        assertThat("parse \"abcde\"", Parsing.strToLong("abcde", 5), is(abcde));
        // Test byte array
        assertThat("Incorrect parsing of " + abcde, Parsing.byteArrayToLong(temp, 5), is(abcde));
        assertThat("Incorrect parsing of " + abcde + " BE", Parsing.byteArrayToLong(temp, 5, true), is(abcde));
        assertThat("Incorrect parsing of " + edcba + " LE", Parsing.byteArrayToLong(temp, 5, false), is(edcba));
    }

    @Test
    void testByteArrayToLongSizeTooBig() {
        assertThrows(IllegalArgumentException.class, () -> {
            Parsing.byteArrayToLong(new byte[10], 9);
        });
    }

    @Test
    void testByteArrayToLongSizeBigger() {
        assertThrows(IllegalArgumentException.class, () -> {
            Parsing.byteArrayToLong(new byte[7], 8);
        });
    }

    /**
     * Test byte array to float
     */
    @Test
    void testByteArrayToFloat() {
        byte[] temp = {(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x9a};
        float f = (temp[0] << 22 | temp[1] << 14 | temp[2] << 6 | temp[3] >>> 2) + (float) (temp[3] & 0x3) / 0x4;
        assertEquals(f, Parsing.byteArrayToFloat(temp, 4, 2), Float.MIN_VALUE);
        f = 0x12345 + (float) 0x6 / 0x10;
        assertEquals(f, Parsing.byteArrayToFloat(temp, 3, 4), Float.MIN_VALUE);
        f = 0x123 + (float) 0x4 / 0x10;
        assertEquals(f, Parsing.byteArrayToFloat(temp, 2, 4), Float.MIN_VALUE);
    }

    /**
     * Test unsigned int to long
     */
    @Test
    void testUnsignedIntToLong() {
        assertThat("parse 0 as long", Parsing.unsignedIntToLong(0), is(0L));
        assertThat("parse 123 as long", Parsing.unsignedIntToLong(123), is(123L));
        assertThat("parse 4294967295L as long", Parsing.unsignedIntToLong(0xffffffff), is(4294967295L));
    }

    /**
     * Test unsigned long to signed long
     */
    @Test
    void testUnsignedLongToSignedLong() {
        assertThat("parse 1 as signed long", Parsing.unsignedLongToSignedLong(Long.MAX_VALUE + 2), is(1L));
        assertThat("parse 123 as signed long", Parsing.unsignedLongToSignedLong(123), is(123L));
        assertThat("parse 9223372036854775807 as signed long", Parsing.unsignedLongToSignedLong(9223372036854775807L),
                is(9223372036854775807L));
    }

    /**
     * Test hex string to string
     */
    @Test
    void testHexStringToString() {
        assertThat("parse ABC as string", Parsing.hexStringToString("414243"), is("ABC"));
        assertThat("parse ab00cd as string", Parsing.hexStringToString("ab00cd"), is("ab00cd"));
        assertThat("parse ab88cd as string", Parsing.hexStringToString("ab88cd"), is("ab88cd"));
        assertThat("parse notHex as string", Parsing.hexStringToString("notHex"), is("notHex"));
        assertThat("parse 320 as string", Parsing.hexStringToString("320"), is("320"));
        assertThat("parse 0 as string", Parsing.hexStringToString("0"), is("0"));
    }

    /**
     * Test parse int
     */
    @Test
    void testParseIntOrDefault() {
        assertThat("parse 123", Parsing.parseIntOrDefault("123", 45), is(123));
        assertThat("parse 45", Parsing.parseIntOrDefault("123X", 45), is(45));
    }

    /**
     * Test parse long
     */
    @Test
    void testParseLongOrDefault() {
        assertThat("parse 123", Parsing.parseLongOrDefault("123", 45L), is(123L));
        assertThat("parse 45", Parsing.parseLongOrDefault("123L", 45L), is(45L));
    }

    /**
     * Test parse long
     */
    @Test
    void testParseUnsignedLongOrDefault() {
        assertThat("parse 9223372036854775807L", Parsing.parseUnsignedLongOrDefault("9223372036854775807", 123L),
                is(9223372036854775807L));
        assertThat("parse 9223372036854775808L", Parsing.parseUnsignedLongOrDefault("9223372036854775808", 45L),
                is(-9223372036854775808L));
        assertThat("parse 1L", Parsing.parseUnsignedLongOrDefault("18446744073709551615", 123L), is(-1L));
        assertThat("parse 0L", Parsing.parseUnsignedLongOrDefault("18446744073709551616", 45L), is(0L));
        assertThat("parse 123L", Parsing.parseUnsignedLongOrDefault("9223372036854775808L", 123L), is(123L));
    }

    /**
     * Test parse double
     */
    @Test
    void testParseDoubleOrDefault() {
        assertThat("parse 1.23d", Parsing.parseDoubleOrDefault("1.23", 4.5d), is(closeTo(1.23d, EPSILON)));
        assertThat("parse 4.5d", Parsing.parseDoubleOrDefault("one.twentythree", 4.5d), is(closeTo(4.5d, EPSILON)));
    }

    /**
     * Test parse DHMS
     */
    @Test
    void testParseDHMSOrDefault() {
        assertThat("parse 93784050L", Parsing.parseDHMSOrDefault("1-02:03:04.05", 0L), is(93784050L));
        assertThat("parse 93784000L", Parsing.parseDHMSOrDefault("1-02:03:04", 0L), is(93784000L));
        assertThat("parse 7384000L", Parsing.parseDHMSOrDefault("02:03:04", 0L), is(7384000L));
        assertThat("parse 184050L", Parsing.parseDHMSOrDefault("03:04.05", 0L), is(184050L));
        assertThat("parse 184000L", Parsing.parseDHMSOrDefault("03:04", 0L), is(184000L));
        assertThat("parse 4000L", Parsing.parseDHMSOrDefault("04", 0L), is(4000L));
        assertThat("parse 0L", Parsing.parseDHMSOrDefault("04:05-06", 0L), is(0L));
    }

    /**
     * Test parse UUID
     */
    @Test
    void testParseUuidOrDefault() {
        assertThat("parse uuid", Parsing.parseUuidOrDefault("123e4567-e89b-12d3-a456-426655440000", "default"),
                is("123e4567-e89b-12d3-a456-426655440000"));
        assertThat("parse uuid in string",
                Parsing.parseUuidOrDefault("The UUID is 123E4567-E89B-12D3-A456-426655440000!", "default"),
                is("123e4567-e89b-12d3-a456-426655440000"));
        assertThat("parse foo or default", Parsing.parseUuidOrDefault("foo", "default"), is("default"));
    }

    /**
     * Test parse SingleQuoteString
     */
    @Test
    void testGetSingleQuoteStringValue() {
        assertThat("parse bar", Parsing.getSingleQuoteStringValue("foo = 'bar' (string)"), is("bar"));
        assertThat("parse empty string", Parsing.getSingleQuoteStringValue("foo = bar (string)"), is(""));
    }

    @Test
    void testGetDoubleQuoteStringValue() {
        assertThat("parse bar", Parsing.getDoubleQuoteStringValue("foo = \"bar\" (string)"), is("bar"));
        assertThat("parse empty string", Parsing.getDoubleQuoteStringValue("hello"), is(""));
    }

    /**
     * Test parse SingleQuoteBetweenMultipleQuotes
     */
    @Test
    void testGetStringBetweenMultipleQuotes() {
        assertThat("parse Single quotes between Multiple quotes",
                Parsing.getStringBetween("hello = $hello $ is $", '$'), is("hello $ is"));
        assertThat("parse Single quotes between Multiple quotes",
                Parsing.getStringBetween("pci.device = 'Realtek AC'97 Audio'", '\''), is("Realtek AC'97 Audio"));
    }

    /**
     * Test parse FirstIntValue
     */
    @Test
    void testGetFirstIntValue() {
        assertThat("parse FirstIntValue", Parsing.getFirstIntValue("foo = 42 (0x2a) (int)"), is(42));
        assertThat("parse FirstIntValue", Parsing.getFirstIntValue("foo = 0x2a (int)"), is(0));
        assertThat("parse FirstIntValue", Parsing.getFirstIntValue("42"), is(42));
        assertThat("parse FirstIntValue", Parsing.getFirstIntValue("10.12.2"), is(10));
    }

    /**
     * Test parse NthIntValue
     */
    @Test
    void testGetNthIntValue() {
        assertThat("parse NthIntValue", Parsing.getNthIntValue("foo = 42 (0x2a) (int)", 3), is(2));
        assertThat("parse NthIntValue", Parsing.getNthIntValue("foo = 0x2a (int)", 3), is(0));
        assertThat("parse NthIntValue", Parsing.getNthIntValue("10.12.2", 2), is(12));
    }

    /**
     * Test parse removeMatchingString
     */
    @Test
    void testRemoveMatchingString() {
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("foo = 42 (0x2a) (int)", "0x2a"),
                is("foo = 42 () (int)"));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("foo = 0x2a (int)", "qqq"),
                is("foo = 0x2a (int)"));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("10.12.2", "2"), is("10.1."));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("10.12.2", "10.12.2"),
                is(emptyString()));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("", "10.12.2"), is(emptyString()));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString(null, "10.12.2"), is(nullValue()));
        assertThat("parse removeMatchingString", Parsing.removeMatchingString("10.12.2", "10.12."), is("2"));
    }

    /**
     * Test parse string to array
     */
    @Test
    void testParseStringToLongArray() {
        int[] indices = {1, 3};
        long now = System.currentTimeMillis();

        String foo = String.format(Locale.ROOT, "The numbers are %d %d %d %d", 123, 456, 789, now);
        int count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));
        long[] result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[0] should be 456 using parseStringToLongArray on \"" + foo + "\"", result[0], is(456L));
        assertThat("result[1] should be " + now + " using parseStringToLongArray on \"" + foo + "\"", result[1],
                is(now));

        foo = String.format(Locale.ROOT, "The numbers are %d %d %d %d %s", 123, 456, 789, now,
                "709af748-5f8e-41b3-b73a-b440ef4406c8");
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));
        result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[0] should be 456 using parseStringToLongArray on \"" + foo + "\"", result[0], is(456L));
        assertThat("result[1] should be " + now + " using parseStringToLongArray on \"" + foo + "\"", result[1],
                is(now));

        foo = String.format(Locale.ROOT, "The numbers are %d -%d %d +%d", 123, 456, 789, now);
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));
        result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[0] should be -4456 using parseStringToLongArray on \"" + foo + "\"", result[0], is(-456L));
        assertThat("result[1] index should be 456 using parseStringToLongArray on \"" + foo + "\"", result[1], is(now));

        foo = String.format(Locale.ROOT, "NOLOG: Invalid character %d %s %d %d", 123, "4v6", 789, now);
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 2 for \"" + foo + "\"", count, is(2));
        result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[1] index should be 0 using parseStringToLongArray on \"" + foo + "\"", result[1], is(0L));

        foo = String.format(Locale.ROOT, "Exceeds max long %d %d %d 1%d", 123, 456, 789, Long.MAX_VALUE);
        result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[1] index should be " + Long.MAX_VALUE
                + " (Long.MAX_VALUE) using parseStringToLongArray on \"" + foo + "\"", result[1], is(Long.MAX_VALUE));

        foo = String.format(Locale.ROOT, "NOLOG: String too short %d %d %d %d", 123, 456, 789, now);
        result = Parsing.parseStringToLongArray(foo, indices, 9, ' ');
        assertThat("result[1] index should be 0 using parseStringToLongArray on \"" + foo + "\"", result[1], is(0L));

        foo = String.format(Locale.ROOT, "NOLOG: Array too short %d %d %d %d", 123, 456, 789, now);
        result = Parsing.parseStringToLongArray(foo, indices, 2, ' ');
        assertThat("result[1] index should be 0 using parseStringToLongArray on \"" + foo + "\"", result[1], is(0L));

        foo = String.format(Locale.ROOT, "%d %d %d %d", 123, 456, 789, now);
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));

        foo = String.format(Locale.ROOT, "%d %d %d %d nonNumeric", 123, 456, 789, now);
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));

        foo = String.format(Locale.ROOT, "%d %d %d %d 123-456", 123, 456, 789, now);
        count = Parsing.countStringToLongArray(foo, ' ');
        assertThat("countStringToLongArray should return 4 for \"" + foo + "\"", count, is(4));

        foo = String.format(Locale.ROOT, "%d %d %d %d", 123, 456, 789, now);
        indices = new int[]{0};
        result = Parsing.parseStringToLongArray(foo, indices, 4, ' ');
        assertThat("result[0] should be 123 using parseStringToLongArray on \"" + foo + "\"", result[0], is(123L));
    }

    @Test
    void testTextBetween() {
        String text = "foo bar baz";
        String before = "foo";
        String after = "baz";
        assertThat(Parsing.getTextBetweenStrings(text, before, after), is(" bar "));

        before = "";
        assertThat(Parsing.getTextBetweenStrings(text, before, after), is("foo bar "));

        before = "food";
        assertThat(Parsing.getTextBetweenStrings(text, before, after), is(emptyString()));

        before = "foo";
        after = "qux";
        assertThat(Parsing.getTextBetweenStrings(text, before, after), is(emptyString()));
    }

    @Test
    void testFiletimeToMs() {
        assertThat(Parsing.filetimeToUtcMs(128166372003061629L, false), is(1172163600306L));
    }

    @Test
    void testParseCimDateTimeToOffset() {
        String cimDateTime = "20160513072950.782000-420";
        OffsetDateTime parsedTime = Parsing.parseCimDateTimeToOffset(cimDateTime);
        assertNotNull(parsedTime);
        // 2016-05-13T07:29:50 == 1463124590
        // Add 420 minutes to get unix seconds
        Instant timeInst = Instant.ofEpochMilli(1463124590_782L + 60 * 420_000L);
        assertThat(parsedTime.toInstant(), is(timeInst));
        OffsetDateTime badParsingTime = Parsing.parseCimDateTimeToOffset("Not a datetime");
        assertNotNull(badParsingTime);
        assertThat(badParsingTime.toInstant(), is(Instant.EPOCH));
    }

    @Test
    void testFilePathStartsWith() {
        List<String> prefixList = Arrays.asList("/foo", "/bar");
        assertThat(Parsing.filePathStartsWith(prefixList, "/foo"), is(true));
        assertThat(Parsing.filePathStartsWith(prefixList, "/foo/bar"), is(true));
        assertThat(Parsing.filePathStartsWith(prefixList, "/foobar"), is(false));
        assertThat(Parsing.filePathStartsWith(prefixList, "/foo/baz"), is(true));
        assertThat(Parsing.filePathStartsWith(prefixList, "/baz/foo"), is(false));
    }

    @Test
    void testParseDecimalMemorySizeToBinary() {
        assertThat(Parsing.parseDecimalMemorySizeToBinary("Not a number"), is(0L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1"), is(1L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1 kB"), is(1024L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1 KB"), is(1024L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1 MB"), is(1_048_576L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1MB"), is(1_048_576L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1 GB"), is(1_073_741_824L));
        assertThat(Parsing.parseDecimalMemorySizeToBinary("1 TB"), is(1_099_511_627_776L));
    }

    @Test
    void testParseDeviceIdToVendorProductSerial() {
        Triplet<String, String, String> idsAndSerial = Parsing
                .parseDeviceIdToVendorProductSerial("PCI\\VEN_10DE&DEV_134B&SUBSYS_00081414&REV_A2\\4&25BACB6&0&00E0");
        assertThat("VEN_ DEV_ deviceID failed to parse", idsAndSerial, is(notNullValue()));
        assertThat("Vendor ID failed to parse", idsAndSerial.getLeft(), is("0x10de"));
        assertThat("Product ID failed to parse", idsAndSerial.getMiddle(), is("0x134b"));
        assertThat("SerialNumber should not have parsed", idsAndSerial.getRight(), is(emptyString()));

        idsAndSerial = Parsing.parseDeviceIdToVendorProductSerial("USB\\VID_045E&PID_07C6\\000001000000");
        assertThat("VID_ PID_ serial deviceID failed to parse", idsAndSerial, is(notNullValue()));
        assertThat("Vendor ID failed to parse", idsAndSerial.getLeft(), is("0x045e"));
        assertThat("Product ID failed to parse", idsAndSerial.getMiddle(), is("0x07c6"));
        assertThat("SerialNumber failed to parse", idsAndSerial.getRight(), is("000001000000"));

        idsAndSerial = Parsing.parseDeviceIdToVendorProductSerial("USB\\VID_045E&PID_07C6\\5&000001000000");
        assertThat("VID_ PID_ nonserial deviceID failed to parse", idsAndSerial, is(notNullValue()));
        assertThat("Vendor ID failed to parse", idsAndSerial.getLeft(), is("0x045e"));
        assertThat("Product ID failed to parse", idsAndSerial.getMiddle(), is("0x07c6"));
        assertThat("SerialNumber should not have parsed", idsAndSerial.getRight(), is(emptyString()));

        idsAndSerial = Parsing
                .parseDeviceIdToVendorProductSerial("PCI\\VEN_80286&DEV_19116&SUBSYS_00141414&REV_07\\3&11583659&0&10");
        assertThat("Vender and Product IDs should not have parsed", idsAndSerial, is(nullValue()));
    }

    @Test
    void testParseLshwResourceString() {
        assertThat(
                Parsing.parseLshwResourceString(
                        "irq:46 ioport:6000(size=32) memory:b0000000-bfffffff memory:e2000000-e200ffff"),
                is(268_435_456L + 65_536L));
        assertThat(
                Parsing.parseLshwResourceString(
                        "irq:46 ioport:6000(size=32) memory:b0000000-bfffffff memory:x2000000-e200ffff"),
                is(268_435_456L));
        assertThat(Parsing.parseLshwResourceString(
                "irq:46 ioport:6000(size=32) memory:x0000000-bfffffff memory:e2000000-e200ffff"), is(65_536L));
        assertThat(Parsing.parseLshwResourceString("some random string"), is(0L));
    }

    @Test
    void testParseLspciMachineReadable() {
        Pair<String, String> pair = Parsing.parseLspciMachineReadable("foo [bar]");
        assertThat("First element of pair mismatch.", pair.getLeft(), is("foo"));
        assertThat("Second element of pair mismatch.", pair.getRight(), is("bar"));
        assertThat(Parsing.parseLspciMachineReadable("Bad format"), is(nullValue()));
    }

    @Test
    void testParseLspciMemorySize() {
        assertThat(Parsing.parseLspciMemorySize("Doesn't parse"), is(0L));
        assertThat(Parsing.parseLspciMemorySize("Foo [size=64K]"), is(64L * 1024L));
        assertThat(Parsing.parseLspciMemorySize("Foo [size=256M]"), is(256L * 1024L * 1024L));
    }

    @Test
    void testParseHyphenatedIntList() {
        String s = "1";
        List<Integer> parsed = Parsing.parseHyphenatedIntList(s);
        assertThat(parsed, not(hasItems(0)));
        assertThat(parsed, contains(1));

        s = "0 2-5 7";
        parsed = Parsing.parseHyphenatedIntList(s);
        assertThat(parsed, contains(0, 2, 3, 4, 5, 7));
        assertThat(parsed, not(hasItems(1)));
        assertThat(parsed, not(hasItems(6)));

        s = "0, 2-5, 7-8, 9";
        parsed = Parsing.parseHyphenatedIntList(s);
        assertThat(parsed, contains(0, 2, 3, 4, 5, 7, 8, 9));
        assertThat(parsed, not(hasItems(1)));
        assertThat(parsed, not(hasItems(6)));
    }

    @Test
    void testParseMmDdYyyyToYyyyMmDD() {
        assertThat("Unable to parse MM-DD-YYYY date string into YYYY-MM-DD date string",
                Parsing.parseMmDdYyyyToYyyyMmDD("00-11-2222"), is("2222-00-11"));
        assertThat("Date string should not be parsed", Parsing.parseMmDdYyyyToYyyyMmDD("badstr"), is("badstr"));
    }

    @Test
    void testParseIntToIP() {
        // IP addresses are big endian
        int ip = 1 | 2 << 8 | 3 << 16 | 4 << 24;
        byte[] ipb = {(byte) 1, (byte) 2, (byte) 3, (byte) 4};
        assertThat("IP did not parse properly", Parsing.parseIntToIP(ip), is(ipb));
    }

    @Test
    void testParseIntArrayToIP() {
        // IP addresses are big endian
        int[] ip = new int[4];
        ip[0] = 1 | 2 << 8 | 3 << 16 | 4 << 24;
        ip[1] = 5 | 6 << 8 | 7 << 16 | 8 << 24;
        ip[2] = 9 | 10 << 8 | 11 << 16 | 12 << 24;
        ip[3] = 13 | 14 << 8 | 15 << 16 | 16 << 24;
        byte[] ipb = {(byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9,
                (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16};
        assertThat("IP array did not parse properly", Parsing.parseIntArrayToIP(ip), is(ipb));
    }

    @Test
    void testBigEndian16ToLittleEndian() {
        assertThat("Port 80 did not convert properly", Parsing.bigEndian16ToLittleEndian(0x5000), is(80));
        assertThat("Port 443 did not convert properly", Parsing.bigEndian16ToLittleEndian(0xBB01), is(443));
    }

    @Test
    void testParseUtAddrV6toIP() {
        int[] zero = {0, 0, 0, 0};
        int[] loopback = {0, 0, 0, 1};
        String v6test = "2001:db8:85a3::8a2e:370:7334";
        int[] v6 = new int[4];
        v6[0] = Integer.parseUnsignedInt("20010db8", 16);
        v6[1] = Integer.parseUnsignedInt("85a30000", 16);
        v6[2] = Integer.parseUnsignedInt("00008a2e", 16);
        v6[3] = Integer.parseUnsignedInt("03707334", 16);
        String v4test = "127.0.0.1";
        int[] v4 = new int[4];
        v4[0] = (127 << 24) + 1;
        int[] invalid = {0, 0, 0};

        assertThat("Unspecified address failed", Parsing.parseUtAddrV6toIP(zero), is("::"));
        assertThat("Loopback address failed", Parsing.parseUtAddrV6toIP(loopback), is("::1"));
        assertThat("V6 parsing failed", Parsing.parseUtAddrV6toIP(v6), is(v6test));
        assertThat("V4 parsing failed", Parsing.parseUtAddrV6toIP(v4), is(v4test));
        assertThrows(IllegalArgumentException.class, () -> {
            Parsing.parseUtAddrV6toIP(invalid);
        });
    }

    @Test
    void testHexStringToInt() {
        assertThat("Parsing ff failed", Parsing.hexStringToInt("ff", 0), is(255));
        assertThat("Parsing 830f53a0 failed", Parsing.hexStringToInt("830f53a0", 0), is(-2096147552));
        assertThat("Parsing pqwe failed", Parsing.hexStringToInt("pqwe", 0), is(0));
        assertThat("Parsing 0xff failed", Parsing.hexStringToInt("0xff", 0), is(255));
        assertThat("Parsing 0x830f53a0 failed", Parsing.hexStringToInt("0x830f53a0", 0), is(-2096147552));
        assertThat("Parsing 0xpqwe failed", Parsing.hexStringToInt("0xpqwe", 0), is(0));
    }

    @Test
    void testHexStringToLong() {
        assertThat("Parsing ff failed", Parsing.hexStringToLong("ff", 0L), is(255L));
        assertThat("Parsing 830f53a0 failed", Parsing.hexStringToLong("ffffffff830f53a0", 0L), is(-2096147552L));
        assertThat("Parsing pqwe failed", Parsing.hexStringToLong("pqwe", 0L), is(0L));
        assertThat("Parsing 0xff failed", Parsing.hexStringToLong("0xff", 0L), is(255L));
        assertThat("Parsing 0x830f53a0 failed", Parsing.hexStringToLong("0xffffffff830f53a0", 0L), is(-2096147552L));
        assertThat("Parsing 0xpqwe failed", Parsing.hexStringToLong("0xpqwe", 0L), is(0L));
    }

    @Test
    void testRemoveLeadingDots() {
        assertThat(Parsing.removeLeadingDots("foo"), is("foo"));
        assertThat(Parsing.removeLeadingDots("...bar"), is("bar"));
        assertThat(Parsing.removeLeadingDots("..."), is(""));
    }

    @Test
    void testParseMultipliedToLongs() {
        assertThat(Parsing.parseMultipliedToLongs("Not a number"), is(0L));
        assertThat(Parsing.parseMultipliedToLongs("1"), is(1L));
        assertThat(Parsing.parseMultipliedToLongs("1.2"), is(1L));
        assertThat(Parsing.parseMultipliedToLongs("1 k"), is(1_000L));
        assertThat(Parsing.parseMultipliedToLongs("1 M"), is(1_000_000L));
        assertThat(Parsing.parseMultipliedToLongs("1MB"), is(1_000_000L));
        assertThat(Parsing.parseMultipliedToLongs("1MC"), is(1_000_000L));
        assertThat(Parsing.parseMultipliedToLongs("1 T"), is(1_000_000_000_000L));
        assertThat(Parsing.parseMultipliedToLongs("1073M"), is(1073000000L));
        assertThat(Parsing.parseMultipliedToLongs("1073 G"), is(1073000000000L));
        assertThat(Parsing.parseMultipliedToLongs("12K"), is(12000L));
    }

    @Test
    void parseByteArrayToStrings() {
        byte[] bytes = "foo bar".getBytes(StandardCharsets.US_ASCII);
        bytes[3] = 0;
        List<String> list = Parsing.parseByteArrayToStrings(bytes);
        assertThat(list, contains("foo", "bar"));

        bytes[4] = 0;
        list = Parsing.parseByteArrayToStrings(bytes);
        assertThat(list, contains("foo"));

        bytes[0] = 0;
        list = Parsing.parseByteArrayToStrings(bytes);
        assertThat(list, empty());

        bytes = new byte[0];
        list = Parsing.parseByteArrayToStrings(bytes);
        assertThat(list, empty());
    }

    @Test
    void parseByteArrayToStringMap() {
        byte[] bytes = "foo=1 bar=2".getBytes(StandardCharsets.US_ASCII);
        bytes[5] = 0;
        Map<String, String> map = Parsing.parseByteArrayToStringMap(bytes);
        assertThat(map.keySet(), containsInAnyOrder("foo", "bar"));
        assertThat(map.values(), containsInAnyOrder("1", "2"));
        assertThat(map.get("foo"), is("1"));
        assertThat(map.get("bar"), is("2"));

        bytes[10] = 0;
        map = Parsing.parseByteArrayToStringMap(bytes);
        assertThat(map.keySet(), containsInAnyOrder("foo", "bar"));
        assertThat(map.values(), containsInAnyOrder("1", ""));
        assertThat(map.get("foo"), is("1"));
        assertThat(map.get("bar"), is(""));

        bytes = "foo=1 bar=2".getBytes(StandardCharsets.US_ASCII);
        bytes[5] = 0;
        bytes[6] = 0;
        map = Parsing.parseByteArrayToStringMap(bytes);
        assertThat(map.keySet(), contains("foo"));
        assertThat(map.values(), contains("1"));
        assertThat(map.get("foo"), is("1"));

        bytes[0] = 0;
        map = Parsing.parseByteArrayToStringMap(bytes);
        assertThat(map, anEmptyMap());

        bytes = new byte[0];
        map = Parsing.parseByteArrayToStringMap(bytes);
        assertThat(map, anEmptyMap());
    }

    @Test
    void parseCharArrayToStringMap() {
        char[] chars = "foo=1 bar=2".toCharArray();
        chars[5] = 0;
        Map<String, String> map = Parsing.parseCharArrayToStringMap(chars);
        assertThat(map.keySet(), containsInAnyOrder("foo", "bar"));
        assertThat(map.values(), containsInAnyOrder("1", "2"));
        assertThat(map.get("foo"), is("1"));
        assertThat(map.get("bar"), is("2"));

        chars[10] = 0;
        map = Parsing.parseCharArrayToStringMap(chars);
        assertThat(map.keySet(), containsInAnyOrder("foo", "bar"));
        assertThat(map.values(), containsInAnyOrder("1", ""));
        assertThat(map.get("foo"), is("1"));
        assertThat(map.get("bar"), is(""));

        chars = "foo=1 bar=2".toCharArray();
        chars[5] = 0;
        chars[6] = 0;
        map = Parsing.parseCharArrayToStringMap(chars);
        assertThat(map.keySet(), contains("foo"));
        assertThat(map.values(), contains("1"));
        assertThat(map.get("foo"), is("1"));

        chars[0] = 0;
        map = Parsing.parseCharArrayToStringMap(chars);
        assertThat(map, anEmptyMap());

        chars = new char[0];
        map = Parsing.parseCharArrayToStringMap(chars);
        assertThat(map, anEmptyMap());
    }

    @Test
    void teststringToEnumMap() {
        String two = "one,two";
        Map<TestEnum, String> map = Parsing.stringToEnumMap(TestEnum.class, two, ',');
        assertThat(map.get(TestEnum.FOO), is("one"));
        assertThat(map.get(TestEnum.BAR), is("two"));
        assertThat(map.containsKey(TestEnum.BAZ), is(false));

        String three = "one,,two,three";
        map = Parsing.stringToEnumMap(TestEnum.class, three, ',');
        assertThat(map.get(TestEnum.FOO), is("one"));
        assertThat(map.get(TestEnum.BAR), is("two"));
        assertThat(map.get(TestEnum.BAZ), is("three"));

        String four = "one,two,three,four";
        map = Parsing.stringToEnumMap(TestEnum.class, four, ',');
        assertThat(map.get(TestEnum.FOO), is("one"));
        assertThat(map.get(TestEnum.BAR), is("two"));
        assertThat(map.get(TestEnum.BAZ), is("three,four"));

        String empty = "";
        map = Parsing.stringToEnumMap(TestEnum.class, empty, ',');
        assertThat(map.get(TestEnum.FOO), is(""));
    }

    @Test
    void testgetValueOrUnknown() {
        String key = "key";
        Map<String, String> map = new HashMap<>();
        assertThat(Parsing.getValueOrUnknown(map, key), is(Normal.UNKNOWN));

        map.put("key", "value");
        assertThat(Parsing.getValueOrUnknown(map, key), is("value"));
    }

    private enum TestEnum {
        FOO, BAR, BAZ;
    }

}

package org.miaixz.bus.core.center.date.format.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.PatternKit;

import java.util.Date;
import java.util.regex.Pattern;

public class RegexDateParserTest {

    // hh:mm:ss.SSSSZ hh:mm:ss.SSSS hh:mm:ss hh:mm
    final String timeRegex = "(" +
            "\\s(?<hour>\\d{1,2})" +
            ":(?<minute>\\d{1,2})" +
            "(:(?<second>\\d{1,2}))?" +
            "(?:[.,](?<ns>\\d{1,9}))?(?<zero>z)?" +
            "(\\s?(?<m>am|pm))?" +
            ")?";
    // +08:00 +0800 +08
    final String zoneOffsetRegex = "(\\s?(?<zoneOffset>[-+]\\d{1,2}:?(?:\\d{2})?))?";
    // CST UTC (CST)
    final String zoneNameRegex = "(\\s[(]?(?<zoneName>[a-z ]+)[)]?)?";

    private static void assertMatch(final String regex, final String dateStr) {
        Assertions.assertTrue(PatternKit.isMatch(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), dateStr));
    }

    @Test
    void timeMatchTest() {
        assertMatch(timeRegex, " 15:04:05");
        assertMatch(timeRegex, " 15:04:05.3186369");
        assertMatch(timeRegex, " 15:04:05.3186369z");
        assertMatch(timeRegex, " 15:04:05.318");
        assertMatch(timeRegex, " 15:04:05.318");
        assertMatch(timeRegex, " 15:04");
        assertMatch(timeRegex, " 05:04pm");
        assertMatch(timeRegex, " 05:04 PM");
        assertMatch(timeRegex, " 05:04:12PM");
        assertMatch(timeRegex, " 05:04:12 PM");
        assertMatch(timeRegex, " 5:4pm");
        assertMatch(timeRegex, " 5:4am");
    }

    @Test
    void zoneOffsetMatchTest() {
        assertMatch(zoneOffsetRegex, "+0800");
        assertMatch(zoneOffsetRegex, "+08");
        assertMatch(zoneOffsetRegex, "+08:00");
        assertMatch(zoneOffsetRegex, " +08:00");
        assertMatch(zoneOffsetRegex, "+0000");
        assertMatch(zoneOffsetRegex, " -0700");
    }

    @Test
    void zoneNameMatchTest() {
        assertMatch(zoneNameRegex, " (CST)");
        assertMatch(zoneNameRegex, " CST");
        assertMatch(zoneNameRegex, " (GMT)");
        assertMatch(zoneNameRegex, " (CEST)");
        assertMatch(zoneNameRegex, " (GMT Daylight Time)");
    }

    @Test
    void parsePureTest() {
        // yyyyMMdd
        final RegexDateParser parser = RegexDateParser.of("^(?<year>\\d{4})(?<month>\\d{2})(?<day>\\d{2})$");
        final Date parse = parser.parse("20220101");
        Assertions.assertEquals("2022-01-01", DateKit.date(parse).toDateString());
    }

    @Test
    void parseMonthFirstTest() {
        final String dateRegex = "(?<month>\\w+{3,9})\\W+(?<day>\\d{1,2})(?:th)?\\W+(?<year>\\d{2,4})";

        // +08:00
        // +0800
        // +08
        final String zoneOffsetRegex = "(\\s?(?<zoneOffset>[-+]\\d{1,2}:?(?:\\d{2})?))?";

        // May 8, 2009 5:57:51
        final RegexDateParser parser = RegexDateParser.of(dateRegex + timeRegex + zoneOffsetRegex);

        Date parse = parser.parse("May 8, 2009 5:57:51");
        Assertions.assertEquals("2009-05-08 05:57:51", DateKit.date(parse).toString());

        parse = parser.parse("May 8, 2009 5:57:51 +08:00");
        Assertions.assertEquals("2009-05-08 05:57:51", DateKit.date(parse).toString());

        parse = parser.parse("May 8, 2009 5:57:51 +0800");
        Assertions.assertEquals("2009-05-08 05:57:51", DateKit.date(parse).toString());

        parse = parser.parse("May 8, 2009 5:57:51 +08");
        Assertions.assertEquals("2009-05-08 05:57:51", DateKit.date(parse).toString());

        parse = parser.parse("May 8, 2009");
        Assertions.assertEquals("2009-05-08 00:00:00", DateKit.date(parse).toString());

        parse = parser.parse("May 8th, 2009");
        Assertions.assertEquals("2009-05-08 00:00:00", DateKit.date(parse).toString());

        parse = parser.parse("May 8th, 09");
        Assertions.assertEquals("2009-05-08 00:00:00", DateKit.date(parse).toString());

        parse = parser.parse("may. 8th, 09");
        Assertions.assertEquals("2009-05-08 00:00:00", DateKit.date(parse).toString());
    }

}

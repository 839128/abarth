package org.miaixz.bus.core.center.date.format;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.function.Function;

public class FormatPeriodTest {

    Function<FormatPeriod.Level, String> levelFormatterEn = level -> {
        switch (level) {
            case DAY:
                return " day";
            case HOUR:
                return " hour";
            case MINUTE:
                return " minute";
            case SECOND:
                return " second";
            case MILLISECOND:
                return " millisecond";
            default:
                return " " + level.name();
        }
    };

    @Test
    public void formatTest() {
        final long betweenMs = DateKit.betweenMs(DateKit.parse("2017-01-01 22:59:59"), DateKit.parse("2017-01-02 23:59:58"));
        final FormatPeriod formatter = new FormatPeriod(betweenMs, FormatPeriod.Level.MILLISECOND, 1);
        Assertions.assertEquals(formatter.toString(), "1天");
    }

    @Test
    public void formatTestEn() {
        final long betweenMs = DateKit.betweenMs(DateKit.parse("2017-01-01 22:59:59"), DateKit.parse("2017-01-02 23:59:58"));
        final FormatPeriod formatter = new FormatPeriod(betweenMs, FormatPeriod.Level.MILLISECOND, 1);
        formatter.setFormatter(levelFormatterEn);
        Assertions.assertEquals(formatter.toString(), "1 day");
    }

    @Test
    public void formatBetweenTest() {
        final long betweenMs = DateKit.betweenMs(DateKit.parse("2018-07-16 11:23:19"), DateKit.parse("2018-07-16 11:23:20"));
        final FormatPeriod formatter = new FormatPeriod(betweenMs, FormatPeriod.Level.SECOND, 1);
        Assertions.assertEquals(formatter.toString(), "1秒");
    }

    @Test
    public void formatBetweenTest2() {
        final long betweenMs = DateKit.betweenMs(DateKit.parse("2018-07-16 12:25:23"), DateKit.parse("2018-07-16 11:23:20"));
        final FormatPeriod formatter = new FormatPeriod(betweenMs, FormatPeriod.Level.SECOND, 5);
        Assertions.assertEquals(formatter.toString(), "1小时2分3秒");
    }

    @Test
    public void formatTest2() {
        final FormatPeriod formatter = new FormatPeriod(584, FormatPeriod.Level.SECOND, 1);
        Assertions.assertEquals(formatter.toString(), "0秒");
    }

    @Test
    void issueTest() {
        String s = FormatPeriod.of(3609000, FormatPeriod.Level.SECOND).setSimpleMode(false).format();
        Assertions.assertEquals("1小时0分9秒", s);

        s = FormatPeriod.of(9000, FormatPeriod.Level.MILLISECOND).setSimpleMode(false).format();
        Assertions.assertEquals("9秒", s);

        s = FormatPeriod.of(3600000, FormatPeriod.Level.MILLISECOND).setSimpleMode(false).format();
        Assertions.assertEquals("1小时0分0秒", s);

        s = FormatPeriod.of(3600000, FormatPeriod.Level.MILLISECOND).setSimpleMode(false).setFormatter(levelFormatterEn).setSeparator(",").format();
        Assertions.assertEquals("1 hour,0 minute,0 second", s);
    }
}

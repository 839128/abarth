package org.miaixz.bus.core.center.date.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateBuilderTest {

    @Test
    public void testNormal() {
        final DateBuilder builder = new DateBuilder();
        builder.setYear(2019);
        builder.setMonth(10);
        builder.setDay(1);
        final Date date = builder.toDate();

        Assertions.assertEquals("2019-10-01", DateKit.date(date).toDateString());
    }

    @Test
    public void testLocalDateTime() {
        final DateBuilder builder = DateBuilder.of()
                .setYear(2019)
                .setMonth(10)
                .setDay(1)
                .setHour(10)
                .setMinute(20)
                .setSecond(30)
                .setNs(900000000)
                .setZone(TimeZone.getDefault());

        final LocalDateTime dateTime = builder.toLocalDateTime();
        Assertions.assertEquals("2019-10-01T10:20:30.900", builder.toLocalDateTime().toString());
    }

    @Test
    public void testTimestamp() {
        final String timestamp = "946656000";
        final DateBuilder dateBuilder = DateBuilder.of().setUnixsecond(Long.parseLong(timestamp));
        Assertions.assertEquals("2000-01-01T00:00", dateBuilder.toLocalDateTime().toString());
    }

}

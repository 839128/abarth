package org.aoju.bus.core.convert;

import org.aoju.bus.core.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvertTest {

    @Test
    public void toDateTest() {
        String a = "2017-05-06";
        Date value = Convert.toDate(a);
        Assertions.assertEquals(a, DateUtils.formatDate(value));

        long timeLong = DateUtils.date().getTime();
        Date value2 = Convert.toDate(timeLong);
        Assertions.assertEquals(timeLong, value2.getTime());
    }

    @Test
    public void toDateFromIntTest() {
        int dateLong = -1497600000;
        Date value = Convert.toDate(dateLong);
        Assertions.assertNotNull(value);
        Assertions.assertEquals("Mon Dec 15 00:00:00 CST 1969", value.toString());

        final java.sql.Date sqlDate = Convert.convert(java.sql.Date.class, dateLong);
        Assertions.assertNotNull(sqlDate);
        Assertions.assertEquals("1969-12-15", sqlDate.toString());
    }

    @Test
    public void toDateFromLocalDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Date value = Convert.toDate(localDateTime);
        Assertions.assertNotNull(value);
        Assertions.assertEquals("2017-05-06", DateUtils.formatDate(value));
    }

    @Test
    public void toSqlDateTest() {
        String a = "2017-05-06";
        java.sql.Date value = Convert.convert(java.sql.Date.class, a);
        Assertions.assertEquals("2017-05-06", value.toString());

        long timeLong = DateUtils.date().getTime();
        java.sql.Date value2 = Convert.convert(java.sql.Date.class, timeLong);
        Assertions.assertEquals(timeLong, value2.getTime());
    }

}

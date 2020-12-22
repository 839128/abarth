package org.aoju.bus.core.convert;

import org.aoju.bus.core.toolkit.DateKit;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConvertTest {

    @Test
    public void toDateTest() {
        String a = "2017-05-06";
        Date value = Convert.toDate(a);
        Assert.assertEquals(a, DateKit.formatDate(value));

        long timeLong = DateKit.date().getTime();
        Date value2 = Convert.toDate(timeLong);
        Assert.assertEquals(timeLong, value2.getTime());
    }

    @Test
    public void toDateFromIntTest() {
        int dateLong = -1497600000;
        Date value = Convert.toDate(dateLong);
        Assert.assertNotNull(value);
        Assert.assertEquals("Mon Dec 15 00:00:00 CST 1969", value.toString());

        final java.sql.Date sqlDate = Convert.convert(java.sql.Date.class, dateLong);
        Assert.assertNotNull(sqlDate);
        Assert.assertEquals("1969-12-15", sqlDate.toString());
    }

    @Test
    public void toDateFromLocalDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.parse("2017-05-06T08:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Date value = Convert.toDate(localDateTime);
        Assert.assertNotNull(value);
        Assert.assertEquals("2017-05-06", DateKit.formatDate(value));
    }

    @Test
    public void toSqlDateTest() {
        String a = "2017-05-06";
        java.sql.Date value = Convert.convert(java.sql.Date.class, a);
        Assert.assertEquals("2017-05-06", value.toString());

        long timeLong = DateKit.date().getTime();
        java.sql.Date value2 = Convert.convert(java.sql.Date.class, timeLong);
        Assert.assertEquals(timeLong, value2.getTime());
    }

}

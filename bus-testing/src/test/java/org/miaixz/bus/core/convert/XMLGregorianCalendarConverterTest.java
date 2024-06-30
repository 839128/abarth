package org.miaixz.bus.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarConverterTest {

    @Test
    public void convertTest() {
        final XMLGregorianCalendar calendar = Convert.convert(XMLGregorianCalendar.class, DateKit.parse("2022-01-03 04:00:00"));
        Assertions.assertNotNull(calendar);
    }
}

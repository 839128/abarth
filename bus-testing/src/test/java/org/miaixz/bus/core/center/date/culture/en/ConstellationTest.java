package org.miaixz.bus.core.center.date.culture.en;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Calendar;

public class ConstellationTest {

    @Test
    public void getConstellationTest() {
        Assertions.assertEquals("摩羯座", Constellation.getName(Month.JANUARY, 19));
        Assertions.assertEquals("水瓶座", Constellation.getName(Month.JANUARY, 20));
        Assertions.assertEquals("巨蟹座", Constellation.getName(6, 17));
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY, 17);
        Assertions.assertEquals("巨蟹座", Constellation.getName(calendar.getTime()));
        Assertions.assertEquals("巨蟹座", Constellation.getName(calendar));
        Assertions.assertNull(Constellation.getName((Calendar) null));
    }


    @Test
    public void getConstellationOutOfRangeTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DateKit.getConstellation(Month.UNDECIMBER.getValue(), 10));
    }

}

package org.miaixz.bus.core.center.date.culture.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.en.Month;
import org.miaixz.bus.core.xyz.DateKit;

import java.util.Calendar;

public class ZodiacTest {

    @Test
    public void getZodiacTest() {
        Assertions.assertEquals("狗", Zodiac.getName(1994));
        Assertions.assertEquals("狗", Zodiac.getName(2018));
        Assertions.assertEquals("猪", Zodiac.getName(2019));
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY, 17);
        Assertions.assertEquals("虎", Zodiac.getName(calendar.getTime()));
        Assertions.assertEquals("虎", Zodiac.getName(calendar));
        Assertions.assertNull(Zodiac.getName(1899));
        Assertions.assertNull(Zodiac.getName((Calendar) null));
    }

    @Test
    public void getZodiacOutOfRangeTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DateKit.getZodiac(Month.UNDECIMBER.getValue()));
    }

}

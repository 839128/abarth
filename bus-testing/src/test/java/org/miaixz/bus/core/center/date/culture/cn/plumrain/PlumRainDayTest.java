package org.miaixz.bus.core.center.date.culture.cn.plumrain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 梅雨天测试
 */
public class PlumRainDayTest {

    @Test
    public void test0() {
        PlumRainDay d = SolarDay.fromYmd(2024, 6, 10).getPlumRainDay();
        Assertions.assertNull(d);
    }

    @Test
    public void test1() {
        PlumRainDay d = SolarDay.fromYmd(2024, 6, 11).getPlumRainDay();
        Assertions.assertEquals("入梅", d.getName());
        Assertions.assertEquals("入梅", d.getPlumRain().toString());
        Assertions.assertEquals("入梅第1天", d.toString());
    }

    @Test
    public void test2() {
        PlumRainDay d = SolarDay.fromYmd(2024, 7, 6).getPlumRainDay();
        Assertions.assertEquals("出梅", d.getName());
        Assertions.assertEquals("出梅", d.getPlumRain().toString());
        Assertions.assertEquals("出梅", d.toString());
    }

    @Test
    public void test3() {
        PlumRainDay d = SolarDay.fromYmd(2024, 7, 5).getPlumRainDay();
        Assertions.assertEquals("入梅", d.getName());
        Assertions.assertEquals("入梅", d.getPlumRain().toString());
        Assertions.assertEquals("入梅第25天", d.toString());
    }

}

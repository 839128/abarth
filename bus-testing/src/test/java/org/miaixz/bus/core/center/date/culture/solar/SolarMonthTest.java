package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 公历月测试
 */
public class SolarMonthTest {

    @Test
    public void test0() {
        SolarMonth m = SolarMonth.fromYm(2019, 5);
        Assertions.assertEquals("5月", m.getName());
        Assertions.assertEquals("2019年5月", m.toString());
    }

    @Test
    public void test1() {
        SolarMonth m = SolarMonth.fromYm(2023, 1);
        Assertions.assertEquals(5, m.getWeekCount(0));
        Assertions.assertEquals(6, m.getWeekCount(1));
        Assertions.assertEquals(6, m.getWeekCount(2));
        Assertions.assertEquals(5, m.getWeekCount(3));
        Assertions.assertEquals(5, m.getWeekCount(4));
        Assertions.assertEquals(5, m.getWeekCount(5));
        Assertions.assertEquals(5, m.getWeekCount(6));
    }

    @Test
    public void test2() {
        SolarMonth m = SolarMonth.fromYm(2023, 2);
        Assertions.assertEquals(5, m.getWeekCount(0));
        Assertions.assertEquals(5, m.getWeekCount(1));
        Assertions.assertEquals(5, m.getWeekCount(2));
        Assertions.assertEquals(4, m.getWeekCount(3));
        Assertions.assertEquals(5, m.getWeekCount(4));
        Assertions.assertEquals(5, m.getWeekCount(5));
        Assertions.assertEquals(5, m.getWeekCount(6));
    }

    @Test
    public void test3() {
        SolarMonth m = SolarMonth.fromYm(2023, 10).next(1);
        Assertions.assertEquals("11月", m.getName());
        Assertions.assertEquals("2023年11月", m.toString());
    }

    @Test
    public void test4() {
        SolarMonth m = SolarMonth.fromYm(2023, 10);
        Assertions.assertEquals("2023年12月", m.next(2).toString());
        Assertions.assertEquals("2024年1月", m.next(3).toString());
        Assertions.assertEquals("2023年5月", m.next(-5).toString());
        Assertions.assertEquals("2023年1月", m.next(-9).toString());
        Assertions.assertEquals("2022年12月", m.next(-10).toString());
        Assertions.assertEquals("2025年10月", m.next(24).toString());
        Assertions.assertEquals("2021年10月", m.next(-24).toString());
    }

}

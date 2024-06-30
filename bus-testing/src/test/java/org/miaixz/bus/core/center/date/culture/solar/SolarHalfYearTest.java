package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 公历半年测试
 */
public class SolarHalfYearTest {

    @Test
    public void test0() {
        Assertions.assertEquals("上半年", SolarHalfYear.fromIndex(2023, 0).getName());
        Assertions.assertEquals("2023年上半年", SolarHalfYear.fromIndex(2023, 0).toString());
    }

    @Test
    public void test1() {
        Assertions.assertEquals("下半年", SolarHalfYear.fromIndex(2023, 1).getName());
        Assertions.assertEquals("2023年下半年", SolarHalfYear.fromIndex(2023, 1).toString());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("下半年", SolarHalfYear.fromIndex(2023, 0).next(1).getName());
        Assertions.assertEquals("2023年下半年", SolarHalfYear.fromIndex(2023, 0).next(1).toString());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("上半年", SolarHalfYear.fromIndex(2023, 0).next(2).getName());
        Assertions.assertEquals("2024年上半年", SolarHalfYear.fromIndex(2023, 0).next(2).toString());
    }

    @Test
    public void test4() {
        Assertions.assertEquals("上半年", SolarHalfYear.fromIndex(2023, 0).next(-2).getName());
        Assertions.assertEquals("2022年上半年", SolarHalfYear.fromIndex(2023, 0).next(-2).toString());
    }

    @Test
    public void test5() {
        Assertions.assertEquals("2021年上半年", SolarHalfYear.fromIndex(2023, 0).next(-4).toString());
        Assertions.assertEquals("2021年下半年", SolarHalfYear.fromIndex(2023, 0).next(-3).toString());
    }
}

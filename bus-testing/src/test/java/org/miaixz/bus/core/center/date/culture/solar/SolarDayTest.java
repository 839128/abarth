package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 公历日测试
 */
public class SolarDayTest {

    @Test
    public void test0() {
        Assertions.assertEquals("1日", SolarDay.fromYmd(2023, 1, 1).getName());
        Assertions.assertEquals("2023年1月1日", SolarDay.fromYmd(2023, 1, 1).toString());
    }

    @Test
    public void test1() {
        Assertions.assertEquals("29日", SolarDay.fromYmd(2000, 2, 29).getName());
        Assertions.assertEquals("2000年2月29日", SolarDay.fromYmd(2000, 2, 29).toString());
    }

    @Test
    public void test2() {
        Assertions.assertEquals(0, SolarDay.fromYmd(2023, 1, 1).getIndexInYear());
        Assertions.assertEquals(364, SolarDay.fromYmd(2023, 12, 31).getIndexInYear());
        Assertions.assertEquals(365, SolarDay.fromYmd(2020, 12, 31).getIndexInYear());
    }

    @Test
    public void test3() {
        Assertions.assertEquals(0, SolarDay.fromYmd(2023, 1, 1).subtract(SolarDay.fromYmd(2023, 1, 1)));
        Assertions.assertEquals(1, SolarDay.fromYmd(2023, 1, 2).subtract(SolarDay.fromYmd(2023, 1, 1)));
        Assertions.assertEquals(-1, SolarDay.fromYmd(2023, 1, 1).subtract(SolarDay.fromYmd(2023, 1, 2)));
        Assertions.assertEquals(31, SolarDay.fromYmd(2023, 2, 1).subtract(SolarDay.fromYmd(2023, 1, 1)));
        Assertions.assertEquals(-31, SolarDay.fromYmd(2023, 1, 1).subtract(SolarDay.fromYmd(2023, 2, 1)));
        Assertions.assertEquals(365, SolarDay.fromYmd(2024, 1, 1).subtract(SolarDay.fromYmd(2023, 1, 1)));
        Assertions.assertEquals(-365, SolarDay.fromYmd(2023, 1, 1).subtract(SolarDay.fromYmd(2024, 1, 1)));
        Assertions.assertEquals(1, SolarDay.fromYmd(1582, 10, 15).subtract(SolarDay.fromYmd(1582, 10, 4)));
    }

    @Test
    public void test4() {
        Assertions.assertEquals("1582年10月4日", SolarDay.fromYmd(1582, 10, 15).next(-1).toString());
    }

    @Test
    public void test5() {
        Assertions.assertEquals("2000年3月1日", SolarDay.fromYmd(2000, 2, 28).next(2).toString());
    }

    @Test
    public void test6() {
        Assertions.assertEquals("农历庚子年闰四月初二", SolarDay.fromYmd(2020, 5, 24).getLunarDay().toString());
    }

    @Test
    public void test7() {
        Assertions.assertEquals(31, SolarDay.fromYmd(2020, 5, 24).subtract(SolarDay.fromYmd(2020, 4, 23)));
    }

    @Test
    public void test8() {
        Assertions.assertEquals("农历丙子年十一月十二", SolarDay.fromYmd(16, 11, 30).getLunarDay().toString());
    }

    @Test
    public void test9() {
        Assertions.assertEquals("霜降", SolarDay.fromYmd(2023, 10, 27).getTerm().toString());
    }

    @Test
    public void test10() {
        Assertions.assertEquals("豺乃祭兽第4天", SolarDay.fromYmd(2023, 10, 27).getPhenologyDay().toString());
    }

    @Test
    public void test11() {
        Assertions.assertEquals("初候", SolarDay.fromYmd(2023, 10, 27).getPhenologyDay().getClimate().getThree().toString());
    }

    @Test
    public void test22() {
        Assertions.assertEquals("甲辰", SolarDay.fromYmd(2024, 2, 10).getLunarDay().getMonth().getYear().getSixtyCycle().getName());
    }

    @Test
    public void test23() {
        Assertions.assertEquals("癸卯", SolarDay.fromYmd(2024, 2, 9).getLunarDay().getMonth().getYear().getSixtyCycle().getName());
    }

}

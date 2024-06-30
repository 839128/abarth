package org.miaixz.bus.core.center.date.culture.lunar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 时辰测试
 */
public class LunarHourTest {

    @Test
    public void test1() {
        LunarHour h = LunarHour.fromYmdHms(2020, -4, 5, 23, 0, 0);
        Assertions.assertEquals("子时", h.getName());
        Assertions.assertEquals("农历庚子年闰四月初五戊子时", h.toString());
    }

    @Test
    public void test2() {
        LunarHour h = LunarHour.fromYmdHms(2020, -4, 5, 0, 59, 0);
        Assertions.assertEquals("子时", h.getName());
        Assertions.assertEquals("农历庚子年闰四月初五丙子时", h.toString());
    }

    @Test
    public void test3() {
        LunarHour h = LunarHour.fromYmdHms(2020, -4, 5, 1, 0, 0);
        Assertions.assertEquals("丑时", h.getName());
        Assertions.assertEquals("农历庚子年闰四月初五丁丑时", h.toString());
    }

    @Test
    public void test4() {
        LunarHour h = LunarHour.fromYmdHms(2020, -4, 5, 21, 30, 0);
        Assertions.assertEquals("亥时", h.getName());
        Assertions.assertEquals("农历庚子年闰四月初五丁亥时", h.toString());
    }

    @Test
    public void test5() {
        LunarHour h = LunarHour.fromYmdHms(2020, -4, 2, 23, 30, 0);
        Assertions.assertEquals("子时", h.getName());
        Assertions.assertEquals("农历庚子年闰四月初二壬子时", h.toString());
    }

    @Test
    public void test6() {
        LunarHour h = LunarHour.fromYmdHms(2020, 4, 28, 23, 30, 0);
        Assertions.assertEquals("子时", h.getName());
        Assertions.assertEquals("农历庚子年四月廿八甲子时", h.toString());
    }

    @Test
    public void test7() {
        LunarHour h = LunarHour.fromYmdHms(2020, 4, 29, 0, 0, 0);
        Assertions.assertEquals("子时", h.getName());
        Assertions.assertEquals("农历庚子年四月廿九甲子时", h.toString());
    }

    @Test
    public void test8() {
        LunarHour h = LunarHour.fromYmdHms(2023, 11, 14, 23, 0, 0);
        Assertions.assertEquals("甲子", h.getSixtyCycle().getName());

        Assertions.assertEquals("己未", h.getDaySixtyCycle().getName());
        Assertions.assertEquals("戊午", h.getDay().getSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年十一月十四", h.getDay().toString());

        Assertions.assertEquals("甲子", h.getMonthSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年十一月", h.getDay().getMonth().toString());
        Assertions.assertEquals("乙丑", h.getDay().getMonth().getSixtyCycle().getName());

        Assertions.assertEquals("癸卯", h.getYearSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年", h.getDay().getMonth().getYear().toString());
        Assertions.assertEquals("癸卯", h.getDay().getMonth().getYear().getSixtyCycle().getName());
    }

    @Test
    public void test9() {
        LunarHour h = LunarHour.fromYmdHms(2023, 11, 14, 6, 0, 0);
        Assertions.assertEquals("乙卯", h.getSixtyCycle().getName());

        Assertions.assertEquals("戊午", h.getDaySixtyCycle().getName());
        Assertions.assertEquals("戊午", h.getDay().getSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年十一月十四", h.getDay().toString());

        Assertions.assertEquals("甲子", h.getMonthSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年十一月", h.getDay().getMonth().toString());
        Assertions.assertEquals("乙丑", h.getDay().getMonth().getSixtyCycle().getName());

        Assertions.assertEquals("癸卯", h.getYearSixtyCycle().getName());
        Assertions.assertEquals("农历癸卯年", h.getDay().getMonth().getYear().toString());
        Assertions.assertEquals("癸卯", h.getDay().getMonth().getYear().getSixtyCycle().getName());
    }

}

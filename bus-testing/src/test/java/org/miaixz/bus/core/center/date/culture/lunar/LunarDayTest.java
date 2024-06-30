package org.miaixz.bus.core.center.date.culture.lunar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.cn.star.twentyeight.TwentyEightStar;

/**
 * 农历日测试
 */
public class LunarDayTest {
    @Test
    public void test1() {
        Assertions.assertEquals("1年1月1日", LunarDay.fromYmd(0, 11, 18).getSolarDay().toString());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("9999年12月31日", LunarDay.fromYmd(9999, 12, 2).getSolarDay().toString());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("1905年2月4日", LunarDay.fromYmd(1905, 1, 1).getSolarDay().toString());
    }

    @Test
    public void test4() {
        Assertions.assertEquals("2039年1月23日", LunarDay.fromYmd(2038, 12, 29).getSolarDay().toString());
    }

    @Test
    public void test5() {
        Assertions.assertEquals("1500年1月31日", LunarDay.fromYmd(1500, 1, 1).getSolarDay().toString());
    }

    @Test
    public void test6() {
        Assertions.assertEquals("1501年1月18日", LunarDay.fromYmd(1500, 12, 29).getSolarDay().toString());
    }

    @Test
    public void test7() {
        Assertions.assertEquals("1582年10月4日", LunarDay.fromYmd(1582, 9, 18).getSolarDay().toString());
    }

    @Test
    public void test8() {
        Assertions.assertEquals("1582年10月15日", LunarDay.fromYmd(1582, 9, 19).getSolarDay().toString());
    }

    @Test
    public void test9() {
        Assertions.assertEquals("2020年1月6日", LunarDay.fromYmd(2019, 12, 12).getSolarDay().toString());
    }

    @Test
    public void test10() {
        Assertions.assertEquals("2033年12月22日", LunarDay.fromYmd(2033, -11, 1).getSolarDay().toString());
    }

    @Test
    public void test11() {
        Assertions.assertEquals("2021年7月16日", LunarDay.fromYmd(2021, 6, 7).getSolarDay().toString());
    }

    @Test
    public void test12() {
        Assertions.assertEquals("2034年2月19日", LunarDay.fromYmd(2034, 1, 1).getSolarDay().toString());
    }

    @Test
    public void test13() {
        Assertions.assertEquals("2034年1月20日", LunarDay.fromYmd(2033, 12, 1).getSolarDay().toString());
    }

    @Test
    public void test14() {
        Assertions.assertEquals("7013年12月24日", LunarDay.fromYmd(7013, -11, 4).getSolarDay().toString());
    }

    @Test
    public void test15() {
        Assertions.assertEquals("己亥", LunarDay.fromYmd(2023, 8, 24).getSixtyCycle().toString());
    }

    @Test
    public void test16() {
        Assertions.assertEquals("癸酉", LunarDay.fromYmd(1653, 1, 6).getSixtyCycle().toString());
    }

    @Test
    public void test17() {
        Assertions.assertEquals("农历庚寅年二月初二", LunarDay.fromYmd(2010, 1, 1).next(31).toString());
    }

    @Test
    public void test18() {
        Assertions.assertEquals("农历壬辰年闰四月初一", LunarDay.fromYmd(2012, 3, 1).next(60).toString());
    }

    @Test
    public void test19() {
        Assertions.assertEquals("农历壬辰年闰四月廿九", LunarDay.fromYmd(2012, 3, 1).next(88).toString());
    }

    @Test
    public void test20() {
        Assertions.assertEquals("农历壬辰年五月初一", LunarDay.fromYmd(2012, 3, 1).next(89).toString());
    }

    @Test
    public void test21() {
        Assertions.assertEquals("2020年4月23日", LunarDay.fromYmd(2020, 4, 1).getSolarDay().toString());
    }

    @Test
    public void test22() {
        Assertions.assertEquals("甲辰", LunarDay.fromYmd(2024, 1, 1).getMonth().getYear().getSixtyCycle().getName());
    }

    @Test
    public void test23() {
        Assertions.assertEquals("癸卯", LunarDay.fromYmd(2023, 12, 30).getMonth().getYear().getSixtyCycle().getName());
    }

    /**
     * 二十八宿
     */
    @Test
    public void test24() {
        LunarDay d = LunarDay.fromYmd(2020, 4, 13);
        TwentyEightStar star = d.getTwentyEightStar();
        Assertions.assertEquals("南", star.getZone().getName());
        Assertions.assertEquals("朱雀", star.getZone().getBeast().getName());
        Assertions.assertEquals("翼", star.getName());
        Assertions.assertEquals("火", star.getSevenStar().getName());
        Assertions.assertEquals("蛇", star.getAnimal().getName());
        Assertions.assertEquals("凶", star.getLuck().getName());

        Assertions.assertEquals("阳天", star.getLand().getName());
        Assertions.assertEquals("东南", star.getLand().getDirection().getName());
    }

    @Test
    public void test25() {
        LunarDay d = LunarDay.fromYmd(2023, 9, 28);
        TwentyEightStar star = d.getTwentyEightStar();
        Assertions.assertEquals("南", star.getZone().getName());
        Assertions.assertEquals("朱雀", star.getZone().getBeast().getName());
        Assertions.assertEquals("柳", star.getName());
        Assertions.assertEquals("土", star.getSevenStar().getName());
        Assertions.assertEquals("獐", star.getAnimal().getName());
        Assertions.assertEquals("凶", star.getLuck().getName());

        Assertions.assertEquals("炎天", star.getLand().getName());
        Assertions.assertEquals("南", star.getLand().getDirection().getName());
    }

    @Test
    public void test26() {
        LunarDay lunar = LunarDay.fromYmd(2005, 11, 23);
        Assertions.assertEquals("戊子", lunar.getMonth().getSixtyCycle().getName());
        Assertions.assertEquals("戊子", lunar.getMonthSixtyCycle().getName());
    }

}

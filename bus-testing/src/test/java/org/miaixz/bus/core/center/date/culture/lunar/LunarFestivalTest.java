package org.miaixz.bus.core.center.date.culture.lunar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 农历传统节日测试
 */
public class LunarFestivalTest {

    @Test
    public void test0() {
        for (int i = 0, j = LunarFestival.NAMES.length; i < j; i++) {
            LunarFestival f = LunarFestival.fromIndex(2023, i);
            Assertions.assertNotNull(f);
            Assertions.assertEquals(LunarFestival.NAMES[i], f.getName());
        }
    }

    @Test
    public void test1() {
        LunarFestival f = LunarFestival.fromIndex(2023, 0);
        Assertions.assertNotNull(f);
        for (int i = 0, j = LunarFestival.NAMES.length; i < j; i++) {
            Assertions.assertEquals(LunarFestival.NAMES[i], f.next(i).getName());
        }
    }

    @Test
    public void test2() {
        LunarFestival f = LunarFestival.fromIndex(2023, 0);
        Assertions.assertNotNull(f);
        Assertions.assertEquals("农历甲辰年正月初一 春节", f.next(13).toString());
        Assertions.assertEquals("农历壬寅年十一月廿九 冬至节", f.next(-3).toString());
    }

    @Test
    public void test3() {
        LunarFestival f = LunarFestival.fromIndex(2023, 0);
        Assertions.assertNotNull(f);
        Assertions.assertEquals("农历壬寅年三月初五 清明节", f.next(-9).toString());
    }

    @Test
    public void test4() {
        LunarFestival f = LunarDay.fromYmd(2010, 1, 15).getFestival();
        Assertions.assertNotNull(f);
        Assertions.assertEquals("农历庚寅年正月十五 元宵节", f.toString());
    }

    @Test
    public void test5() {
        LunarFestival f = LunarDay.fromYmd(2021, 12, 29).getFestival();
        Assertions.assertNotNull(f);
        Assertions.assertEquals("农历辛丑年十二月廿九 除夕", f.toString());
    }

}

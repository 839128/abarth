package org.miaixz.bus.core.center.date.culture.cn.star.six;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 六曜测试
 */
public class SixStarTest {

    @Test
    public void test0() {
        Assertions.assertEquals("佛灭", SolarDay.fromYmd(2020, 4, 23).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals("友引", SolarDay.fromYmd(2021, 1, 15).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("先胜", SolarDay.fromYmd(2017, 1, 5).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("友引", SolarDay.fromYmd(2020, 4, 10).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test4() {
        Assertions.assertEquals("大安", SolarDay.fromYmd(2020, 6, 11).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test5() {
        Assertions.assertEquals("先胜", SolarDay.fromYmd(2020, 6, 1).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test6() {
        Assertions.assertEquals("先负", SolarDay.fromYmd(2020, 12, 8).getLunarDay().getSixStar().getName());
    }

    @Test
    public void test8() {
        Assertions.assertEquals("赤口", SolarDay.fromYmd(2020, 12, 11).getLunarDay().getSixStar().getName());
    }

}

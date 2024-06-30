package org.miaixz.bus.core.center.date.culture.cn.star.twelve;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 黄道黑道十二神测试
 */
public class EclipticTest {

    @Test
    public void test0() {
        TwelveStar star = SolarDay.fromYmd(2023, 10, 30).getLunarDay().getTwelveStar();
        Assertions.assertEquals("天德", star.getName());
        Assertions.assertEquals("黄道", star.getEcliptic().getName());
        Assertions.assertEquals("吉", star.getEcliptic().getLuck().getName());
    }

    @Test
    public void test1() {
        TwelveStar star = SolarDay.fromYmd(2023, 10, 19).getLunarDay().getTwelveStar();
        Assertions.assertEquals("白虎", star.getName());
        Assertions.assertEquals("黑道", star.getEcliptic().getName());
        Assertions.assertEquals("凶", star.getEcliptic().getLuck().getName());
    }

    @Test
    public void test2() {
        TwelveStar star = SolarDay.fromYmd(2023, 10, 7).getLunarDay().getTwelveStar();
        Assertions.assertEquals("天牢", star.getName());
        Assertions.assertEquals("黑道", star.getEcliptic().getName());
        Assertions.assertEquals("凶", star.getEcliptic().getLuck().getName());
    }

    @Test
    public void test3() {
        TwelveStar star = SolarDay.fromYmd(2023, 10, 8).getLunarDay().getTwelveStar();
        Assertions.assertEquals("玉堂", star.getName());
        Assertions.assertEquals("黄道", star.getEcliptic().getName());
        Assertions.assertEquals("吉", star.getEcliptic().getLuck().getName());
    }

}

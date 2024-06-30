package org.miaixz.bus.core.center.date.culture.cn.nine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 数九测试
 */
public class NineDayTest {

    @Test
    public void test0() {
        NineDay d = SolarDay.fromYmd(2020, 12, 21).getNineDay();
        Assertions.assertEquals("一九", d.getName());
        Assertions.assertEquals("一九", d.getNine().toString());
        Assertions.assertEquals("一九第1天", d.toString());
    }

    @Test
    public void test1() {
        NineDay d = SolarDay.fromYmd(2020, 12, 22).getNineDay();
        Assertions.assertEquals("一九", d.getName());
        Assertions.assertEquals("一九", d.getNine().toString());
        Assertions.assertEquals("一九第2天", d.toString());
    }

    @Test
    public void test2() {
        NineDay d = SolarDay.fromYmd(2020, 1, 7).getNineDay();
        Assertions.assertEquals("二九", d.getName());
        Assertions.assertEquals("二九", d.getNine().toString());
        Assertions.assertEquals("二九第8天", d.toString());
    }

    @Test
    public void test3() {
        NineDay d = SolarDay.fromYmd(2021, 1, 6).getNineDay();
        Assertions.assertEquals("二九", d.getName());
        Assertions.assertEquals("二九", d.getNine().toString());
        Assertions.assertEquals("二九第8天", d.toString());
    }

    @Test
    public void test4() {
        NineDay d = SolarDay.fromYmd(2021, 1, 8).getNineDay();
        Assertions.assertEquals("三九", d.getName());
        Assertions.assertEquals("三九", d.getNine().toString());
        Assertions.assertEquals("三九第1天", d.toString());
    }

    @Test
    public void test5() {
        NineDay d = SolarDay.fromYmd(2021, 3, 5).getNineDay();
        Assertions.assertEquals("九九", d.getName());
        Assertions.assertEquals("九九", d.getNine().toString());
        Assertions.assertEquals("九九第3天", d.toString());
    }

    @Test
    public void test6() {
        NineDay d = SolarDay.fromYmd(2021, 7, 5).getNineDay();
        Assertions.assertNull(d);
    }

}

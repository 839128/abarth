package org.miaixz.bus.core.center.date.culture.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 建除十二值神测试
 */
public class DutyTest {

    @Test
    public void test0() {
        Assertions.assertEquals("闭", SolarDay.fromYmd(2023, 10, 30).getLunarDay().getDuty().getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals("建", SolarDay.fromYmd(2023, 10, 19).getLunarDay().getDuty().getName());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("除", SolarDay.fromYmd(2023, 10, 7).getLunarDay().getDuty().getName());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("除", SolarDay.fromYmd(2023, 10, 8).getLunarDay().getDuty().getName());
    }

}

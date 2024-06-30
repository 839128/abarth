package org.miaixz.bus.core.center.date.culture.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;


/**
 * 方位测试
 */
public class DirectionTest {

    /**
     * 福神方位
     */
    @Test
    public void test1() {
        Assertions.assertEquals("东南", SolarDay.fromYmd(2021, 11, 13).getLunarDay().getSixtyCycle().getHeavenStem().getMascotDirection().getName());
    }

    /**
     * 福神方位
     */
    @Test
    public void test2() {
        Assertions.assertEquals("东南", SolarDay.fromYmd(2024, 1, 1).getLunarDay().getSixtyCycle().getHeavenStem().getMascotDirection().getName());
    }

    /**
     * 太岁方位
     */
    @Test
    public void test3() {
        Assertions.assertEquals("东", SolarDay.fromYmd(2023, 11, 6).getLunarDay().getJupiterDirection().getName());
    }

}

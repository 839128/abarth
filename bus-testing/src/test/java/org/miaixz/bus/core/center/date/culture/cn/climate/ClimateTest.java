package org.miaixz.bus.core.center.date.culture.cn.climate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 物候测试
 */
public class ClimateTest {

    @Test
    public void test0() {
        SolarDay solarDay = SolarDay.fromYmd(2020, 4, 23);
        // 七十二候
        ClimateDay climateDay = solarDay.getPhenologyDay();
        // 三候
        ThreeClimate three = climateDay.getClimate().getThree();
        Assertions.assertEquals("谷雨", solarDay.getTerm().getName());
        Assertions.assertEquals("初候", three.getName());
        Assertions.assertEquals("萍始生", climateDay.getName());
        // 该候的第5天
        Assertions.assertEquals(4, climateDay.getDayIndex());
    }

    @Test
    public void test1() {
        SolarDay solarDay = SolarDay.fromYmd(2021, 12, 26);
        // 七十二候
        ClimateDay climateDay = solarDay.getPhenologyDay();
        // 三候
        ThreeClimate three = climateDay.getClimate().getThree();
        Assertions.assertEquals("冬至", solarDay.getTerm().getName());
        Assertions.assertEquals("二候", three.getName());
        Assertions.assertEquals("麋角解", climateDay.getName());
        // 该候的第1天
        Assertions.assertEquals(0, climateDay.getDayIndex());
    }

}

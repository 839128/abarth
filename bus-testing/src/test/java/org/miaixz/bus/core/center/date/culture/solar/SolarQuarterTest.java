package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 公历季度测试
 */
public class SolarQuarterTest {

    @Test
    public void test0() {
        SolarQuarter quarter = SolarQuarter.fromIndex(2023, 0);
        Assertions.assertEquals("2023年一季度", quarter.toString());
        Assertions.assertEquals("2021年四季度", quarter.next(-5).toString());
    }

}

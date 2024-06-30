package org.miaixz.bus.core.center.date.culture.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 儒略日测试
 */
public class JulianDayTest {

    @Test
    public void test0() {
        Assertions.assertEquals("2023年1月1日", SolarDay.fromYmd(2023, 1, 1).getJulianDay().getSolarDay().toString());
    }

}

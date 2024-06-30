package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 公历时刻测试
 */
public class SolarTimeTest {

    @Test
    public void test0() {
        SolarTime time = SolarTime.fromYmdHms(2023, 1, 1, 13, 5, 20);
        Assertions.assertEquals("13:05:20", time.getName());
        Assertions.assertEquals("13:04:59", time.next(-21).getName());
    }

    @Test
    public void test1() {
        SolarTime time = SolarTime.fromYmdHms(2023, 1, 1, 13, 5, 20);
        Assertions.assertEquals("13:05:20", time.getName());
        Assertions.assertEquals("14:06:01", time.next(3641).getName());
    }

}

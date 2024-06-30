package org.miaixz.bus.core.center.date.culture.cn.fetus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 胎神测试
 */
public class FetusTest {

    /**
     * 逐日胎神
     */
    @Test
    public void test1() {
        Assertions.assertEquals("碓磨厕 外东南", SolarDay.fromYmd(2021, 11, 13).getLunarDay().getFetusDay().getName());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("占门碓 外东南", SolarDay.fromYmd(2021, 11, 12).getLunarDay().getFetusDay().getName());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("厨灶厕 外西南", SolarDay.fromYmd(2011, 11, 12).getLunarDay().getFetusDay().getName());
    }

}

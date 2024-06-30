package org.miaixz.bus.core.center.date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.Holiday;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 法定节假日测试
 */
public class HolidayTest {

    @Test
    public void test0() {
        Holiday d = Holiday.fromYmd(2011, 5, 1);
        Assertions.assertNotNull(d);
        Assertions.assertEquals("2011年5月1日 劳动节(休)", d.toString());

        Assertions.assertEquals("2011年5月2日 劳动节(休)", d.next(1).toString());
        Assertions.assertEquals("2011年6月4日 端午节(休)", d.next(2).toString());
        Assertions.assertEquals("2011年4月30日 劳动节(休)", d.next(-1).toString());
        Assertions.assertEquals("2011年4月5日 清明节(休)", d.next(-2).toString());
    }

    @Test
    public void test1() {
        Holiday d = Holiday.fromYmd(2010, 1, 1);
        Assertions.assertNotNull(d);
        while (d.getDay().getMonth().getYear().getYear() < 2012) {
            System.out.println(d);
            d = d.next(1);
        }
    }

    @Test
    public void test2() {
        Holiday d = Holiday.fromYmd(2010, 1, 1);
        Assertions.assertNotNull(d);
        while (d.getDay().getMonth().getYear().getYear() > 2007) {
            System.out.println(d);
            d = d.next(-1);
        }
    }

    @Test
    public void test3() {
        Holiday d = Holiday.fromYmd(2001, 12, 29);
        Assertions.assertNotNull(d);
        Assertions.assertEquals("2001年12月29日 元旦节(班)", d.toString());
        Assertions.assertNull(d.next(-1));
    }

    @Test
    public void test4() {
        Holiday d = Holiday.fromYmd(2022, 10, 5);
        Assertions.assertNotNull(d);
        Assertions.assertEquals("2022年10月5日 国庆节(休)", d.toString());
        Assertions.assertEquals("2022年10月4日 国庆节(休)", d.next(-1).toString());
        Assertions.assertEquals("2022年10月6日 国庆节(休)", d.next(1).toString());
    }

    @Test
    public void test5() {
        Holiday d = SolarDay.fromYmd(2010, 10, 1).getHoliday();
        Assertions.assertNotNull(d);
        Assertions.assertEquals("2010年10月1日 国庆节(休)", d.toString());
    }

}

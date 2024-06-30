package org.miaixz.bus.core.center.date.culture.solar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 节气测试
 */
public class SolarTermsTest {

    @Test
    public void test0() {
        // 冬至在去年，2022-12-22 05:48:11
        SolarTerms dongZhi = SolarTerms.fromName(2023, "冬至");
        Assertions.assertEquals("冬至", dongZhi.getName());
        Assertions.assertEquals(0, dongZhi.getIndex());
        // 儒略日
        Assertions.assertEquals(2459935.7417939813, dongZhi.getJulianDay().getDay(), 0.00001);
        // 公历日
        Assertions.assertEquals("2022年12月22日", dongZhi.getJulianDay().getSolarDay().toString());

        // 冬至顺推23次，就是大雪 2023-12-07 17:32:55
        SolarTerms daXue = dongZhi.next(23);
        Assertions.assertEquals("大雪", daXue.getName());
        Assertions.assertEquals(23, daXue.getIndex());
        Assertions.assertEquals(2460286.2311921297, daXue.getJulianDay().getDay(), 0.00001);
        Assertions.assertEquals("2023年12月7日", daXue.getJulianDay().getSolarDay().toString());

        // 冬至逆推2次，就是上一年的小雪 2022-11-22 16:20:28
        SolarTerms xiaoXue = dongZhi.next(-2);
        Assertions.assertEquals("小雪", xiaoXue.getName());
        Assertions.assertEquals(22, xiaoXue.getIndex());
        Assertions.assertEquals(2459906.1808796297, xiaoXue.getJulianDay().getDay(), 0.00001);
        Assertions.assertEquals("2022年11月22日", xiaoXue.getJulianDay().getSolarDay().toString());

        // 冬至顺推24次，就是下一个冬至 2023-12-22 11:27:20
        SolarTerms dongZhi2 = dongZhi.next(24);
        Assertions.assertEquals("冬至", dongZhi2.getName());
        Assertions.assertEquals(0, dongZhi2.getIndex());
        Assertions.assertEquals(2460300.977314815, dongZhi2.getJulianDay().getDay(), 0.00001);
        Assertions.assertEquals("2023年12月22日", dongZhi2.getJulianDay().getSolarDay().toString());
    }

    @Test
    public void test1() {
        // 公历2023年的雨水，2023-02-19 06:34:16
        SolarTerms jq = SolarTerms.fromName(2023, "雨水");
        Assertions.assertEquals("雨水", jq.getName());
        Assertions.assertEquals(4, jq.getIndex());
        Assertions.assertEquals(2459994.773796296, jq.getJulianDay().getDay(), 0.00001);
    }

    @Test
    public void test2() {
        // 公历2023年的大雪，2023-12-07 17:32:55
        SolarTerms jq = SolarTerms.fromName(2023, "大雪");
        Assertions.assertEquals("大雪", jq.getName());
        // 索引
        Assertions.assertEquals(23, jq.getIndex());
        // 公历
        Assertions.assertEquals("2023年12月7日", jq.getJulianDay().getSolarDay().toString());
        // 农历
        Assertions.assertEquals("农历癸卯年十月廿五", jq.getJulianDay().getSolarDay().getLunarDay().toString());
        // 儒略日
        Assertions.assertEquals(2460286.2311921297, jq.getJulianDay().getDay(), 0.00001);
        // 推移
        Assertions.assertEquals("雨水", jq.next(5).getName());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("寒露", SolarDay.fromYmd(2023, 10, 10).getTerm().getName());
    }

    @Test
    public void test4() {
        // 大雪当天
        Assertions.assertEquals("大雪第1天", SolarDay.fromYmd(2023, 12, 7).getTermDay().toString());
        // 天数索引
        Assertions.assertEquals(0, SolarDay.fromYmd(2023, 12, 7).getTermDay().getDayIndex());

        Assertions.assertEquals("大雪第2天", SolarDay.fromYmd(2023, 12, 8).getTermDay().toString());
        Assertions.assertEquals("大雪第15天", SolarDay.fromYmd(2023, 12, 21).getTermDay().toString());

        Assertions.assertEquals("冬至第1天", SolarDay.fromYmd(2023, 12, 22).getTermDay().toString());
    }

}

package org.aoju.bus.core.date;

import org.junit.Assert;
import org.junit.Test;

/**
 * 阳历测试
 */
public class SolarTest {

  @Test
  public void test() {
    Solar date = new Solar(2019, 5, 1);
    Assert.assertEquals("2019-05-01", date.toString());
    Assert.assertEquals("2019-05-01 00:00:00 星期三 (劳动节) 金牛座", date.toFullString());
    Assert.assertEquals("二〇一九年三月廿七", date.getLunar().toString());
    Assert.assertEquals("二〇一九年三月廿七 己亥(猪)年 戊辰(龙)月 戊戌(狗)日 子(鼠)时 纳音[平地木 大林木 平地木 桑柘木] 星期三 (七殿泰山王诞) 西方白虎 星宿[参水猿](吉) 彭祖百忌[戊不受田田主不祥 戌不吃犬作怪上床] 喜神方位[巽](东南) 阳贵神方位[艮](东北) 阴贵神方位[坤](西南) 福神方位[坎](正北) 财神方位[坎](正北) 冲[(壬辰)龙] 煞[北]", date.getLunar().toFullString());
  }

  @Test
  public void testNext() {
    Solar date = new Solar(2020, 1, 23);
    Assert.assertEquals("2020-01-24", date.next(1).toString());
    // 仅工作日，跨越春节假期
    Assert.assertEquals("2020-02-03", date.next(1, true).toString());

    date = new Solar(2020, 2, 3);
    Assert.assertEquals("2020-01-31", date.next(-3).toString());
    // 仅工作日，跨越春节假期
    Assert.assertEquals("2020-01-21", date.next(-3, true).toString());

    date = new Solar(2020, 2, 9);
    Assert.assertEquals("2020-02-15", date.next(6).toString());
    // 仅工作日，跨越周末
    Assert.assertEquals("2020-02-17", date.next(6, true).toString());

    date = new Solar(2020, 1, 17);
    Assert.assertEquals("2020-01-18", date.next(1).toString());
    // 仅工作日，周日调休按上班算
    Assert.assertEquals("2020-01-19", date.next(1, true).toString());
  }

  @Test
  public void testa() {
    Solar.Semester halfYear = new Solar.Semester(2019, 5);
    Assert.assertEquals("2019.1", halfYear.toString());
    Assert.assertEquals("2019年上半年", halfYear.toFullString());

    Assert.assertEquals("2019.2", halfYear.next(1).toString());
    Assert.assertEquals("2019年下半年", halfYear.next(1).toFullString());
  }

  @Test
  public void test0() {
    Solar.Year year = new Solar.Year(2019);
    Assert.assertEquals("2019", year.toString());
    Assert.assertEquals("2019年", year.toFullString());

    Assert.assertEquals("2020", year.next(1).toString());
    Assert.assertEquals("2020年", year.next(1).toFullString());
  }


  @Test
  public void testFromMonday() {
    //一周的开始从星期一开始计
    int start = 1;
    Solar.Week week = new Solar.Week(2019, 5, 1, start);
    Assert.assertEquals("2019.5.1", week.toString());
    Assert.assertEquals("2019年5月第1周", week.toFullString());
    //当月共几周
    Assert.assertEquals(5, Solar.getWeeksOfMonth(week.getYear(), week.getMonth(), start));
    //当周第一天
    Assert.assertEquals("2019-04-29", week.getFirstDay().toString());
    //当周第一天（本月）
    Assert.assertEquals("2019-05-01", week.getFirstDayInMonth().toString());
  }

  @Test
  public void testFromSunday() {
    //一周的开始从星期日开始计
    int start = 0;
    Solar.Week week = new Solar.Week(2019, 5, 1, start);
    Assert.assertEquals("2019.5.1", week.toString());
    Assert.assertEquals("2019年5月第1周", week.toFullString());
    //当月共几周
    Assert.assertEquals(5, Solar.getWeeksOfMonth(week.getYear(), week.getMonth(), start));
    //当周第一天
    Assert.assertEquals("2019-04-28", week.getFirstDay().toString());
    //当周第一天（本月）
    Assert.assertEquals("2019-05-01", week.getFirstDayInMonth().toString());
  }

  @Test
  public void test1() {
    Solar.Season season = new Solar.Season(2019, 5);
    Assert.assertEquals("2019.2", season.toString());
    Assert.assertEquals("2019年2季度", season.toFullString());

    Assert.assertEquals("2019.3", season.next(1).toString());
    Assert.assertEquals("2019年3季度", season.next(1).toFullString());
  }

  @Test
  public void test2() {
    Solar.Month month = new Solar.Month(2019, 5);
    Assert.assertEquals("2019-5", month.toString());
    Assert.assertEquals("2019年5月", month.toFullString());

    Assert.assertEquals("2019-6", month.next(1).toString());
    Assert.assertEquals("2019年6月", month.next(1).toFullString());
  }

}

package org.aoju.bus.core.date;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 农历测试
 */
public class LunarTest {

  @Test
  public void test() {
    Lunar date = new Lunar(2019, 3, 27, 0, 0, 0);
    Assert.assertEquals("二〇一九年三月廿七", date.toString());
    Assert.assertEquals("二〇一九年三月廿七 己亥(猪)年 戊辰(龙)月 戊戌(狗)日 子(鼠)时 纳音[平地木 大林木 平地木 桑柘木] 星期三 (七殿泰山王诞) 西方白虎 星宿[参水猿](吉) 彭祖百忌[戊不受田田主不祥 戌不吃犬作怪上床] 喜神方位[巽](东南) 阳贵神方位[艮](东北) 阴贵神方位[坤](西南) 福神方位[坎](正北) 财神方位[坎](正北) 冲[(壬辰)龙] 煞[北]", date.toFullString());
    Assert.assertEquals("2019-05-01", date.getSolar().toString());
    Assert.assertEquals("2019-05-01 00:00:00 星期三 (劳动节) 金牛座", date.getSolar().toFullString());
  }

  @Test
  public void testNext() {
    Solar solar = new Solar(2020, 1, 10, 12, 0, 0);
    Lunar lunar = solar.getLunar();
    for (int i = -500; i < 500; i++) {
      Assert.assertEquals("推移天数：" + i, solar.next(i).getLunar().toFullString(), lunar.next(i).toFullString());
    }
  }

  /**
   * 节日测试
   */
  @Test
  public void testSolar() {
    Solar solar = Solar.fromYmd(2020, 11, 26);
    Assert.assertEquals("[感恩节]", solar.getFestivals() + "");

    solar = Solar.fromYmd(2020, 6, 21);
    Assert.assertEquals("[父亲节]", solar.getFestivals() + "");

    solar = Solar.fromYmd(2021, 5, 9);
    Assert.assertEquals("[母亲节]", solar.getFestivals() + "");

    solar = Solar.fromYmd(1986, 11, 27);
    Assert.assertEquals("[感恩节]", solar.getFestivals() + "");

    solar = Solar.fromYmd(1985, 6, 16);
    Assert.assertEquals("[父亲节]", solar.getFestivals() + "");

    solar = Solar.fromYmd(1984, 5, 13);
    Assert.assertEquals("[母亲节]", solar.getFestivals() + "");
  }

  /**
   * 儒略日测试
   */
  @Test
  public void testSolar2JD() {
    Solar solar = Solar.fromYmd(2020, 7, 15);
    Assert.assertEquals(2459045.5, solar.getJulianDay(), 0);
  }

  @Test
  public void testJD2Solar() {
    Solar solar = Solar.fromJulianDay(2459045.5);
    Assert.assertEquals("2020-07-15 00:00:00", solar.toYmdHms());
  }


  /**
   * 节气测试
   */
  @Test
  public void test2022() {

    Map<String, String> jieQi = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;

      {
        put("冬至", "2021-12-21 23:59:09");
        put("小寒", "2022-01-05 17:13:54");
        put("大寒", "2022-01-20 10:38:56");
        put("立春", "2022-02-04 04:50:36");
        put("雨水", "2022-02-19 00:42:50");
        put("惊蛰", "2022-03-05 22:43:34");
        put("春分", "2022-03-20 23:33:15");
        put("清明", "2022-04-05 03:20:03");
        put("谷雨", "2022-04-20 10:24:07");
        put("立夏", "2022-05-05 20:25:46");
        put("小满", "2022-05-21 09:22:25");
        put("芒种", "2022-06-06 00:25:37");
        put("夏至", "2022-06-21 17:13:40");
        put("小暑", "2022-07-07 10:37:49");
        put("大暑", "2022-07-23 04:06:49");
        put("立秋", "2022-08-07 20:28:57");
        put("处暑", "2022-08-23 11:15:59");
        put("白露", "2022-09-07 23:32:07");
        put("秋分", "2022-09-23 09:03:31");
        put("寒露", "2022-10-08 15:22:16");
        put("霜降", "2022-10-23 18:35:31");
        put("立冬", "2022-11-07 18:45:18");
        put("小雪", "2022-11-22 16:20:18");
        put("大雪", "2022-12-07 11:46:04");
      }
    };

    Solar solar = Solar.fromYmd(2022, 7, 15);
    Map<String, Solar> result = solar.getLunar().getJieQiTable();
    for (Map.Entry<String, String> entry : jieQi.entrySet()) {
      String name = entry.getKey();
      Assert.assertEquals(name, entry.getValue(), result.get(name).toYmdHms());
    }
  }

  @Test
  public void test1986() {

    Map<String, String> jieQi = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;

      {
        put("冬至", "1985-12-22 06:07:40");
        put("小寒", "1986-01-05 23:28:02");
        put("大寒", "1986-01-20 16:46:12");
        put("立春", "1986-02-04 11:07:42");
        put("雨水", "1986-02-19 06:57:31");
        put("惊蛰", "1986-03-06 05:12:08");
        put("春分", "1986-03-21 06:02:41");
        put("清明", "1986-04-05 10:06:07");
        put("谷雨", "1986-04-20 17:12:08");
        put("立夏", "1986-05-06 03:30:36");
        put("小满", "1986-05-21 16:27:55");
        put("芒种", "1986-06-06 07:44:23");
        put("夏至", "1986-06-22 00:29:57");
        put("小暑", "1986-07-07 18:00:45");
        put("大暑", "1986-07-23 11:24:23");
        put("立秋", "1986-08-08 03:45:36");
        put("处暑", "1986-08-23 18:25:47");
        put("白露", "1986-09-08 06:34:37");
        put("秋分", "1986-09-23 15:58:52");
        put("寒露", "1986-10-08 22:06:45");
        put("霜降", "1986-10-24 01:14:11");
        put("立冬", "1986-11-08 01:12:49");
        put("小雪", "1986-11-22 22:44:20");
        put("大雪", "1986-12-07 18:00:56");
      }
    };

    Solar solar = Solar.fromYmd(1986, 7, 15);
    Map<String, Solar> result = solar.getLunar().getJieQiTable();
    for (Map.Entry<String, String> entry : jieQi.entrySet()) {
      String name = entry.getKey();
      Assert.assertEquals(name, entry.getValue(), result.get(name).toYmdHms());
    }
  }

  @Test
  public void testLunar() {
    Solar solar = Solar.fromYmd(1986, 1, 5);
    Lunar lunar = solar.getLunar();
    Assert.assertEquals("小寒", lunar.getJie());
    Assert.assertEquals("小寒", lunar.getJieQi());
    Assert.assertEquals("小寒", lunar.getCurrentJieQi().getName());
    Assert.assertEquals("小寒", lunar.getCurrentJie().getName());
    Assert.assertNull(lunar.getCurrentQi());
    Assert.assertEquals("", lunar.getQi());
    Assert.assertEquals("大雪", lunar.getPrevJie().getName());
    Assert.assertEquals("冬至", lunar.getPrevQi().getName());
    Assert.assertEquals("冬至", lunar.getPrevJieQi().getName());

    solar = Solar.fromYmdHms(1986, 1, 20, 17, 0, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("大寒", lunar.getQi());
    Assert.assertEquals("大寒", lunar.getJieQi());
    Assert.assertEquals("大寒", lunar.getCurrentJieQi().getName());
    Assert.assertEquals("大寒", lunar.getCurrentQi().getName());
    Assert.assertNull(lunar.getCurrentJie());
    Assert.assertEquals("", lunar.getJie());
    Assert.assertEquals("立春", lunar.getNextJie().getName());
    Assert.assertEquals("雨水", lunar.getNextQi().getName());
    Assert.assertEquals("立春", lunar.getNextJieQi().getName());
    solar = Solar.fromYmdHms(1986, 1, 20, 14, 0, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("小寒", lunar.getPrevJie().getName());
    Assert.assertEquals("冬至", lunar.getPrevQi().getName());
    Assert.assertEquals("小寒", lunar.getPrevJieQi().getName());

    solar = Solar.fromYmd(1986, 12, 7);
    lunar = solar.getLunar();
    Assert.assertEquals("大雪", lunar.getJie());
    Assert.assertEquals("大雪", lunar.getJieQi());
    Assert.assertEquals("大雪", lunar.getCurrentJieQi().getName());
    Assert.assertEquals("大雪", lunar.getCurrentJie().getName());
    Assert.assertNull(lunar.getCurrentQi());
    Assert.assertEquals("", lunar.getQi());
    Assert.assertEquals("大雪", lunar.getNextJie().getName());
    Assert.assertEquals("冬至", lunar.getNextQi().getName());
    Assert.assertEquals("大雪", lunar.getNextJieQi().getName());

    solar = Solar.fromYmd(1986, 1, 1);
    lunar = solar.getLunar();
    Assert.assertEquals("", lunar.getJie());
    Assert.assertEquals("", lunar.getQi());
    Assert.assertEquals("", lunar.getJieQi());
    Assert.assertNull(lunar.getCurrentJieQi());
    Assert.assertNull(lunar.getCurrentJie());
    Assert.assertNull(lunar.getCurrentQi());
    Assert.assertEquals("大雪", lunar.getPrevJie().getName());
    Assert.assertEquals("冬至", lunar.getPrevQi().getName());
    Assert.assertEquals("冬至", lunar.getPrevJieQi().getName());
    Assert.assertEquals("小寒", lunar.getNextJie().getName());
    Assert.assertEquals("大寒", lunar.getNextQi().getName());
    Assert.assertEquals("小寒", lunar.getNextJieQi().getName());


    solar = Solar.fromYmd(2012, 12, 25);
    lunar = solar.getLunar();
    Assert.assertEquals("", lunar.getJie());
    Assert.assertEquals("", lunar.getQi());
    Assert.assertEquals("", lunar.getJieQi());
    Assert.assertNull(lunar.getCurrentJie());
    Assert.assertNull(lunar.getCurrentQi());
    Assert.assertNull(lunar.getCurrentJieQi());

    Assert.assertEquals("小寒", lunar.getNextJie().getName());
    Assert.assertEquals("大寒", lunar.getNextQi().getName());
    Assert.assertEquals("小寒", lunar.getNextJieQi().getName());

    Assert.assertEquals("大雪", lunar.getPrevJie().getName());
    Assert.assertEquals("冬至", lunar.getPrevQi().getName());
    Assert.assertEquals("冬至", lunar.getPrevJieQi().getName());
  }

  /**
   * 干支测试
   */
  @Test
  public void testGanZhi() {
    Solar solar = new Solar(2020, 1, 1, 13, 22, 0);
    Lunar lunar = solar.getLunar();
    Assert.assertEquals("己亥", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丙子", lunar.getMonthInGanZhi());
    Assert.assertEquals("丙子", lunar.getMonthInGanZhiExact());


    //小寒
    solar = new Solar(2020, 1, 6, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("己亥", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丁丑", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 1, 20, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("己亥", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丁丑", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    //春节
    solar = new Solar(2020, 1, 25, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丁丑", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 1, 30, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丁丑", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 2, 1, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("丁丑", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 2, 4, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("戊寅", lunar.getMonthInGanZhi());
    Assert.assertEquals("丁丑", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 2, 4, 18, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("戊寅", lunar.getMonthInGanZhi());
    Assert.assertEquals("戊寅", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 2, 5, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("戊寅", lunar.getMonthInGanZhi());
    Assert.assertEquals("戊寅", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 5, 22, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("辛巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("辛巳", lunar.getMonthInGanZhiExact());


    solar = new Solar(2020, 5, 23, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("辛巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("辛巳", lunar.getMonthInGanZhiExact());

    solar = new Solar(2020, 5, 29, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("辛巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("辛巳", lunar.getMonthInGanZhiExact());

    solar = new Solar(2020, 6, 1, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("辛巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("辛巳", lunar.getMonthInGanZhiExact());

    solar = new Solar(2020, 6, 29, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("庚子", lunar.getYearInGanZhi());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("庚子", lunar.getYearInGanZhiExact());

    Assert.assertEquals("壬午", lunar.getMonthInGanZhi());
    Assert.assertEquals("壬午", lunar.getMonthInGanZhiExact());

    solar = new Solar(2019, 5, 1, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("己亥", lunar.getYearInGanZhi());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("己亥", lunar.getYearInGanZhiExact());

    Assert.assertEquals("戊辰", lunar.getMonthInGanZhi());
    Assert.assertEquals("戊辰", lunar.getMonthInGanZhiExact());

    solar = new Solar(1986, 5, 29, 13, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("丙寅", lunar.getYearInGanZhi());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiExact());

    Assert.assertEquals("癸巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("癸巳", lunar.getMonthInGanZhiExact());

    solar = new Solar(1986, 5, 1, 1, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("丙寅", lunar.getYearInGanZhi());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiExact());

    Assert.assertEquals("壬辰", lunar.getMonthInGanZhi());
    Assert.assertEquals("壬辰", lunar.getMonthInGanZhiExact());

    solar = new Solar(1986, 5, 6, 1, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("丙寅", lunar.getYearInGanZhi());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiExact());

    Assert.assertEquals("癸巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("壬辰", lunar.getMonthInGanZhiExact());

    solar = new Solar(1986, 5, 6, 23, 22, 0);
    lunar = solar.getLunar();
    Assert.assertEquals("丙寅", lunar.getYearInGanZhi());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiByLiChun());
    Assert.assertEquals("丙寅", lunar.getYearInGanZhiExact());

    Assert.assertEquals("癸巳", lunar.getMonthInGanZhi());
    Assert.assertEquals("癸巳", lunar.getMonthInGanZhiExact());
  }


  @Test
  public void testXun() {
    Map<String, String> xun = new HashMap<String, String>();
    xun.put("甲子", "甲子");
    xun.put("乙丑", "甲子");
    xun.put("丙寅", "甲子");
    xun.put("丁卯", "甲子");
    xun.put("戊辰", "甲子");
    xun.put("己巳", "甲子");
    xun.put("庚午", "甲子");
    xun.put("辛未", "甲子");
    xun.put("壬申", "甲子");
    xun.put("癸酉", "甲子");
    xun.put("甲戌", "甲戌");
    xun.put("乙亥", "甲戌");
    xun.put("丙子", "甲戌");
    xun.put("丁丑", "甲戌");
    xun.put("戊寅", "甲戌");
    xun.put("己卯", "甲戌");
    xun.put("庚辰", "甲戌");
    xun.put("辛巳", "甲戌");
    xun.put("壬午", "甲戌");
    xun.put("癸未", "甲戌");
    xun.put("甲申", "甲申");
    xun.put("乙酉", "甲申");
    xun.put("丙戌", "甲申");
    xun.put("丁亥", "甲申");
    xun.put("戊子", "甲申");
    xun.put("己丑", "甲申");
    xun.put("庚寅", "甲申");
    xun.put("辛卯", "甲申");
    xun.put("壬辰", "甲申");
    xun.put("癸巳", "甲申");
    xun.put("甲午", "甲午");
    xun.put("乙未", "甲午");
    xun.put("丙申", "甲午");
    xun.put("丁酉", "甲午");
    xun.put("戊戌", "甲午");
    xun.put("己亥", "甲午");
    xun.put("庚子", "甲午");
    xun.put("辛丑", "甲午");
    xun.put("壬寅", "甲午");
    xun.put("癸卯", "甲午");
    xun.put("甲辰", "甲辰");
    xun.put("乙巳", "甲辰");
    xun.put("丙午", "甲辰");
    xun.put("丁未", "甲辰");
    xun.put("戊申", "甲辰");
    xun.put("己酉", "甲辰");
    xun.put("庚戌", "甲辰");
    xun.put("辛亥", "甲辰");
    xun.put("壬子", "甲辰");
    xun.put("癸丑", "甲辰");
    xun.put("甲寅", "甲寅");
    xun.put("乙卯", "甲寅");
    xun.put("丙辰", "甲寅");
    xun.put("丁巳", "甲寅");
    xun.put("戊午", "甲寅");
    xun.put("己未", "甲寅");
    xun.put("庚申", "甲寅");
    xun.put("辛酉", "甲寅");
    xun.put("壬戌", "甲寅");
    xun.put("癸亥", "甲寅");

    for (Map.Entry<String, String> entry : xun.entrySet()) {
      Assert.assertEquals(entry.getValue(), Lunar.getXun(entry.getKey()));
    }
  }

  @Test
  public void testXunKong() {
    Map<String, String> kong = new HashMap<>();
    kong.put("甲子", "戌亥");
    kong.put("乙丑", "戌亥");
    kong.put("丙寅", "戌亥");
    kong.put("丁卯", "戌亥");
    kong.put("戊辰", "戌亥");
    kong.put("己巳", "戌亥");
    kong.put("庚午", "戌亥");
    kong.put("辛未", "戌亥");
    kong.put("壬申", "戌亥");
    kong.put("癸酉", "戌亥");
    kong.put("甲戌", "申酉");
    kong.put("乙亥", "申酉");
    kong.put("丙子", "申酉");
    kong.put("丁丑", "申酉");
    kong.put("戊寅", "申酉");
    kong.put("己卯", "申酉");
    kong.put("庚辰", "申酉");
    kong.put("辛巳", "申酉");
    kong.put("壬午", "申酉");
    kong.put("癸未", "申酉");
    kong.put("甲申", "午未");
    kong.put("乙酉", "午未");
    kong.put("丙戌", "午未");
    kong.put("丁亥", "午未");
    kong.put("戊子", "午未");
    kong.put("己丑", "午未");
    kong.put("庚寅", "午未");
    kong.put("辛卯", "午未");
    kong.put("壬辰", "午未");
    kong.put("癸巳", "午未");
    kong.put("甲午", "辰巳");
    kong.put("乙未", "辰巳");
    kong.put("丙申", "辰巳");
    kong.put("丁酉", "辰巳");
    kong.put("戊戌", "辰巳");
    kong.put("己亥", "辰巳");
    kong.put("庚子", "辰巳");
    kong.put("辛丑", "辰巳");
    kong.put("壬寅", "辰巳");
    kong.put("癸卯", "辰巳");
    kong.put("甲辰", "寅卯");
    kong.put("乙巳", "寅卯");
    kong.put("丙午", "寅卯");
    kong.put("丁未", "寅卯");
    kong.put("戊申", "寅卯");
    kong.put("己酉", "寅卯");
    kong.put("庚戌", "寅卯");
    kong.put("辛亥", "寅卯");
    kong.put("壬子", "寅卯");
    kong.put("癸丑", "寅卯");
    kong.put("甲寅", "子丑");
    kong.put("乙卯", "子丑");
    kong.put("丙辰", "子丑");
    kong.put("丁巳", "子丑");
    kong.put("戊午", "子丑");
    kong.put("己未", "子丑");
    kong.put("庚申", "子丑");
    kong.put("辛酉", "子丑");
    kong.put("壬戌", "子丑");
    kong.put("癸亥", "子丑");

    for (Map.Entry<String, String> entry : kong.entrySet()) {
      Assert.assertEquals(entry.getValue(), Lunar.getXunKong(entry.getKey()));
    }
  }

  @Test
  public void testXun1() {
    Solar solar = new Solar(2020, 11, 19, 0, 0, 0);
    Lunar lunar = solar.getLunar();
    Assert.assertEquals("甲午", lunar.getYearXun());
  }

  @Test
  public void testXunKong1() {
    Solar solar = new Solar(2020, 11, 19, 0, 0, 0);
    Lunar lunar = solar.getLunar();
    Assert.assertEquals("辰巳", lunar.getYearXunKong());
    Assert.assertEquals("午未", lunar.getMonthXunKong());
    Assert.assertEquals("戌亥", lunar.getDayXunKong());
  }

  @Test
  public void testBaZiDayXunKong() {
    Solar solar = new Solar(1990, 12, 23, 8, 37, 0);
    Lunar lunar = solar.getLunar();
    EightChar eightChar = lunar.getEightChar();
    Assert.assertEquals("子丑", eightChar.getDayXunKong());
  }

}

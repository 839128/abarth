package org.miaixz.bus.core.center.date.culture.lunar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 农历年测试
 */
public class LunarYearTest {

    @Test
    public void test0() {
        Assertions.assertEquals("农历癸卯年", LunarYear.fromYear(2023).getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals("农历戊申年", LunarYear.fromYear(2023).next(5).getName());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("农历戊戌年", LunarYear.fromYear(2023).next(-5).getName());
    }

    /**
     * 农历年的干支
     */
    @Test
    public void test3() {
        Assertions.assertEquals("庚子", LunarYear.fromYear(2020).getSixtyCycle().getName());
    }

    /**
     * 农历年的生肖(农历年.干支.地支.生肖)
     */
    @Test
    public void test4() {
        Assertions.assertEquals("虎", LunarYear.fromYear(1986).getSixtyCycle().getEarthBranch().getZodiac().getName());
    }

    @Test
    public void test5() {
        Assertions.assertEquals(12, LunarYear.fromYear(151).getLeapMonth());
    }

    @Test
    public void test6() {
        Assertions.assertEquals(1, LunarYear.fromYear(2357).getLeapMonth());
    }

    @Test
    public void test7() {
        LunarYear y = LunarYear.fromYear(2023);
        Assertions.assertEquals("癸卯", y.getSixtyCycle().getName());
        Assertions.assertEquals("兔", y.getSixtyCycle().getEarthBranch().getZodiac().getName());
    }

    @Test
    public void test8() {
        Assertions.assertEquals("上元", LunarYear.fromYear(1864).getTwenty().getSixty().getName());
    }

    @Test
    public void test9() {
        Assertions.assertEquals("上元", LunarYear.fromYear(1923).getTwenty().getSixty().getName());
    }

    @Test
    public void test10() {
        Assertions.assertEquals("中元", LunarYear.fromYear(1924).getTwenty().getSixty().getName());
    }

    @Test
    public void test11() {
        Assertions.assertEquals("中元", LunarYear.fromYear(1983).getTwenty().getSixty().getName());
    }

    @Test
    public void test12() {
        Assertions.assertEquals("下元", LunarYear.fromYear(1984).getTwenty().getSixty().getName());
    }

    @Test
    public void test13() {
        Assertions.assertEquals("下元", LunarYear.fromYear(2043).getTwenty().getSixty().getName());
    }

    @Test
    public void test14() {
        Assertions.assertEquals("一运", LunarYear.fromYear(1864).getTwenty().getName());
    }

    @Test
    public void test15() {
        Assertions.assertEquals("一运", LunarYear.fromYear(1883).getTwenty().getName());
    }

    @Test
    public void test16() {
        Assertions.assertEquals("二运", LunarYear.fromYear(1884).getTwenty().getName());
    }

    @Test
    public void test17() {
        Assertions.assertEquals("二运", LunarYear.fromYear(1903).getTwenty().getName());
    }

    @Test
    public void test18() {
        Assertions.assertEquals("三运", LunarYear.fromYear(1904).getTwenty().getName());
    }

    @Test
    public void test19() {
        Assertions.assertEquals("三运", LunarYear.fromYear(1923).getTwenty().getName());
    }

    @Test
    public void test20() {
        Assertions.assertEquals("八运", LunarYear.fromYear(2004).getTwenty().getName());
    }

    @Test
    public void test21() {
        LunarYear year = LunarYear.fromYear(1);
        Assertions.assertEquals("六运", year.getTwenty().getName());
        Assertions.assertEquals("中元", year.getTwenty().getSixty().getName());
    }

    @Test
    public void test22() {
        LunarYear year = LunarYear.fromYear(1863);
        Assertions.assertEquals("九运", year.getTwenty().getName());
        Assertions.assertEquals("下元", year.getTwenty().getSixty().getName());
    }

    /**
     * 生成农历年历示例
     */
    @Test
    public void test23() {
        LunarYear year = LunarYear.fromYear(2023);
        for (LunarMonth month : year.getMonths()) {
            System.out.println(month);
            for (LunarWeek week : month.getWeeks(1)) {
                System.out.print(week.getName());
                for (LunarDay day : week.getDays()) {
                    System.out.print(" " + day.getName());
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}

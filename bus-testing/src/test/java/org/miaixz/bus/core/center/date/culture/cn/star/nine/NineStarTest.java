package org.miaixz.bus.core.center.date.culture.cn.star.nine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.lunar.LunarDay;
import org.miaixz.bus.core.center.date.culture.lunar.LunarHour;
import org.miaixz.bus.core.center.date.culture.lunar.LunarMonth;
import org.miaixz.bus.core.center.date.culture.lunar.LunarYear;
import org.miaixz.bus.core.center.date.culture.solar.SolarDay;

/**
 * 九星测试
 */
public class NineStarTest {

    @Test
    public void test0() {
        NineStar nineStar = LunarYear.fromYear(1985).getNineStar();
        Assertions.assertEquals("六", nineStar.getName());
        Assertions.assertEquals("六白金", nineStar.toString());
    }

    @Test
    public void test1() {
        NineStar nineStar = LunarYear.fromYear(2022).getNineStar();
        Assertions.assertEquals("五黄土", nineStar.toString());
        Assertions.assertEquals("玉衡", nineStar.getDipper().toString());
    }

    @Test
    public void test2() {
        NineStar nineStar = LunarYear.fromYear(2033).getNineStar();
        Assertions.assertEquals("三碧木", nineStar.toString());
        Assertions.assertEquals("天玑", nineStar.getDipper().toString());
    }

    @Test
    public void test3() {
        NineStar nineStar = LunarMonth.fromYm(1985, 2).getNineStar();
        Assertions.assertEquals("四绿木", nineStar.toString());
        Assertions.assertEquals("天权", nineStar.getDipper().toString());
    }

    @Test
    public void test4() {
        NineStar nineStar = LunarMonth.fromYm(1985, 2).getNineStar();
        Assertions.assertEquals("四绿木", nineStar.toString());
        Assertions.assertEquals("天权", nineStar.getDipper().toString());
    }

    @Test
    public void test5() {
        NineStar nineStar = LunarMonth.fromYm(2022, 1).getNineStar();
        Assertions.assertEquals("二黒土", nineStar.toString());
        Assertions.assertEquals("天璇", nineStar.getDipper().toString());
    }

    @Test
    public void test6() {
        NineStar nineStar = LunarMonth.fromYm(2033, 1).getNineStar();
        Assertions.assertEquals("五黄土", nineStar.toString());
        Assertions.assertEquals("玉衡", nineStar.getDipper().toString());
    }

    @Test
    public void test7() {
        NineStar nineStar = SolarDay.fromYmd(1985, 2, 19).getLunarDay().getNineStar();
        Assertions.assertEquals("五黄土", nineStar.toString());
        Assertions.assertEquals("玉衡", nineStar.getDipper().toString());
    }

    @Test
    public void test8() {
        NineStar nineStar = LunarDay.fromYmd(2022, 1, 1).getNineStar();
        Assertions.assertEquals("四绿木", nineStar.toString());
        Assertions.assertEquals("天权", nineStar.getDipper().toString());
    }

    @Test
    public void test9() {
        NineStar nineStar = LunarDay.fromYmd(2033, 1, 1).getNineStar();
        Assertions.assertEquals("一白水", nineStar.toString());
        Assertions.assertEquals("天枢", nineStar.getDipper().toString());
    }

    @Test
    public void test10() {
        NineStar nineStar = LunarHour.fromYmdHms(2033, 1, 1, 12, 0, 0).getNineStar();
        Assertions.assertEquals("七赤金", nineStar.toString());
        Assertions.assertEquals("摇光", nineStar.getDipper().toString());
    }

    @Test
    public void test11() {
        NineStar nineStar = LunarHour.fromYmdHms(2011, 5, 3, 23, 0, 0).getNineStar();
        Assertions.assertEquals("七赤金", nineStar.toString());
        Assertions.assertEquals("摇光", nineStar.getDipper().toString());
    }

}

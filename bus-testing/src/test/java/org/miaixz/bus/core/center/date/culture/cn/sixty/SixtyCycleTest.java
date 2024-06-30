package org.miaixz.bus.core.center.date.culture.cn.sixty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 六十甲子测试
 */
public class SixtyCycleTest {

    @Test
    public void test0() {
        Assertions.assertEquals("丁丑", SixtyCycle.fromIndex(13).getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals(13, SixtyCycle.fromName("丁丑").getIndex());
    }

    /**
     * 五行
     */
    @Test
    public void test2() {
        Assertions.assertEquals("石榴木", SixtyCycle.fromName("辛酉").getSound().getName());
        Assertions.assertEquals("剑锋金", SixtyCycle.fromName("癸酉").getSound().getName());
        Assertions.assertEquals("平地木", SixtyCycle.fromName("己亥").getSound().getName());
    }

    /**
     * 旬
     */
    @Test
    public void test3() {
        Assertions.assertEquals("甲子", SixtyCycle.fromName("甲子").getTen().getName());
        Assertions.assertEquals("甲寅", SixtyCycle.fromName("乙卯").getTen().getName());
        Assertions.assertEquals("甲申", SixtyCycle.fromName("癸巳").getTen().getName());
    }

    /**
     * 旬空
     */
    @Test
    public void test4() {
        Assertions.assertArrayEquals(new EarthBranch[]{EarthBranch.fromName("戌"), EarthBranch.fromName("亥")}, SixtyCycle.fromName("甲子").getExtraEarthBranches());
        Assertions.assertArrayEquals(new EarthBranch[]{EarthBranch.fromName("子"), EarthBranch.fromName("丑")}, SixtyCycle.fromName("乙卯").getExtraEarthBranches());
        Assertions.assertArrayEquals(new EarthBranch[]{EarthBranch.fromName("午"), EarthBranch.fromName("未")}, SixtyCycle.fromName("癸巳").getExtraEarthBranches());
    }

    /**
     * 地势(长生十二神)
     */
    @Test
    public void test5() {
        Assertions.assertEquals("长生", HeavenStem.fromName("丙").getTerrain(EarthBranch.fromName("寅")).getName());
        Assertions.assertEquals("沐浴", HeavenStem.fromName("辛").getTerrain(EarthBranch.fromName("亥")).getName());
    }

}

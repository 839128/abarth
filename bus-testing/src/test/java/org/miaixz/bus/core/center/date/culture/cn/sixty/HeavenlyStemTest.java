package org.miaixz.bus.core.center.date.culture.cn.sixty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 天干测试
 */
public class HeavenlyStemTest {

    @Test
    public void test0() {
        Assertions.assertEquals("甲", HeavenStem.fromIndex(0).getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals(0, HeavenStem.fromName("甲").getIndex());
    }

    /**
     * 天干的五行生克
     */
    @Test
    public void test2() {
        Assertions.assertEquals(HeavenStem.fromName("丙").getElement(), HeavenStem.fromName("甲").getElement().getReinforce());
    }

    /**
     * 十神
     */
    @Test
    public void test3() {
        Assertions.assertEquals("比肩", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("甲")).getName());
        Assertions.assertEquals("劫财", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("乙")).getName());
        Assertions.assertEquals("食神", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("丙")).getName());
        Assertions.assertEquals("伤官", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("丁")).getName());
        Assertions.assertEquals("偏财", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("戊")).getName());
        Assertions.assertEquals("正财", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("己")).getName());
        Assertions.assertEquals("七杀", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("庚")).getName());
        Assertions.assertEquals("正官", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("辛")).getName());
        Assertions.assertEquals("偏印", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("壬")).getName());
        Assertions.assertEquals("正印", HeavenStem.fromName("甲").getTenStar(HeavenStem.fromName("癸")).getName());
    }

}

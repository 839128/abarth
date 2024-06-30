package org.miaixz.bus.core.center.date.culture.cn.sixty;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 地支测试
 */
public class EarthlyBranchTest {

    @Test
    public void test0() {
        Assertions.assertEquals("子", EarthBranch.fromIndex(0).getName());
    }

    @Test
    public void test1() {
        Assertions.assertEquals(0, EarthBranch.fromName("子").getIndex());
    }

}

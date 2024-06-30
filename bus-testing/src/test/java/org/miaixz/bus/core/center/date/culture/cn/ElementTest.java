package org.miaixz.bus.core.center.date.culture.cn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.cn.sixty.EarthBranch;
import org.miaixz.bus.core.center.date.culture.cn.sixty.HeavenStem;

/**
 * 五行测试
 */
public class ElementTest {

    /**
     * 金克木
     */
    @Test
    public void test0() {
        Assertions.assertEquals(Element.fromName("木"), Element.fromName("金").getRestrain());
    }

    /**
     * 火生土
     */
    @Test
    public void test1() {
        Assertions.assertEquals(Element.fromName("土"), Element.fromName("火").getReinforce());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("火", HeavenStem.fromName("丙").getElement().getName());
    }

    @Test
    public void test3() {
        // 地支寅的五行为木
        Assertions.assertEquals("木", EarthBranch.fromName("寅").getElement().getName());

        // 地支寅的五行(木)生火
        Assertions.assertEquals(Element.fromName("火"), EarthBranch.fromName("寅").getElement().getReinforce());
    }

    /**
     * 生我的：火生土
     */
    @Test
    public void test4() {
        Assertions.assertEquals(Element.fromName("火"), Element.fromName("土").getReinforced());
    }

}

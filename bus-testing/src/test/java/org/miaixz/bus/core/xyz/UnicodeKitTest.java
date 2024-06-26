package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * UnicodeKit 单元测试
 */
public class UnicodeKitTest {
    @Test
    public void convertTest() {
        final String s = UnicodeKit.toUnicode("aaa123中文", true);
        Assertions.assertEquals("aaa123\\u4e2d\\u6587", s);

        final String s1 = UnicodeKit.toString(s);
        Assertions.assertEquals("aaa123中文", s1);
    }

    @Test
    public void convertTest2() {
        final String str = "aaaa\\u0026bbbb\\u0026cccc";
        final String unicode = UnicodeKit.toString(str);
        Assertions.assertEquals("aaaa&bbbb&cccc", unicode);
    }

    @Test
    public void convertTest3() {
        final String str = "aaa\\u111";
        final String res = UnicodeKit.toString(str);
        Assertions.assertEquals("aaa\\u111", res);
    }

    @Test
    public void convertTest4() {
        final String str = "aaa\\U4e2d\\u6587\\u111\\urtyu\\u0026";
        final String res = UnicodeKit.toString(str);
        Assertions.assertEquals("aaa中文\\u111\\urtyu&", res);
    }

    @Test
    public void convertTest5() {
        final String str = "{\"code\":403,\"enmsg\":\"Product not found\",\"cnmsg\":\"\\u4ea7\\u54c1\\u4e0d\\u5b58\\u5728\\uff0c\\u6216\\u5df2\\u5220\\u9664\",\"data\":null}";
        final String res = UnicodeKit.toString(str);
        Assertions.assertEquals("{\"code\":403,\"enmsg\":\"Product not found\",\"cnmsg\":\"产品不存在，或已删除\",\"data\":null}", res);
    }

    @Test
    public void issuesTest() {
        final String s = UnicodeKit.toUnicode("烟", true);
        Assertions.assertEquals("\\u70df", s);
    }

}

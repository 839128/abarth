package org.aoju.bus.core.lang;

import org.aoju.bus.core.toolkit.CallerKit;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CallerKit} 单元测试
 */
public class CallerTest {

    @Test
    public void getCallerTest() {
        Class<?> caller = CallerKit.getCaller();
        Assert.assertEquals(this.getClass(), caller);

        Class<?> caller0 = CallerKit.getCaller(0);
        Assert.assertEquals(CallerKit.class, caller0);

        Class<?> caller1 = CallerKit.getCaller(1);
        Assert.assertEquals(this.getClass(), caller1);
    }

    @Test
    public void getCallerCallerTest() {
        Class<?> callerCaller = CallerTestClass.getCaller();
        Assert.assertEquals(this.getClass(), callerCaller);
    }

    private static class CallerTestClass {
        public static Class<?> getCaller() {
            return CallerKit.getCallers();
        }
    }

}

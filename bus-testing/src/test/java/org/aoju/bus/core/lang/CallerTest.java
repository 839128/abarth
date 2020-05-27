package org.aoju.bus.core.lang;

import org.aoju.bus.core.toolkit.CallerKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link CallerKit} 单元测试
 */
public class CallerTest {

    @Test
    public void getCallerTest() {
        Class<?> caller = CallerKit.getCaller();
        Assertions.assertEquals(this.getClass(), caller);

        Class<?> caller0 = CallerKit.getCaller(0);
        Assertions.assertEquals(CallerKit.class, caller0);

        Class<?> caller1 = CallerKit.getCaller(1);
        Assertions.assertEquals(this.getClass(), caller1);
    }

    @Test
    public void getCallerCallerTest() {
        Class<?> callerCaller = CallerTestClass.getCaller();
        Assertions.assertEquals(this.getClass(), callerCaller);
    }

    private static class CallerTestClass {
        public static Class<?> getCaller() {
            return CallerKit.getCallers();
        }
    }

}

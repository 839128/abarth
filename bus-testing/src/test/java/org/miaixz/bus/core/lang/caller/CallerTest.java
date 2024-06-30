package org.miaixz.bus.core.lang.caller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.CallerKit;

/**
 * {@link CallerKit} 单元测试
 */
public class CallerTest {

    @Test
    public void getCallerTest() {
        final Class<?> caller = CallerKit.getCaller();
        Assertions.assertEquals(this.getClass(), caller);

        final Class<?> caller0 = CallerKit.getCaller(0);
        Assertions.assertEquals(CallerKit.class, caller0);

        final Class<?> caller1 = CallerKit.getCaller(1);
        Assertions.assertEquals(this.getClass(), caller1);
    }

    @Test
    public void getCallerCallerTest() {
        final Class<?> callerCaller = CallerTestClass.getCaller();
        Assertions.assertEquals(this.getClass(), callerCaller);
    }

    private static class CallerTestClass {
        public static Class<?> getCaller() {
            return CallerKit.getCallers();
        }
    }

}

package org.aoju.bus.core.lang;

import org.aoju.bus.core.utils.CallerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link CallerUtils} 单元测试
 */
public class CallerTest {

    @Test
    public void getCallerTest() {
        Class<?> caller = CallerUtils.getCaller();
        Assertions.assertEquals(this.getClass(), caller);

        Class<?> caller0 = CallerUtils.getCaller(0);
        Assertions.assertEquals(CallerUtils.class, caller0);

        Class<?> caller1 = CallerUtils.getCaller(1);
        Assertions.assertEquals(this.getClass(), caller1);
    }

    @Test
    public void getCallerCallerTest() {
        Class<?> callerCaller = CallerTestClass.getCaller();
        Assertions.assertEquals(this.getClass(), callerCaller);
    }

    private static class CallerTestClass {
        public static Class<?> getCaller() {
            return CallerUtils.getCallers();
        }
    }

}

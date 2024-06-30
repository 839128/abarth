package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CallerKitTest {

    @Test
    public void getCallerMethodNameTest() {
        final String callerMethodName = CallerKit.getCallerMethodName(false);
        Assertions.assertEquals("getCallerMethodNameTest", callerMethodName);

        final String fullCallerMethodName = CallerKit.getCallerMethodName(true);
        Assertions.assertEquals("org.miaixz.bus.core.xyz.CallerKitTest.getCallerMethodNameTest", fullCallerMethodName);
    }

}

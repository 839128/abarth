package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.convert.Convert;
import org.miaixz.bus.core.lang.exception.InternalException;

import java.io.IOException;

/**
 * 异常工具单元测试
 */
public class ExceptionKitTest {

    @Test
    public void wrapTest() {
        final InternalException e = ExceptionKit.wrap(new IOException(), InternalException.class);
        Assertions.assertNotNull(e);
    }

    @Test
    public void getRootTest() {
        // 查找入口方法
        final StackTraceElement ele = ExceptionKit.getRootStackElement();
        Assertions.assertEquals("main", ele.getMethodName());
    }

    @Test
    public void convertTest() {
        // RuntimeException e = new RuntimeException();
        final IOException ioException = new IOException();
        final IllegalArgumentException argumentException = new IllegalArgumentException(ioException);
        final IOException ioException1 = ExceptionKit.convertFromOrSuppressedThrowable(argumentException, IOException.class, true);
        Assertions.assertNotNull(ioException1);
    }

    @Test
    public void bytesIntConvertTest() {
        final String s = Convert.toString(12);
        final int integer = Convert.toInt(s);
        Assertions.assertEquals(12, integer);

        final byte[] bytes = Convert.intToBytes(12);
        final int i = Convert.bytesToInt(bytes);
        Assertions.assertEquals(12, i);
    }

}

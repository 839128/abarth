package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

import java.nio.ByteBuffer;

/**
 * 单元测试
 */
public class BufferKitTest {

    @Test
    public void copyTest() {
        final byte[] bytes = "AAABBB".getBytes();
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);

        final ByteBuffer buffer2 = BufferKit.copy(buffer, ByteBuffer.allocate(5));
        Assertions.assertEquals("AAABB", StringKit.toString(buffer2));
    }

    @Test
    public void readBytesTest() {
        final byte[] bytes = "AAABBB".getBytes();
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);

        final byte[] bs = BufferKit.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringKit.toString(bs));
    }

    @Test
    public void readBytes2Test() {
        final byte[] bytes = "AAABBB".getBytes();
        final ByteBuffer buffer = ByteBuffer.wrap(bytes);

        final byte[] bs = BufferKit.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringKit.toString(bs));
    }

    @Test
    public void readLineTest() {
        final String text = "aa\r\nbbb\ncc";
        final ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());

        // 第一行
        String line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assertions.assertEquals("aa", line);

        // 第二行
        line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assertions.assertEquals("bbb", line);

        // 第三行因为没有行结束标志，因此返回null
        line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assertions.assertNull(line);

        // 读取剩余部分
        Assertions.assertEquals("cc", StringKit.toString(BufferKit.readBytes(buffer)));
    }

}

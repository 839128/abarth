package org.aoju.bus.core.io;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.BufferUtils;
import org.aoju.bus.core.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * BufferUtils单元测试
 */
public class BufferUtilsTest {

    @Test
    public void copyTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        ByteBuffer buffer2 = BufferUtils.copy(buffer, ByteBuffer.allocate(5));
        Assertions.assertEquals("AAABB", StringUtils.toString(buffer2));
    }

    @Test
    public void readBytesTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferUtils.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringUtils.toString(bs));
    }

    @Test
    public void readBytes2Test() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferUtils.readBytes(buffer, 5);
        Assertions.assertEquals("AAABB", StringUtils.toString(bs));
    }

    @Test
    public void readLineTest() {
        String text = "aa\r\nbbb\ncc";
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());

        // 第一行
        String line = BufferUtils.readLine(buffer, Charset.UTF_8);
        Assertions.assertEquals("aa", line);

        // 第二行
        line = BufferUtils.readLine(buffer, Charset.UTF_8);
        Assertions.assertEquals("bbb", line);

        // 第三行因为没有行结束标志，因此返回null
        line = BufferUtils.readLine(buffer, Charset.UTF_8);
        Assertions.assertNull(line);

        // 读取剩余部分
        Assertions.assertEquals("cc", StringUtils.toString(BufferUtils.readBytes(buffer)));
    }

}

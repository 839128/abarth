package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Charset;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * BufferKit单元测试
 */
public class BufferKitTest {

    @Test
    public void copyTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        ByteBuffer buffer2 = BufferKit.copy(buffer, ByteBuffer.allocate(5));
        Assert.assertEquals("AAABB", StringKit.toString(buffer2));
    }

    @Test
    public void readBytesTest() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferKit.readBytes(buffer, 5);
        Assert.assertEquals("AAABB", StringKit.toString(bs));
    }

    @Test
    public void readBytes2Test() {
        byte[] bytes = "AAABBB".getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] bs = BufferKit.readBytes(buffer, 5);
        Assert.assertEquals("AAABB", StringKit.toString(bs));
    }

    @Test
    public void readLineTest() {
        String text = "aa\r\nbbb\ncc";
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());

        // 第一行
        String line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assert.assertEquals("aa", line);

        // 第二行
        line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assert.assertEquals("bbb", line);

        // 第三行因为没有行结束标志，因此返回null
        line = BufferKit.readLine(buffer, Charset.UTF_8);
        Assert.assertNull(line);

        // 读取剩余部分
        Assert.assertEquals("cc", StringKit.toString(BufferKit.readBytes(buffer)));
    }

}

package org.aoju.bus.core.text;

import org.aoju.bus.core.date.TimeInterval;
import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.toolkit.DateKit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Builders单元测试
 */
public class BuildersTest {

    /**
     * Builders的性能测试
     */
    @Test
    public void benchTest() {
        TimeInterval timer = DateKit.timer();
        Builders builder = Builders.create();
        for (int i = 0; i < 1000000; i++) {
            builder.append("test");
        }
        Console.log(timer.interval());

        timer.restart();
        StringBuilder b2 = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            b2.append("test");
            b2 = new StringBuilder();
        }
        Console.log(timer.interval());
    }

    @Test
    public void appendTest() {
        Builders builder = Builders.create();
        builder.append("aaa").append("你好").append('r');
        Assert.assertEquals("aaa你好r", builder.toString());
    }

    @Test
    public void insertTest() {
        Builders builder = Builders.create(1);
        builder.append("aaa").append("你好").append('r');
        builder.insert(3, "数据插入");
        Assert.assertEquals("aaa数据插入你好r", builder.toString());
    }

    @Test
    public void insertTest2() {
        Builders builder = Builders.create(1);
        builder.append("aaa").append("你好").append('r');
        builder.insert(6, "数据插入");
        Assert.assertEquals("aaa你好r数据插入", builder.toString());
    }

    @Test
    public void appendObjectTest() {
        Builders builder = Builders.create(1);
        builder.append(123).append(456.123D).append(true).append('\n');
        Assert.assertEquals("123456.123true\n", builder.toString());
    }

    @Test
    public void delTest() {
        // 删除全部测试
        Builders Builders = new Builders("ABCDEFG");
        int length = Builders.length();
        Builders builder = Builders.delete(0, length);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void delTest2() {
        // 删除中间部分测试
        Builders Builders = new Builders("ABCDEFG");
        int length = Builders.length();
        Builders builder = Builders.delete(2, 6);
        Assert.assertEquals("ABG", builder.toString());
    }

}

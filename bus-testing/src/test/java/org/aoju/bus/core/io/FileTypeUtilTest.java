package org.aoju.bus.core.io;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.lang.FileType;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * 文件类型判断单元测试
 */
public class FileTypeUtilTest {

    @Test
    public void fileTypeUtilTest() {
        File file = FileKit.file("123.jpg");
        String type = FileType.getType(file);
        Assert.assertEquals("jpg", type);

        FileType.putFileType("ffd8ffe000104a464946", "new_jpg");
        String newType = FileType.getType(file);
        Assert.assertEquals("jpg", newType);
    }

    @Test
    public void emptyTest() {
        File file = FileKit.file("/data/empty.txt");
        String type = FileType.getType(file);
        Console.log(type);
    }

    @Test
    public void docTest() {
        File file = FileKit.file("/data/test/test.doc");
        String type = FileType.getType(file);
        Console.log(type);
    }

}

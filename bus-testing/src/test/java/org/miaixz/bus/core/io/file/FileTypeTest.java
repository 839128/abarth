package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ResourceKit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

/**
 * 文件类型判断单元测试
 */
public class FileTypeTest {

    @Test
    public void getTypeTest() {
        final String type = FileType.getType(ResourceKit.getStream("LOGO.svg"));
        Assertions.assertEquals("jpg", type);
    }

    @Test
    public void customTypeTest() {
        final File file = FileKit.file("LOGO.svg");
        final String type = FileType.getType(file);
        Assertions.assertEquals("jpg", type);

        final String oldType = FileType.putFileType("ffd8ffe000104a464946", "new_jpg");
        Assertions.assertNull(oldType);

        final String newType = FileType.getType(file);
        Assertions.assertEquals("new_jpg", newType);

        FileType.removeFileType("ffd8ffe000104a464946");
        final String type2 = FileType.getType(file);
        Assertions.assertEquals("jpg", type2);
    }

    @Test
    @Disabled
    public void emptyTest() {
        final File file = FileKit.file("/test/core/empty.txt");
        final String type = FileType.getType(file);
        Console.log(type);
    }

    @Test
    @Disabled
    public void docTest() {
        final File file = FileKit.file("/test/core/test.doc");
        final String type = FileType.getType(file);
        Console.log(type);
    }

    @Test
    @Disabled
    public void inputStreamAndFilenameTest() {
        final File file = FileKit.file("/test/core/test.xlsx");
        final String type = FileType.getType(file);
        Assertions.assertEquals("xlsx", type);
    }

    @Test
    @Disabled
    public void getTypeFromInputStream() throws IOException {
        final File file = FileKit.file("/test/core/pic.jpg");
        final BufferedInputStream inputStream = FileKit.getInputStream(file);
        inputStream.mark(0);
        final String type = FileType.getType(inputStream);
        Assertions.assertEquals("jpg", type);

        inputStream.reset();
    }

    @Test
    @Disabled
    public void webpTest() {
        final File file = FileKit.file("/test/core/a.webp");
        final BufferedInputStream inputStream = FileKit.getInputStream(file);
        final String type = FileType.getType(inputStream);
        Console.log(type);
    }

    @Test
    public void issueTest() {
        final File file = FileKit.file("1.txt");
        final String type = FileType.getType(file);
        Assertions.assertEquals("txt", type);
    }

}

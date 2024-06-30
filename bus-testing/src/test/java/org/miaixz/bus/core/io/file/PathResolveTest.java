package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ArrayKit;
import org.miaixz.bus.core.xyz.FileKit;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

public class PathResolveTest {

    @Test
    void ofTest() {
        // 绝对路径测试
        Path path = PathResolve.of(Paths.get("/test/core"), Paths.get("data1"), Paths.get("data2"));
        assertEquals("/test/core/data2", path.toString().replace('\\', '/'));

        // 相对路径测试
        path = PathResolve.of(Paths.get("bus"), Paths.get("data1"), Paths.get("data2"));
        assertEquals("test/core/data2", path.toString().replace('\\', '/'));

        path = PathResolve.of(Paths.get("bus"));
        assertEquals("bus", path.toString().replace('\\', '/'));

        path = PathResolve.of((Path) null);
        Assertions.assertNull(path);
    }

    @Test
    @Disabled
    public void copyFileTest() {
        PathResolve.copy(
                Paths.get("/test/core/1595232240113.jpg"),
                Paths.get("/test/core/1595232240113_copy.jpg"),
                StandardCopyOption.COPY_ATTRIBUTES,
                StandardCopyOption.REPLACE_EXISTING
        );
    }

    @Test
    @Disabled
    public void copyTest() {
        PathResolve.copy(
                Paths.get("/test/coreRed2_LYY"),
                Paths.get("/test/core/aaa/aaa.txt")
        );
    }

    @Test
    @Disabled
    public void copyContentTest() {
        PathResolve.copyContent(
                Paths.get("/test/core/Red2_LYY"),
                Paths.get("/test/core/aaa/")
        );
    }

    @Test
    @Disabled
    public void moveTest() {
        PathResolve.move(Paths.get("/test/corelombok.jar"), Paths.get("/test/core/"), false);
    }

    @Test
    @Disabled
    public void moveDirTest() {
        PathResolve.move(Paths.get("c:\\aaa"), Paths.get("/test/core/bus"), false);
    }

    @Test
    @Disabled
    public void delDirTest() {
        PathResolve.remove(Paths.get("/test/core/bus"));
    }

    @Test
    @Disabled
    public void getMimeTypeTest() {
        String mimeType = PathResolve.getMimeType(Paths.get("/test/core/test.jpg"));
        assertEquals("image/jpeg", mimeType);

        mimeType = PathResolve.getMimeType(Paths.get("/test/core/test.mov"));
        assertEquals("video/quicktime", mimeType);
    }

    @Test
    public void getMimeOfRarTest() {
        final String contentType = FileKit.getMimeType("a001.rar");
        assertTrue(
                ArrayKit.contains(
                        new String[]{
                                "application/x-rar-compressed",
                                // JDK9+修改为此
                                "application/vnd.rar"},
                        contentType));
    }

    @Test
    public void getMimeOf7zTest() {
        final String contentType = FileKit.getMimeType("a001.7z");
        assertEquals("application/x-7z-compressed", contentType);
    }

    /**
     * target不存在空导致异常
     */
    @Test
    @Disabled
    public void moveTest2() {
        PathResolve.move(Paths.get("D:\\project\\test1.txt"), Paths.get("D:\\project\\test2.txt"), false);
    }

    @Test
    public void issueTest() {
        final String mimeType = PathResolve.getMimeType(Paths.get("xxxx.jpg"));
        assertEquals("image/jpeg", mimeType);
    }

    @Test
    public void equalsTest() {
        // 源文件和目标文件都不存在
        final File srcFile = FileKit.file("LOGO.svg");
        final File destFile = FileKit.file("LOGO.svg");

        final boolean equals = PathResolve.equals(srcFile.toPath(), destFile.toPath());
        assertTrue(equals);

        // 源文件存在，目标文件不存在
        final File srcFile1 = FileKit.file("LOGO.svg");
        final File destFile1 = FileKit.file("LOGO.svg");

        final boolean notEquals = PathResolve.equals(srcFile1.toPath(), destFile1.toPath());
        assertFalse(notEquals);
    }

    @Test
    @Disabled
    void isSameFileTest() {
        // 源文件和目标文件都不存在
        final File srcFile = FileKit.file("LOGO.svg");
        final File destFile = FileKit.file("LOGO.svg");

        assertTrue(PathResolve.isSameFile(srcFile.toPath(), destFile.toPath()));
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.miaixz.bus.core.io.file.FileName;
import org.miaixz.bus.core.io.file.LineSeparator;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.Keys;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * {@link FileKit} 单元测试类
 */
public class FileKitTest {

    @Test
    void fileTest1() {
        final File file = FileKit.file("/test/core/aaa", "bbb");
        Assertions.assertNotNull(file);
    }

    @Test
    public void fileTest2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            // 构建目录中出现非子目录抛出异常
            FileKit.file("/test/core/bbb", "../ccc");
        });
    }

    @Test
    public void getAbsolutePathTest() {
        final String absolutePath = FileKit.getAbsolutePath("LICENSE-junit.txt");
        Assertions.assertNotNull(absolutePath);
        final String absolutePath2 = FileKit.getAbsolutePath(absolutePath);
        Assertions.assertNotNull(absolutePath2);
        Assertions.assertEquals(absolutePath, absolutePath2);

        String path = FileKit.getAbsolutePath("中文.xml");
        Assertions.assertTrue(path.contains("中文.xml"));

        path = FileKit.getAbsolutePath("d:");
        Assertions.assertEquals("d:", path);
    }

    @Test
    @Disabled
    public void touchTest() {
        FileKit.touch("d:\\tea\\a.jpg");
    }

    @Test
    @Disabled
    public void renameTest() {
        FileKit.rename(FileKit.file("/test/core/3.jpg"), "2.jpg", false);
    }

    @Test
    @Disabled
    public void renameTest2() {
        FileKit.move(FileKit.file("/test/core/a"), FileKit.file("/test/core/b"), false);
    }

    @Test
    @Disabled
    public void renameToSubTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            // 移动到子目录，报错
            FileKit.move(FileKit.file("/test/core/a"), FileKit.file("/test/core/a/c"), false);
        });
    }

    @Test
    @Disabled
    public void renameSameTest() {
        // 目标和源相同，不处理
        FileKit.move(FileKit.file("/test/core/a"), FileKit.file("/test/core/a"), false);
    }

    @Test
    public void copyTest() {
        final File srcFile = FileKit.file("LOGO.svg");
        final File destFile = FileKit.file("bus.copy.jpg");

        FileKit.copy(srcFile, destFile, true);

        Assertions.assertTrue(destFile.exists());
        Assertions.assertEquals(srcFile.length(), destFile.length());
    }

    @Test
    @Disabled
    public void copySameTest() {
        final File srcFile = FileKit.file("/test/core/a");
        final File destFile = FileKit.file("/test/core/");

        // 拷贝到当前目录，不做处理
        FileKit.copy(srcFile, destFile, true);
    }

    @Test
    @Disabled
    public void copyDirTest() {
        final File srcFile = FileKit.file("D:\\test");
        final File destFile = FileKit.file("E:\\");

        FileKit.copy(srcFile, destFile, true);
    }

    @Test
    @Disabled
    public void moveDirTest() {
        final File srcFile = FileKit.file("E:\\test2");
        final File destFile = FileKit.file("D:\\");

        FileKit.move(srcFile, destFile, true);
    }

    @Test
    public void equalsTest() {
        // 源文件和目标文件都不存在
        final File srcFile = FileKit.file("LOGO.svg");
        final File destFile = FileKit.file("LOGO.svg");

        final boolean equals = FileKit.equals(srcFile, destFile);
        Assertions.assertTrue(equals);

        // 源文件存在，目标文件不存在
        final File srcFile1 = FileKit.file("LOGO.svg");
        final File destFile1 = FileKit.file("LOGO.svg");

        final boolean notEquals = FileKit.equals(srcFile1, destFile1);
        Assertions.assertFalse(notEquals);
    }

    @Test
    @Disabled
    public void convertLineSeparatorTest() {
        FileKit.convertLineSeparator(FileKit.file("/test/core/aaa.txt"), Charset.UTF_8, LineSeparator.WINDOWS);
    }

    @Test
    public void normalizeHomePathTest() {
        final String home = Keys.getUserHomePath().replace('\\', '/');
        Assertions.assertEquals(home + "/bar/", FileKit.normalize("~/foo/../bar/"));
    }

    @Test
    public void normalizeHomePathTest2() {
        final String home = Keys.getUserHomePath().replace('\\', '/');
        // 多个~应该只替换开头的
        Assertions.assertEquals(home + "/~bar/", FileKit.normalize("~/foo/../~bar/"));
    }

    @Test
    public void normalizeClassPathTest() {
        Assertions.assertEquals("", FileKit.normalize("classpath:"));
    }

    @Test
    public void normalizeClassPathTest2() {
        Assertions.assertEquals("../a/b.csv", FileKit.normalize("../a/b.csv"));
        Assertions.assertEquals("../../../a/b.csv", FileKit.normalize("../../../a/b.csv"));
    }

    @Test
    public void doubleNormalizeTest() {
        final String normalize = FileKit.normalize("/aa/b:/c");
        final String normalize2 = FileKit.normalize(normalize);
        Assertions.assertEquals("/aa/b:/c", normalize);
        Assertions.assertEquals(normalize, normalize2);
    }

    @Test
    public void subPathTest() {
        final Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path subPath = FileKit.subPath(path, 5, 4);
        Assertions.assertEquals("eee", subPath.toString());
        subPath = FileKit.subPath(path, 0, 1);
        Assertions.assertEquals("aaa", subPath.toString());
        subPath = FileKit.subPath(path, 1, 0);
        Assertions.assertEquals("aaa", subPath.toString());

        // 负数
        subPath = FileKit.subPath(path, -1, 0);
        Assertions.assertEquals("aaa/bbb/ccc/ddd/eee", subPath.toString().replace('\\', '/'));
        subPath = FileKit.subPath(path, -1, Integer.MAX_VALUE);
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileKit.subPath(path, -1, path.getNameCount());
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileKit.subPath(path, -2, -3);
        Assertions.assertEquals("ddd", subPath.toString());
    }

    @Test
    public void subPathTest2() {
        String subPath = FileKit.subPath("/test/core/aaa/bbb/", "/test/core/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb", "/test/core/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb", "/test/core/aaa/bbb/ccc/test.txt");
        Assertions.assertEquals("ccc/test.txt", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb/", "/test/core/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb", "/test/core/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb", "/test/core/aaa/bbb");
        Assertions.assertEquals("", subPath);

        subPath = FileKit.subPath("/test/core/aaa/bbb/", "/test/core/aaa/bbb");
        Assertions.assertEquals("", subPath);
    }

    @Test
    public void getPathEle() {
        final Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path ele = FileKit.getPathEle(path, -1);
        Assertions.assertEquals("fff", ele.toString());
        ele = FileKit.getPathEle(path, 0);
        Assertions.assertEquals("aaa", ele.toString());
        ele = FileKit.getPathEle(path, -5);
        Assertions.assertEquals("bbb", ele.toString());
        ele = FileKit.getPathEle(path, -6);
        Assertions.assertEquals("aaa", ele.toString());
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_8)
    public void listFileNamesTest() {
        // JDK9+中，由于模块化问题，获取的classoath路径非项目下，而是junit下的。
        List<String> names = FileKit.listFileNames("classpath:");
        Assertions.assertTrue(names.contains("LOGO.svg"));

        names = FileKit.listFileNames("");
        Assertions.assertTrue(names.contains("LOGO.svg"));

        names = FileKit.listFileNames(".");
        Assertions.assertTrue(names.contains("LOGO.svg"));
    }

    @Test
    @Disabled
    public void listFileNamesInJarTest() {
        final List<String> names = FileKit.listFileNames("/test/core/bus-core-5.1.0.jar!/org/miaixz/bus/core/xyz");
        for (final String name : names) {
            Console.log(name);
        }
    }

    @Test
    @Disabled
    public void listFileNamesTest2() {
        final List<String> names = FileKit.listFileNames("D:\\m2_repo\\commons-cli\\commons-cli\\1.0\\commons-cli-1.0.jar!org/mina/commons/cli/");
        for (final String string : names) {
            Console.log(string);
        }
    }

    @Test
    @Disabled
    public void loopFilesTest() {
        final List<File> files = FileKit.loopFiles("/test/core/");
        for (final File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    @Disabled
    public void loopFileTest() {
        final List<File> files = FileKit.loopFiles("/test/core/cglib-3.3.0.jar");
        Console.log(files);
    }

    @Test
    @Disabled
    public void loopFilesTest2() {
        FileKit.loopFiles("").forEach(Console::log);
    }

    @Test
    @Disabled
    public void loopFilesWithDepthTest() {
        final List<File> files = FileKit.loopFiles(FileKit.file("/test/core/m2_repo"), 2, null);
        for (final File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void getParentTest() {
        // 只在Windows下测试
        if (FileKit.isWindows()) {
            File parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 0);
            Assertions.assertEquals(FileKit.file("\\test\\core\\aaa\\bbb\\cc\\ddd"), parent);

            parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 1);
            Assertions.assertEquals(FileKit.file("\\test\\core\\aaa\\bbb\\cc"), parent);

            parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 2);
            Assertions.assertEquals(FileKit.file("\\test\\core\\aaa\\bbb"), parent);

            parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 4);
            Assertions.assertEquals(FileKit.file("\\test\\core\\"), parent);

            parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 5);
            Assertions.assertNull(parent);

            parent = FileKit.getParent(FileKit.file("/test/core/aaa/bbb/cc/ddd"), 10);
            Assertions.assertNull(parent);
        }
    }

    @Test
    public void lastIndexOfSeparatorTest() {
        final String dir = "d:\\aaa\\bbb\\cc\\ddd";
        final int index = FileKit.lastIndexOfSeparator(dir);
        Assertions.assertEquals(13, index);

        final String file = "ddd.jpg";
        final int index2 = FileKit.lastIndexOfSeparator(file);
        Assertions.assertEquals(-1, index2);
    }

    @Test
    public void getNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String name = FileName.getName(path);
        Assertions.assertEquals("ddd", name);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        name = FileName.getName(path);
        Assertions.assertEquals("ddd.jpg", name);
    }

    @Test
    public void mainNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileName.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileName.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileName.mainName(path);
        Assertions.assertEquals("ddd", mainName);
    }

    @Test
    public void extNameTest() {
        String path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\ddd\\" : "/test/core/ddd/";
        String mainName = FileName.extName(path);
        Assertions.assertEquals("", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\ddd" : "/test/core/ddd";
        mainName = FileName.extName(path);
        Assertions.assertEquals("", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\ddd.jpg" : "/test/core/ddd.jpg";
        mainName = FileName.extName(path);
        Assertions.assertEquals("jpg", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\fff.xlsx" : "/test/core/fff.xlsx";
        mainName = FileName.extName(path);
        Assertions.assertEquals("xlsx", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\fff.tar.gz" : "/test/core/fff.tar.gz";
        mainName = FileName.extName(path);
        Assertions.assertEquals("tar.gz", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\fff.tar.Z" : "/test/core/fff.tar.Z";
        mainName = FileName.extName(path);
        Assertions.assertEquals("tar.Z", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\fff.tar.bz2" : "/test/core/fff.tar.bz2";
        mainName = FileName.extName(path);
        Assertions.assertEquals("tar.bz2", mainName);

        path = FileKit.isWindows() ? "d:\\aaa\\bbb\\cc\\fff.tar.xz" : "/test/core/fff.tar.xz";
        mainName = FileName.extName(path);
        Assertions.assertEquals("tar.xz", mainName);
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_8)
    public void getWebRootTest() {
        // JDK9+环境中，由于模块问题，junit获取的classpath路径和实际不同
        final File webRoot = FileKit.getWebRoot();
        Assertions.assertNotNull(webRoot);
        Assertions.assertEquals("bus-core", webRoot.getName());
    }

    @Test
    public void getMimeTypeTest() {
        String mimeType = FileKit.getMimeType("test2Write.jpg");
        Assertions.assertEquals("image/jpeg", mimeType);

        mimeType = FileKit.getMimeType("test2Write.html");
        Assertions.assertEquals("text/html", mimeType);

        mimeType = FileKit.getMimeType("main.css");
        Assertions.assertEquals("text/css", mimeType);

        mimeType = FileKit.getMimeType("test.js");
        // 在 jdk 11+ 会获取到 text/javascript,而非 自定义的 application/x-javascript
        final List<String> list = ListKit.of("text/javascript", "application/x-javascript");
        Assertions.assertTrue(list.contains(mimeType));

        // office03
        mimeType = FileKit.getMimeType("test.doc");
        Assertions.assertEquals("application/msword", mimeType);
        mimeType = FileKit.getMimeType("test.xls");
        Assertions.assertEquals("application/vnd.ms-excel", mimeType);
        mimeType = FileKit.getMimeType("test.ppt");
        Assertions.assertEquals("application/vnd.ms-powerpoint", mimeType);

        // office07+
        mimeType = FileKit.getMimeType("test.docx");
        Assertions.assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", mimeType);
        mimeType = FileKit.getMimeType("test.xlsx");
        Assertions.assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", mimeType);
        mimeType = FileKit.getMimeType("test.pptx");
        Assertions.assertEquals("application/vnd.openxmlformats-officedocument.presentationml.presentation", mimeType);

        mimeType = FileKit.getMimeType("test.wgt");
        Assertions.assertEquals("application/widget", mimeType);

        mimeType = FileKit.getMimeType("https://xxx.oss-cn-hangzhou.aliyuncs.com/xxx.webp");
        Assertions.assertEquals("image/webp", mimeType);
    }

    @Test
    public void isSubTest() {
        final File file = new File("/test/core");
        final File file2 = new File("//test/core/aaa");
        Assertions.assertFalse(FileKit.isSub(file, file2));
    }

    @Test
    public void isSubRelativeTest() {
        final File file = new File("..");
        final File file2 = new File(".");
        Assertions.assertTrue(FileKit.isSub(file, file2));
    }

    @Test
    @Disabled
    public void appendLinesTest() {
        final List<String> list = ListKit.of("a", "b", "c");
        FileKit.appendLines(list, FileKit.file("/test/core/appendLines.txt"), Charset.UTF_8);
    }

    @Test
    public void createTempFileTest() {
        final File nullDirTempFile = FileKit.createTempFile();
        Assertions.assertTrue(nullDirTempFile.exists());

        final File suffixDirTempFile = FileKit.createTempFile(".xlsx", true);
        Assertions.assertEquals("xlsx", FileName.getSuffix(suffixDirTempFile));

        final File prefixDirTempFile = FileKit.createTempFile("prefix", ".xlsx", true);
        Console.log(prefixDirTempFile);
        Assertions.assertTrue(FileName.getPrefix(prefixDirTempFile).startsWith("prefix"));
    }

    @Test
    public void getTotalLinesTest() {
        // 此文件最后一行有换行符，则最后的空行算作一行
        final int totalLines = FileKit.getTotalLines(FileKit.file("test_lines.csv"));
        Assertions.assertEquals(8, totalLines);
    }

    @Test
    public void issueTest() {
        // 此文件最后一行末尾无换行符
        final int totalLines = FileKit.getTotalLines(FileKit.file("1_psi_index_0.txt"));
        Assertions.assertEquals(11, totalLines);
    }

    @Test
    public void isAbsolutePathTest() {
        String path = "//test/core/aaa.txt";
        Assertions.assertTrue(FileKit.isAbsolutePath(path));

        path = "/test/core/aaa.txt";
        Assertions.assertFalse(FileKit.isAbsolutePath(path));
    }

    @Test
    @Disabled
    void readBytesTest() {
        final byte[] bytes = FileKit.readBytes("test.properties");
        Assertions.assertEquals(125, bytes.length);
    }

    @Test
    void checkSlipTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FileKit.checkSlip(FileKit.file("test/a"), FileKit.file("test/../a"));
        });
    }

}

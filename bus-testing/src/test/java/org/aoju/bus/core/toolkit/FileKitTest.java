package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.io.file.LineSeparator;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * {@link FileKit} 单元测试类
 */
public class FileKitTest {

    @Test
    public void fileTest() {
        File file = FileKit.file("/data/aaa", "bbb");
        Assertions.assertNotNull(file);

        // 构建目录中出现非子目录抛出异常
        FileKit.file(file, "../ccc");

        FileKit.file("/data/");
    }

    @Test
    public void getAbsolutePathTest() {
        String absolutePath = FileKit.getAbsolutePath("LICENSE-junit.txt");
        Assertions.assertNotNull(absolutePath);
        String absolutePath2 = FileKit.getAbsolutePath(absolutePath);
        Assertions.assertNotNull(absolutePath2);
        Assertions.assertEquals(absolutePath, absolutePath2);

        String path = FileKit.getAbsolutePath("中文.xml");
        Assertions.assertTrue(path.contains("中文.xml"));

        path = FileKit.getAbsolutePath("d:");
        Assertions.assertEquals("d:", path);
    }

    @Test
    public void touchTest() {
        FileKit.touch("d:\\tea\\a.jpg");
    }

    @Test
    public void delTest() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileKit.delete("/data/test_3434543533409843.txt");
        Assertions.assertTrue(result);
    }

    @Test
    public void delTest2() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileKit.delete(Paths.get("/data/test_3434543533409843.txt"));
        Assertions.assertTrue(result);
    }

    @Test
    public void renameTest() {
        FileKit.rename(FileKit.file("123.jpg"), "b.png", false, false);
    }

    @Test
    public void copyTest() {
        File srcFile = FileKit.file("123.jpg");
        File destFile = FileKit.file("123.copy.jpg");

        FileKit.copy(srcFile, destFile, true);

        Assertions.assertTrue(destFile.exists());
        Assertions.assertEquals(srcFile.length(), destFile.length());
    }

    @Test
    public void copyFilesFromDir() {
        File srcFile = FileKit.file("/驱动");
        File destFile = FileKit.file("d/驱动备份");

        FileKit.copyFile(srcFile, destFile, true);
    }

    @Test
    public void equlasTest() {
        // 源文件和目标文件都不存在
        File srcFile = FileKit.file("/data/123.jpg");
        File destFile = FileKit.file("/data/123.jpg");

        boolean equals = FileKit.equals(srcFile, destFile);
        Assertions.assertTrue(equals);

        // 源文件存在，目标文件不存在
        File srcFile1 = FileKit.file("123.jpg");
        File destFile1 = FileKit.file("/data/123.jpg");

        boolean notEquals = FileKit.equals(srcFile1, destFile1);
        Assertions.assertFalse(notEquals);
    }

    @Test
    public void convertLineSeparatorTest() {
        FileKit.convertLineSeparator(FileKit.file("/data/aaa.txt"), Charset.UTF_8, LineSeparator.WINDOWS);
    }

    @Test
    public void normalizeTest() {
        Assertions.assertEquals("/foo/", FileKit.normalize("/foo//"));
        Assertions.assertEquals("/foo/", FileKit.normalize("/foo/./"));
        Assertions.assertEquals("/bar", FileKit.normalize("/foo/../bar"));
        Assertions.assertEquals("/bar/", FileKit.normalize("/foo/../bar/"));
        Assertions.assertEquals("/baz", FileKit.normalize("/foo/../bar/../baz"));
        Assertions.assertEquals("/", FileKit.normalize("/../"));
        Assertions.assertEquals("foo", FileKit.normalize("foo/bar/.."));
        Assertions.assertEquals("bar", FileKit.normalize("foo/../../bar"));
        Assertions.assertEquals("bar", FileKit.normalize("foo/../bar"));
        Assertions.assertEquals("/server/bar", FileKit.normalize("//server/foo/../bar"));
        Assertions.assertEquals("/bar", FileKit.normalize("//server/../bar"));
        Assertions.assertEquals("/data/bar", FileKit.normalize("\\data\\foo\\..\\bar"));
        Assertions.assertEquals("/data/bar", FileKit.normalize("\\data\\..\\bar"));
        Assertions.assertEquals("bar", FileKit.normalize("../../bar"));
        Assertions.assertEquals("/data/bar", FileKit.normalize("/data/bar"));
        Assertions.assertEquals("/data", FileKit.normalize("C:"));

        Assertions.assertEquals("\\/192.168.1.1/Share/", FileKit.normalize("\\\\192.168.1.1\\Share\\"));
    }

    @Test
    public void normalizeHomePathTest() {
        String home = FileKit.getUserHomePath().replace('\\', '/');
        Assertions.assertEquals(home + "/bar/", FileKit.normalize("~/foo/../bar/"));
    }

    @Test
    public void normalizeClassPathTest() {
        Assertions.assertEquals("", FileKit.normalize("classpath:"));
    }

    @Test
    public void doubleNormalizeTest() {
        String normalize = FileKit.normalize("/aa/b:/c");
        String normalize2 = FileKit.normalize(normalize);
        Assertions.assertEquals("/aa/b:/c", normalize);
        Assertions.assertEquals(normalize, normalize2);
    }

    @Test
    public void subPathTest() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

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
        String subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/test.txt");
        Assertions.assertEquals("ccc/test.txt", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb");
        Assertions.assertEquals("", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb");
        Assertions.assertEquals("", subPath);
    }

    @Test
    public void getPathEle() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

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
    public void listFileNamesTest() {
        List<String> names = FileKit.listFileNames("classpath:");
        Assertions.assertTrue(names.contains("123.jpg"));

        names = FileKit.listFileNames("");
        Assertions.assertTrue(names.contains("123.jpg"));

        names = FileKit.listFileNames(".");
        Assertions.assertTrue(names.contains("123.jpg"));
    }

    @Test
    public void listFileNamesInJarTest() {
        List<String> names = FileKit.listFileNames("/data/test/bus-core-5.1.0.jar!/org/aoju/bus/core/utils");
        for (String name : names) {
            Console.log(name);
        }
    }

    @Test
    public void listFileNamesTest2() {
        List<String> names = FileKit.listFileNames("D:\\works\\commons-cli\\commons-cli\\1.0\\commons-cli-1.0.jar!org/apache/commons/cli/");
        for (String string : names) {
            Console.log(string);
        }
    }

    @Test
    public void loopFilesTest() {
        List<File> files = FileKit.loopFiles("/data/");
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void loopFilesWithDepthTest() {
        List<File> files = FileKit.loopFiles(FileKit.file("/data/m2_repo"), 2, null);
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void getParentTest() {
        // 只在Windows下测试
        if (FileKit.isWindows()) {
            File parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 0);
            Assertions.assertEquals(FileKit.file("d:\\aaa\\bbb\\cc\\ddd"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 1);
            Assertions.assertEquals(FileKit.file("d:\\aaa\\bbb\\cc"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 2);
            Assertions.assertEquals(FileKit.file("d:\\aaa\\bbb"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 4);
            Assertions.assertEquals(FileKit.file("d:\\"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 5);
            Assertions.assertNull(parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 10);
            Assertions.assertNull(parent);
        }
    }

    @Test
    public void lastIndexOfSeparatorTest() {
        String dir = "d:\\aaa\\bbb\\cc\\ddd";
        int index = FileKit.lastIndexOfSeparator(dir);
        Assertions.assertEquals(13, index);

        String file = "ddd.jpg";
        int index2 = FileKit.lastIndexOfSeparator(file);
        Assertions.assertEquals(-1, index2);
    }

    @Test
    public void getNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String name = FileKit.getName(path);
        Assertions.assertEquals("ddd", name);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        name = FileKit.getName(path);
        Assertions.assertEquals("ddd.jpg", name);
    }

    @Test
    public void mainNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileKit.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileKit.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileKit.mainName(path);
        Assertions.assertEquals("ddd", mainName);
    }

    @Test
    public void extNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileKit.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileKit.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileKit.extName(path);
        Assertions.assertEquals("jpg", mainName);
    }

    @Test
    public void getWebRootTest() {
        File webRoot = FileKit.getWebRoot();
        Assertions.assertNotNull(webRoot);
        Assertions.assertEquals("bus-core", webRoot.getName());
    }

    @Test
    public void getMimeTypeTest() {
        String mimeType = FileKit.getMimeType("test2Write.jpg");
        Assertions.assertEquals("image/jpeg", mimeType);
    }

}

package org.aoju.bus.core.utils;

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
 * {@link FileUtils} 单元测试类
 */
public class FileUtilsTest {

    @Test
    public void fileTest() {
        File file = FileUtils.file("/data/aaa", "bbb");
        Assertions.assertNotNull(file);

        // 构建目录中出现非子目录抛出异常
        FileUtils.file(file, "../ccc");

        FileUtils.file("/data/");
    }

    @Test
    public void getAbsolutePathTest() {
        String absolutePath = FileUtils.getAbsolutePath("LICENSE-junit.txt");
        Assertions.assertNotNull(absolutePath);
        String absolutePath2 = FileUtils.getAbsolutePath(absolutePath);
        Assertions.assertNotNull(absolutePath2);
        Assertions.assertEquals(absolutePath, absolutePath2);

        String path = FileUtils.getAbsolutePath("中文.xml");
        Assertions.assertTrue(path.contains("中文.xml"));

        path = FileUtils.getAbsolutePath("d:");
        Assertions.assertEquals("d:", path);
    }

    @Test
    public void touchTest() {
        FileUtils.touch("d:\\tea\\a.jpg");
    }

    @Test
    public void delTest() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileUtils.delete("/data/test_3434543533409843.txt");
        Assertions.assertTrue(result);
    }

    @Test
    public void delTest2() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileUtils.delete(Paths.get("/data/test_3434543533409843.txt"));
        Assertions.assertTrue(result);
    }

    @Test
    public void renameTest() {
        FileUtils.rename(FileUtils.file("123.jpg"), "b.png", false, false);
    }

    @Test
    public void copyTest() {
        File srcFile = FileUtils.file("123.jpg");
        File destFile = FileUtils.file("123.copy.jpg");

        FileUtils.copy(srcFile, destFile, true);

        Assertions.assertTrue(destFile.exists());
        Assertions.assertEquals(srcFile.length(), destFile.length());
    }

    @Test
    public void copyFilesFromDir() {
        File srcFile = FileUtils.file("/驱动");
        File destFile = FileUtils.file("d/驱动备份");

        FileUtils.copyFilesFromDir(srcFile, destFile, true);
    }

    @Test
    public void equlasTest() {
        // 源文件和目标文件都不存在
        File srcFile = FileUtils.file("/data/123.jpg");
        File destFile = FileUtils.file("/data/123.jpg");

        boolean equals = FileUtils.equals(srcFile, destFile);
        Assertions.assertTrue(equals);

        // 源文件存在，目标文件不存在
        File srcFile1 = FileUtils.file("123.jpg");
        File destFile1 = FileUtils.file("/data/123.jpg");

        boolean notEquals = FileUtils.equals(srcFile1, destFile1);
        Assertions.assertFalse(notEquals);
    }

    @Test
    public void convertLineSeparatorTest() {
        FileUtils.convertLineSeparator(FileUtils.file("/data/aaa.txt"), Charset.UTF_8, LineSeparator.WINDOWS);
    }

    @Test
    public void normalizeTest() {
        Assertions.assertEquals("/foo/", FileUtils.normalize("/foo//"));
        Assertions.assertEquals("/foo/", FileUtils.normalize("/foo/./"));
        Assertions.assertEquals("/bar", FileUtils.normalize("/foo/../bar"));
        Assertions.assertEquals("/bar/", FileUtils.normalize("/foo/../bar/"));
        Assertions.assertEquals("/baz", FileUtils.normalize("/foo/../bar/../baz"));
        Assertions.assertEquals("/", FileUtils.normalize("/../"));
        Assertions.assertEquals("foo", FileUtils.normalize("foo/bar/.."));
        Assertions.assertEquals("bar", FileUtils.normalize("foo/../../bar"));
        Assertions.assertEquals("bar", FileUtils.normalize("foo/../bar"));
        Assertions.assertEquals("/server/bar", FileUtils.normalize("//server/foo/../bar"));
        Assertions.assertEquals("/bar", FileUtils.normalize("//server/../bar"));
        Assertions.assertEquals("/data/bar", FileUtils.normalize("\\data\\foo\\..\\bar"));
        Assertions.assertEquals("/data/bar", FileUtils.normalize("\\data\\..\\bar"));
        Assertions.assertEquals("bar", FileUtils.normalize("../../bar"));
        Assertions.assertEquals("/data/bar", FileUtils.normalize("/data/bar"));
        Assertions.assertEquals("/data", FileUtils.normalize("C:"));

        Assertions.assertEquals("\\/192.168.1.1/Share/", FileUtils.normalize("\\\\192.168.1.1\\Share\\"));
    }

    @Test
    public void normalizeHomePathTest() {
        String home = FileUtils.getUserHomePath().replace('\\', '/');
        Assertions.assertEquals(home + "/bar/", FileUtils.normalize("~/foo/../bar/"));
    }

    @Test
    public void normalizeClassPathTest() {
        Assertions.assertEquals("", FileUtils.normalize("classpath:"));
    }

    @Test
    public void doubleNormalizeTest() {
        String normalize = FileUtils.normalize("/aa/b:/c");
        String normalize2 = FileUtils.normalize(normalize);
        Assertions.assertEquals("/aa/b:/c", normalize);
        Assertions.assertEquals(normalize, normalize2);
    }

    @Test
    public void subPathTest() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path subPath = FileUtils.subPath(path, 5, 4);
        Assertions.assertEquals("eee", subPath.toString());
        subPath = FileUtils.subPath(path, 0, 1);
        Assertions.assertEquals("aaa", subPath.toString());
        subPath = FileUtils.subPath(path, 1, 0);
        Assertions.assertEquals("aaa", subPath.toString());

        // 负数
        subPath = FileUtils.subPath(path, -1, 0);
        Assertions.assertEquals("aaa/bbb/ccc/ddd/eee", subPath.toString().replace('\\', '/'));
        subPath = FileUtils.subPath(path, -1, Integer.MAX_VALUE);
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileUtils.subPath(path, -1, path.getNameCount());
        Assertions.assertEquals("fff", subPath.toString());
        subPath = FileUtils.subPath(path, -2, -3);
        Assertions.assertEquals("ddd", subPath.toString());
    }

    @Test
    public void subPathTest2() {
        String subPath = FileUtils.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/");
        Assertions.assertEquals("ccc/", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/test.txt");
        Assertions.assertEquals("ccc/test.txt", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc");
        Assertions.assertEquals("ccc", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb", "/data/aaa/bbb");
        Assertions.assertEquals("", subPath);

        subPath = FileUtils.subPath("/data/aaa/bbb/", "/data/aaa/bbb");
        Assertions.assertEquals("", subPath);
    }

    @Test
    public void getPathEle() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path ele = FileUtils.getPathEle(path, -1);
        Assertions.assertEquals("fff", ele.toString());
        ele = FileUtils.getPathEle(path, 0);
        Assertions.assertEquals("aaa", ele.toString());
        ele = FileUtils.getPathEle(path, -5);
        Assertions.assertEquals("bbb", ele.toString());
        ele = FileUtils.getPathEle(path, -6);
        Assertions.assertEquals("aaa", ele.toString());
    }

    @Test
    public void listFileNamesTest() {
        List<String> names = FileUtils.listFileNames("classpath:");
        Assertions.assertTrue(names.contains("123.jpg"));

        names = FileUtils.listFileNames("");
        Assertions.assertTrue(names.contains("123.jpg"));

        names = FileUtils.listFileNames(".");
        Assertions.assertTrue(names.contains("123.jpg"));
    }

    @Test
    public void listFileNamesInJarTest() {
        List<String> names = FileUtils.listFileNames("/data/test/bus-core-5.1.0.jar!/org/aoju/bus/core/utils");
        for (String name : names) {
            Console.log(name);
        }
    }

    @Test
    public void listFileNamesTest2() {
        List<String> names = FileUtils.listFileNames("D:\\works\\commons-cli\\commons-cli\\1.0\\commons-cli-1.0.jar!org/apache/commons/cli/");
        for (String string : names) {
            Console.log(string);
        }
    }

    @Test
    public void loopFilesTest() {
        List<File> files = FileUtils.loopFiles("/data/");
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void loopFilesWithDepthTest() {
        List<File> files = FileUtils.loopFiles(FileUtils.file("/data/m2_repo"), 2, null);
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void getParentTest() {
        // 只在Windows下测试
        if (FileUtils.isWindows()) {
            File parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 0);
            Assertions.assertEquals(FileUtils.file("d:\\aaa\\bbb\\cc\\ddd"), parent);

            parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 1);
            Assertions.assertEquals(FileUtils.file("d:\\aaa\\bbb\\cc"), parent);

            parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 2);
            Assertions.assertEquals(FileUtils.file("d:\\aaa\\bbb"), parent);

            parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 4);
            Assertions.assertEquals(FileUtils.file("d:\\"), parent);

            parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 5);
            Assertions.assertNull(parent);

            parent = FileUtils.getParent(FileUtils.file("/data/aaa/bbb/cc/ddd"), 10);
            Assertions.assertNull(parent);
        }
    }

    @Test
    public void lastIndexOfSeparatorTest() {
        String dir = "d:\\aaa\\bbb\\cc\\ddd";
        int index = FileUtils.lastIndexOfSeparator(dir);
        Assertions.assertEquals(13, index);

        String file = "ddd.jpg";
        int index2 = FileUtils.lastIndexOfSeparator(file);
        Assertions.assertEquals(-1, index2);
    }

    @Test
    public void getNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String name = FileUtils.getName(path);
        Assertions.assertEquals("ddd", name);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        name = FileUtils.getName(path);
        Assertions.assertEquals("ddd.jpg", name);
    }

    @Test
    public void mainNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileUtils.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileUtils.mainName(path);
        Assertions.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileUtils.mainName(path);
        Assertions.assertEquals("ddd", mainName);
    }

    @Test
    public void extNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileUtils.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileUtils.extName(path);
        Assertions.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileUtils.extName(path);
        Assertions.assertEquals("jpg", mainName);
    }

    @Test
    public void getWebRootTest() {
        File webRoot = FileUtils.getWebRoot();
        Assertions.assertNotNull(webRoot);
        Assertions.assertEquals("bus-core", webRoot.getName());
    }

    @Test
    public void getMimeTypeTest() {
        String mimeType = FileUtils.getMimeType("test2Write.jpg");
        Assertions.assertEquals("image/jpeg", mimeType);
    }

}

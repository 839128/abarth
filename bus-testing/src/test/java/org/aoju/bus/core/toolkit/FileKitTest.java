package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.io.file.LineSeparator;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.lang.Console;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertNotNull(file);

        // 构建目录中出现非子目录抛出异常
        FileKit.file(file, "../ccc");

        FileKit.file("/data/");
    }

    @Test
    public void getAbsolutePathTest() {
        String absolutePath = FileKit.getAbsolutePath("LICENSE-junit.txt");
        Assert.assertNotNull(absolutePath);
        String absolutePath2 = FileKit.getAbsolutePath(absolutePath);
        Assert.assertNotNull(absolutePath2);
        Assert.assertEquals(absolutePath, absolutePath2);

        String path = FileKit.getAbsolutePath("中文.xml");
        Assert.assertTrue(path.contains("中文.xml"));

        path = FileKit.getAbsolutePath("d:");
        Assert.assertEquals("d:", path);
    }

    @Test
    public void touchTest() {
        FileKit.touch("d:\\tea\\a.jpg");
    }

    @Test
    public void delTest() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileKit.delete("/data/test_3434543533409843.txt");
        Assert.assertTrue(result);
    }

    @Test
    public void delTest2() {
        // 删除一个不存在的文件，应返回true
        boolean result = FileKit.delete(Paths.get("/data/test_3434543533409843.txt"));
        Assert.assertTrue(result);
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

        Assert.assertTrue(destFile.exists());
        Assert.assertEquals(srcFile.length(), destFile.length());
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
        Assert.assertTrue(equals);

        // 源文件存在，目标文件不存在
        File srcFile1 = FileKit.file("123.jpg");
        File destFile1 = FileKit.file("/data/123.jpg");

        boolean notEquals = FileKit.equals(srcFile1, destFile1);
        Assert.assertFalse(notEquals);
    }

    @Test
    public void convertLineSeparatorTest() {
        FileKit.convertLineSeparator(FileKit.file("/data/aaa.txt"), Charset.UTF_8, LineSeparator.WINDOWS);
    }

    @Test
    public void normalizeTest() {
        Assert.assertEquals("/foo/", FileKit.normalize("/foo//"));
        Assert.assertEquals("/foo/", FileKit.normalize("/foo/./"));
        Assert.assertEquals("/bar", FileKit.normalize("/foo/../bar"));
        Assert.assertEquals("/bar/", FileKit.normalize("/foo/../bar/"));
        Assert.assertEquals("/baz", FileKit.normalize("/foo/../bar/../baz"));
        Assert.assertEquals("/", FileKit.normalize("/../"));
        Assert.assertEquals("foo", FileKit.normalize("foo/bar/.."));
        Assert.assertEquals("bar", FileKit.normalize("foo/../../bar"));
        Assert.assertEquals("bar", FileKit.normalize("foo/../bar"));
        Assert.assertEquals("/server/bar", FileKit.normalize("//server/foo/../bar"));
        Assert.assertEquals("/bar", FileKit.normalize("//server/../bar"));
        Assert.assertEquals("/data/bar", FileKit.normalize("\\data\\foo\\..\\bar"));
        Assert.assertEquals("/data/bar", FileKit.normalize("\\data\\..\\bar"));
        Assert.assertEquals("bar", FileKit.normalize("../../bar"));
        Assert.assertEquals("/data/bar", FileKit.normalize("/data/bar"));
        Assert.assertEquals("/data", FileKit.normalize("C:"));

        Assert.assertEquals("\\/192.168.1.1/Share/", FileKit.normalize("\\\\192.168.1.1\\Share\\"));
    }

    @Test
    public void normalizeHomePathTest() {
        String home = FileKit.getUserHomePath().replace('\\', '/');
        Assert.assertEquals(home + "/bar/", FileKit.normalize("~/foo/../bar/"));
    }

    @Test
    public void normalizeClassPathTest() {
        Assert.assertEquals("", FileKit.normalize("classpath:"));
    }

    @Test
    public void doubleNormalizeTest() {
        String normalize = FileKit.normalize("/aa/b:/c");
        String normalize2 = FileKit.normalize(normalize);
        Assert.assertEquals("/aa/b:/c", normalize);
        Assert.assertEquals(normalize, normalize2);
    }

    @Test
    public void subPathTest() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path subPath = FileKit.subPath(path, 5, 4);
        Assert.assertEquals("eee", subPath.toString());
        subPath = FileKit.subPath(path, 0, 1);
        Assert.assertEquals("aaa", subPath.toString());
        subPath = FileKit.subPath(path, 1, 0);
        Assert.assertEquals("aaa", subPath.toString());

        // 负数
        subPath = FileKit.subPath(path, -1, 0);
        Assert.assertEquals("aaa/bbb/ccc/ddd/eee", subPath.toString().replace('\\', '/'));
        subPath = FileKit.subPath(path, -1, Integer.MAX_VALUE);
        Assert.assertEquals("fff", subPath.toString());
        subPath = FileKit.subPath(path, -1, path.getNameCount());
        Assert.assertEquals("fff", subPath.toString());
        subPath = FileKit.subPath(path, -2, -3);
        Assert.assertEquals("ddd", subPath.toString());
    }

    @Test
    public void subPathTest2() {
        String subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc/");
        Assert.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/");
        Assert.assertEquals("ccc/", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc/test.txt");
        Assert.assertEquals("ccc/test.txt", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb/ccc");
        Assert.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb/ccc");
        Assert.assertEquals("ccc", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb", "/data/aaa/bbb");
        Assert.assertEquals("", subPath);

        subPath = FileKit.subPath("/data/aaa/bbb/", "/data/aaa/bbb");
        Assert.assertEquals("", subPath);
    }

    @Test
    public void getPathEle() {
        Path path = Paths.get("/aaa/bbb/ccc/ddd/eee/fff");

        Path ele = FileKit.getPathEle(path, -1);
        Assert.assertEquals("fff", ele.toString());
        ele = FileKit.getPathEle(path, 0);
        Assert.assertEquals("aaa", ele.toString());
        ele = FileKit.getPathEle(path, -5);
        Assert.assertEquals("bbb", ele.toString());
        ele = FileKit.getPathEle(path, -6);
        Assert.assertEquals("aaa", ele.toString());
    }

    @Test
    public void listFileNamesTest() {
        List<String> names = FileKit.listFileNames("classpath:");
        Assert.assertTrue(names.contains("123.jpg"));

        names = FileKit.listFileNames("");
        Assert.assertTrue(names.contains("123.jpg"));

        names = FileKit.listFileNames(".");
        Assert.assertTrue(names.contains("123.jpg"));
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
        List<File> files = FileKit.loopFiles("/data/m2_repo");
        for (File file : files) {
            Console.log(file.getPath());
        }
    }

    @Test
    public void getParentTest() {
        // 只在Windows下测试
        if (FileKit.isWindows()) {
            File parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 0);
            Assert.assertEquals(FileKit.file("d:\\aaa\\bbb\\cc\\ddd"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 1);
            Assert.assertEquals(FileKit.file("d:\\aaa\\bbb\\cc"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 2);
            Assert.assertEquals(FileKit.file("d:\\aaa\\bbb"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 4);
            Assert.assertEquals(FileKit.file("d:\\"), parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 5);
            Assert.assertNull(parent);

            parent = FileKit.getParent(FileKit.file("/data/aaa/bbb/cc/ddd"), 10);
            Assert.assertNull(parent);
        }
    }

    @Test
    public void lastIndexOfSeparatorTest() {
        String dir = "d:\\aaa\\bbb\\cc\\ddd";
        int index = FileKit.lastIndexOfSeparator(dir);
        Assert.assertEquals(13, index);

        String file = "ddd.jpg";
        int index2 = FileKit.lastIndexOfSeparator(file);
        Assert.assertEquals(-1, index2);
    }

    @Test
    public void getNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String name = FileKit.getName(path);
        Assert.assertEquals("ddd", name);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        name = FileKit.getName(path);
        Assert.assertEquals("ddd.jpg", name);
    }

    @Test
    public void mainNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileKit.mainName(path);
        Assert.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileKit.mainName(path);
        Assert.assertEquals("ddd", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileKit.mainName(path);
        Assert.assertEquals("ddd", mainName);
    }

    @Test
    public void extNameTest() {
        String path = "d:\\aaa\\bbb\\cc\\ddd\\";
        String mainName = FileKit.extName(path);
        Assert.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd";
        mainName = FileKit.extName(path);
        Assert.assertEquals("", mainName);

        path = "d:\\aaa\\bbb\\cc\\ddd.jpg";
        mainName = FileKit.extName(path);
        Assert.assertEquals("jpg", mainName);
    }

    @Test
    public void getWebRootTest() {
        File webRoot = FileKit.getWebRoot();
        Assert.assertNotNull(webRoot);
        Assert.assertEquals("bus-core", webRoot.getName());
    }

    @Test
    public void getMimeTypeTest() {
        String mimeType = FileKit.getMimeType("test2Write.jpg");
        Assert.assertEquals("image/jpeg", mimeType);
    }

}

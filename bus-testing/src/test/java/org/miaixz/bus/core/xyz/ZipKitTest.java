package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.compress.ZipReader;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.exception.InternalException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * {@link ZipKit}单元测试
 */
public class ZipKitTest {

    @Test
    public void appendTest() throws IOException {
        final File appendFile = FileKit.file("test-zip/addFile.txt");
        final File zipFile = FileKit.file("test-zip/test.zip");

        // 用于测试完成后将被测试文件恢复
        final File tempZipFile = FileKit.createTempFile(FileKit.file("test-zip"));
        tempZipFile.deleteOnExit();
        FileKit.copy(zipFile, tempZipFile, true);

        // test file add
        List<String> beforeNames = zipEntryNames(tempZipFile);
        ZipKit.append(tempZipFile.toPath(), appendFile.toPath());
        List<String> afterNames = zipEntryNames(tempZipFile);

        // 确认增加了文件
        Assertions.assertEquals(beforeNames.size() + 1, afterNames.size());
        Assertions.assertTrue(afterNames.containsAll(beforeNames));
        Assertions.assertTrue(afterNames.contains(appendFile.getName()));

        // test dir add
        beforeNames = zipEntryNames(tempZipFile);
        final File addDirFile = FileKit.file("test-zip/test-add");
        ZipKit.append(tempZipFile.toPath(), addDirFile.toPath());
        afterNames = zipEntryNames(tempZipFile);

        // 确认增加了文件和目录，增加目录和目录下一个文件，故此处+2
        Assertions.assertEquals(beforeNames.size() + 2, afterNames.size());
        Assertions.assertTrue(afterNames.containsAll(beforeNames));
        Assertions.assertTrue(afterNames.contains(appendFile.getName()));

        // rollback
        Assertions.assertTrue(tempZipFile.delete(), String.format("delete temp file %s failed", tempZipFile.getCanonicalPath()));
    }

    /**
     * 获取zip文件中所有一级文件/文件夹的name
     *
     * @param zipFile 待测试的zip文件
     * @return zip文件中一级目录下的所有文件/文件夹名
     */
    private List<String> zipEntryNames(final File zipFile) {
        final List<String> fileNames = new ArrayList<>();
        final ZipReader reader = ZipReader.of(zipFile, Charset.UTF_8);
        reader.read(zipEntry -> fileNames.add(zipEntry.getName()));
        reader.close();
        return fileNames;
    }

    @Test
    @Disabled
    public void zipDirTest() {
        ZipKit.zip(new File("//test/core"));
    }

    @Test
    @Disabled
    public void unzipTest() {
        final File unzip = ZipKit.unzip("/test/core/apache-maven-3.6.2.zip", "f:\\test");
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipTest2() {
        final File unzip = ZipKit.unzip("/test/core/各种资源.zip", "/test/core/各种资源", Charset.GBK);
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipFromStreamTest() {
        final File unzip = ZipKit.unzip(FileKit.getInputStream("/test/core/bus-core-5.1.0.jar"), FileKit.file("/test/core/"), Charset.UTF_8);
        Console.log(unzip);
    }

    @Test
    @Disabled
    public void unzipChineseTest() {
        ZipKit.unzip("//test/core/测试.zip");
    }

    @Test
    @Disabled
    public void unzipFileBytesTest() {
        final byte[] fileBytes = ZipKit.unzipFileBytes(FileKit.file("/test/core/02 电力相关设备及服务2-241-.zip"), Charset.GBK, "images/CE-EP-HY-MH01-ES-0001.jpg");
        Assertions.assertNotNull(fileBytes);
    }

    @Test
    public void gzipTest() {
        final String data = "我是一个需要压缩的很长很长的字符串";
        final byte[] bytes = ByteKit.toBytes(data);
        final byte[] gzip = ZipKit.gzip(bytes);

        //保证gzip长度正常
        Assertions.assertEquals(68, gzip.length);

        final byte[] unGzip = ZipKit.unGzip(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringKit.toString(unGzip));
    }

    @Test
    public void zlibTest() {
        final String data = "我是一个需要压缩的很长很长的字符串";
        final byte[] bytes = ByteKit.toBytes(data);
        byte[] gzip = ZipKit.zlib(bytes, 0);

        //保证zlib长度正常
        Assertions.assertEquals(62, gzip.length);
        final byte[] unGzip = ZipKit.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringKit.toString(unGzip));

        gzip = ZipKit.zlib(bytes, 9);
        //保证zlib长度正常
        Assertions.assertEquals(56, gzip.length);
        final byte[] unGzip2 = ZipKit.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringKit.toString(unGzip2));
    }

    @Test
    @Disabled
    public void zipStreamTest() {
        final String dir = "//test/core/test";
        final String zip = "//test/core/test.zip";
        try (final OutputStream out = Files.newOutputStream(Paths.get(zip))) {
            //实际应用中, out 为 HttpServletResponse.getOutputStream
            ZipKit.zip(out, Charset.defaultCharset(), false, null, new File(dir));
        } catch (final IOException e) {
            throw new InternalException(e);
        }
    }

    @Test
    @Disabled
    public void zipStreamTest2() {
        final String file1 = "/test/core/a.txt";
        final String file2 = "/test/core/a.txt";
        final String file3 = "/test/core/asn1.key";

        final String zip = "/test/core/test2.zip";
        //实际应用中, out 为 HttpServletResponse.getOutputStream
        ZipKit.zip(FileKit.getOutputStream(zip), Charset.defaultCharset(), false, null,
                new File(file1),
                new File(file2),
                new File(file3)
        );
    }

    @Test
    @Disabled
    public void zipToStreamTest() {
        final String zip = "/test/core/testToStream.zip";
        final OutputStream out = FileKit.getOutputStream(zip);
        ZipKit.zip(out, new String[]{"sm1_alias.txt"},
                new InputStream[]{FileKit.getInputStream("/test/core/sm4_1.txt")});
    }

    @Test
    @Disabled
    public void zipMultiFileTest() {
        final File[] dd = {FileKit.file("d:\\test\\qr_a.jpg")
                , FileKit.file("d:\\test\\qr_b.jpg")};

        ZipKit.zip(FileKit.file("d:\\test\\qr.zip"), false, dd);
    }

    @Test
    @Disabled
    public void sizeUnzip() throws IOException {
        final String zipPath = "F:\\BaiduNetdiskDownload\\demo.zip";
        final String outPath = "F:\\BaiduNetdiskDownload\\test";
        final ZipFile zipFile = new ZipFile(zipPath, java.nio.charset.Charset.forName("GBK"));
        final File file = new File(outPath);
        // 限制解压文件大小为637KB
        final long size = 637 * 1024L;

        // 限制解压文件大小为636KB
        // long size = 636*1024L;
        ZipKit.unzip(zipFile, file, size);
    }

    @Test
    @Disabled
    public void unzipTest3() {
        ZipKit.unzip("/test/core/default.zip", "/test/core/");
    }

}

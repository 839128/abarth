package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.lang.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * {@link ZipUtils}单元测试
 */
public class ZipUtilsTest {

    @Test
    public void zipDirTest() {
        ZipUtils.zip(new File("/data/picTest/picSubTest"));
    }

    @Test
    public void unzipTest() {
        File unzip = ZipUtils.unzip("/data/test/apache-maven-3.6.2.zip", "f:\\test");
        Console.log(unzip);
    }

    @Test
    public void unzipTest2() {
        File unzip = ZipUtils.unzip("/data/test/各种资源.zip", "/data/test/各种资源", Charset.GBK);
        Console.log(unzip);
    }

    @Test
    public void unzipFromStreamTest() {
        File unzip = ZipUtils.unzip(FileUtils.getInputStream("/data/test/123-core-5.1.0.jar"), FileUtils.file("/data/test/"), Charset.UTF_8);
        Console.log(unzip);
    }

    @Test
    public void unzipChineseTest() {
        ZipUtils.unzip("/data/测试.zip");
    }

    @Test
    public void unzipFileBytesTest() {
        byte[] fileBytes = ZipUtils.unzipFileBytes(FileUtils.file("/data/02 电力相关设备及服务2-241-.zip"), Charset.GBK, "images/CE-EP-HY-MH01-ES-0001.jpg");
        Assertions.assertNotNull(fileBytes);
    }

    @Test
    public void gzipTest() {
        String data = "我是一个需要压缩的很长很长的字符串";
        byte[] bytes = StringUtils.bytes(data);
        byte[] gzip = ZipUtils.gzip(bytes);

        //保证gzip长度正常
        Assertions.assertEquals(68, gzip.length);

        byte[] unGzip = ZipUtils.unGzip(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtils.str(unGzip));
    }

    @Test
    public void zlibTest() {
        String data = "我是一个需要压缩的很长很长的字符串";
        byte[] bytes = StringUtils.bytes(data);
        byte[] gzip = ZipUtils.zlib(bytes, 0);

        //保证zlib长度正常
        Assertions.assertEquals(62, gzip.length);
        byte[] unGzip = ZipUtils.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtils.str(unGzip));

        gzip = ZipUtils.zlib(bytes, 9);
        //保证zlib长度正常
        Assertions.assertEquals(56, gzip.length);
        byte[] unGzip2 = ZipUtils.unZlib(gzip);
        //保证正常还原
        Assertions.assertEquals(data, StringUtils.str(unGzip2));
    }

}

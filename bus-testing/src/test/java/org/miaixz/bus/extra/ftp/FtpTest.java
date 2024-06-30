package org.miaixz.bus.extra.ftp;

import org.apache.commons.net.ftp.FTPSClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.extra.ssh.provider.jsch.JschSftp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class FtpTest {

    @Test
    @Disabled
    public void ftpsTest() {
        final FTPSClient ftpsClient = new FTPSClient();
        final CommonsFtp ftp = new CommonsFtp(ftpsClient);

        ftp.cd("/file/aaa");
        Console.log(ftp.pwd());

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void cdTest() {
        final CommonsFtp ftp = CommonsFtp.of("bus.centos");

        ftp.cd("/file/aaa");
        Console.log(ftp.pwd());

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void uploadTest() {
        final CommonsFtp ftp = CommonsFtp.of("localhost");

        final boolean upload = ftp.uploadFile("/temp", FileKit.file("d:/test/test.zip"));
        Console.log(upload);

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void reconnectIfTimeoutTest() throws InterruptedException {
        final CommonsFtp ftp = CommonsFtp.of("bus.centos");

        Console.log("打印pwd: " + ftp.pwd());

        Console.log("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
        Thread.sleep(35 * 1000);

        try {
            Console.log("打印pwd: " + ftp.pwd());
        } catch (final InternalException e) {
            Console.error(e, e.getMessage());
        }

        Console.log("判断是否超时并重连...");
        ftp.reconnectIfTimeout();

        Console.log("打印pwd: " + ftp.pwd());

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void recursiveDownloadFolder() {
        final CommonsFtp ftp = CommonsFtp.of("bus.centos");
        ftp.recursiveDownloadFolder("/", FileKit.file("d:/test/download"));

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void recursiveDownloadFolderSftp() {
        final JschSftp ftp = JschSftp.of("127.0.0.1", 22, "test", "test");

        ftp.cd("/file/aaa");
        Console.log(ftp.pwd());
        ftp.recursiveDownloadFolder("/", FileKit.file("d:/test/download"));

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void downloadTest() {
        final CommonsFtp ftp = CommonsFtp.of("localhost");

        final List<String> fileNames = ftp.ls("temp/");
        for (final String name : fileNames) {
            ftp.download("",
                    name,
                    FileKit.file("d:/test/download/" + name));
        }

        IoKit.closeQuietly(ftp);
    }

    @Test
    @Disabled
    public void isDirTest() throws Exception {
        try (final CommonsFtp ftp = CommonsFtp.of("127.0.0.1", 21)) {
            Console.log(ftp.pwd());
            ftp.isDir("/test");
            Console.log(ftp.pwd());
        }
    }

    @Test
    @Disabled
    public void readTest() throws Exception {
        try (final CommonsFtp ftp = CommonsFtp.of("localhost");
             final BufferedReader reader = new BufferedReader(new InputStreamReader(ftp.getFileStream("d://test/read/", "test.txt")))) {
            String line;
            while (StringKit.isNotBlank(line = reader.readLine())) {
                Console.log(line);
            }
        }
    }

    @Test
    @Disabled
    public void existSftpTest() {
        try (final JschSftp ftp = JschSftp.of("127.0.0.1", 22, "test", "test")) {
            Console.log(ftp.pwd());
            Console.log(ftp.exist(null));
            Console.log(ftp.exist(""));
            Console.log(ftp.exist("."));
            Console.log(ftp.exist(".."));
            Console.log(ftp.exist("/"));
            Console.log(ftp.exist("a"));
            Console.log(ftp.exist("/home/test"));
            Console.log(ftp.exist("/home/test/"));
            Console.log(ftp.exist("/home/test//////"));
            Console.log(ftp.exist("/home/test/file1"));
            Console.log(ftp.exist("/home/test/file1/"));
            Console.log(ftp.exist("///////////"));
            Console.log(ftp.exist("./"));
            Console.log(ftp.exist("./file1"));
            Console.log(ftp.pwd());
        }
    }

    @Test
    @Disabled
    public void existFtpTest() throws Exception {
        try (final CommonsFtp ftp = CommonsFtp.of("127.0.0.1", 21)) {
            Console.log(ftp.pwd());
            Console.log(ftp.exist(null));
            Console.log(ftp.exist(""));
            Console.log(ftp.exist("."));
            Console.log(ftp.exist(".."));
            Console.log(ftp.exist("/"));
            Console.log(ftp.exist("a"));
            Console.log(ftp.exist("/test"));
            Console.log(ftp.exist("/test/"));
            Console.log(ftp.exist("/test//////"));
            Console.log(ftp.exist("/test/.."));
            Console.log(ftp.exist("/test/."));
            Console.log(ftp.exist("/file1"));
            Console.log(ftp.exist("/file1/"));
            Console.log(ftp.exist("///////////"));
            Console.log(ftp.exist("./"));
            Console.log(ftp.exist("./file1"));
            Console.log(ftp.exist("./2/3/4/.."));
            Console.log(ftp.pwd());
        }
    }

}

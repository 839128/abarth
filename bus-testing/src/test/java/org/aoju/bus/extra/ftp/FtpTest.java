package org.aoju.bus.extra.ftp;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.lang.exception.InstrumentException;
import org.aoju.bus.core.toolkit.FileKit;
import org.aoju.bus.core.toolkit.IoKit;
import org.aoju.bus.extra.ssh.Sftp;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class FtpTest {

    @Test
    @Ignore
    public void cdTest() {
        Ftp ftp = new Ftp("bus.centos");

        ftp.cd("/test/aa");
        Console.log(ftp.pwd());

        IoKit.close(ftp);
    }

    @Test
    @Ignore
    public void uploadTest() {
        Ftp ftp = new Ftp("bus.centos");

        List<String> ls = ftp.ls("/test");
        Console.log(ls);

        boolean upload = ftp.upload("/test/aaa", FileKit.file("/qrcode.jpg"));
        Console.log(upload);

        IoKit.close(ftp);
    }

    @Test
    @Ignore
    public void reconnectIfTimeoutTest() throws InterruptedException {
        Ftp ftp = new Ftp("bus.centos");

        Console.log("打印pwd: " + ftp.pwd());

        Console.log("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
        Thread.sleep(35 * 1000);

        try {
            Console.log("打印pwd: " + ftp.pwd());
        } catch (InstrumentException e) {
            e.printStackTrace();
        }

        Console.log("判断是否超时并重连...");
        ftp.reconnectIfTimeout();

        Console.log("打印pwd: " + ftp.pwd());

        IoKit.close(ftp);
    }

    @Test
    @Ignore
    public void recursiveDownloadFolder() {
        Ftp ftp = new Ftp("bus.centos");
        ftp.download("/", FileKit.file("/test/download"));

        IoKit.close(ftp);
    }

    @Test
    @Ignore
    public void recursiveDownloadFolderSftp() {
        Sftp ftp = new Sftp("127.0.0.1", 22, "test", "test");

        ftp.cd("/test/aaa");
        Console.log(ftp.pwd());
        ftp.download("/", FileKit.file("/test/download"));

        IoKit.close(ftp);
    }

}

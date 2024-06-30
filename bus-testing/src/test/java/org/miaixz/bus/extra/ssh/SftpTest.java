package org.miaixz.bus.extra.ssh;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.extra.ssh.provider.sshj.SshjSftp;

import java.io.File;
import java.util.List;

/**
 * 基于sshj 框架SFTP 封装测试.
 */
public class SftpTest {

    private SshjSftp sshjSftp;

    @BeforeEach
    @Disabled
    public void init() {
        sshjSftp = SshjSftp.of("ip", 22, "test", "test");
    }

    @Test
    @Disabled
    public void lsTest() {
        final List<String> files = sshjSftp.ls("/test/extra/ssh");
        if (files != null && !files.isEmpty()) {
            files.forEach(System.out::print);
        }
    }

    @Test
    @Disabled
    public void downloadTest() {
        sshjSftp.recursiveDownloadFolder("/test/extra/ssh/temp", new File("C:\\Users\\akwangl\\Downloads\\temp"));
    }

    @Test
    @Disabled
    public void uploadTest() {
        sshjSftp.uploadFile("/test/extra/ssh/temp/", new File("C:\\Users\\akwangl\\Downloads\\temp\\辽宁_20190718_104324.CIME"));
    }

    @Test
    @Disabled
    public void mkDirTest() {
        final boolean flag = sshjSftp.mkdir("/test/extra/ssh/temp");
        System.out.println("是否创建成功: " + flag);
    }

    @Test
    @Disabled
    public void mkDirsTest() {
        // 在当前目录下批量创建目录
        sshjSftp.mkDirs("/home/test/temp");
    }

    @Test
    @Disabled
    public void delDirTest() {
        sshjSftp.delDir("/test/extra/ssh/temp");
    }

}

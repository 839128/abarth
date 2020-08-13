package org.aoju.bus.extra.ssh;

import com.jcraft.jsch.Session;
import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.lang.exception.InstrumentException;
import org.aoju.bus.core.toolkit.IoKit;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 工具类单元测试
 */
public class SshKitTest {

    @Test
    @Ignore
    public void bindPortTest() {
        //新建会话，此会话用于ssh连接到跳板机（堡垒机），此处为10.1.1.1:22
        Session session = SshKit.getSession("bus.centos", 22, "test", "123456");
        // 将堡垒机保护的内网8080端口映射到localhost，我们就可以通过访问http://localhost:8080/访问内网服务了
        SshKit.bindPort(session, "172.10.10.10", 8080, 8080);
    }

    @Test
    @Ignore
    public void sftpTest() {
        Session session = SshKit.getSession("bus.centos", 22, "root", "123456");
        Sftp sftp = SshKit.createSftp(session);
        sftp.mkDirs("/test/aaa");
        Console.log("OK");
    }

    @Test
    @Ignore
    public void reconnectIfTimeoutTest() throws InterruptedException {
        Session session = SshKit.getSession("sunnyserver", 22, "mysftp", "liuyang1234");
        Sftp sftp = SshKit.createSftp(session);

        Console.log("打印pwd: " + sftp.pwd());
        Console.log("cd / : " + sftp.cd("/"));
        Console.log("休眠一段时间，查看是否超时");
        Thread.sleep(30 * 1000);

        try {
            // 当连接超时时，isConnected()仍然返回true，pwd命令也能正常返回，因此，利用发送cd命令的返回结果，来判断是否连接超时
            Console.log("isConnected " + sftp.getClient().isConnected());
            Console.log("打印pwd: " + sftp.pwd());
            Console.log("cd / : " + sftp.cd("/"));
        } catch (InstrumentException e) {
            e.printStackTrace();
        }

        Console.log("调用reconnectIfTimeout方法，判断是否超时并重连");
        sftp.reconnectIfTimeout();

        Console.log("打印pwd: " + sftp.pwd());

        IoKit.close(sftp);
    }

    @Test
    @Ignore
    public void getSessionTest() {
        SshKit.getSession("192.168.1.100", 22, "root", "aaa");
    }

}

package org.miaixz.bus.extra.ssh;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.extra.ssh.provider.jsch.JschSession;
import org.miaixz.bus.extra.ssh.provider.jsch.JschSftp;

import java.net.InetSocketAddress;

/**
 * Jsch工具类单元测试
 */
public class JschTest {

    @Test
    @Disabled
    public void bindPortTest() {
        //新建会话，此会话用于ssh连接到跳板机（堡垒机），此处为10.1.1.1:22
        final JschSession session = new JschSession(Connector.of("bus.centos", 22, "test", "123456"));
        // 将堡垒机保护的内网8080端口映射到localhost，我们就可以通过访问http://localhost:8080/访问内网服务了
        session.bindLocalPort(8080, new InetSocketAddress("172.20.12.123", 8080));
    }


    @Test
    @Disabled
    public void bindRemotePort() {
        // 建立会话
        final JschSession session = new JschSession(Connector.of("bus.centos", 22, "test", "123456"));
        // 绑定ssh服务端8089端口到本机的8000端口上
        session.bindRemotePort(new InetSocketAddress(8089), new InetSocketAddress("localhost", 8000));
        // 保证一直运行
    }


    @Test
    @Disabled
    public void sftpTest() {
        final JschSession session = new JschSession(Connector.of("bus.centos", 22, "root", "123456"));
        final JschSftp jschSftp = session.openSftp(Charset.UTF_8);
        jschSftp.mkDirs("/opt/test/aaa/bbb");
        Console.log("OK");
    }

    @Test
    @Disabled
    public void reconnectIfTimeoutTest() throws InterruptedException {
        final JschSession session = new JschSession(Connector.of("sunnyserver", 22, "mysftp", "liuyang1234"));
        final JschSftp jschSftp = session.openSftp(Charset.UTF_8);

        Console.log("打印pwd: " + jschSftp.pwd());
        Console.log("cd / : " + jschSftp.cd("/"));
        Console.log("休眠一段时间，查看是否超时");
        Thread.sleep(30 * 1000);

        // 当连接超时时，isConnected()仍然返回true，pwd命令也能正常返回，因此，利用发送cd命令的返回结果，来判断是否连接超时
        Console.log("isConnected " + jschSftp.getClient().isConnected());
        Console.log("打印pwd: " + jschSftp.pwd());
        Console.log("cd / : " + jschSftp.cd("/"));

        Console.log("调用reconnectIfTimeout方法，判断是否超时并重连");
        jschSftp.reconnectIfTimeout();

        Console.log("打印pwd: " + jschSftp.pwd());

        IoKit.closeQuietly(jschSftp);
    }

}

package org.miaixz.bus.extra.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送测试
 */
public class MailTest {

    @Test
    @Disabled
    public void sendWithFileTest() {
        MailKit.send("bus@foxmail.com", "测试", "<h1>邮件来自测试</h1>", true, FileKit.file("/test/extra/mail/测试附件文本.txt"));
    }

    @Test
    @Disabled
    public void sendWithLongNameFileTest() {
        //附件名长度大于60时的测试
        MailKit.send("bus@foxmail.com", "测试", "<h1>邮件来自测试</h1>", true, FileKit.file("/test/extra/mail/6-LongLong一阶段平台建设周报2018.3.12-3.16.xlsx"));
    }

    @Test
    @Disabled
    public void sendWithImageTest() {
        final Map<String, InputStream> map = new HashMap<>();
        map.put("testImage", FileKit.getInputStream("f:/test/me.png"));
        MailKit.sendHtml("bus@foxmail.com", "测试", "<h1>邮件来自测试</h1><img src=\"cid:testImage\" />", map);
    }

    @Test
    @Disabled
    public void sendHtmlTest() {
        MailKit.send("bus@foxmail.com", "测试", "<h1>邮件来自测试</h1>", true);
    }

    @Test
    @Disabled
    public void sendByAccountTest() {
        final MailAccount account = new MailAccount();
        account.setHost("smtp.yeah.net");
        account.setPort(465);
        account.setSslEnable(true);
        account.setFrom("bus@yeah.net");
        account.setUser("bus");
        account.setPass("q1w2e3".toCharArray());
        MailKit.send(account, "bus@foxmail.com", "测试", "<h1>邮件来自测试</h1>", true);
    }

    @Test
    public void mailAccountTest() {
        final MailAccount account = new MailAccount();
        account.setFrom("bus@yeah.net");
        account.setDebug(true);
        account.defaultIfEmpty();
        final Properties props = account.getSmtpProps();
        Assertions.assertEquals("true", props.getProperty("mail.debug"));
    }

    @Test
    @Disabled
    public void sendHtmlWithPicsTest() {
        HashMap<String, InputStream> map = new HashMap<>();
        map.put("abc", FileKit.getInputStream("D:/test/abc.png"));
        map.put("abcd", FileKit.getInputStream("D:/test/def.png"));

        MailKit.sendHtml("bus@foxmail.com", "测试", "<h1>邮件来自测试</h1><img src=\"cid:abc\"/><img src=\"cid:abcd\"/>",
                map);
    }

}

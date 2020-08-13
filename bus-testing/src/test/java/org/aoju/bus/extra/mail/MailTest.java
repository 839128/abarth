package org.aoju.bus.extra.mail;

import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

/**
 * 邮件发送测试
 */
public class MailTest {

    @Test
    public void parseSettingTest() {
        MailAccount account = GlobalMailAccount.INSTANCE.getAccount();
        account.getSmtpProps();

        Assert.assertNotNull(account.getCharset());
        Assert.assertTrue(account.isSslEnable());
    }

    @Test
    @Ignore
    public void sendWithFileTest() {
        MailKit.send("bus@aoju.org", "测试", "<h1>邮件来自测试</h1>", true, FileKit.file("/测试附件文本.txt"));
    }

    @Test
    @Ignore
    public void sendWithLongNameFileTest() {
        //附件名长度大于60时的测试
        MailKit.send("bus@aoju.org", "测试", "<h1>邮件来自测试</h1>", true, FileKit.file("/周报.xlsx"));
    }

    @Test
    @Ignore
    public void sendHtmlTest() {
        MailKit.send("bus@aoju.org", "测试", "<h1>邮件来自测试</h1>", true);
    }

    @Test
    @Ignore
    public void sendByAccountTest() {
        MailAccount account = new MailAccount();
        account.setHost("smtp.aoju.org");
        account.setPort(465);
        account.setSslEnable(true);
        account.setFrom("bus@aoju.org");
        account.setUser("bus");
        account.setPass("123213");
        MailKit.send(account, "12345@qq.com", "测试", "<h1>邮件来自测试</h1>", true);
    }

    @Test
    public void mailAccountTest() {
        MailAccount account = new MailAccount();
        account.setFrom("bus@aoju.org");
        account.setDebug(true);
        account.defaultIfEmpty();
        Properties props = account.getSmtpProps();
        Assert.assertEquals("true", props.getProperty("mail.debug"));
    }

}

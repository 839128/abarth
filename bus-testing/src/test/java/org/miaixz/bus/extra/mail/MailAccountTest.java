package org.miaixz.bus.extra.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 默认邮件帐户设置测试
 */
public class MailAccountTest {

    @Test
    public void parseSettingTest() {
        final MailAccount account = GlobalMailAccount.INSTANCE.getAccount();
        account.getSmtpProps();

        Assertions.assertNotNull(account.getPass());
        Assertions.assertNotNull(account.getCharset());
        Assertions.assertTrue(account.isSslEnable());
    }

    /**
     * 测试案例：使用QQ邮箱、AOL邮箱，如果不改SocketFactory实例，会报错（unable to find valid certification path to requested target），
     * mail中仅提供了'mail.smtp.socketFactory.class'属性，但是没提供'mail.smtp.ssl.socketFactory'属性
     * 参见 com.sun.mail.util.SocketFetcher.getSocket(java.lang.String, int, java.util.Properties, java.lang.String, boolean)
     * 已经测试通过
     */
    @Test
    @Disabled
    public void customPropertyTest() {
        final MailAccount mailAccount = new MailAccount();
        mailAccount.setFrom("xxx@xxx.com");
        mailAccount.setPass("xxxxxx".toCharArray());

        mailAccount.setHost("smtp.aol.com");

        mailAccount.setAuth(true);
        mailAccount.setSslEnable(true);

        final Mail mail = Mail.of(mailAccount)
                .setTos("xx@xx.com")
                .setTitle("邮箱验证")
                .setContent("您的验证码是：<h3>2333</h3>")
                .setHtml(true);

        mail.send();
    }

}

package org.miaixz.bus.extra.captcha;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.extra.captcha.provider.CircleProvider;
import org.miaixz.bus.extra.captcha.provider.GifProvider;
import org.miaixz.bus.extra.captcha.provider.LineProvider;
import org.miaixz.bus.extra.captcha.provider.ShearProvider;
import org.miaixz.bus.extra.captcha.strategy.MathStrategy;

import java.awt.*;

/**
 * 直线干扰验证码单元测试
 */
public class CaptchaBuilderTest {

    @Test
    @Disabled
    public void createTest() {
        for (int i = 0; i < 1; i++) {
            CaptchaBuilder.ofShear(320, 240);
        }
    }

    @Test
    public void lineCaptchaTest1() {
        // 定义图形验证码的长和宽
        final LineProvider lineCaptcha = CaptchaBuilder.ofLine(200, 100);
        Assertions.assertNotNull(lineCaptcha.get());
        Assertions.assertTrue(lineCaptcha.verify(lineCaptcha.get()));
    }

    @Test
    @Disabled
    public void lineCaptchaTest3() {
        // 定义图形验证码的长和宽
        final LineProvider lineCaptcha = CaptchaBuilder.ofLine(200, 70, 4, 15);
        lineCaptcha.setBackground(Color.yellow);
        lineCaptcha.write("/test/extra/captcha/tellow.png");
    }

    @Test
    @Disabled
    public void noBackgroundCaptchaTest() {
        // 定义图形验证码的长和宽
        final LineProvider lineCaptcha = CaptchaBuilder.ofLine(200, 70, 4, 15);
        lineCaptcha.setBackground(null);
        lineCaptcha.write("/test/extra/captcha/tellow.png");
    }

    @Test
    @Disabled
    public void lineCaptchaWithMathTest() {
        // 定义图形验证码的长和宽
        final LineProvider lineCaptcha = CaptchaBuilder.ofLine(200, 80);
        lineCaptcha.setGenerator(new MathStrategy());
        lineCaptcha.setTextAlpha(0.8f);
        lineCaptcha.write("/test/extra/captcha/math.png");
    }

    @Test
    @Disabled
    public void lineCaptchaTest2() {

        // 定义图形验证码的长和宽
        final LineProvider lineCaptcha = CaptchaBuilder.ofLine(200, 100);
        // LineProvider lineCaptcha = new LineProvider(200, 100, 4, 150);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("/test/extra/captcha/line.png");
        Console.log(lineCaptcha.get());
        // 验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

        lineCaptcha.create();
        lineCaptcha.write("/test/extra/captcha/line2.png");
        Console.log(lineCaptcha.get());
        // 验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");
    }

    @Test
    @Disabled
    public void circleCaptchaTest() {

        // 定义图形验证码的长和宽
        final CircleProvider captcha = CaptchaBuilder.ofCircle(200, 100, 4, 20);
        // CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/test/extra/captcha/circle.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Disabled
    public void shearCaptchaTest() {

        // 定义图形验证码的长和宽
        final ShearProvider captcha = CaptchaBuilder.ofShear(203, 100, 4, 4);
        // ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/test/extra/captcha/shear.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Disabled
    public void shearCaptchaTest2() {

        // 定义图形验证码的长和宽
        final ShearProvider captcha = new ShearProvider(200, 100, 4, 4);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/test/extra/captcha/shear.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Disabled
    public void ShearCaptchaWithMathTest() {
        // 定义图形验证码的长和宽
        final ShearProvider captcha = CaptchaBuilder.ofShear(200, 45, 4, 4);
        captcha.setGenerator(new MathStrategy());
        // ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/test/extra/captcha/shear_math.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Disabled
    public void GifCaptchaTest() {
        final GifProvider captcha = CaptchaBuilder.ofGif(200, 100, 4);
        captcha.write("/test/extra/captcha/gif_captcha.gif");
        assert captcha.verify(captcha.get());
    }

    @Test
    @Disabled
    public void bgTest() {
        final LineProvider captcha = CaptchaBuilder.ofLine(200, 100, 4, 1);
        captcha.setBackground(Color.WHITE);
        captcha.write("/test/extra/captcha/test.jpg");
    }

}

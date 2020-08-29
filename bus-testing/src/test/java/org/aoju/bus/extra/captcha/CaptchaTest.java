package org.aoju.bus.extra.captcha;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.extra.captcha.provider.CircleProvider;
import org.aoju.bus.extra.captcha.provider.LineProvider;
import org.aoju.bus.extra.captcha.provider.ShearProvider;
import org.aoju.bus.extra.captcha.strategy.MathStrategy;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;

/**
 * 直线干扰验证码单元测试
 */
public class CaptchaTest {

    @Test
    public void lineCaptchaTest1() {
        // 定义图形验证码的长和宽
        LineProvider lineCaptcha = CaptchaBuilder.createLineCaptcha(200, 100);
        Assert.assertNotNull(lineCaptcha.get());
        Assert.assertTrue(lineCaptcha.verify(lineCaptcha.get()));
    }

    @Test
    @Ignore
    public void lineCaptchaTest3() {
        // 定义图形验证码的长和宽
        LineProvider lineCaptcha = CaptchaBuilder.createLineCaptcha(200, 70, 4, 15);
        lineCaptcha.setBackground(Color.yellow);
        lineCaptcha.write("/captcha/tellow.png");
    }

    @Test
    @Ignore
    public void lineCaptchaWithMathTest() {
        // 定义图形验证码的长和宽
        LineProvider lineCaptcha = CaptchaBuilder.createLineCaptcha(200, 80);
        lineCaptcha.setGenerator(new MathStrategy());
        lineCaptcha.setTextAlpha(0.8f);
        lineCaptcha.write("/captcha/math.png");
    }

    @Test
    @Ignore
    public void lineCaptchaTest2() {

        // 定义图形验证码的长和宽
        LineProvider lineCaptcha = CaptchaBuilder.createLineCaptcha(200, 100);
        // LineCaptcha lineCaptcha = new LineCaptcha(200, 100, 4, 150);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("/captcha/line.png");
        Console.log(lineCaptcha.get());
        // 验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");

        lineCaptcha.create();
        lineCaptcha.write("/captcha/line2.png");
        Console.log(lineCaptcha.get());
        // 验证图形验证码的有效性，返回boolean值
        lineCaptcha.verify("1234");
    }

    @Test
    @Ignore
    public void circleCaptchaTest() {

        // 定义图形验证码的长和宽
        CircleProvider captcha = CaptchaBuilder.createCircleCaptcha(200, 100, 4, 20);
        // CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/captcha/circle.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Ignore
    public void ShearCaptchaTest() {

        // 定义图形验证码的长和宽
        ShearProvider captcha = CaptchaBuilder.createShearCaptcha(203, 100, 4, 4);
        // ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/captcha/shear.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Ignore
    public void ShearCaptchaWithMathTest() {
        // 定义图形验证码的长和宽
        ShearProvider captcha = CaptchaBuilder.createShearCaptcha(200, 45, 4, 4);
        captcha.setGenerator(new MathStrategy());
        // ShearCaptcha captcha = new ShearCaptcha(200, 100, 4, 4);
        // 图形验证码写出，可以写出到文件，也可以写出到流
        captcha.write("/captcha/shear.png");
        // 验证图形验证码的有效性，返回boolean值
        captcha.verify("1234");
    }

    @Test
    @Ignore
    public void createTest() {
        for (int i = 0; i < 1; i++) {
            CaptchaBuilder.createShearCaptcha(320, 240);
        }
    }

}

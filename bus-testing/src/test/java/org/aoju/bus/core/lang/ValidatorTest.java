package org.aoju.bus.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 验证器单元测试
 */
public class ValidatorTest {

    @Test
    public void isNumberTest() {
        Assertions.assertTrue(Validator.isNumber("45345365465"));
        Assertions.assertTrue(Validator.isNumber("0004545435"));
        Assertions.assertTrue(Validator.isNumber("5.222"));
        Assertions.assertTrue(Validator.isNumber("0.33323"));
    }

    @Test
    public void isLetterTest() {
        Assertions.assertTrue(Validator.isLetter("asfdsdsfds"));
        Assertions.assertTrue(Validator.isLetter("asfdsdfdsfVCDFDFGdsfds"));
        Assertions.assertTrue(Validator.isLetter("asfdsdf你好dsfVCDFDFGdsfds"));
    }

    @Test
    public void isUperCaseTest() {
        Assertions.assertTrue(Validator.isUpperCase("VCDFDFG"));
        Assertions.assertTrue(Validator.isUpperCase("ASSFD"));

        Assertions.assertFalse(Validator.isUpperCase("asfdsdsfds"));
        Assertions.assertFalse(Validator.isUpperCase("ASSFD你好"));
    }

    @Test
    public void isLowerCaseTest() {
        Assertions.assertTrue(Validator.isLowerCase("asfdsdsfds"));

        Assertions.assertFalse(Validator.isLowerCase("aaaa你好"));
        Assertions.assertFalse(Validator.isLowerCase("VCDFDFG"));
        Assertions.assertFalse(Validator.isLowerCase("ASSFD"));
        Assertions.assertFalse(Validator.isLowerCase("ASSFD你好"));
    }

    @Test
    public void isBirthdayTest() {
        boolean b = Validator.isBirthday("20150101");
        Assertions.assertTrue(b);
        boolean b2 = Validator.isBirthday("2015-01-01");
        Assertions.assertTrue(b2);
        boolean b3 = Validator.isBirthday("2015.01.01");
        Assertions.assertTrue(b3);
        boolean b4 = Validator.isBirthday("2015年01月01日");
        Assertions.assertTrue(b4);
        boolean b5 = Validator.isBirthday("2015.01.01");
        Assertions.assertTrue(b5);
        boolean b6 = Validator.isBirthday("2018-08-15");
        Assertions.assertTrue(b6);

        //验证年非法
        Assertions.assertFalse(Validator.isBirthday("2095.05.01"));
        //验证月非法
        Assertions.assertFalse(Validator.isBirthday("2015.13.01"));
        //验证日非法
        Assertions.assertFalse(Validator.isBirthday("2015.02.29"));
    }

    @Test
    public void isCitizenIdTest() {
        boolean b = Validator.isCitizenId("150218199012123389");
        Assertions.assertTrue(b);
    }

    @Test
    public void validateTest() {
        Validator.validateChinese("我是一段中文", "内容中包含非中文");
    }

    @Test
    public void isEmailTest() {
        boolean email = Validator.isEmail("abc_cde@163.com");
        Assertions.assertTrue(email);
        boolean email1 = Validator.isEmail("abc_%cde@163.com");
        Assertions.assertTrue(email1);
        boolean email2 = Validator.isEmail("abc_%cde@aaa.c");
        Assertions.assertTrue(email2);
        boolean email3 = Validator.isEmail("xiaolei.lu@aaa.b");
        Assertions.assertTrue(email3);
        boolean email4 = Validator.isEmail("xiaolei.Lu@aaa.b");
        Assertions.assertTrue(email4);
    }

    @Test
    public void isMobileTest() {
        boolean m1 = Validator.isMobile("13900221432");
        Assertions.assertTrue(m1);
        boolean m2 = Validator.isMobile("015100221432");
        Assertions.assertTrue(m2);
        boolean m3 = Validator.isMobile("+8618600221432");
        Assertions.assertTrue(m3);
    }

    @Test
    public void isMatchTest() {
        String url = "http://aaa-bbb.somthing.com/a.php?a=b&c=2";
        Assertions.assertTrue(Validator.isMatchRegex(RegEx.URL_HTTP, url));

        url = "https://aaa-bbb.somthing.com/a.php?a=b&c=2";
        Assertions.assertTrue(Validator.isMatchRegex(RegEx.URL_HTTP, url));

        url = "https://aaa-bbb.somthing.com:8080/a.php?a=b&c=2";
        Assertions.assertTrue(Validator.isMatchRegex(RegEx.URL_HTTP, url));
    }

    @Test
    public void isGeneralTest() {
        String str = "";
        boolean general = Validator.isGeneral(str, -1, 5);
        Assertions.assertTrue(general);

        str = "123_abc_ccc";
        general = Validator.isGeneral(str, -1, 100);
        Assertions.assertTrue(general);

        // 不允许中文
        str = "123_abc_ccc中文";
        general = Validator.isGeneral(str, -1, 100);
        Assertions.assertFalse(general);
    }

    @Test
    public void isPasswordTest() {
        Assertions.assertFalse(Validator.isPassword("12345678d"));
        Assertions.assertTrue(Validator.isPassword("12345678d", true));
        Assertions.assertTrue(Validator.isPassword("12345678d#"));
    }

}

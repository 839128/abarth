package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * {@link PhoneKit} 单元测试类
 */
public class PhoneKitTest {

    @Test
    public void testCheck() {
        final String mobile = "13612345678";
        final String tel = "010-88993108";
        final String errMobile = "136123456781";
        final String errTel = "010-889931081";

        Assertions.assertTrue(PhoneKit.isMobile(mobile));
        Assertions.assertTrue(PhoneKit.isTel(tel));
        Assertions.assertTrue(PhoneKit.isPhone(mobile));
        Assertions.assertTrue(PhoneKit.isPhone(tel));

        Assertions.assertFalse(PhoneKit.isMobile(errMobile));
        Assertions.assertFalse(PhoneKit.isTel(errTel));
        Assertions.assertFalse(PhoneKit.isPhone(errMobile));
        Assertions.assertFalse(PhoneKit.isPhone(errTel));
    }

    @Test
    public void testTel() {
        final ArrayList<String> tels = new ArrayList<>();
        tels.add("010-12345678");
        tels.add("020-9999999");
        tels.add("0755-7654321");
        final ArrayList<String> errTels = new ArrayList<>();
        errTels.add("010 12345678");
        errTels.add("A20-9999999");
        errTels.add("0755-7654.321");
        errTels.add("13619887123");
        for (final String s : tels) {
            Assertions.assertTrue(PhoneKit.isTel(s));
        }
        for (final String s : errTels) {
            Assertions.assertFalse(PhoneKit.isTel(s));
        }
    }

    @Test
    public void testHide() {
        final String mobile = "13612345678";

        Assertions.assertEquals("*******5678", PhoneKit.hideBefore(mobile));
        Assertions.assertEquals("136****5678", PhoneKit.hideBetween(mobile));
        Assertions.assertEquals("1361234****", PhoneKit.hideAfter(mobile));
    }

    @Test
    public void testSubString() {
        final String mobile = "13612345678";
        Assertions.assertEquals("136", PhoneKit.subBefore(mobile));
        Assertions.assertEquals("1234", PhoneKit.subBetween(mobile));
        Assertions.assertEquals("5678", PhoneKit.subAfter(mobile));
    }

    @Test
    public void testNewTel() {
        final ArrayList<String> tels = new ArrayList<>();
        tels.add("010-12345678");
        tels.add("01012345678");
        tels.add("020-9999999");
        tels.add("0209999999");
        tels.add("0755-7654321");
        tels.add("07557654321");
        final ArrayList<String> errTels = new ArrayList<>();
        errTels.add("010 12345678");
        errTels.add("A20-9999999");
        errTels.add("0755-7654.321");
        errTels.add("13619887123");
        for (final String s : tels) {
            Assertions.assertTrue(PhoneKit.isTel(s));
        }
        for (final String s : errTels) {
            Assertions.assertFalse(PhoneKit.isTel(s));
        }
        Assertions.assertEquals("010", PhoneKit.subTelBefore("010-12345678"));
        Assertions.assertEquals("010", PhoneKit.subTelBefore("01012345678"));
        Assertions.assertEquals("12345678", PhoneKit.subTelAfter("010-12345678"));
        Assertions.assertEquals("12345678", PhoneKit.subTelAfter("01012345678"));

        Assertions.assertEquals("0755", PhoneKit.subTelBefore("0755-7654321"));
        Assertions.assertEquals("0755", PhoneKit.subTelBefore("07557654321"));
        Assertions.assertEquals("7654321", PhoneKit.subTelAfter("0755-7654321"));
        Assertions.assertEquals("7654321", PhoneKit.subTelAfter("07557654321"));
    }

    @Test
    public void isTel400800Test() {
        boolean tel400800 = PhoneKit.isTel400800("400-860-8608");//800-830-3811
        Assertions.assertTrue(tel400800);

        tel400800 = PhoneKit.isTel400800("400-8608608");//800-830-3811
        Assertions.assertTrue(tel400800);
    }
}

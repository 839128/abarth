package org.miaixz.bus.core.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.EnumMap;

/**
 * 脱敏工具类
 */
public class MaskingTest {

    @Test
    public void maskingTest() {
        Assertions.assertEquals("", Masking.masking("100", EnumMap.Masking.CLEAR_TO_EMPTY));
        Assertions.assertNull(Masking.masking("100", EnumMap.Masking.CLEAR_TO_NULL));

        Assertions.assertEquals("0", Masking.masking("100", EnumMap.Masking.USER_ID));
        Assertions.assertEquals("段**", Masking.masking("段正淳", EnumMap.Masking.CHINESE_NAME));
        Assertions.assertEquals("5***************1X", Masking.masking("51343620000320711X", EnumMap.Masking.ID_CARD));
        Assertions.assertEquals("0915*****79", Masking.masking("09157518479", EnumMap.Masking.FIXED_PHONE));
        Assertions.assertEquals("180****1999", Masking.masking("18049531999", EnumMap.Masking.MOBILE_PHONE));
        Assertions.assertEquals("北京市海淀区马********", Masking.masking("北京市海淀区马连洼街道289号", EnumMap.Masking.ADDRESS));
        Assertions.assertEquals("d*************@gmail.com.cn", Masking.masking("duandazhi-jack@gmail.com.cn", EnumMap.Masking.EMAIL));
        Assertions.assertEquals("**********", Masking.masking("1234567890", EnumMap.Masking.PASSWORD));

        Assertions.assertEquals("0", Masking.masking("100", EnumMap.Masking.USER_ID));
        Assertions.assertEquals("段**", Masking.masking("段正淳", EnumMap.Masking.CHINESE_NAME));
        Assertions.assertEquals("5***************1X", Masking.masking("51343620000320711X", EnumMap.Masking.ID_CARD));
        Assertions.assertEquals("0915*****79", Masking.masking("09157518479", EnumMap.Masking.FIXED_PHONE));
        Assertions.assertEquals("180****1999", Masking.masking("18049531999", EnumMap.Masking.MOBILE_PHONE));
        Assertions.assertEquals("北京市海淀区马********", Masking.masking("北京市海淀区马连洼街道289号", EnumMap.Masking.ADDRESS));
        Assertions.assertEquals("d*************@gmail.com.cn", Masking.masking("duandazhi-jack@gmail.com.cn", EnumMap.Masking.EMAIL));
        Assertions.assertEquals("**********", Masking.masking("1234567890", EnumMap.Masking.PASSWORD));
        Assertions.assertEquals("1101 **** **** **** 3256", Masking.masking("11011111222233333256", EnumMap.Masking.BANK_CARD));
        Assertions.assertEquals("6227 **** **** **** 123", Masking.masking("6227880100100105123", EnumMap.Masking.BANK_CARD));
        Assertions.assertEquals("192.*.*.*", Masking.masking("192.168.1.1", EnumMap.Masking.IPV4));
        Assertions.assertEquals("2001:*:*:*:*:*:*:*", Masking.masking("2001:0db8:86a3:08d3:1319:8a2e:0370:7344", EnumMap.Masking.IPV6));
    }

    @Test
    public void userIdTest() {
        Assertions.assertEquals(Long.valueOf(0L), Masking.userId());
    }

    @Test
    public void chineseNameTest() {
        Assertions.assertEquals("段**", Masking.chineseName("段正淳"));
    }

    @Test
    public void idCardNumTest() {
        Assertions.assertEquals("5***************1X", Masking.idCardNum("51343620000320711X", 1, 2));
    }

    @Test
    public void fixedPhoneTest() {
        Assertions.assertEquals("0915*****79", Masking.fixedPhone("09157518479"));
    }

    @Test
    public void mobilePhoneTest() {
        Assertions.assertEquals("180****1999", Masking.mobilePhone("18049531999"));
    }

    @Test
    public void addressTest() {
        Assertions.assertEquals("北京市海淀区马连洼街*****", Masking.address("北京市海淀区马连洼街道289号", 5));
        Assertions.assertEquals("***************", Masking.address("北京市海淀区马连洼街道289号", 50));
        Assertions.assertEquals("北京市海淀区马连洼街道289号", Masking.address("北京市海淀区马连洼街道289号", 0));
        Assertions.assertEquals("北京市海淀区马连洼街道289号", Masking.address("北京市海淀区马连洼街道289号", -1));
    }

    @Test
    public void emailTest() {
        Assertions.assertEquals("d********@126.com", Masking.email("duandazhi@126.com"));
        Assertions.assertEquals("d********@gmail.com.cn", Masking.email("duandazhi@gmail.com.cn"));
        Assertions.assertEquals("d*************@gmail.com.cn", Masking.email("duandazhi-jack@gmail.com.cn"));
    }

    @Test
    public void passwordTest() {
        Assertions.assertEquals("**********", Masking.password("1234567890"));
    }

    @Test
    public void carLicenseTest() {
        Assertions.assertEquals("", Masking.carLicense(null));
        Assertions.assertEquals("", Masking.carLicense(""));
        Assertions.assertEquals("苏D4***0", Masking.carLicense("苏D40000"));
        Assertions.assertEquals("陕A1****D", Masking.carLicense("陕A12345D"));
        Assertions.assertEquals("京A123", Masking.carLicense("京A123"));
    }

    @Test
    public void bankCardTest() {
        Assertions.assertNull(Masking.bankCard(null));
        Assertions.assertEquals("", Masking.bankCard(""));
        Assertions.assertEquals("1234 **** **** **** **** 9", Masking.bankCard("1234 2222 3333 4444 6789 9"));
        Assertions.assertEquals("1234 **** **** **** **** 91", Masking.bankCard("1234 2222 3333 4444 6789 91"));
        Assertions.assertEquals("1234 **** **** **** 6789", Masking.bankCard("1234 2222 3333 4444 6789"));
        Assertions.assertEquals("1234 **** **** **** 678", Masking.bankCard("1234 2222 3333 4444 678"));
    }

}

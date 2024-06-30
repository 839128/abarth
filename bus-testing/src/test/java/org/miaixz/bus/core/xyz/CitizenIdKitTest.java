package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.DateTime;

/**
 * 身份证单元测试
 */
public class CitizenIdKitTest {

    private static final String ID_18 = "321083197812162119";
    private static final String ID_15 = "150102880730303";

    @Test
    public void isValidCardTest() {
        final boolean valid = CitizenIdKit.isValidCard(ID_18);
        Assertions.assertTrue(valid);

        final boolean valid15 = CitizenIdKit.isValidCard(ID_15);
        Assertions.assertTrue(valid15);

        // 无效
        String idCard = "360198910283844";
        Assertions.assertFalse(CitizenIdKit.isValidCard(idCard));

        // 生日无效
        idCard = "201511221897205960";
        Assertions.assertFalse(CitizenIdKit.isValidCard(idCard));

        // 生日无效
        idCard = "815727834224151";
        Assertions.assertFalse(CitizenIdKit.isValidCard(idCard));
    }

    @Test
    public void convert15To18Test() {
        final String convert15To18 = CitizenIdKit.convert15To18(ID_15);
        Assertions.assertEquals("150102198807303035", convert15To18);

        final String convert15To18Second = CitizenIdKit.convert15To18("330102200403064");
        Assertions.assertEquals("33010219200403064X", convert15To18Second);
    }

    @Test
    public void convert18To15Test() {
        final String idcard15 = CitizenIdKit.convert18To15("150102198807303035");
        Assertions.assertEquals(ID_15, idcard15);
    }

    @Test
    public void getAgeTest() {
        final DateTime date = DateKit.parse("2017-04-10");

        final int age = CitizenIdKit.getAge(ID_18, date);
        Assertions.assertEquals(age, 38);

        final int age2 = CitizenIdKit.getAge(ID_15, date);
        Assertions.assertEquals(age2, 28);
    }

    @Test
    public void getBirthTest() {
        final String birth = CitizenIdKit.getBirth(ID_18);
        Assertions.assertEquals(birth, "19781216");

        final String birth2 = CitizenIdKit.getBirth(ID_15);
        Assertions.assertEquals(birth2, "19880730");
    }

    @Test
    public void getProvinceTest() {
        final String province = CitizenIdKit.getProvince(ID_18);
        Assertions.assertEquals(province, "江苏");

        final String province2 = CitizenIdKit.getProvince(ID_15);
        Assertions.assertEquals(province2, "内蒙古");
    }

    @Test
    public void getCityCodeTest() {
        final String code = CitizenIdKit.getCityCode(ID_18);
        Assertions.assertEquals("3210", code);
    }

    @Test
    public void getDistrictCodeTest() {
        final String code = CitizenIdKit.getDistrictCode(ID_18);
        Assertions.assertEquals("321083", code);
    }

    @Test
    public void getGenderTest() {
        final int gender = CitizenIdKit.getGender(ID_18);
        Assertions.assertEquals(1, gender);
    }

    @Test
    public void isValidCard18Test() {
        boolean isValidCard18 = CitizenIdKit.isValidCard18("3301022011022000D6");
        Assertions.assertFalse(isValidCard18);

        // 不忽略大小写情况下，X严格校验必须大写
        isValidCard18 = CitizenIdKit.isValidCard18("33010219200403064x", false);
        Assertions.assertFalse(isValidCard18);
        isValidCard18 = CitizenIdKit.isValidCard18("33010219200403064X", false);
        Assertions.assertTrue(isValidCard18);

        // 非严格校验下大小写皆可
        isValidCard18 = CitizenIdKit.isValidCard18("33010219200403064x");
        Assertions.assertTrue(isValidCard18);
        isValidCard18 = CitizenIdKit.isValidCard18("33010219200403064X");
        Assertions.assertTrue(isValidCard18);

        // 香港人在大陆身份证
        isValidCard18 = CitizenIdKit.isValidCard18("81000019980902013X");
        Assertions.assertTrue(isValidCard18);

        // 澳门人在大陆身份证
        isValidCard18 = CitizenIdKit.isValidCard18("820000200009100032");
        Assertions.assertTrue(isValidCard18);

        // 台湾人在大陆身份证
        isValidCard18 = CitizenIdKit.isValidCard18("830000200209060065");
        Assertions.assertTrue(isValidCard18);

        // 身份证允许调用为空null
        isValidCard18 = !CitizenIdKit.isValidCard18(null);
        Assertions.assertTrue(isValidCard18);
    }

    @Test
    public void isValidHKCardIdTest() {
        final String hkCard = "P174468(6)";
        final boolean flag = CitizenIdKit.isValidHKCard(hkCard);
        Assertions.assertTrue(flag);
    }

    @Test
    public void isValidTWCardIdTest() {
        final String twCard = "B221690311";
        boolean flag = CitizenIdKit.isValidTWCard(twCard);
        Assertions.assertTrue(flag);
        final String errTwCard1 = "M517086311";
        flag = CitizenIdKit.isValidTWCard(errTwCard1);
        Assertions.assertFalse(flag);
        final String errTwCard2 = "B2216903112";
        flag = CitizenIdKit.isValidTWCard(errTwCard2);
        Assertions.assertFalse(flag);
    }

    @Test
    void foreignTest() {
        // 新版外国人永久居留身份证号码
        final String FOREIGN_ID_18 = "932682198501010017";
        Assertions.assertTrue(CitizenIdKit.isValidCard(FOREIGN_ID_18));

        final DateTime date = DateKit.parse("2017-04-10");
        Assertions.assertEquals(CitizenIdKit.getAge(FOREIGN_ID_18, date), 32);

        // 新版外国人永久居留身份证
        Assertions.assertTrue(CitizenIdKit.isValidCard18("932682198501010017"));
    }

}

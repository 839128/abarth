package org.aoju.bus.core.utils;

import org.aoju.bus.core.date.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 身份证单元测试
 */
public class CitizenIdUtilsTest {

    private static final String ID_18 = "321083197812162119";
    private static final String ID_15 = "150102880730303";

    @Test
    public void isValidCardTest() {
        boolean valid = CitizenIdUtils.isValidCard(ID_18);
        Assertions.assertTrue(valid);

        boolean valid15 = CitizenIdUtils.isValidCard(ID_15);
        Assertions.assertTrue(valid15);

        String idCard = "360198910283844";
        Assertions.assertFalse(CitizenIdUtils.isValidCard(idCard));
    }

    @Test
    public void convert15To18Test() {
        String convert15To18 = CitizenIdUtils.convert15To18(ID_15);
        Assertions.assertEquals("150102198807303035", convert15To18);

        String convert15To18Second = CitizenIdUtils.convert15To18("330102200403064");
        Assertions.assertEquals("33010219200403064x", convert15To18Second);
    }

    @Test
    public void getAgeByIdCardTest() {
        DateTime date = DateUtils.parse("2017-04-10");

        int age = CitizenIdUtils.getAgeByIdCard(ID_18, date);
        Assertions.assertEquals(age, 38);

        int age2 = CitizenIdUtils.getAgeByIdCard(ID_15, date);
        Assertions.assertEquals(age2, 28);
    }

    @Test
    public void getBirthByIdCardTest() {
        String birth = CitizenIdUtils.getBirthByIdCard(ID_18);
        Assertions.assertEquals(birth, "19781216");

        String birth2 = CitizenIdUtils.getBirthByIdCard(ID_15);
        Assertions.assertEquals(birth2, "19880730");
    }

    @Test
    public void getProvinceByIdCardTest() {
        String province = CitizenIdUtils.getProvinceByIdCard(ID_18);
        Assertions.assertEquals(province, "江苏");

        String province2 = CitizenIdUtils.getProvinceByIdCard(ID_15);
        Assertions.assertEquals(province2, "内蒙古");
    }

    @Test
    public void getGenderByIdCardTest() {
        int gender = CitizenIdUtils.getGenderByIdCard(ID_18);
        Assertions.assertEquals(1, gender);
    }

    @Test
    public void isValidCard18Test() {
        final boolean isValidCard18 = CitizenIdUtils.isValidCard18("3301022011022000D6");
        Assertions.assertFalse(isValidCard18);
    }

}

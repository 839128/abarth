package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@link MathUtils} 单元测试类
 */
public class MathUtilsTest {

    @Test
    public void addTest() {
        Float a = 3.15f;
        Double b = 4.22;
        double result = MathUtils.add(a, b).doubleValue();
        Assertions.assertEquals(7.37, result, 2);
    }

    @Test
    public void addTest2() {
        double a = 3.15f;
        double b = 4.22;
        double result = MathUtils.add(a, b);
        Assertions.assertEquals(7.37, result, 2);
    }

    @Test
    public void addTest3() {
        float a = 3.15f;
        double b = 4.22;
        double result = MathUtils.add(a, b, a, b).doubleValue();
        Assertions.assertEquals(14.74, result, 2);
    }

    @Test
    public void addTest4() {
        BigDecimal result = MathUtils.add(new BigDecimal("133"), new BigDecimal("331"));
        Assertions.assertEquals(new BigDecimal("464"), result);
    }

    @Test
    public void isIntegerTest() {
        Assertions.assertTrue(MathUtils.isInteger("-12"));
        Assertions.assertTrue(MathUtils.isInteger("256"));
        Assertions.assertTrue(MathUtils.isInteger("0256"));
        Assertions.assertTrue(MathUtils.isInteger("0"));
        Assertions.assertFalse(MathUtils.isInteger("23.4"));
    }

    @Test
    public void isLongTest() {
        Assertions.assertTrue(MathUtils.isLong("-12"));
        Assertions.assertTrue(MathUtils.isLong("256"));
        Assertions.assertTrue(MathUtils.isLong("0256"));
        Assertions.assertTrue(MathUtils.isLong("0"));
        Assertions.assertFalse(MathUtils.isLong("23.4"));
    }

    @Test
    public void isNumberTest() {
        Assertions.assertTrue(MathUtils.isNumber("28.55"));
        Assertions.assertTrue(MathUtils.isNumber("0"));
        Assertions.assertTrue(MathUtils.isNumber("+100.10"));
        Assertions.assertTrue(MathUtils.isNumber("-22.022"));
        Assertions.assertTrue(MathUtils.isNumber("0X22"));
    }

    @Test
    public void divTest() {
        double result = MathUtils.div(0, 1);
        Assertions.assertEquals(0.0, result, 0);
    }

    @Test
    public void roundTest() {

        // 四舍
        String round1 = MathUtils.roundStr(2.674, 2);
        String round2 = MathUtils.roundStr("2.674", 2);
        Assertions.assertEquals("2.67", round1);
        Assertions.assertEquals("2.67", round2);

        // 五入
        String round3 = MathUtils.roundStr(2.675, 2);
        String round4 = MathUtils.roundStr("2.675", 2);
        Assertions.assertEquals("2.68", round3);
        Assertions.assertEquals("2.68", round4);

        // 四舍六入五成双
        String round31 = MathUtils.roundStr(4.245, 2, RoundingMode.HALF_EVEN);
        String round41 = MathUtils.roundStr("4.2451", 2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals("4.24", round31);
        Assertions.assertEquals("4.25", round41);

        // 补0
        String round5 = MathUtils.roundStr(2.6005, 2);
        String round6 = MathUtils.roundStr("2.6005", 2);
        Assertions.assertEquals("2.60", round5);
        Assertions.assertEquals("2.60", round6);

        // 补0
        String round7 = MathUtils.roundStr(2.600, 2);
        String round8 = MathUtils.roundStr("2.600", 2);
        Assertions.assertEquals("2.60", round7);
        Assertions.assertEquals("2.60", round8);
    }

    @Test
    public void roundStrTest() {
        String roundStr = MathUtils.roundStr(2.647, 2);
        Assertions.assertEquals(roundStr, "2.65");
    }

    @Test
    public void roundHalfEvenTest() {
        String roundStr = MathUtils.roundHalfEven(4.245, 2).toString();
        Assertions.assertEquals(roundStr, "4.24");
        roundStr = MathUtils.roundHalfEven(4.2450, 2).toString();
        Assertions.assertEquals(roundStr, "4.24");
        roundStr = MathUtils.roundHalfEven(4.2451, 2).toString();
        Assertions.assertEquals(roundStr, "4.25");
        roundStr = MathUtils.roundHalfEven(4.2250, 2).toString();
        Assertions.assertEquals(roundStr, "4.22");

        roundStr = MathUtils.roundHalfEven(1.2050, 2).toString();
        Assertions.assertEquals(roundStr, "1.20");
        roundStr = MathUtils.roundHalfEven(1.2150, 2).toString();
        Assertions.assertEquals(roundStr, "1.22");
        roundStr = MathUtils.roundHalfEven(1.2250, 2).toString();
        Assertions.assertEquals(roundStr, "1.22");
        roundStr = MathUtils.roundHalfEven(1.2350, 2).toString();
        Assertions.assertEquals(roundStr, "1.24");
        roundStr = MathUtils.roundHalfEven(1.2450, 2).toString();
        Assertions.assertEquals(roundStr, "1.24");
        roundStr = MathUtils.roundHalfEven(1.2550, 2).toString();
        Assertions.assertEquals(roundStr, "1.26");
        roundStr = MathUtils.roundHalfEven(1.2650, 2).toString();
        Assertions.assertEquals(roundStr, "1.26");
        roundStr = MathUtils.roundHalfEven(1.2750, 2).toString();
        Assertions.assertEquals(roundStr, "1.28");
        roundStr = MathUtils.roundHalfEven(1.2850, 2).toString();
        Assertions.assertEquals(roundStr, "1.28");
        roundStr = MathUtils.roundHalfEven(1.2950, 2).toString();
        Assertions.assertEquals(roundStr, "1.30");
    }

    @Test
    public void decimalFormatTest() {
        long c = 299792458;// 光速

        String format = MathUtils.decimalFormat(",###", c);
        Assertions.assertEquals("299,792,458", format);
    }

    @Test
    public void decimalFormatMoneyTest() {
        double c = 299792400.543534534;

        String format = MathUtils.decimalFormatMoney(c);
        Assertions.assertEquals("299,792,400.54", format);

        double value = 0.5;
        String money = MathUtils.decimalFormatMoney(value);
        Assertions.assertEquals("0.50", money);
    }

    @Test
    public void equalsTest() {
        Assertions.assertTrue(MathUtils.equals(new BigDecimal("0.00"), BigDecimal.ZERO));
    }

    @Test
    public void formatPercentTest() {
        String str = MathUtils.formatPercent(0.33543545, 2);
        Assertions.assertEquals("33.54%", str);
    }

    @Test
    public void toBigDecimalTest() {
        double a = 3.14;

        BigDecimal bigDecimal = MathUtils.toBigDecimal(a);
        Assertions.assertEquals("3.14", bigDecimal.toString());
    }

    @Test
    public void maxTest() {
        int max = MathUtils.max(5, 4, 3, 6, 1);
        Assertions.assertEquals(6, max);
    }

    @Test
    public void minTest() {
        int min = MathUtils.min(5, 4, 3, 6, 1);
        Assertions.assertEquals(1, min);
    }

    @Test
    public void parseIntTest() {
        int v1 = MathUtils.parseInt("0xFF");
        Assertions.assertEquals(255, v1);
        int v2 = MathUtils.parseInt("010");
        Assertions.assertEquals(10, v2);
        int v3 = MathUtils.parseInt("10");
        Assertions.assertEquals(10, v3);
        int v4 = MathUtils.parseInt("   ");
        Assertions.assertEquals(0, v4);
        int v5 = MathUtils.parseInt("10F");
        Assertions.assertEquals(10, v5);
        int v6 = MathUtils.parseInt("22.4D");
        Assertions.assertEquals(22, v6);

        int v7 = MathUtils.parseInt("0");
        Assertions.assertEquals(0, v7);
    }

    @Test
    public void parseLongTest() {
        long v1 = MathUtils.parseLong("0xFF");
        Assertions.assertEquals(255L, v1);
        long v2 = MathUtils.parseLong("010");
        Assertions.assertEquals(10L, v2);
        long v3 = MathUtils.parseLong("10");
        Assertions.assertEquals(10L, v3);
        long v4 = MathUtils.parseLong("   ");
        Assertions.assertEquals(0L, v4);
        long v5 = MathUtils.parseLong("10F");
        Assertions.assertEquals(10L, v5);
        long v6 = MathUtils.parseLong("22.4D");
        Assertions.assertEquals(22L, v6);
    }

    @Test
    public void factorialTest() {
        long factorial = MathUtils.factorial(0);
        Assertions.assertEquals(1, factorial);

        factorial = MathUtils.factorial(5, 0);
        Assertions.assertEquals(120, factorial);
        factorial = MathUtils.factorial(5, 1);
        Assertions.assertEquals(120, factorial);
    }

    @Test
    public void mulTest() {
        final BigDecimal mul = MathUtils.mul(new BigDecimal("10"), null);
        Assertions.assertEquals(BigDecimal.ZERO, mul);
    }

}

package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@link MathKit} 单元测试类
 */
public class MathKitTest {

    @Test
    public void addTest() {
        Float a = 3.15f;
        Double b = 4.22;
        double result = MathKit.add(a, b).doubleValue();
        Assertions.assertEquals(7.37, result, 2);
    }

    @Test
    public void addTest2() {
        double a = 3.15f;
        double b = 4.22;
        double result = MathKit.add(a, b);
        Assertions.assertEquals(7.37, result, 2);
    }

    @Test
    public void addTest3() {
        float a = 3.15f;
        double b = 4.22;
        double result = MathKit.add(a, b, a, b).doubleValue();
        Assertions.assertEquals(14.74, result, 2);
    }

    @Test
    public void addTest4() {
        BigDecimal result = MathKit.add(new BigDecimal("133"), new BigDecimal("331"));
        Assertions.assertEquals(new BigDecimal("464"), result);
    }

    @Test
    public void isIntegerTest() {
        Assertions.assertTrue(MathKit.isInteger("-12"));
        Assertions.assertTrue(MathKit.isInteger("256"));
        Assertions.assertTrue(MathKit.isInteger("0256"));
        Assertions.assertTrue(MathKit.isInteger("0"));
        Assertions.assertFalse(MathKit.isInteger("23.4"));
    }

    @Test
    public void isLongTest() {
        Assertions.assertTrue(MathKit.isLong("-12"));
        Assertions.assertTrue(MathKit.isLong("256"));
        Assertions.assertTrue(MathKit.isLong("0256"));
        Assertions.assertTrue(MathKit.isLong("0"));
        Assertions.assertFalse(MathKit.isLong("23.4"));
    }

    @Test
    public void isNumberTest() {
        Assertions.assertTrue(MathKit.isNumber("28.55"));
        Assertions.assertTrue(MathKit.isNumber("0"));
        Assertions.assertTrue(MathKit.isNumber("+100.10"));
        Assertions.assertTrue(MathKit.isNumber("-22.022"));
        Assertions.assertTrue(MathKit.isNumber("0X22"));
    }

    @Test
    public void divTest() {
        double result = MathKit.div(0, 1);
        Assertions.assertEquals(0.0, result, 0);
    }

    @Test
    public void roundTest() {

        // 四舍
        String round1 = MathKit.roundStr(2.674, 2);
        String round2 = MathKit.roundStr("2.674", 2);
        Assertions.assertEquals("2.67", round1);
        Assertions.assertEquals("2.67", round2);

        // 五入
        String round3 = MathKit.roundStr(2.675, 2);
        String round4 = MathKit.roundStr("2.675", 2);
        Assertions.assertEquals("2.68", round3);
        Assertions.assertEquals("2.68", round4);

        // 四舍六入五成双
        String round31 = MathKit.roundStr(4.245, 2, RoundingMode.HALF_EVEN);
        String round41 = MathKit.roundStr("4.2451", 2, RoundingMode.HALF_EVEN);
        Assertions.assertEquals("4.24", round31);
        Assertions.assertEquals("4.25", round41);

        // 补0
        String round5 = MathKit.roundStr(2.6005, 2);
        String round6 = MathKit.roundStr("2.6005", 2);
        Assertions.assertEquals("2.60", round5);
        Assertions.assertEquals("2.60", round6);

        // 补0
        String round7 = MathKit.roundStr(2.600, 2);
        String round8 = MathKit.roundStr("2.600", 2);
        Assertions.assertEquals("2.60", round7);
        Assertions.assertEquals("2.60", round8);
    }

    @Test
    public void roundStrTest() {
        String roundStr = MathKit.roundStr(2.647, 2);
        Assertions.assertEquals(roundStr, "2.65");
    }

    @Test
    public void roundHalfEvenTest() {
        String roundStr = MathKit.roundHalfEven(4.245, 2).toString();
        Assertions.assertEquals(roundStr, "4.24");
        roundStr = MathKit.roundHalfEven(4.2450, 2).toString();
        Assertions.assertEquals(roundStr, "4.24");
        roundStr = MathKit.roundHalfEven(4.2451, 2).toString();
        Assertions.assertEquals(roundStr, "4.25");
        roundStr = MathKit.roundHalfEven(4.2250, 2).toString();
        Assertions.assertEquals(roundStr, "4.22");

        roundStr = MathKit.roundHalfEven(1.2050, 2).toString();
        Assertions.assertEquals(roundStr, "1.20");
        roundStr = MathKit.roundHalfEven(1.2150, 2).toString();
        Assertions.assertEquals(roundStr, "1.22");
        roundStr = MathKit.roundHalfEven(1.2250, 2).toString();
        Assertions.assertEquals(roundStr, "1.22");
        roundStr = MathKit.roundHalfEven(1.2350, 2).toString();
        Assertions.assertEquals(roundStr, "1.24");
        roundStr = MathKit.roundHalfEven(1.2450, 2).toString();
        Assertions.assertEquals(roundStr, "1.24");
        roundStr = MathKit.roundHalfEven(1.2550, 2).toString();
        Assertions.assertEquals(roundStr, "1.26");
        roundStr = MathKit.roundHalfEven(1.2650, 2).toString();
        Assertions.assertEquals(roundStr, "1.26");
        roundStr = MathKit.roundHalfEven(1.2750, 2).toString();
        Assertions.assertEquals(roundStr, "1.28");
        roundStr = MathKit.roundHalfEven(1.2850, 2).toString();
        Assertions.assertEquals(roundStr, "1.28");
        roundStr = MathKit.roundHalfEven(1.2950, 2).toString();
        Assertions.assertEquals(roundStr, "1.30");
    }

    @Test
    public void decimalFormatTest() {
        long c = 299792458;// 光速

        String format = MathKit.decimalFormat(",###", c);
        Assertions.assertEquals("299,792,458", format);
    }

    @Test
    public void decimalFormatMoneyTest() {
        double c = 299792400.543534534;

        String format = MathKit.decimalFormatMoney(c);
        Assertions.assertEquals("299,792,400.54", format);

        double value = 0.5;
        String money = MathKit.decimalFormatMoney(value);
        Assertions.assertEquals("0.50", money);
    }

    @Test
    public void equalsTest() {
        Assertions.assertTrue(MathKit.equals(new BigDecimal("0.00"), BigDecimal.ZERO));
    }

    @Test
    public void formatPercentTest() {
        String str = MathKit.formatPercent(0.33543545, 2);
        Assertions.assertEquals("33.54%", str);
    }

    @Test
    public void toBigDecimalTest() {
        double a = 3.14;

        BigDecimal bigDecimal = MathKit.toBigDecimal(a);
        Assertions.assertEquals("3.14", bigDecimal.toString());
    }

    @Test
    public void maxTest() {
        int max = MathKit.max(5, 4, 3, 6, 1);
        Assertions.assertEquals(6, max);
    }

    @Test
    public void minTest() {
        int min = MathKit.min(5, 4, 3, 6, 1);
        Assertions.assertEquals(1, min);
    }

    @Test
    public void parseIntTest() {
        int v1 = MathKit.parseInt("0xFF");
        Assertions.assertEquals(255, v1);
        int v2 = MathKit.parseInt("010");
        Assertions.assertEquals(10, v2);
        int v3 = MathKit.parseInt("10");
        Assertions.assertEquals(10, v3);
        int v4 = MathKit.parseInt("   ");
        Assertions.assertEquals(0, v4);
        int v5 = MathKit.parseInt("10F");
        Assertions.assertEquals(10, v5);
        int v6 = MathKit.parseInt("22.4D");
        Assertions.assertEquals(22, v6);

        int v7 = MathKit.parseInt("0");
        Assertions.assertEquals(0, v7);
    }

    @Test
    public void parseLongTest() {
        long v1 = MathKit.parseLong("0xFF");
        Assertions.assertEquals(255L, v1);
        long v2 = MathKit.parseLong("010");
        Assertions.assertEquals(10L, v2);
        long v3 = MathKit.parseLong("10");
        Assertions.assertEquals(10L, v3);
        long v4 = MathKit.parseLong("   ");
        Assertions.assertEquals(0L, v4);
        long v5 = MathKit.parseLong("10F");
        Assertions.assertEquals(10L, v5);
        long v6 = MathKit.parseLong("22.4D");
        Assertions.assertEquals(22L, v6);
    }

    @Test
    public void factorialTest() {
        long factorial = MathKit.factorial(0);
        Assertions.assertEquals(1, factorial);

        factorial = MathKit.factorial(5, 0);
        Assertions.assertEquals(120, factorial);
        factorial = MathKit.factorial(5, 1);
        Assertions.assertEquals(120, factorial);
    }

    @Test
    public void mulTest() {
        final BigDecimal mul = MathKit.mul(new BigDecimal("10"), null);
        Assertions.assertEquals(BigDecimal.ZERO, mul);
    }

}

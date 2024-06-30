package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.lang.Symbol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathKitTest {

    @Test
    public void addTest() {
        final Float a = 3.15f;
        final Double b = 4.22;
        final double result = MathKit.add(a, b).doubleValue();
        assertEquals(7.37, result, 0);
    }

    @Test
    public void addTest2() {
        final double a = 3.15f;// 转换丢失精度
        final double b = 4.22;
        final double result = MathKit.add(a, b).doubleValue();
        assertEquals(7.37, result, 0.0001);
    }

    @Test
    public void addTest3() {
        final float a = 3.15f;
        final double b = 4.22;
        final double result = MathKit.add(a, b, a, b).doubleValue();
        assertEquals(14.74, result, 0);
    }

    @Test
    public void addTest4() {
        BigDecimal result = MathKit.add(new BigDecimal("133"), new BigDecimal("331"));
        assertEquals(new BigDecimal("464"), result);

        result = MathKit.add(new BigDecimal[]{new BigDecimal("133"), new BigDecimal("331")});
        assertEquals(new BigDecimal("464"), result);
    }

    @Test
    void addTest5() {
        final BigDecimal add = MathKit.add(1686036549717L, 1000);
        assertEquals(1686036550717L, add.longValue());
    }

    @Test
    public void addBlankTest() {
        final BigDecimal result = MathKit.add("123", " ");
        assertEquals(new BigDecimal("123"), result);
    }

    @Test
    public void subTest() {
        BigDecimal result = MathKit.sub(new BigDecimal("133"), new BigDecimal("331"));
        assertEquals(new BigDecimal("-198"), result);

        result = MathKit.sub(new BigDecimal[]{new BigDecimal("133"), new BigDecimal("331")});
        assertEquals(new BigDecimal("-198"), result);
    }

    @Test
    public void mulTest() {
        BigDecimal result = MathKit.mul(new BigDecimal("133"), new BigDecimal("331"));
        assertEquals(new BigDecimal("44023"), result);

        result = MathKit.mul(new BigDecimal[]{new BigDecimal("133"), new BigDecimal("331")});
        assertEquals(new BigDecimal("44023"), result);
    }

    @Test
    public void mulNullTest() {
        final BigDecimal mul = MathKit.mul(new BigDecimal("10"), null);
        assertEquals(BigDecimal.ZERO, mul);
    }

    @Test
    public void isIntegerTest() {
        final String[] validNumArr = {"0", "-0", "+0", "12", "+12", "1234567890", "2147483647", "-2147483648",
                "0x012345678", "0X012345678", "0xabcdef", "-0xabcdef", "0x12abcdef", "0x7FFFFFFF", "-0x80000000",
                "01234567", "017777777777", "-020000000000"
        };
        privateIsIntegerTest(validNumArr, true);

        final String[] invalidNumArr = {null, "", "  ", "+", "+1.", ".1", "99L", "99D", "99F", "12.3", "123e1", "-12.3", "1.2.3",
                "2147483648", "0x80000000", "020000000000", "-2147483649", "-0x80000001", "-020000000001", "-020000000001",
                "a", "+a", "123abc", "09"
        };
        privateIsIntegerTest(invalidNumArr, false);
    }

    private void privateIsIntegerTest(final String[] numStrArr, final boolean expected) {
        for (final String numStr : numStrArr) {
            assertEquals(expected, MathKit.isInteger(numStr), "未通过的数字为: " + numStr);
        }
    }

    @Test
    public void isLongTest() {
        final String[] validNumArr = {
                "0", "0L", "-0L", "+0L", "12", "+12", "1234567890123456789", "99L",
                "2147483648", "0x80000000", "020000000000", "-2147483649", "-0x80000001", "-020000000001", "-020000000001",
                "9223372036854775807", "-9223372036854775808",
                "0x0123456789", "0X0123456789", "0x123456789abcdef", "0xabcdef123456789", "0x7FFFFFFF", "-0x80000000",
                "0x7fffffffffffffff", "-0x8000000000000000",
                "01234567", "0777777777777777777777", "-01000000000000000000000"
        };
        privateIsLongTest(validNumArr, true);

        final String[] invalidNumArr = {null, "", "  ", "+", "+1.", ".1", "99D", "99F", "12.3", "123e1", "-12.3", "1.2.3",
                "a", "+a", "123abc", "09",
                "9223372036854775808", "-9223372036854775809",
                "0x8000000000000000", "-0x8000000000000001",
                "01000000000000000000000", "-01000000000000000000001"
        };
        privateIsLongTest(invalidNumArr, false);
    }

    private void privateIsLongTest(final String[] numStrArr, final boolean expected) {
        for (final String numStr : numStrArr) {
            assertEquals(expected, MathKit.isLong(numStr), "未通过的数字为: " + numStr);
        }
    }

    @Test
    public void isNumberTest() {
        privateIsNumberTest("28.55", true);
        privateIsNumberTest("12345", true);
        privateIsNumberTest("1234.5", true);
        privateIsNumberTest(".12345", true);
        privateIsNumberTest("1234E5", true);
        privateIsNumberTest("1234E+5", true);
        privateIsNumberTest("1234E-5", true);
        privateIsNumberTest("123.4E5", true);
        privateIsNumberTest("-1234", true);
        privateIsNumberTest("-1234.5", true);
        privateIsNumberTest("-.12345", true);
        privateIsNumberTest("-1234E5", true);
        privateIsNumberTest("0", true);
        privateIsNumberTest("0.1", true); // LANG-1216
        privateIsNumberTest("-0", true);
        privateIsNumberTest("01234", true);
        privateIsNumberTest("-01234", true);
        privateIsNumberTest("-0xABC123", true);
        privateIsNumberTest("-0x0", true);
        privateIsNumberTest("123.4E21D", true);
        privateIsNumberTest("-221.23F", true);
        privateIsNumberTest("22338L", true);

        privateIsNumberTest(null, false);
        privateIsNumberTest("", false);
        privateIsNumberTest(" ", false);
        privateIsNumberTest("\r\n\t", false);
        privateIsNumberTest("--2.3", false);
        privateIsNumberTest(".12.3", false);
        privateIsNumberTest("-123E", false);
        privateIsNumberTest("-123E+-212", false);
        privateIsNumberTest("-123E2.12", false);
        privateIsNumberTest("0xGF", false);
        privateIsNumberTest("0xFAE-1", false);
        privateIsNumberTest(".", false);
        privateIsNumberTest("-0ABC123", false);
        privateIsNumberTest("123.4E-D", false);
        privateIsNumberTest("123.4ED", false);
        privateIsNumberTest("1234E5l", false);
        privateIsNumberTest("11a", false);
        privateIsNumberTest("1a", false);
        privateIsNumberTest("a", false);
        privateIsNumberTest("11g", false);
        privateIsNumberTest("11z", false);
        privateIsNumberTest("11def", false);
        privateIsNumberTest("11d11", false);
        privateIsNumberTest("11 11", false);
        privateIsNumberTest(" 1111", false);
        privateIsNumberTest("1111 ", false);

        privateIsNumberTest("2.", true); // LANG-521
        privateIsNumberTest("1.1L", false); // LANG-664
        privateIsNumberTest("+0xF", true); // LANG-1645
        privateIsNumberTest("+0xFFFFFFFF", true); // LANG-1645
        privateIsNumberTest("+0xFFFFFFFFFFFFFFFF", true); // LANG-1645
        privateIsNumberTest(".0", true); // LANG-1646
        privateIsNumberTest("0.", true); // LANG-1646
        privateIsNumberTest("0.D", true); // LANG-1646
        privateIsNumberTest("0e1", true); // LANG-1646
        privateIsNumberTest("0e1D", true); // LANG-1646
        privateIsNumberTest(".D", false); // LANG-1646
        privateIsNumberTest(".e10", false); // LANG-1646
        privateIsNumberTest(".e10D", false); // LANG-1646
    }

    private void privateIsNumberTest(final String numStr, final boolean expected) {
        assertEquals(expected, MathKit.isNumber(numStr));
    }

    @Test
    public void divTest() {
        final double result = MathKit.div(0, 1).doubleValue();
        assertEquals(0.0, result, 0);
    }

    @Test
    public void divBigDecimalTest() {
        final BigDecimal result = MathKit.div(BigDecimal.ZERO, BigDecimal.ONE);
        assertEquals(BigDecimal.ZERO, result.stripTrailingZeros());
    }

    @Test
    public void roundTest() {

        // 四舍
        final String round1 = MathKit.roundString(2.674, 2);
        final String round2 = MathKit.roundString("2.674", 2);
        assertEquals("2.67", round1);
        assertEquals("2.67", round2);

        // 五入
        final String round3 = MathKit.roundString(2.675, 2);
        final String round4 = MathKit.roundString("2.675", 2);
        assertEquals("2.68", round3);
        assertEquals("2.68", round4);

        // 四舍六入五成双
        final String round31 = MathKit.roundString(4.245, 2, RoundingMode.HALF_EVEN);
        final String round41 = MathKit.roundString("4.2451", 2, RoundingMode.HALF_EVEN);
        assertEquals("4.24", round31);
        assertEquals("4.25", round41);

        // 补0
        final String round5 = MathKit.roundString(2.6005, 2);
        final String round6 = MathKit.roundString("2.6005", 2);
        assertEquals("2.60", round5);
        assertEquals("2.60", round6);

        // 补0
        final String round7 = MathKit.roundString(2.600, 2);
        final String round8 = MathKit.roundString("2.600", 2);
        assertEquals("2.60", round7);
        assertEquals("2.60", round8);
    }

    @Test
    public void roundStrTest() {
        final String roundString = MathKit.roundString(2.647, 2);
        assertEquals(roundString, "2.65");

        final String roundStr1 = MathKit.roundString(0, 10);
        assertEquals(roundStr1, "0.0000000000");
    }

    @Test
    public void roundHalfEvenTest() {
        String roundString = MathKit.roundHalfEven(4.245, 2).toString();
        assertEquals(roundString, "4.24");
        roundString = MathKit.roundHalfEven(4.2450, 2).toString();
        assertEquals(roundString, "4.24");
        roundString = MathKit.roundHalfEven(4.2451, 2).toString();
        assertEquals(roundString, "4.25");
        roundString = MathKit.roundHalfEven(4.2250, 2).toString();
        assertEquals(roundString, "4.22");

        roundString = MathKit.roundHalfEven(1.2050, 2).toString();
        assertEquals(roundString, "1.20");
        roundString = MathKit.roundHalfEven(1.2150, 2).toString();
        assertEquals(roundString, "1.22");
        roundString = MathKit.roundHalfEven(1.2250, 2).toString();
        assertEquals(roundString, "1.22");
        roundString = MathKit.roundHalfEven(1.2350, 2).toString();
        assertEquals(roundString, "1.24");
        roundString = MathKit.roundHalfEven(1.2450, 2).toString();
        assertEquals(roundString, "1.24");
        roundString = MathKit.roundHalfEven(1.2550, 2).toString();
        assertEquals(roundString, "1.26");
        roundString = MathKit.roundHalfEven(1.2650, 2).toString();
        assertEquals(roundString, "1.26");
        roundString = MathKit.roundHalfEven(1.2750, 2).toString();
        assertEquals(roundString, "1.28");
        roundString = MathKit.roundHalfEven(1.2850, 2).toString();
        assertEquals(roundString, "1.28");
        roundString = MathKit.roundHalfEven(1.2950, 2).toString();
        assertEquals(roundString, "1.30");
    }

    @Test
    public void decimalFormatTest() {
        final long c = 299792458;// 光速

        final String format = MathKit.format(",###", c);
        assertEquals("299,792,458", format);
    }

    @Test
    public void decimalFormatNaNTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final Double a = 0D;
            final Double b = 0D;

            final Double c = a / b;
            Console.log(MathKit.format("#%", c));
        });
    }

    @Test
    public void decimalFormatNaNTest2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final Double a = 0D;
            final Double b = 0D;

            Console.log(MathKit.format("#%", a / b));
        });
    }

    @Test
    public void decimalFormatDoubleTest() {
        final Double c = 467.8101;

        final String format = MathKit.format("0.00", c);
        assertEquals("467.81", format);
    }

    @Test
    public void decimalFormatMoneyTest() {
        final double c = 299792400.543534534;

        final String format = MathKit.formatMoney(c);
        assertEquals("299,792,400.54", format);

        final double value = 0.5;
        final String money = MathKit.formatMoney(value);
        assertEquals("0.50", money);
    }

    @Test
    public void equalsTest() {
        Assertions.assertTrue(MathKit.equals(new BigDecimal("0.00"), BigDecimal.ZERO));
    }

    @Test
    public void toBigDecimalTest() {
        final double a = 3.14;

        BigDecimal bigDecimal = MathKit.toBigDecimal(a);
        assertEquals("3.14", bigDecimal.toString());

        bigDecimal = MathKit.toBigDecimal("1,234.55");
        assertEquals("1234.55", bigDecimal.toString());

        bigDecimal = MathKit.toBigDecimal("1,234.56D");
        assertEquals("1234.56", bigDecimal.toString());

        assertEquals(new BigDecimal("9.0E+7"), MathKit.toBigDecimal("9.0E+7"));
    }

    @Test
    void emptyToBigDecimalTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MathKit.toBigDecimal("");
        });
    }

    @Test
    void naNToBigDecimalTest() {
        assertEquals(BigDecimal.ZERO, MathKit.toBigDecimal("NaN"));
    }

    @Test
    public void issuesTest() throws ParseException {
        // 当数字中包含一些非数字字符时，按照JDK的规则，不做修改。
        final BigDecimal bigDecimal = MathKit.toBigDecimal("345.sdf");
        assertEquals(NumberFormat.getInstance().parse("345.sdf"), bigDecimal.longValue());
    }

    @Test
    public void parseIntTest() {
        int number = MathKit.parseInt("0xFF");
        assertEquals(255, number);

        number = MathKit.parseInt("0xFE");
        assertEquals(254, number);

        // 0开头
        number = MathKit.parseInt("010");
        assertEquals(10, number);

        number = MathKit.parseInt("10");
        assertEquals(10, number);

        number = MathKit.parseInt("   ");
        assertEquals(0, number);

        number = MathKit.parseInt("10F");
        assertEquals(10, number);

        number = MathKit.parseInt("22.4D");
        assertEquals(22, number);

        number = MathKit.parseInt("22.6D");
        assertEquals(22, number);

        number = MathKit.parseInt("0");
        assertEquals(0, number);

        number = MathKit.parseInt(".123");
        assertEquals(0, number);
    }

    @Test
    public void parseIntTest2() {
        // 千位分隔符去掉
        final int v1 = MathKit.parseInt("1,482.00");
        assertEquals(1482, v1);
    }

    @Test
    public void parseIntTest3() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            final int v1 = MathKit.parseInt("d");
            assertEquals(0, v1);
        });
    }

    @Test
    public void parseIntTest4() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            // 科学计数法忽略支持，科学计数法一般用于表示非常小和非常大的数字，这类数字转换为int后精度丢失，没有意义。
            final String numberStr = "429900013E20220812163344551";
            MathKit.parseInt(numberStr);
        });
    }

    @Test
    public void parseIntTest5() {

        // -------------------------- Parse failed -----------------------

        Assertions.assertNull(MathKit.parseInt("abc", null));

        assertEquals(456, MathKit.parseInt("abc", 456));

        // -------------------------- Parse success -----------------------

        assertEquals(123, MathKit.parseInt("123.abc", 789));

        assertEquals(123, MathKit.parseInt("123.3", null));

    }

    @Test
    public void parseIntOfNaNTest() {
        // https://stackoverflow.com/questions/5876369/why-does-casting-double-nan-to-int-not-throw-an-exception-in-java
        final int v1 = MathKit.parseInt("NaN");
        assertEquals(0, v1);
    }

    @Test
    public void parseNumberTest() {
        // 千位分隔符去掉
        final int v1 = MathKit.parseNumber("1,482.00").intValue();
        assertEquals(1482, v1);

        final Number v2 = MathKit.parseNumber("1,482.00D");
        assertEquals(1482L, v2.longValue());
    }

    @Test
    public void parseNumberTest2() {
        final String numberStr = "429900013E20220812163344551";
        final Number number = MathKit.parseNumber(numberStr);
        Assertions.assertNotNull(number);
    }

    @Test
    public void parseNumberTest3() {

        // -------------------------- Parse failed -----------------------

        Assertions.assertNull(MathKit.parseNumber("abc", (Number) null));

        Assertions.assertNull(MathKit.parseNumber(Normal.EMPTY, (Number) null));

        Assertions.assertNull(MathKit.parseNumber(StringKit.repeat(Symbol.SPACE, 10), (Number) null));

        assertEquals(456, MathKit.parseNumber("abc", 456).intValue());

        // -------------------------- Parse success -----------------------

        assertEquals(123, MathKit.parseNumber("123.abc", 789).intValue());

        assertEquals(123.3D, MathKit.parseNumber("123.3", (Number) null).doubleValue());

        assertEquals(0.123D, MathKit.parseNumber("0.123.3", (Number) null).doubleValue());

    }

    @Test
    public void parseHexNumberTest() {
        // 千位分隔符去掉
        final int v1 = MathKit.parseNumber("0xff").intValue();
        assertEquals(255, v1);
    }

    @Test
    public void parseNumberOfNaNTest() {
        // https://stackoverflow.com/questions/5876369/why-does-casting-double-nan-to-int-not-throw-an-exception-in-java
        final Number v1 = MathKit.parseNumber("NaN");
        assertEquals(0, v1.intValue());
    }

    @Test
    public void parseLongTest() {
        long number = MathKit.parseLong("0xFF");
        assertEquals(255, number);

        // 0开头
        number = MathKit.parseLong("010");
        assertEquals(10, number);

        number = MathKit.parseLong("10");
        assertEquals(10, number);

        number = MathKit.parseLong("   ");
        assertEquals(0, number);

        number = MathKit.parseLong("10F");
        assertEquals(10, number);

        number = MathKit.parseLong("22.4D");
        assertEquals(22, number);

        number = MathKit.parseLong("22.6D");
        assertEquals(22, number);

        number = MathKit.parseLong("0");
        assertEquals(0, number);

        number = MathKit.parseLong(".123");
        assertEquals(0, number);
    }

    @Test
    public void parseLongTest2() {

        // -------------------------- Parse failed -----------------------

        final Long v1 = MathKit.parseLong(null, null);
        Assertions.assertNull(v1);

        final Long v2 = MathKit.parseLong(Normal.EMPTY, null);
        Assertions.assertNull(v2);

        final Long v3 = MathKit.parseLong("L3221", 1233L);
        assertEquals(1233L, v3);

        // -------------------------- Parse success -----------------------

        final Long v4 = MathKit.parseLong("1233L", null);
        assertEquals(1233L, v4);

    }

    @Test
    public void isPowerOfTwoTest() {
        Assertions.assertFalse(MathKit.isPowerOfTwo(-1));
        Assertions.assertTrue(MathKit.isPowerOfTwo(16));
        Assertions.assertTrue(MathKit.isPowerOfTwo(65536));
        Assertions.assertTrue(MathKit.isPowerOfTwo(1));
        Assertions.assertFalse(MathKit.isPowerOfTwo(17));
    }

    @Test
    public void toStrTest() {
        assertEquals("1", MathKit.toString(new BigDecimal("1.0000000000")));
        assertEquals("0", MathKit.toString(MathKit.sub(new BigDecimal("9600.00000"), new BigDecimal("9600.00000"))));
        assertEquals("0", MathKit.toString(MathKit.sub(new BigDecimal("9600.0000000000"), new BigDecimal("9600.000000"))));
        assertEquals("0", MathKit.toString(new BigDecimal("9600.00000").subtract(new BigDecimal("9600.000000000"))));
    }

    @Test
    public void toPlainNumberTest() {
        final String num = "5344.34234e3";
        final String s = new BigDecimal(num).toPlainString();
        assertEquals("5344342.34", s);
    }

    @Test
    public void isOddOrEvenTest() {
        final int[] a = {0, 32, -32, 123, -123};
        Assertions.assertFalse(MathKit.isOdd(a[0]));
        Assertions.assertTrue(MathKit.isEven(a[0]));

        Assertions.assertFalse(MathKit.isOdd(a[1]));
        Assertions.assertTrue(MathKit.isEven(a[1]));

        Assertions.assertFalse(MathKit.isOdd(a[2]));
        Assertions.assertTrue(MathKit.isEven(a[2]));

        Assertions.assertTrue(MathKit.isOdd(a[3]));
        Assertions.assertFalse(MathKit.isEven(a[3]));

        Assertions.assertTrue(MathKit.isOdd(a[4]));
        Assertions.assertFalse(MathKit.isEven(a[4]));
    }

    @Test
    public void toBigIntegerTest() {
        final Number number = 1123123;
        final Number number2 = 1123123.123;
        Assertions.assertNotNull(MathKit.toBigInteger(number));
        Assertions.assertNotNull(MathKit.toBigInteger(number2));
    }

    @Test
    public void divIntegerTest() {
        final BigDecimal div = MathKit.div(100101300, 100);
        assertEquals(1001013, div.intValue());
    }

    @Test
    public void isDoubleTest() {
        Assertions.assertFalse(MathKit.isDouble(null));
        Assertions.assertFalse(MathKit.isDouble(""));
        Assertions.assertFalse(MathKit.isDouble("  "));
        Assertions.assertFalse(MathKit.isDouble("1"));
        Assertions.assertFalse(MathKit.isDouble("-1"));
        Assertions.assertFalse(MathKit.isDouble("+1"));
        Assertions.assertFalse(MathKit.isDouble("0.1."));
        Assertions.assertFalse(MathKit.isDouble("01"));
        Assertions.assertFalse(MathKit.isDouble("NaN"));
        Assertions.assertFalse(MathKit.isDouble("-NaN"));
        Assertions.assertFalse(MathKit.isDouble("-Infinity"));

        Assertions.assertTrue(MathKit.isDouble("0."));
        Assertions.assertTrue(MathKit.isDouble("0.1"));
        Assertions.assertTrue(MathKit.isDouble("-0.1"));
        Assertions.assertTrue(MathKit.isDouble("+0.1"));
        Assertions.assertTrue(MathKit.isDouble("0.110"));
        Assertions.assertTrue(MathKit.isDouble("00.1"));
        Assertions.assertTrue(MathKit.isDouble("01.1"));
    }

    @Test
    public void rangeTest() {
        final int[] range = MathKit.range(0, 10);
        assertEquals(0, range[0]);
        assertEquals(1, range[1]);
        assertEquals(2, range[2]);
        assertEquals(3, range[3]);
        assertEquals(4, range[4]);
        assertEquals(5, range[5]);
        assertEquals(6, range[6]);
        assertEquals(7, range[7]);
        assertEquals(8, range[8]);
        assertEquals(9, range[9]);
        assertEquals(10, range[10]);
    }

    @Test
    public void rangeMinTest() {
        Assertions.assertThrows(NegativeArraySizeException.class, () -> {
            MathKit.range(0, Integer.MIN_VALUE);
        });
    }

    @Test
    public void isPrimeTest() {
        Assertions.assertTrue(MathKit.isPrime(2));
        Assertions.assertTrue(MathKit.isPrime(3));
        Assertions.assertTrue(MathKit.isPrime(7));
        Assertions.assertTrue(MathKit.isPrime(17));
        Assertions.assertTrue(MathKit.isPrime(296731));
        Assertions.assertTrue(MathKit.isPrime(99999989));

        Assertions.assertFalse(MathKit.isPrime(4));
        Assertions.assertFalse(MathKit.isPrime(296733));
        Assertions.assertFalse(MathKit.isPrime(20_4123_2399));
    }

    @Test
    public void parseFloatTest() {

        // -------------------------- Parse failed -----------------------

        Assertions.assertNull(MathKit.parseFloat("abc", null));

        Assertions.assertNull(MathKit.parseFloat("a123.33", null));

        Assertions.assertNull(MathKit.parseFloat("..123", null));

        assertEquals(1233F, MathKit.parseFloat(Normal.EMPTY, 1233F));

        // -------------------------- Parse success -----------------------

        assertEquals(123.33F, MathKit.parseFloat("123.33a", null));

        assertEquals(0.123F, MathKit.parseFloat(".123", null));

    }

    @Test
    public void parseDoubleTest() {

        // -------------------------- Parse failed -----------------------

        Assertions.assertNull(MathKit.parseDouble("abc", null));
        Assertions.assertNull(MathKit.parseDouble("a123.33", null));
        Assertions.assertNull(MathKit.parseDouble("..123", null));
        assertEquals(1233D, MathKit.parseDouble(Normal.EMPTY, 1233D));

        // -------------------------- Parse success -----------------------

        assertEquals(123.33D, MathKit.parseDouble("123.33a", null));
        assertEquals(0.123D, MathKit.parseDouble(".123", null));
    }

    @Test
    void naNToIntTest() {
        assertEquals(0, Double.valueOf(Double.NaN).intValue());
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_8)
    void numberFormatTest() {
        // JDK8下，NaN解析报错，JDK9+中返回0
        Assertions.assertThrows(ParseException.class, () -> {
            NumberFormat.getInstance().parse("NaN");
        });
    }

    @Test
    void issueTest() {
        Assertions.assertFalse(MathKit.isInteger("999999999999999"));
    }

    @Test
    void formatThousands() {
        assertEquals(
                "123,456,789.111111",
                MathKit.formatThousands(123456789.111111D, 6));
    }

    @Test
    void nullToZeroTest() {
        assertEquals(0, MathKit.nullToZero((Integer) null));
        assertEquals(0L, MathKit.nullToZero((Long) null));
        assertEquals(0D, MathKit.nullToZero((Double) null));
        assertEquals(0D, MathKit.nullToZero((Double) null));
        assertEquals(0F, MathKit.nullToZero((Float) null));
        assertEquals(0, MathKit.nullToZero((Short) null));
        assertEquals(0, MathKit.nullToZero((Byte) null));
        assertEquals(BigDecimal.ZERO, MathKit.nullToZero((BigDecimal) null));
        assertEquals(BigInteger.ZERO, MathKit.nullToZero((BigInteger) null));
    }

    @Test
    public void isValidNumberTest() {
        final boolean validNumber = MathKit.isValidNumber(1);
        Assertions.assertTrue(validNumber);
    }

    @Test
    public void testPowZero() {
        final BigDecimal number = new BigDecimal("2.5");
        final int exponent = 0;
        final BigDecimal expected = new BigDecimal("1");
        assertEquals(expected, MathKit.pow(number, exponent));
    }

    @Test
    public void testPowNegative() {
        final BigDecimal number = new BigDecimal("2.5");
        final int exponent = -2;
        final BigDecimal expected = new BigDecimal("0.16");
        assertEquals(expected, MathKit.pow(number, exponent));
    }

    @Test
    public void testPowSmallNumber() {
        final BigDecimal number = new BigDecimal("0.1");
        final int exponent = -3;
        final BigDecimal expected = new BigDecimal("1000.00");
        assertEquals(expected, MathKit.pow(number, exponent));
    }

    @Test
    public void factorialTest() {
        long factorial = MathKit.factorial(0);
        Assertions.assertEquals(1, factorial);

        Assertions.assertEquals(1L, MathKit.factorial(1));
        Assertions.assertEquals(1307674368000L, MathKit.factorial(15));
        Assertions.assertEquals(2432902008176640000L, MathKit.factorial(20));

        factorial = MathKit.factorial(5, 0);
        Assertions.assertEquals(120, factorial);
        factorial = MathKit.factorial(5, 1);
        Assertions.assertEquals(120, factorial);

        Assertions.assertEquals(5, MathKit.factorial(5, 4));
        Assertions.assertEquals(2432902008176640000L, MathKit.factorial(20, 0));
    }

    @Test
    public void factorialTest2() {
        long factorial = MathKit.factorial(new BigInteger("0")).longValue();
        Assertions.assertEquals(1, factorial);

        Assertions.assertEquals(1L, MathKit.factorial(new BigInteger("1")).longValue());
        Assertions.assertEquals(1307674368000L, MathKit.factorial(new BigInteger("15")).longValue());
        Assertions.assertEquals(2432902008176640000L, MathKit.factorial(20));

        factorial = MathKit.factorial(new BigInteger("5"), new BigInteger("0")).longValue();
        Assertions.assertEquals(120, factorial);
        factorial = MathKit.factorial(new BigInteger("5"), BigInteger.ONE).longValue();
        Assertions.assertEquals(120, factorial);

        Assertions.assertEquals(5, MathKit.factorial(new BigInteger("5"), new BigInteger("4")).longValue());
        Assertions.assertEquals(2432902008176640000L, MathKit.factorial(new BigInteger("20"), BigInteger.ZERO).longValue());
    }
}

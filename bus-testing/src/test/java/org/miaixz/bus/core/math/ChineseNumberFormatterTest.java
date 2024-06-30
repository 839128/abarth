package org.miaixz.bus.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.convert.Convert;

public class ChineseNumberFormatterTest {

    @Test
    public void formatThousandTest() {
        String f = ChineseNumberFormatter.of().setColloquialMode(true).format(10);
        Assertions.assertEquals("十", f);
        f = ChineseNumberFormatter.of().setColloquialMode(true).format(11);
        Assertions.assertEquals("十一", f);
        f = ChineseNumberFormatter.of().setColloquialMode(true).format(19);
        Assertions.assertEquals("十九", f);
    }

    // 测试千
    @Test
    public void formatThousandLongTest() {
        String f = ChineseNumberFormatter.of().format(0);
        Assertions.assertEquals("零", f);
        f = ChineseNumberFormatter.of().format(1);
        Assertions.assertEquals("一", f);
        f = ChineseNumberFormatter.of().format(10);
        Assertions.assertEquals("一十", f);
        f = ChineseNumberFormatter.of().format(12);
        Assertions.assertEquals("一十二", f);
        f = ChineseNumberFormatter.of().format(100);
        Assertions.assertEquals("一百", f);
        f = ChineseNumberFormatter.of().format(101);
        Assertions.assertEquals("一百零一", f);
        f = ChineseNumberFormatter.of().format(110);
        Assertions.assertEquals("一百一十", f);
        f = ChineseNumberFormatter.of().format(112);
        Assertions.assertEquals("一百一十二", f);
        f = ChineseNumberFormatter.of().format(1000);
        Assertions.assertEquals("一千", f);
        f = ChineseNumberFormatter.of().format(1001);
        Assertions.assertEquals("一千零一", f);
        f = ChineseNumberFormatter.of().format(1010);
        Assertions.assertEquals("一千零一十", f);
        f = ChineseNumberFormatter.of().format(1100);
        Assertions.assertEquals("一千一百", f);
        f = ChineseNumberFormatter.of().format(1101);
        Assertions.assertEquals("一千一百零一", f);
        f = ChineseNumberFormatter.of().format(9999);
        Assertions.assertEquals("九千九百九十九", f);
    }

    // 测试万
    @Test
    public void formatTenThousandLongTest() {
        String f = ChineseNumberFormatter.of().format(1_0000);
        Assertions.assertEquals("一万", f);
        f = ChineseNumberFormatter.of().format(1_0001);
        Assertions.assertEquals("一万零一", f);
        f = ChineseNumberFormatter.of().format(1_0010);
        Assertions.assertEquals("一万零一十", f);
        f = ChineseNumberFormatter.of().format(1_0100);
        Assertions.assertEquals("一万零一百", f);
        f = ChineseNumberFormatter.of().format(1_1000);
        Assertions.assertEquals("一万一千", f);
        f = ChineseNumberFormatter.of().format(10_1000);
        Assertions.assertEquals("一十万零一千", f);
        f = ChineseNumberFormatter.of().format(10_0100);
        Assertions.assertEquals("一十万零一百", f);
        f = ChineseNumberFormatter.of().format(100_1000);
        Assertions.assertEquals("一百万零一千", f);
        f = ChineseNumberFormatter.of().format(100_0100);
        Assertions.assertEquals("一百万零一百", f);
        f = ChineseNumberFormatter.of().format(1000_1000);
        Assertions.assertEquals("一千万零一千", f);
        f = ChineseNumberFormatter.of().format(1000_0100);
        Assertions.assertEquals("一千万零一百", f);
        f = ChineseNumberFormatter.of().format(9999_0000);
        Assertions.assertEquals("九千九百九十九万", f);
    }

    // 测试亿
    @Test
    public void formatHundredMillionLongTest() {
        String f = ChineseNumberFormatter.of().format(1_0000_0000L);
        Assertions.assertEquals("一亿", f);
        f = ChineseNumberFormatter.of().format(1_0000_0001L);
        Assertions.assertEquals("一亿零一", f);
        f = ChineseNumberFormatter.of().format(1_0000_1000L);
        Assertions.assertEquals("一亿零一千", f);
        f = ChineseNumberFormatter.of().format(1_0001_0000L);
        Assertions.assertEquals("一亿零一万", f);
        f = ChineseNumberFormatter.of().format(1_0010_0000L);
        Assertions.assertEquals("一亿零一十万", f);
        f = ChineseNumberFormatter.of().format(1_0010_0000L);
        Assertions.assertEquals("一亿零一十万", f);
        f = ChineseNumberFormatter.of().format(1_0100_0000L);
        Assertions.assertEquals("一亿零一百万", f);
        f = ChineseNumberFormatter.of().format(1_1000_0000L);
        Assertions.assertEquals("一亿一千万", f);
        f = ChineseNumberFormatter.of().format(10_1000_0000L);
        Assertions.assertEquals("一十亿零一千万", f);
        f = ChineseNumberFormatter.of().format(100_1000_0000L);
        Assertions.assertEquals("一百亿零一千万", f);
        f = ChineseNumberFormatter.of().format(1000_1000_0000L);
        Assertions.assertEquals("一千亿零一千万", f);
        f = ChineseNumberFormatter.of().format(1100_1000_0000L);
        Assertions.assertEquals("一千一百亿零一千万", f);
        f = ChineseNumberFormatter.of().format(9999_0000_0000L);
        Assertions.assertEquals("九千九百九十九亿", f);
    }

    // 测试万亿
    @Test
    public void formatTrillionsLongTest() {
        String f = ChineseNumberFormatter.of().format(1_0000_0000_0000L);
        Assertions.assertEquals("一万亿", f);
        f = ChineseNumberFormatter.of().format(1_0000_1000_0000L);
        Assertions.assertEquals("一万亿零一千万", f);
        f = ChineseNumberFormatter.of().format(1_0010_0000_0000L);
        Assertions.assertEquals("一万零一十亿", f);
    }

    @Test
    public void formatTest() {
        final String f0 = ChineseNumberFormatter.of().format(5000_8000);
        Assertions.assertEquals("五千万零八千", f0);
        String f1 = ChineseNumberFormatter.of().format(1_0889.72356);
        Assertions.assertEquals("一万零八百八十九点七二", f1);
        f1 = ChineseNumberFormatter.of().format(12653);
        Assertions.assertEquals("一万二千六百五十三", f1);
        f1 = ChineseNumberFormatter.of().format(215.6387);
        Assertions.assertEquals("二百一十五点六四", f1);
        f1 = ChineseNumberFormatter.of().format(1024);
        Assertions.assertEquals("一千零二十四", f1);
        f1 = ChineseNumberFormatter.of().format(100350089);
        Assertions.assertEquals("一亿零三十五万零八十九", f1);
        f1 = ChineseNumberFormatter.of().format(1200);
        Assertions.assertEquals("一千二百", f1);
        f1 = ChineseNumberFormatter.of().format(12);
        Assertions.assertEquals("一十二", f1);
        f1 = ChineseNumberFormatter.of().format(0.05);
        Assertions.assertEquals("零点零五", f1);
    }

    @Test
    public void formatTest2() {
        String f1 = ChineseNumberFormatter.of().format(-0.3);
        Assertions.assertEquals("负零点三", f1);

        f1 = ChineseNumberFormatter.of().format(10);
        Assertions.assertEquals("一十", f1);
    }

    @Test
    public void formatTest3() {
        final String f1 = ChineseNumberFormatter.of().format(5000_8000);
        Assertions.assertEquals("五千万零八千", f1);

        final String f2 = ChineseNumberFormatter.of().format(1_0035_0089);
        Assertions.assertEquals("一亿零三十五万零八十九", f2);
    }

    @Test
    public void formatMaxTest() {
        final String f3 = ChineseNumberFormatter.of().format(99_9999_9999_9999L);
        Assertions.assertEquals("九十九万九千九百九十九亿九千九百九十九万九千九百九十九", f3);
    }

    @Test
    public void formatTraditionalTest() {
        String f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(10889.72356);
        Assertions.assertEquals("壹万零捌佰捌拾玖点柒贰", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(12653);
        Assertions.assertEquals("壹万贰仟陆佰伍拾叁", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(215.6387);
        Assertions.assertEquals("贰佰壹拾伍点陆肆", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(1024);
        Assertions.assertEquals("壹仟零贰拾肆", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(100350089);
        Assertions.assertEquals("壹亿零叁拾伍万零捌拾玖", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(1200);
        Assertions.assertEquals("壹仟贰佰", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(12);
        Assertions.assertEquals("壹拾贰", f1);
        f1 = ChineseNumberFormatter.of().setUseTraditional(true).format(0.05);
        Assertions.assertEquals("零点零伍", f1);
    }

    @Test
    public void formatSimpleTest() {
        String f1 = ChineseNumberFormatter.formatSimple(1_2345);
        Assertions.assertEquals("1.23万", f1);
        f1 = ChineseNumberFormatter.formatSimple(-5_5555);
        Assertions.assertEquals("-5.56万", f1);
        f1 = ChineseNumberFormatter.formatSimple(1_2345_6789);
        Assertions.assertEquals("1.23亿", f1);
        f1 = ChineseNumberFormatter.formatSimple(-5_5555_5555);
        Assertions.assertEquals("-5.56亿", f1);
        f1 = ChineseNumberFormatter.formatSimple(1_2345_6789_1011L);
        Assertions.assertEquals("1.23万亿", f1);
        f1 = ChineseNumberFormatter.formatSimple(-5_5555_5555_5555L);
        Assertions.assertEquals("-5.56万亿", f1);
        f1 = ChineseNumberFormatter.formatSimple(123);
        Assertions.assertEquals("123", f1);
        f1 = ChineseNumberFormatter.formatSimple(-123);
        Assertions.assertEquals("-123", f1);
    }

    @Test
    public void digitToChineseTest() {
        String digitToChinese = Convert.digitToChinese(12_4124_1241_2421.12);
        Assertions.assertEquals("壹拾贰万肆仟壹佰贰拾肆亿壹仟贰佰肆拾壹万贰仟肆佰贰拾壹元壹角贰分", digitToChinese);

        digitToChinese = Convert.digitToChinese(12_0000_1241_2421L);
        Assertions.assertEquals("壹拾贰万亿零壹仟贰佰肆拾壹万贰仟肆佰贰拾壹元整", digitToChinese);

        digitToChinese = Convert.digitToChinese(12_0000_0000_2421L);
        Assertions.assertEquals("壹拾贰万亿零贰仟肆佰贰拾壹元整", digitToChinese);

        digitToChinese = Convert.digitToChinese(12_4124_1241_2421D);
        Assertions.assertEquals("壹拾贰万肆仟壹佰贰拾肆亿壹仟贰佰肆拾壹万贰仟肆佰贰拾壹元整", digitToChinese);

        digitToChinese = Convert.digitToChinese(2421.02);
        Assertions.assertEquals("贰仟肆佰贰拾壹元零贰分", digitToChinese);
    }

    @Test
    public void digitToChineseTest2() {
        double a = 67556.32;
        String digitUppercase = Convert.digitToChinese(a);
        Assertions.assertEquals("陆万柒仟伍佰伍拾陆元叁角贰分", digitUppercase);

        a = 1024.00;
        digitUppercase = Convert.digitToChinese(a);
        Assertions.assertEquals("壹仟零贰拾肆元整", digitUppercase);

        final double b = 1024;
        digitUppercase = Convert.digitToChinese(b);
        Assertions.assertEquals("壹仟零贰拾肆元整", digitUppercase);
    }

    @Test
    public void digitToChineseTest3() {
        String digitToChinese = Convert.digitToChinese(2_0000_0000.00);
        Assertions.assertEquals("贰亿元整", digitToChinese);
        digitToChinese = Convert.digitToChinese(2_0000.00);
        Assertions.assertEquals("贰万元整", digitToChinese);
        digitToChinese = Convert.digitToChinese(2_0000_0000_0000.00);
        Assertions.assertEquals("贰万亿元整", digitToChinese);
    }

    @Test
    public void digitToChineseTest4() {
        final String digitToChinese = Convert.digitToChinese(400_0000.00);
        Assertions.assertEquals("肆佰万元整", digitToChinese);
    }

    @Test
    public void numberCharToChineseTest() {
        char s = ChineseNumberFormatter.formatChar('1', false);
        Assertions.assertEquals('一', s);
        s = ChineseNumberFormatter.formatChar('2', false);
        Assertions.assertEquals('二', s);
        s = ChineseNumberFormatter.formatChar('0', false);
        Assertions.assertEquals('零', s);

        // 非数字字符原样返回
        s = ChineseNumberFormatter.formatChar('A', false);
        Assertions.assertEquals('A', s);
    }

    @Test
    public void singleMoneyTest() {
        String format = ChineseNumberFormatter.of().setMoneyMode(true).format(0.01);
        Assertions.assertEquals("一分", format);
        format = ChineseNumberFormatter.of().setMoneyMode(true).format(0.10);
        Assertions.assertEquals("一角", format);
        format = ChineseNumberFormatter.of().setMoneyMode(true).format(0.12);
        Assertions.assertEquals("一角二分", format);

        format = ChineseNumberFormatter.of().setMoneyMode(true).format(1.00);
        Assertions.assertEquals("一元整", format);
        format = ChineseNumberFormatter.of().setMoneyMode(true).format(1.10);
        Assertions.assertEquals("一元一角", format);
        format = ChineseNumberFormatter.of().setMoneyMode(true).format(1.02);
        Assertions.assertEquals("一元零二分", format);
    }

    @Test
    public void singleNumberTest() {
        String format = ChineseNumberFormatter.of().format(0.01);
        Assertions.assertEquals("零点零一", format);
        format = ChineseNumberFormatter.of().format(0.10);
        Assertions.assertEquals("零点一", format);
        format = ChineseNumberFormatter.of().format(0.12);
        Assertions.assertEquals("零点一二", format);

        format = ChineseNumberFormatter.of().format(1.00);
        Assertions.assertEquals("一", format);
        format = ChineseNumberFormatter.of().format(1.10);
        Assertions.assertEquals("一点一", format);
        format = ChineseNumberFormatter.of().format(1.02);
        Assertions.assertEquals("一点零二", format);
    }

}

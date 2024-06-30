package org.miaixz.bus.core.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreditCodeTest {

    @Test
    public void isCreditCodeBySimple() {
        final String testCreditCode = "91310115591693856A";
        Assertions.assertTrue(CreditCode.isCreditCodeSimple(testCreditCode));
    }

    @Test
    public void isCreditCode() {
        final String testCreditCode = "91310110666007217T";
        Assertions.assertTrue(CreditCode.isCreditCode(testCreditCode));
    }

    @Test
    public void isCreditCode2() {
        // 由于早期部分试点地区推行 法人和其他组织统一社会信用代码 较早，会存在部分代码不符合国家标准的情况。
        // 见：https://github.com/bluesky335/IDCheck
        final String testCreditCode = "91350211M00013FA1N";
        Assertions.assertFalse(CreditCode.isCreditCode(testCreditCode));
    }

    @Test
    public void randomCreditCode() {
        final String s = CreditCode.randomCreditCode();
        Assertions.assertTrue(CreditCode.isCreditCode(s));
    }

}

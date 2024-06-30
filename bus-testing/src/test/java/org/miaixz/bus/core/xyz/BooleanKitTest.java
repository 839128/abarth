package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanKitTest {

    @Test
    public void toBooleanTest() {
        Assertions.assertTrue(BooleanKit.toBoolean("true"));
        Assertions.assertTrue(BooleanKit.toBoolean("yes"));
        Assertions.assertTrue(BooleanKit.toBoolean("t"));
        Assertions.assertTrue(BooleanKit.toBoolean("OK"));
        Assertions.assertTrue(BooleanKit.toBoolean("1"));
        Assertions.assertTrue(BooleanKit.toBoolean("On"));
        Assertions.assertTrue(BooleanKit.toBoolean("是"));
        Assertions.assertTrue(BooleanKit.toBoolean("对"));
        Assertions.assertTrue(BooleanKit.toBoolean("真"));

        Assertions.assertFalse(BooleanKit.toBoolean("false"));
        Assertions.assertFalse(BooleanKit.toBoolean("6455434"));
        Assertions.assertFalse(BooleanKit.toBoolean(""));
    }

    @Test
    public void andTest() {
        Assertions.assertFalse(BooleanKit.and(true, false));
        Assertions.assertFalse(BooleanKit.andOfWrap(true, false));
    }

    @Test
    public void orTest() {
        Assertions.assertTrue(BooleanKit.or(true, false));
        Assertions.assertTrue(BooleanKit.orOfWrap(true, false));
    }

    @Test
    public void xorTest() {
        Assertions.assertTrue(BooleanKit.xor(true, false));
        Assertions.assertTrue(BooleanKit.xor(true, true, true));
        Assertions.assertFalse(BooleanKit.xor(true, true, false));
        Assertions.assertTrue(BooleanKit.xor(true, false, false));
        Assertions.assertFalse(BooleanKit.xor(false, false, false));

        Assertions.assertTrue(BooleanKit.xorOfWrap(true, false));
        Assertions.assertTrue(BooleanKit.xorOfWrap(true, true, true));
        Assertions.assertFalse(BooleanKit.xorOfWrap(true, true, false));
        Assertions.assertTrue(BooleanKit.xorOfWrap(true, false, false));
        Assertions.assertFalse(BooleanKit.xorOfWrap(false, false, false));
    }

    @Test
    public void isTrueIsFalseTest() {
        Assertions.assertFalse(BooleanKit.isTrue(null));
        Assertions.assertFalse(BooleanKit.isFalse(null));
    }

    @Test
    public void orOfWrapTest() {
        Assertions.assertFalse(BooleanKit.orOfWrap(Boolean.FALSE, null));
        Assertions.assertTrue(BooleanKit.orOfWrap(Boolean.TRUE, null));
    }

    @Test
    public void negateTest() {
        Assertions.assertFalse(BooleanKit.negate(Boolean.TRUE));
        Assertions.assertTrue(BooleanKit.negate(Boolean.FALSE));

        Assertions.assertFalse(BooleanKit.negate(Boolean.TRUE.booleanValue()));
        Assertions.assertTrue(BooleanKit.negate(Boolean.FALSE.booleanValue()));
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("true", BooleanKit.toStringTrueFalse(true));
        Assertions.assertEquals("false", BooleanKit.toStringTrueFalse(false));

        Assertions.assertEquals("yes", BooleanKit.toStringYesNo(true));
        Assertions.assertEquals("no", BooleanKit.toStringYesNo(false));

        Assertions.assertEquals("on", BooleanKit.toStringOnOff(true));
        Assertions.assertEquals("off", BooleanKit.toStringOnOff(false));
    }

    @Test
    public void toBooleanObjectTest() {
        Assertions.assertTrue(BooleanKit.toBooleanObject("yes"));
        Assertions.assertTrue(BooleanKit.toBooleanObject("真"));
        Assertions.assertTrue(BooleanKit.toBooleanObject("是"));
        Assertions.assertTrue(BooleanKit.toBooleanObject("√"));

        Assertions.assertNull(BooleanKit.toBooleanObject(null));
        Assertions.assertNull(BooleanKit.toBooleanObject("不识别"));
    }

    @Test
    public void issueTest() {
        final Boolean boolean1 = true;
        final Boolean boolean2 = null;
        final Boolean result = BooleanKit.andOfWrap(boolean1, boolean2);
        Assertions.assertFalse(result);
    }

}

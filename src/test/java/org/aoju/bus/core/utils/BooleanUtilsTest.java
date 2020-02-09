package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanUtilsTest {

    @Test
    public void toBooleanTest() {
        Assertions.assertTrue(BooleanUtils.toBoolean("true"));
        Assertions.assertTrue(BooleanUtils.toBoolean("yes"));
        Assertions.assertTrue(BooleanUtils.toBoolean("t"));
        Assertions.assertTrue(BooleanUtils.toBoolean("OK"));
        Assertions.assertTrue(BooleanUtils.toBoolean("是"));
        Assertions.assertTrue(BooleanUtils.toBoolean("对"));
        Assertions.assertTrue(BooleanUtils.toBoolean("真"));

        Assertions.assertFalse(BooleanUtils.toBoolean("false"));
        Assertions.assertFalse(BooleanUtils.toBoolean("6455434"));
        Assertions.assertFalse(BooleanUtils.toBoolean(""));
    }

}

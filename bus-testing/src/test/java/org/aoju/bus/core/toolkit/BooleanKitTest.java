package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanKitTest {

    @Test
    public void toBooleanTest() {
        Assertions.assertTrue(BooleanKit.toBoolean("true"));
        Assertions.assertTrue(BooleanKit.toBoolean("yes"));
        Assertions.assertTrue(BooleanKit.toBoolean("t"));
        Assertions.assertTrue(BooleanKit.toBoolean("OK"));
        Assertions.assertTrue(BooleanKit.toBoolean("是"));
        Assertions.assertTrue(BooleanKit.toBoolean("对"));
        Assertions.assertTrue(BooleanKit.toBoolean("真"));

        Assertions.assertFalse(BooleanKit.toBoolean("false"));
        Assertions.assertFalse(BooleanKit.toBoolean("6455434"));
        Assertions.assertFalse(BooleanKit.toBoolean(""));
    }

}

package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

public class BooleanKitTest {

    @Test
    public void toBooleanTest() {
        Assert.assertTrue(BooleanKit.toBoolean("true"));
        Assert.assertTrue(BooleanKit.toBoolean("yes"));
        Assert.assertTrue(BooleanKit.toBoolean("t"));
        Assert.assertTrue(BooleanKit.toBoolean("OK"));
        Assert.assertTrue(BooleanKit.toBoolean("是"));
        Assert.assertTrue(BooleanKit.toBoolean("对"));
        Assert.assertTrue(BooleanKit.toBoolean("真"));

        Assert.assertFalse(BooleanKit.toBoolean("false"));
        Assert.assertFalse(BooleanKit.toBoolean("6455434"));
        Assert.assertFalse(BooleanKit.toBoolean(""));
    }

}

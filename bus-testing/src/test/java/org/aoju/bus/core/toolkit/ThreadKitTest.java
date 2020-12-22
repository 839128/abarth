package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

public class ThreadKitTest {

    @Test
    public void executeTest() {
        final boolean isValid = true;
        ThreadKit.execute(() -> Assert.assertTrue(isValid));
    }

}

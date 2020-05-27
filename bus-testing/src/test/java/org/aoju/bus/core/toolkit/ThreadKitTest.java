package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadKitTest {

    @Test
    public void executeTest() {
        final boolean isValid = true;
        ThreadKit.execute(() -> Assertions.assertTrue(isValid));
    }

}

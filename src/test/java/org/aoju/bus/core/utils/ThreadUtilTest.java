package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadUtilTest {

    @Test
    public void executeTest() {
        final boolean isValid = true;
        ThreadUtils.execute(() -> Assertions.assertTrue(isValid));
    }

}

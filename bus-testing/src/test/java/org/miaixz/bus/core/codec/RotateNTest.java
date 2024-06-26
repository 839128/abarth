package org.miaixz.bus.core.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RotateNTest {

    @Test
    public void rot13Test() {
        final String str = "1f2e9df6131b480b9fdddc633cf24996";

        final String encode13 = RotateN.encode13(str);
        Assertions.assertEquals("4s5r2qs9464o713o2sqqqp966ps57229", encode13);

        final String decode13 = RotateN.decode13(encode13);
        Assertions.assertEquals(str, decode13);
    }

}
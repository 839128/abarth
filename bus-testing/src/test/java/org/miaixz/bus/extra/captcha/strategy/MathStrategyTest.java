package org.miaixz.bus.extra.captcha.strategy;

import org.junit.jupiter.api.Test;

public class MathStrategyTest {

    @Test
    public void mathGeneratorTest() {
        final MathStrategy mathGenerator = new MathStrategy();
        for (int i = 0; i < 1000; i++) {
            mathGenerator.verify(mathGenerator.generate(), "0");
        }
    }

}

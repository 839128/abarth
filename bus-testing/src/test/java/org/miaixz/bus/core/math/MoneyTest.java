package org.miaixz.bus.core.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.MathKit;

public class MoneyTest {

    @Test
    public void yuanToCentTest() {
        final Money money = new Money("1234.56");
        Assertions.assertEquals(123456, money.getCent());

        Assertions.assertEquals(123456, MathKit.yuanToCent(1234.56));
    }

    @Test
    public void centToYuanTest() {
        final Money money = new Money(1234, 56);
        Assertions.assertEquals(1234.56D, money.getAmount().doubleValue(), 0);

        Assertions.assertEquals(1234.56D, MathKit.centToYuan(123456), 0);
    }

}

package org.aoju.bus.core.date;

import org.aoju.bus.core.date.format.BetweenFormat;
import org.aoju.bus.core.lang.Fields;
import org.aoju.bus.core.utils.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BetweenFormatTest {

    @Test
    public void formatTest() {
        long betweenMs = DateUtils.betweenMs(DateUtils.parse("2017-01-01 22:59:59"), DateUtils.parse("2017-01-02 23:59:58"));
        BetweenFormat formater = new BetweenFormat(betweenMs, Fields.Level.MILLSECOND, 1);
        Assertions.assertEquals(formater.toString(), "1天");
    }

    @Test
    public void formatBetweenTest() {
        long betweenMs = DateUtils.betweenMs(DateUtils.parse("2018-07-16 11:23:19"), DateUtils.parse("2018-07-16 11:23:20"));
        BetweenFormat formater = new BetweenFormat(betweenMs, Fields.Level.SECOND, 1);
        Assertions.assertEquals(formater.toString(), "1秒");
    }

    @Test
    public void formatTest2() {
        BetweenFormat formater = new BetweenFormat(584, Fields.Level.SECOND, 1);
        Assertions.assertEquals(formater.toString(), "0秒");
    }

}

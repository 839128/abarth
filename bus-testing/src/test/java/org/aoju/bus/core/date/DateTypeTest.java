package org.aoju.bus.core.date;

import org.aoju.bus.core.lang.Fields;
import org.junit.Assert;
import org.junit.Test;

public class DateTypeTest {

    @Test
    public void ofTest() {
        Fields.Type field = Fields.Type.of(11);
        Assert.assertEquals(Fields.Type.HOUR_OF_DAY, field);
        field = Fields.Type.of(12);
        Assert.assertEquals(Fields.Type.MINUTE, field);
    }

}

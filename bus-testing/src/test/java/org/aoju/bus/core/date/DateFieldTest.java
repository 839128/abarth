package org.aoju.bus.core.date;

import org.aoju.bus.core.lang.Fields;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateFieldTest {

    @Test
    public void ofTest() {
        Fields.DateField field = Fields.DateField.of(11);
        Assertions.assertEquals(Fields.DateField.HOUR_OF_DAY, field);
        field = Fields.DateField.of(12);
        Assertions.assertEquals(Fields.DateField.MINUTE, field);
    }
}

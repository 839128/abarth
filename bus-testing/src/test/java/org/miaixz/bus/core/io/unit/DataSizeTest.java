package org.miaixz.bus.core.io.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataSizeTest {

    @Test
    public void parseTest() {
        long parse = DataSize.parse("3M");
        Assertions.assertEquals(3145728, parse);

        parse = DataSize.parse("3m");
        Assertions.assertEquals(3145728, parse);

        parse = DataSize.parse("3MB");
        Assertions.assertEquals(3145728, parse);

        parse = DataSize.parse("3mb");
        Assertions.assertEquals(3145728, parse);

        parse = DataSize.parse("3.1M");
        Assertions.assertEquals(3250585, parse);

        parse = DataSize.parse("3.1m");
        Assertions.assertEquals(3250585, parse);

        parse = DataSize.parse("3.1MB");
        Assertions.assertEquals(3250585, parse);

        parse = DataSize.parse("-3.1MB");
        Assertions.assertEquals(-3250585, parse);

        parse = DataSize.parse("+3.1MB");
        Assertions.assertEquals(3250585, parse);

        parse = DataSize.parse("3.1mb");
        Assertions.assertEquals(3250585, parse);

        parse = DataSize.parse("3.1");
        Assertions.assertEquals(3, parse);

        try {
            DataSize.parse("3.1.3");
        } catch (final IllegalArgumentException ie) {
            Assertions.assertEquals("'3.1.3' is not a valid data size", ie.getMessage());
        }


    }

    @Test
    public void formatTest() {
        String format = DataSize.format(Long.MAX_VALUE);
        Assertions.assertEquals("8 EB", format);

        format = DataSize.format(1024L * 1024 * 1024 * 1024 * 1024);
        Assertions.assertEquals("1 PB", format);

        format = DataSize.format(1024L * 1024 * 1024 * 1024);
        Assertions.assertEquals("1 TB", format);
    }

    @Test
    void issueTest() {
        final String size = DataSize.format(10240000);
        final long bytes = DataSize.parse(size);
        Assertions.assertEquals(10244587, bytes);
    }

}

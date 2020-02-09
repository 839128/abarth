package org.aoju.bus.core.text.csv;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.ResourceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {

    @Test
    public void readTest() {
        CsvReader reader = new CsvReader();
        CsvData data = reader.read(ResourceUtils.getReader("test.csv", Charset.UTF_8));
        Assertions.assertEquals("关注\"对象\"", data.getRow(0).get(2));
    }

}

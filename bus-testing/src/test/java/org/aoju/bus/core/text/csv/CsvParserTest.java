package org.aoju.bus.core.text.csv;

import org.aoju.bus.core.utils.IoUtils;
import org.aoju.bus.core.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

public class CsvParserTest {

    @Test
    public void parseTest1() {
        StringReader reader = StringUtils.getReader("aaa,b\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("b\"bba\"", row.getRawList().get(1));
        IoUtils.close(parser);
    }

    @Test
    public void parseTest2() {
        StringReader reader = StringUtils.getReader("aaa,\"bba\"bbb,ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("\"bba\"bbb", row.getRawList().get(1));
        IoUtils.close(parser);
    }

    @Test
    public void parseTest3() {
        StringReader reader = StringUtils.getReader("aaa,\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("bba", row.getRawList().get(1));
        IoUtils.close(parser);
    }

    @Test
    public void parseTest4() {
        StringReader reader = StringUtils.getReader("aaa,\"\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("", row.getRawList().get(1));
        IoUtils.close(parser);
    }

}

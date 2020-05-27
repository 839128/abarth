package org.aoju.bus.core.text.csv;

import org.aoju.bus.core.toolkit.IoKit;
import org.aoju.bus.core.toolkit.StringKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

public class CsvParserTest {

    @Test
    public void parseTest1() {
        StringReader reader = StringKit.getReader("aaa,b\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("b\"bba\"", row.getRawList().get(1));
        IoKit.close(parser);
    }

    @Test
    public void parseTest2() {
        StringReader reader = StringKit.getReader("aaa,\"bba\"bbb,ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("\"bba\"bbb", row.getRawList().get(1));
        IoKit.close(parser);
    }

    @Test
    public void parseTest3() {
        StringReader reader = StringKit.getReader("aaa,\"bba\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("bba", row.getRawList().get(1));
        IoKit.close(parser);
    }

    @Test
    public void parseTest4() {
        StringReader reader = StringKit.getReader("aaa,\"\",ccc");
        CsvParser parser = new CsvParser(reader, null);
        CsvRow row = parser.nextRow();
        Assertions.assertEquals("", row.getRawList().get(1));
        IoKit.close(parser);
    }

}

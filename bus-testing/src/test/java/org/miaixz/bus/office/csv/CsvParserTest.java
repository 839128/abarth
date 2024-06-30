package org.miaixz.bus.office.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.core.xyz.StringKit;

import java.io.StringReader;

public class CsvParserTest {

    @Test
    public void parseTest1() {
        final StringReader reader = StringKit.getReader("aaa,b\"bba\",ccc");
        final CsvParser parser = new CsvParser(reader, null);
        final CsvRow row = parser.nextRow();
        Assertions.assertEquals("b\"bba\"", row.getRawList().get(1));
        IoKit.closeQuietly(parser);
    }

    @Test
    public void parseTest2() {
        final StringReader reader = StringKit.getReader("aaa,\"bba\"bbb,ccc");
        final CsvParser parser = new CsvParser(reader, null);
        final CsvRow row = parser.nextRow();

        Assertions.assertEquals("\"bba\"bbb", row.getRawList().get(1));
        IoKit.closeQuietly(parser);
    }

    @Test
    public void parseTest3() {
        final StringReader reader = StringKit.getReader("aaa,\"bba\",ccc");
        final CsvParser parser = new CsvParser(reader, null);
        final CsvRow row = parser.nextRow();

        Assertions.assertEquals("bba", row.getRawList().get(1));
        IoKit.closeQuietly(parser);
    }

    @Test
    public void parseTest4() {
        final StringReader reader = StringKit.getReader("aaa,\"\",ccc");
        final CsvParser parser = new CsvParser(reader, null);
        final CsvRow row = parser.nextRow();
        Assertions.assertEquals("", row.getRawList().get(1));
        IoKit.closeQuietly(parser);
    }

    @Test
    public void parseEscapeTest() {
        // https://datatracker.ietf.org/doc/html/rfc4180#section-2
        // 第七条规则
        final StringReader reader = StringKit.getReader("\"b\"\"bb\"");
        final CsvParser parser = new CsvParser(reader, null);
        final CsvRow row = parser.nextRow();
        Assertions.assertNotNull(row);
        Assertions.assertEquals(1, row.size());
        Assertions.assertEquals("b\"bb", row.get(0));
    }

}

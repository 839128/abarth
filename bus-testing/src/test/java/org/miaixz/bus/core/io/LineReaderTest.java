package org.miaixz.bus.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.ResourceKit;

import java.util.ArrayList;

public class LineReaderTest {

    @Test
    public void readLfTest() {
        final LineReader lineReader = new LineReader(ResourceKit.getReader("multi_line.properties"));
        final ArrayList<String> list = ListKit.of(lineReader);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("test1", list.get(0));
        Assertions.assertEquals("test2=abcd\\e", list.get(1));
        Assertions.assertEquals("test3=abc", list.get(2));
    }

    @Test
    public void readCrLfTest() {
        final LineReader lineReader = new LineReader(ResourceKit.getReader("multi_line_crlf.properties"));
        final ArrayList<String> list = ListKit.of(lineReader);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("test1", list.get(0));
        Assertions.assertEquals("test2=abcd\\e", list.get(1));
        Assertions.assertEquals("test3=abc", list.get(2));
    }

}

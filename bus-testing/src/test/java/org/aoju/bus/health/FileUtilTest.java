package org.aoju.bus.health;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests FileUtil
 */
public class FileUtilTest {

    /*
     * File sources
     */
    private static final String THISCLASS = "src/test/java/oshi/util/FileUtilTest.java";
    private static final String INT_FILE = "src/test/resources/test.integer.txt";
    private static final String STRING_FILE = "src/test/resources/test.string.txt";
    private static final String PROCIO_FILE = "src/test/resources/test.procio.txt";
    private static final String NO_FILE = "does/not/exist";

    /**
     * Test read file.
     */
    @Test
    public void testReadFile() {
        // Try file not found
        List<String> thisFile = FileKit.readLines(NO_FILE, Charset.UTF_8);
        assertEquals(0, thisFile.size());
        // Try this file
        thisFile = FileKit.readLines(THISCLASS, Charset.UTF_8);
        // Comment ONE line
        int lineOne = 0;
        // Comment TWO line
        int lineTwo = 0;
        for (int i = 0; i < thisFile.size(); i++) {
            String line = thisFile.get(i);
            if (lineOne == 0 && line.contains("Comment ONE line")) {
                lineOne = i;
                continue;
            }
            if (lineTwo == 0 && line.contains("Comment TWO line")) {
                lineTwo = i;
                break;
            }
        }
        assertEquals(2, lineTwo - lineOne);
    }

    /**
     * Test get*FromFile
     */
    @Test
    public void testGetFromFile() {
        assertEquals(123L, Builder.getLongFromFile(INT_FILE));
        assertEquals(0L, Builder.getLongFromFile(STRING_FILE));
        assertEquals(0L, Builder.getLongFromFile(NO_FILE));

        assertEquals(123L, Builder.getLongFromFile(INT_FILE));
        assertEquals(0L, Builder.getLongFromFile(STRING_FILE));
        assertEquals(0L, Builder.getLongFromFile(NO_FILE));

        assertEquals(123, Builder.getIntFromFile(INT_FILE));
        assertEquals(0, Builder.getIntFromFile(STRING_FILE));
        assertEquals(0, Builder.getIntFromFile(NO_FILE));

        assertEquals("123", Builder.getStringFromFile(INT_FILE));
        assertEquals("", Builder.getStringFromFile(NO_FILE));
    }

    @Test
    public void testReadProcIo() {
        Map<String, String> expected = new HashMap<>();
        expected.put("rchar", "124788352");
        expected.put("wchar", "124802481");
        expected.put("syscr", "135");
        expected.put("syscw", "1547");
        expected.put("read_bytes", "40304640");
        expected.put("write_bytes", "124780544");
        expected.put("cancelled_write_bytes", "42");
        Map<String, String> actual = Builder.getKeyValueMapFromFile(PROCIO_FILE, ":");
        assertEquals(expected.size(), actual.size());
        for (String key : expected.keySet()) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testReadProperties() {
        Properties props = Builder.readProperties("simplelogger.properties");
        assertEquals("INFO", props.getProperty("org.slf4j.simpleLogger.defaultLogLevel"));
        props = Builder.readProperties("this.file.does.not.exist");
        assertFalse(props.elements().hasMoreElements());
    }

}

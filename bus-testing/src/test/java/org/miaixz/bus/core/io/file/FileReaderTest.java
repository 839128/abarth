package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.StringKit;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件读取测试
 */
public class FileReaderTest {

    @Test
    public void fileReaderTest() {
        final FileReader fileReader = FileReader.of(FileKit.file("test.properties"));
        final String result = fileReader.readString();
        Assertions.assertNotNull(result);
    }

    @Test
    public void readLinesTest() {
        final FileReader fileReader = FileReader.of(FileKit.file("test.properties"));
        final List<String> strings = fileReader.readLines();
        Assertions.assertEquals(6, strings.size());
    }

    @Test
    public void readLinesTest2() {
        final FileReader fileReader = FileReader.of(FileKit.file("test.properties"));
        final List<String> strings = fileReader.readLines(new ArrayList<>(), StringKit::isNotBlank);
        Assertions.assertEquals(5, strings.size());
    }

}

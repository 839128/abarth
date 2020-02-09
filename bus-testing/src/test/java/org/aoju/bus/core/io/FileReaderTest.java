package org.aoju.bus.core.io;

import org.aoju.bus.core.io.file.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * 文件读取测试
 */
public class FileReaderTest {

    @Test
    public void fileReaderTest() {
        FileReader fileReader = new FileReader("test.properties");
        String result = fileReader.readString();
        Assertions.assertNotNull(result);
    }

}

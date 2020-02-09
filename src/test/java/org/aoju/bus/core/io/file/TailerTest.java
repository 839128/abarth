package org.aoju.bus.core.io.file;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.FileUtils;
import org.junit.jupiter.api.Test;

public class TailerTest {

    @Test
    public void tailTest() {
        FileUtils.tail(FileUtils.file("/data/tail.txt"), Charset.GBK);
    }

    @Test
    public void tailWithLinesTest() {
        Tailer tailer = new Tailer(FileUtils.file("/data/test/test.log"), new Tailer.ConsoleLineHandler(), 2);
        tailer.start();
    }

}

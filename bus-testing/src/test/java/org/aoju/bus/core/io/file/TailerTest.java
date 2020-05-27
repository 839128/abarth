package org.aoju.bus.core.io.file;

import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.jupiter.api.Test;

public class TailerTest {

    @Test
    public void tailTest() {
        FileKit.tail(FileKit.file("/data/tail.txt"), Charset.GBK);
    }

    @Test
    public void tailWithLinesTest() {
        Tailer tailer = new Tailer(FileKit.file("/data/test/test.log"), new Tailer.ConsoleLineHandler(), 2);
        tailer.start();
    }

}

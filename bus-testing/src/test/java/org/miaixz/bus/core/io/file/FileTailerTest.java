package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.FileKit;

public class FileTailerTest {

    @Test
    @Disabled
    public void tailTest() {
        FileKit.tail(FileKit.file("/test/core/tail.txt"), Charset.GBK);
    }

    @Test
    @Disabled
    public void tailWithLinesTest() {
        final FileTailer tailer = new FileTailer(FileKit.file("/test/core/test.log"), FileTailer.CONSOLE_HANDLER, 2);
        tailer.start();
    }

}
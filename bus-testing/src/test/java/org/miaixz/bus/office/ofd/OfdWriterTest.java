package org.miaixz.bus.office.ofd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;

public class OfdWriterTest {

    @Test
    @Disabled
    public void writeTest() {
        final OfdWriter ofdWriter = new OfdWriter(FileKit.file("/test/office/test.ofd"));
        ofdWriter.addText(null, "测试文本");
        ofdWriter.close();
    }

}

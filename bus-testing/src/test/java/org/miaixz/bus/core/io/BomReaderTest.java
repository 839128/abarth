package org.miaixz.bus.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.IoKit;

public class BomReaderTest {

    @Test
    public void readTest() {
        final BomReader bomReader = FileKit.getBOMReader(FileKit.file("with_bom.txt"));
        final String read = IoKit.read(bomReader, true);
        Assertions.assertEquals("此文本包含BOM头信息，用于测试BOM头读取", read);
    }

}

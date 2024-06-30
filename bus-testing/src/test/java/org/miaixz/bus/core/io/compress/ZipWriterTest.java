package org.miaixz.bus.core.io.compress;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.resource.FileResource;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ZipKit;

import java.io.File;

public class ZipWriterTest {

    @Test
    @Disabled
    public void zipDirTest() {
        ZipKit.zip(new File("/test/core"));
    }

    @Test
    @Disabled
    public void addTest() {
        final ZipWriter writer = ZipWriter.of(FileKit.file("/test/core/test.zip"), Charset.UTF_8);
        writer.add(new FileResource("/test/core/qr_c.png"));
        writer.close();
    }

}

package org.miaixz.bus.core.io.compress;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ZipKit;

import java.io.File;

public class ZipReaderTest {

    @Test
    @Disabled
    public void unzipTest() {
        final File unzip = ZipKit.unzip("/test/core/java.zip", "/test/core/java");
        Console.log(unzip);
    }

}

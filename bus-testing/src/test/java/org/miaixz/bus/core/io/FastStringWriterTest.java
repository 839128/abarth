package org.miaixz.bus.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.xyz.StringKit;

public class FastStringWriterTest {

    @Test
    public void writeTest() {
        final FastStringWriter fastStringWriter = new FastStringWriter(Normal.DEFAULT_BUFFER_SIZE);
        fastStringWriter.write(StringKit.repeat("bus", 2));

        Assertions.assertEquals("busbus", fastStringWriter.toString());
    }

}

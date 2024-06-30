package org.miaixz.bus.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.ResourceKit;

public class CharsetDetectorTest {

    @Test
    public void detectTest() {
        // 测试多个Charset对同一个流的处理是否有问题
        final java.nio.charset.Charset detect = CharsetDetector.detect(ResourceKit.getStream("test.xml"),
                Charset.GBK, Charset.UTF_8);
        Assertions.assertEquals(Charset.UTF_8, detect);
    }

}

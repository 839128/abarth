package org.aoju.bus.core.swing;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.utils.SwingUtils;
import org.junit.jupiter.api.Test;

public class ClipboardMonitorTest {

    @Test
    public void monitorTest() {
        // 第一个监听
        SwingUtils.listen((clipboard, contents) -> {
            Object object = SwingUtils.getStr(contents);
            Console.log("1# {}", object);
            return contents;
        }, false);

        // 第二个监听
        SwingUtils.listen((clipboard, contents) -> {
            Object object = SwingUtils.getStr(contents);
            Console.log("2# {}", object);
            return contents;
        });

    }

}

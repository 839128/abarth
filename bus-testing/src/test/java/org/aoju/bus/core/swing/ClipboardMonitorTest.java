package org.aoju.bus.core.swing;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.toolkit.SwingKit;
import org.junit.Test;

public class ClipboardMonitorTest {

    @Test
    public void monitorTest() {
        // 第一个监听
        SwingKit.listen((clipboard, contents) -> {
            Object object = SwingKit.getStr(contents);
            Console.log("1# {}", object);
            return contents;
        }, false);

        // 第二个监听
        SwingKit.listen((clipboard, contents) -> {
            Object object = SwingKit.getStr(contents);
            Console.log("2# {}", object);
            return contents;
        });

    }

}

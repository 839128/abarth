package org.miaixz.bus.core.io.watch;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.file.PathResolve;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.WatchKit;

import java.nio.file.Path;

/**
 * 文件监听单元测试
 */
public class WatchMonitorTest {

    @Test
    @Disabled
    void watchTest() {
        final Path path = PathResolve.of("/test/core/");
        Console.log("监听：{}", path);

        final WatchMonitor monitor = WatchKit.ofAll(path, new DelayWatcher(new TestConsoleWatcher(), 500));
        monitor.setMaxDepth(0);
        monitor.start();
    }

}

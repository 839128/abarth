package org.miaixz.bus.core.io.watch;

import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.WatchKit;

import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

public class TestConsoleWatcher extends SimpleWatcher {
    private static final long serialVersionUID = 1L;

    @Override
    public void onCreate(final WatchEvent<?> event, final WatchKey key) {
        Console.log("创建：{}-> {}", key.watchable(), event.context());
        Console.log("Resolved Path：{}", WatchKit.resolvePath(event, key));
    }

    @Override
    public void onModify(final WatchEvent<?> event, final WatchKey key) {
        Console.log("修改：{}-> {}", key.watchable(), event.context());
        Console.log("Resolved Path：{}", WatchKit.resolvePath(event, key));
    }

    @Override
    public void onDelete(final WatchEvent<?> event, final WatchKey key) {
        Console.log("删除：{}-> {}", key.watchable(), event.context());
        Console.log("Resolved Path：{}", WatchKit.resolvePath(event, key));
    }

    @Override
    public void onOverflow(final WatchEvent<?> event, final WatchKey key) {
        Console.log("Overflow：{}-> {}", key.watchable(), event.context());
        Console.log("Resolved Path：{}", WatchKit.resolvePath(event, key));
    }

}

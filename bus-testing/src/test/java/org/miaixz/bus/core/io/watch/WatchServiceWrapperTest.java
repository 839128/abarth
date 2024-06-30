package org.miaixz.bus.core.io.watch;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.file.PathResolve;

public class WatchServiceWrapperTest {

    @Test
    @Disabled
    void watchTest() {
        WatchServiceWrapper.of(WatchKind.ALL)
                .registerPath(PathResolve.of("/test/core/test"), 0)
                .watch(new TestConsoleWatcher(), null);
    }

}

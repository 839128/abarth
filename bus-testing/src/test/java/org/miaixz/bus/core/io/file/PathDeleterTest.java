package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;

import java.nio.file.Paths;

public class PathDeleterTest {
    @Test
    @Disabled
    public void removeFileTest() {
        FileKit.touch("/test/core/exist.txt");
        PathResolve.remove(Paths.get("/test/core/exist.txt"));
    }

    @Test
    @Disabled
    public void removeDirTest() {
        PathResolve.remove(Paths.get("/test/core/dir1"));
    }

    @Test
    @Disabled
    public void cleanDirTest() {
        PathResolve.clean(Paths.get("/test/core/dir1"));
    }

}

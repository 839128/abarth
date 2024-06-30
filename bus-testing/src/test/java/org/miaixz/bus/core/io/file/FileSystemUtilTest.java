package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;

import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystemUtilTest {

    @Test
    @Disabled
    public void listTest() {
        final FileSystem fileSystem = FileKit.createZip("/test/core/test.zip",
                Charset.GBK);
        final Path root = FileKit.getRoot(fileSystem);
        PathResolve.walkFiles(root, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) {
                Console.log(path);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}

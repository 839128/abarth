package org.miaixz.bus.extra.compress;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.extra.compress.archiver.StreamArchiver;

import java.io.File;

public class ArchiverTest {

    @Test
    @Disabled
    public void zipTest() {
        final File file = FileKit.file("/test/extra/compress/test.zip");
        StreamArchiver.of(Charset.UTF_8, ArchiveStreamFactory.ZIP, file)
                .add(FileKit.file("d:/Java"), (f) -> {
                    Console.log("Add: {}", f.getPath());
                    return true;
                })
                .finish().close();
    }

    @Test
    @Disabled
    public void tarTest() {
        final File file = FileKit.file("/test/extra/compress/test.tar");
        StreamArchiver.of(Charset.UTF_8, ArchiveStreamFactory.TAR, file)
                .add(FileKit.file("/test/extra"), (f) -> {
                    Console.log("Add: {}", f.getPath());
                    return true;
                })
                .finish().close();
    }

    @Test
    @Disabled
    public void cpioTest() {
        final File file = FileKit.file("/test/extra/compress/test.cpio");
        StreamArchiver.of(Charset.UTF_8, ArchiveStreamFactory.CPIO, file)
                .add(FileKit.file("/test/extra"), (f) -> {
                    Console.log("Add: {}", f.getPath());
                    return true;
                })
                .finish().close();
    }

    @Test
    @Disabled
    public void sevenZTest() {
        final File file = FileKit.file("/test/extra/compress/test.7z");
        CompressBuilder.createArchiver(Charset.UTF_8, ArchiveStreamFactory.SEVEN_Z, file)
                .add(FileKit.file("/test/extra/compress"), (f) -> {
                    Console.log("Add: {}", f.getPath());
                    return true;
                })
                .finish().close();
    }

    @Test
    @Disabled
    public void tgzTest() {
        final File file = FileKit.file("/test/extra/compress/test.tgz");
        CompressBuilder.createArchiver(Charset.UTF_8, "tgz", file)
                .add(FileKit.file("/test/extra/compress"), (f) -> {
                    Console.log("Add: {}", f.getPath());
                    return true;
                })
                .finish().close();
    }

}

package org.miaixz.bus.extra.compress;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.extra.compress.extractor.Extractor;

public class ExtractorTest {

    @Test
    @Disabled
    public void zipTest() {
        final Extractor extractor = CompressBuilder.createExtractor(
                Charset.defaultCharset(),
                FileKit.file("/test/extra/compress/c_1344112734760931330_20201230104703032.zip"));

        extractor.extract(FileKit.file("/test/extra/compress/test2/"));
    }

    @Test
    @Disabled
    public void sevenZTest() {
        final Extractor extractor = CompressBuilder.createExtractor(
                Charset.defaultCharset(),
                FileKit.file("/test/extra/compress/test.7z"));

        extractor.extract(FileKit.file("/test/extra/compress/test2/"));
    }

    @Test
    @Disabled
    public void tgzTest() {
        Extractor extractor = CompressBuilder.createExtractor(
                Charset.defaultCharset(),
                "tgz",
                FileKit.file("/test/extra/compress/test.tgz"));

        extractor.extract(FileKit.file("/test/extra/compress/tgz/"));
    }

    @Test
    @Disabled
    void createArchiverTest() {
        CompressBuilder.
                createArchiver(Charset.UTF_8, ArchiveStreamFactory.ZIP,
                        FileKit.file("/test/extra/compress/test.zip"))
                .add(FileKit.file("/test/extra"))
                .close();
    }

}

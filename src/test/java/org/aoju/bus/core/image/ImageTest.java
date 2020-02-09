package org.aoju.bus.core.image;

import org.aoju.bus.core.utils.FileUtils;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    public void cutTest1() {
        Image.from(FileUtils.file("/data/pic/face.jpg")).cut(0, 0, 200).write(FileUtils.file("/data/pic/face_radis.png"));
    }

    @Test
    public void compressTest() {
        Image.from(FileUtils.file("/data/test/4347273249269e3fb272341acc42d4e.jpg")).setQuality(0.8).write(FileUtils.file("/data/test/test_dest.jpg"));
    }

    @Test
    public void roundTest() {
        Image.from(FileUtils.file("/data/pic/face.jpg")).round(0.5).write(FileUtils.file("/data/pic/face_round.png"));
    }

}

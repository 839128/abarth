package org.miaixz.bus.extra.image;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.file.FileType;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.UrlKit;

import java.awt.*;
import java.io.File;

public class ImagesTest {

    @Test
    @Disabled
    public void cutTest1() {
        Images.from(FileKit.file("/test/extra/image/face.jpg")).cut(0, 0, 200).write(FileKit.file("/test/extra/image/face_radis.png"));
    }

    @Test
    @Disabled
    public void compressTest() {
        Images.from(FileKit.file("/test/extra/image/4347273249269e3fb272341acc42d4e.jpg")).setQuality(0.8).write(FileKit.file("/test/extra/image/test_dest.jpg"));
    }

    @Test
    @Disabled
    public void writeTest() {
        final Images from = Images.from(FileKit.file("/test/extra/image/81898311-001d6100-95eb-11ea-83c2-a14d7b1010bd.png"));
        ImageKit.write(from.getImg(), FileKit.file("/test/extra/image/dest.jpg"));
    }

    @Test
    @Disabled
    public void roundTest() {
        Images.from(FileKit.file("/test/extra/image/face.jpg")).round(0.5).write(FileKit.file("/test/extra/image/face_round.png"));
    }

    @Test
    @Disabled
    public void pressTextTest() {
        Images.from(FileKit.file("/test/extra/image/617180969474805871.jpg"))
                .setPositionBaseCentre(false)
                .pressText("版权所有", Color.RED, //
                        new Font("黑体", Font.BOLD, 100), //
                        0, //
                        100, //
                        1f)
                .write(FileKit.file("/test/extra/image/test2_result.png"));
    }


    @Test
    @Disabled
    public void pressTextFullScreenTest() {
        Images.from(FileKit.file("/test/extra/image/1435859438434136064.jpg"))
                .setTargetImageType(ImageKit.IMAGE_TYPE_PNG)
                .pressTextFull("版权所有     ", Color.LIGHT_GRAY,
                        new Font("黑体", Font.PLAIN, 30),
                        4,
                        30,
                        0.8f)
                .write(FileKit.file("/test/extra/image/2_result.png"));

    }

    @Test
    @Disabled
    public void pressImgTest() {
        Images.from(FileKit.file("/test/extra/image/图片1.JPG"))
                .pressImage(ImageKit.read("d:/test/617180969474805871.jpg"), new Rectangle(0, 0, 800, 800), 1f)
                .write(FileKit.file("/test/extra/image/pressImg_result.jpg"));
    }

    @Test
    @Disabled
    public void strokeTest() {
        Images.from(FileKit.file("/test/extra/image/公章3.png"))
                .stroke(null, 2f)
                .write(FileKit.file("/test/extra/image/stroke_result.png"));
    }

    @Test
    @Disabled
    public void scaleTest() {
        final String downloadFile = "/test/extra/image/1435859438434136064.JPG";
        final File file = FileKit.file(downloadFile);
        final File fileScale = FileKit.file(downloadFile + ".scale." + FileType.getType(file));

        final Image img = ImageKit.getImage(UrlKit.getURL(file));
        ImageKit.scale(img, fileScale, 0.8f);
        ImageKit.flush(img);
    }

    @Test
    @Disabled
    public void rotateWithBackgroundTest() {
        Images.from(FileKit.file("d:/test/aaa.jpg"))
                .setBackgroundColor(Color.RED)
                .rotate(45)
                .write(FileKit.file("/test/extra/image/aaa45.jpg"));
    }

}

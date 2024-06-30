package org.miaixz.bus.extra.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ColorKit;
import org.miaixz.bus.core.xyz.FileKit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageKitTest {

    @Test
    public void createFontTest() {
        final Font font = ImageKit.createFont();
        Assertions.assertNotNull(font);
    }

    @Test
    @Disabled
    void convertTest() {
        ImageKit.convert(
                FileKit.file("/test/extra/image/1.png"),
                FileKit.file("/test/extra/image/1.jpg"));
    }

    @Test
    @Disabled
    public void scaleTest() {
        ImageKit.scale(FileKit.file("/test/extra/image/test.jpg"), FileKit.file("/test/extra/image/test_result.jpg"), 0.8f);
    }

    @Test
    @Disabled
    public void scaleTest2() {
        ImageKit.scale(
                FileKit.file("/test/extra/image/2.png"),
                FileKit.file("/test/extra/image/2_result.jpg"), 600, 337, null);
    }

    @Test
    @Disabled
    public void scalePngTest() {
        ImageKit.scale(FileKit.file("/test/extra/image/test.png"), FileKit.file("/test/extra/image/test_result.png"), 0.5f);
    }

    @Test
    @Disabled
    public void scaleByWidthAndHeightTest() {
        ImageKit.scale(FileKit.file("/test/extra/image/aaa.jpg"), FileKit.file("/test/extra/image/aaa_result.jpg"), 100, 400, Color.BLUE);
    }

    @Test
    @Disabled
    public void cutTest() {
        ImageKit.cut(FileKit.file("/test/extra/image/logo.png"),
                FileKit.file("/test/extra/image/result.png"),
                new Rectangle(0, 0, 400, 240));
    }

    @Test
    @Disabled
    public void rotateTest() throws IOException {
        final Image image = ImageKit.rotate(ImageIO.read(FileKit.file("/test/extra/image/366466.jpg")), 180);
        ImageKit.write(image, FileKit.file("/test/extra/image/result.png"));
    }

    @Test
    @Disabled
    public void flipTest() {
        ImageKit.flip(FileKit.file("/test/extra/image/logo.png"), FileKit.file("/test/extra/image/result.png"));
    }

    @Test
    @Disabled
    public void pressImgTest() {
        ImageKit.pressImage(
                FileKit.file("/test/extra/image/1435859438434136064.jpg"),
                FileKit.file("/test/extra/image/dest.jpg"),
                ImageKit.read(FileKit.file("/test/extra/image/qrcodeCustom.png")), 0, 0, 0.9f);
    }

    @Test
    @Disabled
    public void pressTextTest() {
        ImageKit.pressText(//
                FileKit.file("/test/extra/image/2.jpg"), //
                FileKit.file("/test/extra/image/2_result.png"), //
                ImageText.of("版权所有", Color.RED, new Font("黑体", Font.BOLD, 100), new Point(0, 0), 1f));
    }

    @Test
    @Disabled
    public void pressImageFullScreenTest() {
        ImageKit.pressImageFull(new File("/test/extra/image/Background.png"),
                new File("/test/extra/image/Background_logo.png"),
                new File("/test/extra/image/logo.png"),
                2,
                30,
                0.5F);

    }

    @Test
    @Disabled
    public void sliceByRowsAndColsTest() {
        ImageKit.sliceByRowsAndCols(FileKit.file("/test/extra/image/logo.jpg"), FileKit.file("/test/extra/image/dest"), ImageKit.IMAGE_TYPE_JPEG, 1, 5);
    }

    @Test
    @Disabled
    public void sliceByRowsAndColsTest2() {
        ImageKit.sliceByRowsAndCols(
                FileKit.file("/test/extra/image/logo.png"),
                FileKit.file("/test/extra/image/dest"), ImageKit.IMAGE_TYPE_PNG, 1, 5);
    }

    @Test
    @Disabled
    public void convertTest1() {
        ImageKit.convert(FileKit.file("/test/extra/image/test2.png"), FileKit.file("/test/extra/image/test2Convert.jpg"));
    }

    @Test
    @Disabled
    public void writeTest() {
        final byte[] bytes = ImageKit.toBytes(ImageKit.read("/test/extra/image/logo_484.png"), "png");
        FileKit.writeBytes(bytes, "/test/extra/image/result.png");
    }

    @Test
    @Disabled
    public void compressTest() {
        ImageKit.compress(FileKit.file("/test/extra/image/dest.png"),
                FileKit.file("/test/extra/image/1111_target.jpg"), 0.1f);
    }

    @Test
    @Disabled
    public void copyTest() {
        final BufferedImage image = ImageKit.copyImage(ImageKit.read("/test/extra/image/test.png"), BufferedImage.TYPE_INT_RGB);
        ImageKit.write(image, FileKit.file("/test/extra/image/test_dest.jpg"));
    }

    @Test
    public void toHexTest() {
        final String s = ColorKit.toHex(Color.RED);
        Assertions.assertEquals("#FF0000", s);
    }

    @Test
    @Disabled
    public void backgroundRemovalTest() {
        // 图片 背景 换成 透明的
        ImageKit.backgroundRemoval(
                "/test/extra/image/617180969474805871.jpg",
                "/test/extra/image/2.jpg", 10);

        // 图片 背景 换成 红色的
        ImageKit.backgroundRemoval(new File(
                        "/test/extra/image/617180969474805871.jpg"),
                new File("/test/extra/image/3.jpg"),
                new Color(200, 0, 0), 10);
    }

    @Test
    @Disabled
    public void getMainColor() throws MalformedURLException {
        final BufferedImage read = ImageKit.read(new URL("https://pic2.zhimg.com/v2-94f5552f2b142ff575306850c5bab65d_b.png"));
        final String mainColor = ColorKit.getMainColor(read, new int[]{64, 84, 116});
        System.out.println(mainColor);
    }

    @Test
    @Disabled
    public void createImageTest() throws IOException {
        ImageKit.createImage(
                "版权所有",
                new Font("黑体", Font.BOLD, 50),
                Color.WHITE,
                Color.BLACK,
                ImageIO.createImageOutputStream(new File("/test/extra/image/createImageTest.png"))
        );
    }

    @Test
    @Disabled
    public void createTransparentImageTest() throws IOException {
        ImageKit.createTransparentImage(
                "版权所有",
                new Font("黑体", Font.BOLD, 50),
                Color.BLACK,
                ImageIO.createImageOutputStream(new File("/test/extra/image/createTransparentImageTest.png"))
        );
    }

}

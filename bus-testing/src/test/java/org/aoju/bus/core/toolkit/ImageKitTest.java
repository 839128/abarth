package org.aoju.bus.core.toolkit;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageKitTest {

    @Test
    public void scaleTest() {
        ImageKit.scale(FileKit.file("/data/pic/test.jpg"), FileKit.file("/data/pic/test_result.jpg"), 0.8f);
    }

    @Test
    public void scaleTest2() {
        ImageKit.scale(FileKit.file("/data/pic/test.jpg"), FileKit.file("/data/pic/test_result.jpg"), 0.8f);
    }

    @Test
    public void scalePngTest() {
        ImageKit.scale(FileKit.file("/data/test/test.png"), FileKit.file("/data/test/test_result.png"), 0.5f);
    }

    @Test
    public void scaleByWidthAndHeightTest() {
        ImageKit.scale(FileKit.file("/data/test/aaa.jpg"), FileKit.file("/data/test/aaa_result.jpg"), 100, 400, Color.BLUE);
    }

    @Test
    public void cutTest() {
        ImageKit.cut(FileKit.file("/data/face.jpg"), FileKit.file("/data/face_result.jpg"), new Rectangle(200, 200, 100, 100));
    }

    @Test
    public void rotateTest() throws IOException {
        java.awt.Image image = ImageKit.rotate(ImageIO.read(FileKit.file("/data/pic/366466.jpg")), 180);
        ImageKit.write(image, FileKit.file("/data/pic/result.png"));
    }

    @Test
    public void flipTest() {
        ImageKit.flip(FileKit.file("/data/logo.png"), FileKit.file("/data/result.png"));
    }

    @Test
    public void pressImgTest() {
        ImageKit.pressImage(FileKit.file("/data/picTest/1.jpg"), FileKit.file("/data/picTest/dest.jpg"), ImageKit.read(FileKit.file("/data/picTest/1432613.jpg")), 0, 0, 0.1f);
    }

    @Test
    public void pressTextTest() {
        ImageKit.pressText(//
                FileKit.file("/data/pic/face.jpg"), //
                FileKit.file("/data/pic/test2_result.png"), //
                "版权所有", Color.WHITE, //
                new Font("黑体", Font.BOLD, 100), //
                0, //
                0, //
                0.8f);
    }

    @Test
    public void sliceByRowsAndColsTest() {
        ImageKit.sliceByRowsAndCols(FileKit.file("/data/pic/1.png"), FileKit.file("/data/pic/dest"), 10, 10);
    }

    @Test
    public void convertTest() {
        ImageKit.convert(FileKit.file("/data/test2.png"), FileKit.file("/data/test2Convert.jpg"));
    }

    @Test
    public void writeTest() {
        ImageKit.write(ImageKit.read("/data/test2.png"), FileKit.file("/data/test2Write.jpg"));
    }

    @Test
    public void compressTest() {
        ImageKit.compress(FileKit.file("/data/pic/1111.png"), FileKit.file("/data/pic/1111_target.jpg"), 0.8f);
    }

    @Test
    public void copyTest() {
        BufferedImage image = ImageKit.copyImage(ImageKit.read("/data/pic/test.png"), BufferedImage.TYPE_INT_RGB);
        ImageKit.write(image, FileKit.file("/data/pic/test_dest.jpg"));
    }

}

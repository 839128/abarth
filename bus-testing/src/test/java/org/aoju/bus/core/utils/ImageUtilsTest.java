package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtilsTest {

    @Test
    public void scaleTest() {
        ImageUtils.scale(FileUtils.file("/data/pic/test.jpg"), FileUtils.file("/data/pic/test_result.jpg"), 0.8f);
    }

    @Test
    public void scaleTest2() {
        ImageUtils.scale(FileUtils.file("/data/pic/test.jpg"), FileUtils.file("/data/pic/test_result.jpg"), 0.8f);
    }

    @Test
    public void scalePngTest() {
        ImageUtils.scale(FileUtils.file("/data/test/test.png"), FileUtils.file("/data/test/test_result.png"), 0.5f);
    }

    @Test
    public void scaleByWidthAndHeightTest() {
        ImageUtils.scale(FileUtils.file("/data/test/aaa.jpg"), FileUtils.file("/data/test/aaa_result.jpg"), 100, 400, Color.BLUE);
    }

    @Test
    public void cutTest() {
        ImageUtils.cut(FileUtils.file("/data/face.jpg"), FileUtils.file("/data/face_result.jpg"), new Rectangle(200, 200, 100, 100));
    }

    @Test
    public void rotateTest() throws IOException {
        java.awt.Image image = ImageUtils.rotate(ImageIO.read(FileUtils.file("/data/pic/366466.jpg")), 180);
        ImageUtils.write(image, FileUtils.file("/data/pic/result.png"));
    }

    @Test
    public void flipTest() {
        ImageUtils.flip(FileUtils.file("/data/logo.png"), FileUtils.file("/data/result.png"));
    }

    @Test
    public void pressImgTest() {
        ImageUtils.pressImage(FileUtils.file("/data/picTest/1.jpg"), FileUtils.file("/data/picTest/dest.jpg"), ImageUtils.read(FileUtils.file("/data/picTest/1432613.jpg")), 0, 0, 0.1f);
    }

    @Test
    public void pressTextTest() {
        ImageUtils.pressText(//
                FileUtils.file("/data/pic/face.jpg"), //
                FileUtils.file("/data/pic/test2_result.png"), //
                "版权所有", Color.WHITE, //
                new Font("黑体", Font.BOLD, 100), //
                0, //
                0, //
                0.8f);
    }

    @Test
    public void sliceByRowsAndColsTest() {
        ImageUtils.sliceByRowsAndCols(FileUtils.file("/data/pic/1.png"), FileUtils.file("/data/pic/dest"), 10, 10);
    }

    @Test
    public void convertTest() {
        ImageUtils.convert(FileUtils.file("/data/test2.png"), FileUtils.file("/data/test2Convert.jpg"));
    }

    @Test
    public void writeTest() {
        ImageUtils.write(ImageUtils.read("/data/test2.png"), FileUtils.file("/data/test2Write.jpg"));
    }

    @Test
    public void compressTest() {
        ImageUtils.compress(FileUtils.file("/data/pic/1111.png"), FileUtils.file("/data/pic/1111_target.jpg"), 0.8f);
    }

    @Test
    public void copyTest() {
        BufferedImage image = ImageUtils.copyImage(ImageUtils.read("/data/pic/test.png"), BufferedImage.TYPE_INT_RGB);
        ImageUtils.write(image, FileUtils.file("/data/pic/test_dest.jpg"));
    }

}

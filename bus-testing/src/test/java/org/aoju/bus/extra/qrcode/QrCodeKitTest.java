package org.aoju.bus.extra.qrcode;

import org.aoju.bus.core.codec.Base64;
import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.File;

/**
 * 二维码工具类单元测试
 */
public class QrCodeKitTest {

    @Test
    @Ignore
    public void generateTest() {
        QrCodeKit.generate("https://www.aoju.org/", 300, 300, FileKit.file("/test/qrcode.jpg"));
    }

    @Test
    @Ignore
    public void generateCustomTest() {
        QrConfig config = new QrConfig();
        config.setMargin(0);
        config.setForeColor(Color.CYAN);
        // 背景色透明
        config.setBackColor(null);
        QrCodeKit.generate("https://www.aoju.org/", config, FileKit.file("/test/qrcode.png"));
    }

    @Test
    @Ignore
    public void generateWithLogoTest() {
        QrCodeKit.generate(
                "http://www.aoju.org/",
                QrConfig.create().setImg("/pic/face.jpg"),
                FileKit.file("/qrcode.jpg"));
    }

    @Test
    @Ignore
    public void decodeTest() {
        String decode = QrCodeKit.decode(FileKit.file("/test/qr.png"));
        Console.log(decode);
    }

    @Test
    @Ignore
    public void generateAsBase64Test() {
        String base64 = QrCodeKit.generate("http://www.aoju.org/", new QrConfig(400, 400), "png");
        System.out.println(base64);

        byte[] bytes = FileKit.readBytes(
                new File("/test/qr.png"));
        String encode = Base64.encode(bytes);
        String base641 = QrCodeKit.generate("http://www.aoju.org/", new QrConfig(400, 400), "png", encode);
        System.out.println(base641);

    }

}

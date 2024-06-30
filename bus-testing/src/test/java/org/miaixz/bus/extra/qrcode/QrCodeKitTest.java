package org.miaixz.bus.extra.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.codec.binary.Base64;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.IoKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.extra.image.ImageKit;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * 二维码工具类单元测试
 */
public class QrCodeKitTest {

    @Test
    public void generateTest() {
        final BufferedImage image = QrCodeKit.generate("https://www.miaixz.org/", 300, 300);
        Assertions.assertNotNull(image);
    }

    @Test
    @Disabled
    public void generateCustomTest() {
        final QrConfig config = QrConfig.of();
        config.setMargin(0);
        config.setForeColor(Color.CYAN);
        // 关闭ECI模式，部分老设备不支持ECI编码； 如果二维码中包含中文，则需要必须开启此配置
        // 现象：使用三方识别二维码工具、手机微信、支付宝扫描正常，使用扫码桩、扫码枪识别会多出来，类似：\000026、\000029字符
        config.setEnableEci(false);
        // 背景色透明
        config.setBackColor(null);
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        final String path = "/test/extra/qrcode/qrcodeCustom.png";
        QrCodeKit.generate("https://www.miaixz.org/", config, FileKit.touch(path));
    }

    @Test
    @Disabled
    public void generateWithLogoTest() {
        final String icon = "/test/extra/qrcode/logo.jpg";
        final String targetPath = "/test/extra/qrcode/qrcodeWithLogo.jpg";
        QrCodeKit.generate(//
                "https://www.miaixz.org/", //
                QrConfig.of().setImg(icon), //
                FileKit.touch(targetPath));
    }

    @Test
    @Disabled
    public void decodeTest() {
        final String decode = QrCodeKit.decode(FileKit.file("d:/test/pic/qr.png"));
        Console.log(decode);
    }

    @Test
    @Disabled
    public void decodeTest2() {
        // 条形码
        final String decode = QrCodeKit.decode(FileKit.file("/test/extra/qrcode/90.png"));
        Console.log(decode);
    }

    @Test
    public void generateAsBase64AndDecodeTest() {
        final String url = "https://www.miaixz.org/";
        String base64 = QrCodeKit.generateAsBase64DataUri(url, new QrConfig(400, 400), "png");
        Assertions.assertNotNull(base64);

        base64 = StringKit.removePrefix(base64, "data:image/png;base64,");
        final String decode = QrCodeKit.decode(IoKit.toStream(Base64.decode(base64)));
        Assertions.assertEquals(url, decode);
    }

    @Test
    @Disabled
    public void generateAsBase64Test2() {
        final byte[] bytes = FileKit.readBytes(new File("/test/extra/qrcode/qr.png"));
        final String base641 = QrCodeKit.generateAsBase64DataUri(
                "https://www.miaixz.org/",
                QrConfig.of(400, 400).setImg(bytes),
                "png"
        );
        Assertions.assertNotNull(base641);
    }

    @Test
    @Disabled
    public void decodeTest3() {
        final String decode = QrCodeKit.decode(ImageKit.read("/test/extra/qrcode/qr_a.png"), false, true);
        Console.log(decode);
    }

    @Test
    public void pdf417Test() {
        final BufferedImage image = QrCodeKit.generate("content111", QrConfig.of().setFormat(BarcodeFormat.PDF_417));
        Assertions.assertNotNull(image);
    }

    @Test
    public void generateDataMatrixTest() {
        final QrConfig qrConfig = QrConfig.of();
        qrConfig.setShapeHint(SymbolShapeHint.FORCE_RECTANGLE);
        final BufferedImage image = QrCodeKit.generate("content111", qrConfig.setFormat(BarcodeFormat.DATA_MATRIX));
        Assertions.assertNotNull(image);
        final QrConfig config = QrConfig.of();
        config.setShapeHint(SymbolShapeHint.FORCE_SQUARE);
        final BufferedImage imageSquare = QrCodeKit.generate("content111", qrConfig.setFormat(BarcodeFormat.DATA_MATRIX));
        Assertions.assertNotNull(imageSquare);
    }

    @Test
    @Disabled
    public void generateSvgTest() {
        final QrConfig qrConfig = QrConfig.of()
                .setImg("d:/test/pic/logo.jpg")
                .setForeColor(Color.blue)
                .setBackColor(Color.pink)
                .setRatio(8)
                .setErrorCorrection(ErrorCorrectionLevel.M)
                .setMargin(1);
        final String svg = QrCodeKit.generateAsSvg("https://www.miaixz.org/", qrConfig);
        Assertions.assertNotNull(svg);
        FileKit.writeString(svg, FileKit.touch("/test/extra/qrcode/qr.svg"), StandardCharsets.UTF_8);
    }

    @Test
    public void generateAsciiArtTest() {
        final QrConfig qrConfig = QrConfig.of()
                .setForeColor(Color.BLUE)
                .setBackColor(Color.MAGENTA)
                .setWidth(0)
                .setHeight(0).setMargin(1);
        final String asciiArt = QrCodeKit.generateAsAsciiArt("https://www.miaixz.org/", qrConfig);
        Assertions.assertNotNull(asciiArt);
        //Console.log(asciiArt);
    }

    @Test
    public void generateAsciiArtNoCustomColorTest() {
        final QrConfig qrConfig = QrConfig.of()
                .setForeColor(null)
                .setBackColor(null)
                .setWidth(0)
                .setHeight(0).setMargin(1);
        final String asciiArt = QrCodeKit.generateAsAsciiArt("https://www.miaixz.org/", qrConfig);
        Assertions.assertNotNull(asciiArt);
        //Console.log(asciiArt);
    }


    @Test
    @Disabled
    public void name() {
        //扫描二维码后   对应的链接正常
        String path = "https://juejin.cn/backend?name=%E5%BC%A0%E7%8F%8A&school=%E5%8E%A6%E9%97%A8%E5%A4%A7%E5%AD%A6";
        QrCodeKit.generate(path, QrConfig.of(), FileKit.file("/test/extra/qrcode/3030.png"));
    }

    @Test
    @Disabled
    void generateTest1() {
        QrCodeKit.generate("https://www.baidu.com/h5/monitorfile/index.html?sadfsfasdfsafsafasfasfsafasfasdfsafdsafsfasfafsfaasfsdfsfsafasfa",
                QrConfig.of().setWidth(600).setHeight(600).setMargin(0), new File("/test/extra/qrcode/3146.jpg"));
    }

    @Test
    @Disabled
    void generateTest2() {
        final QrConfig qrConfig = new QrConfig(300, 300);
        final File file = new File("/test/extra/qrcode/out.png");
        qrConfig.setImg(new File("/test/extra/qrcode/b2dd3614_868440.png"));
        QrCodeKit.generate("111", qrConfig, file);
    }

}

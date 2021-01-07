package org.aoju.bus.core.image;

import org.aoju.bus.core.image.element.TextElement;
import org.aoju.bus.core.lang.FileType;
import org.aoju.bus.core.lang.Scale;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageTest {

    @Test
    public void cutTest1() {
        Image.from(FileKit.file("/data/pic/face.jpg")).cut(0, 0, 200).write(FileKit.file("/data/pic/face_radis.png"));
    }

    @Test
    public void compressTest() {
        Image.from(FileKit.file("/data/test/4347273249269e3fb272341acc42d4e.jpg")).setQuality(0.8).write(FileKit.file("/data/test/test_dest.jpg"));
    }

    @Test
    public void roundTest() {
        Image.from(FileKit.file("/data/pic/face.jpg")).round(0.5).write(FileKit.file("/data/pic/face_round.png"));
    }


    /**
     * 简单测试
     *
     * @throws Exception
     */
    @Test
    public void simpleTest() throws Exception {

        //合成器和背景图（整个图片的宽高和相关计算依赖于背景图，所以背景图的大小是个基准）
        Image combiner = new Image("https://img.thebeastshop.com/combine_image/funny_topic/resource/bg_3x4.png", FileType.TYPE_JPG);

        //加图片元素（居中绘制，圆角，半透明）
        combiner.addImageElement("https://img.thebeastshop.com/image/20201130115835493501.png?x-oss-process=image/resize,m_pad,w_750,h_783/auto-orient,1/quality,q_90/format,jpg", 0, 300)
                .setCenter(true);

        //加文本元素
        combiner.addTextElement("周末大放送", 60, 100, 960)
                .setColor(Color.red);

        //合成图片
        combiner.merge();

        //保存文件（或getCombinedImageStream()并上传图片服务器）
        combiner.out("/test.jpg");

        //或者获取流（并上传oss等）
        //InputStream is = combiner.getCombinedImageStream();
        //String url = ossUtil.upload(is);
    }

    /**
     * 完整功能测试
     *
     * @throws Exception
     */
    @Test
    public void FullTest() throws Exception {
        String bgImageUrl = "https://img.thebeastshop.com/combine_image/funny_topic/resource/bg_3x4.png";                       //背景图（测试url形式）
        String qrCodeUrl = "http://imgtest.thebeastshop.com/file/combine_image/qrcodef3d132b46b474fe7a9cc6e76a511dfd5.jpg";     //二维码
        String productImageUrl = "https://img.thebeastshop.com/combine_image/funny_topic/resource/product_3x4.png";             //商品图
        BufferedImage waterMark = ImageIO.read(new URL("https://img.thebeastshop.com/combine_image/funny_topic/resource/water_mark.png"));  //水印图（测试BufferedImage形式）
        BufferedImage avatar = ImageIO.read(new URL("https://img.thebeastshop.com/member/privilege/level-icon/level-three.jpg"));           //头像
        String title = "# 最爱的家居";                                       //标题文本
        String content = "苏格拉底说：“如果没有那个桌子，可能就没有那个水壶”";  //内容文本

        //合成器和背景图（整个图片的宽高和相关计算依赖于背景图，所以背景图的大小是个基准）
        Image combiner = new Image(bgImageUrl, FileType.TYPE_JPG);
        combiner.setBackgroundBlur(30);     //设置背景高斯模糊（毛玻璃效果）

        //商品图（设置坐标、宽高和缩放模式，若按宽度缩放，则高度按比例自动计算）
        combiner.addImageElement(productImageUrl, 0, 160, 837, 0, Scale.Mode.WIDTH)
                .setRoundCorner(46)     //设置圆角
                .setCenter(true);       //居中绘制，会忽略x坐标参数，改为自动计算

        //标题（默认字体为“阿里巴巴普惠体”，也可以自己指定字体名称或Font对象）
        combiner.addTextElement(title, 55, 150, 1400);

        //内容（设置文本自动换行，需要指定最大宽度（超出则换行）、最大行数（超出则丢弃）、行高）
        combiner.addTextElement(content, "微软雅黑", 40, 150, 1480)
                .setAutoBreakLine(837, 2, 60);

        //头像（圆角设置一定的大小，可以把头像变成圆的）
        combiner.addImageElement(avatar, 200, 1200, 130, 130, Scale.Mode.OPTIONAL)
                .setRoundCorner(200)
                .setBlur(5);       //高斯模糊，毛玻璃效果

        //水印（设置透明度，0.0~1.0）
        combiner.addImageElement(waterMark, 630, 1200)
                .setAlpha(.8f)      //透明度，0.0~1.0
                .setRotate(15);     //旋转，0~360，按中心点旋转

        //二维码（强制按指定宽度、高度缩放）
        combiner.addImageElement(qrCodeUrl, 138, 1707, 186, 186, Scale.Mode.OPTIONAL);

        //元素对象也可以直接new，然后手动加入待绘制列表
        TextElement textPrice = new TextElement("￥1290", 40, 600, 1400);
        textPrice.setStrikeThrough(true);       //删除线
        combiner.addElement(textPrice);         //加入待绘制集合

        //动态计算位置
        int offsetPrice = textPrice.getX() + combiner.getFrontWidth(textPrice) + 10;
        combiner.addTextElement("￥999", 60, offsetPrice, 1400)
                .setColor(Color.red);

        //执行图片合并
        combiner.merge();

        //保存文件
        combiner.out("/test.png");

        //或者获取流（并上传oss等）
        //InputStream is = combiner.getCombinedImageStream();
        //String url = ossUtil.upload(is);
    }

    /**
     * 旋转测试
     *
     * @throws Exception
     */
    @Test
    public void rotateTest() throws Exception {
        String bg = "https://img.thebeastshop.com/combine_image/funny_topic/resource/bg_3x4.png";
        Image combiner = new Image(bg, FileType.TYPE_JPG);

        combiner.addTextElement("我觉得应该可以正常显示", 80, 300, 300)
                .setCenter(true);
        combiner.addTextElement("我觉得应该可以正常显示", 80, 300, 300).setColor(Color.red)
                .setCenter(true)
                .setRotate(40);


        combiner.addTextElement("测试一下多行文本换行加旋转的动作，不知道是否能正常显示", 80, 300, 600)
                .setStrikeThrough(true)
                .setAutoBreakLine(600, 2, 80);
        combiner.addTextElement("测试一下多行文本换行加旋转的动作，不知道是否能正常显示", 80, 300, 600).setColor(Color.red)
                .setStrikeThrough(true)
                .setAutoBreakLine(600, 2, 80)
                .setRotate(40);

        combiner.addImageElement("http://img.thebeastshop.com/images/index/imgs/8wzZ7St7KH.jpg", 300, 1000)
                .setCenter(true)
                .setRotate(45);

        combiner.merge();
        combiner.out("/test.jpg");
    }

    /**
     * 动态计算宽度测试
     *
     * @throws Exception
     */
    @Test
    public void dynamicWidthDemoTest() throws Exception {
        String bg = "https://img.thebeastshop.com/combine_image/funny_topic/resource/bg_3x4.png";
        Image combiner = new Image(bg, FileType.TYPE_JPG);

        String str1 = "您出征";
        String str2 = "某城市";     //内容不定，宽度也不定
        String str3 = "，共在前线战斗了";
        String str4 = "365";     //内容不定，宽度也不定
        String str5 = "天！";
        int fontSize = 60;
        int xxxFontSize = 80;

        int offsetX = 20;
        int y = 300;

        TextElement element1 = combiner.addTextElement(str1, fontSize, offsetX, y);
        offsetX += combiner.getFrontWidth(element1);

        TextElement element2 = combiner.addTextElement(str2, xxxFontSize, offsetX, y)
                .setColor(Color.red);
        offsetX += combiner.getFrontWidth(element2);

        TextElement element3 = combiner.addTextElement(str3, fontSize, offsetX, y);
        offsetX += combiner.getFrontWidth(element3);

        TextElement element4 = combiner.addTextElement(str4, xxxFontSize, offsetX, y)
                .setColor(Color.red);
        offsetX += combiner.getFrontWidth(element4);

        combiner.addTextElement(str5, fontSize, offsetX, y);

        BufferedImage img = ImageIO.read(new URL("http://img.thebeastshop.com/images/index/imgs/8wzZ7St7KH.jpg"));
        combiner.addImageElement(img, 20, 500);

        combiner.merge();
        combiner.out("/demo.jpg");
    }

    /**
     * Png透明背景图测试
     *
     * @throws IOException
     */
    @Test
    public void pngTest() throws Exception {

        BufferedImage bgImage = ImageIO.read(new File("/card.png"));   //背景是圆角透明图
        String content = "2021-12-12 到期";

        //如背景包含透明部分，一定要用OutputFormat.PNG格式，否则合成后透明部分会变黑
        Image combiner = new Image(bgImage, FileType.TYPE_PNG);

        //内容文本
        combiner.addTextElement(content, 38, 72, 260).setColor(Color.white);

        //合成图片
        combiner.merge();

        //上传oss
        combiner.out("/test.png");
    }

}

package org.aoju.bus.office.support.word;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.utils.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;
import java.io.File;

public class WordWriterTest {

    @Test
    @Ignore
    public void writeTest() {
        Word07Writer writer = new Word07Writer();
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
        writer.flush(FileUtils.file("test.docx"));
        writer.close();
        Console.log("OK");
    }

    @Test
    @Ignore
    public void writePicTest() {
        Word07Writer writer = new Word07Writer();
        writer.addPicture(new File("test.jpg"), 100, 200);
        // 写出到文件
        writer.flush(FileUtils.file("test.docx"));
        // 关闭
        writer.close();
    }

}

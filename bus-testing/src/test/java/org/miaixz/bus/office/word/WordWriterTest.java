package org.miaixz.bus.office.word;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ListKit;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WordWriterTest {

    @Test
    @Disabled
    public void writeTest() {
        final Word07Writer writer = new Word07Writer();
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
        writer.flush(FileKit.file("/test/office/wordWrite.docx"));
        writer.close();
        Console.log("OK");
    }

    @Test
    @Disabled
    public void writePicTest() {
        final Word07Writer writer = new Word07Writer();
        writer.addPicture(new File("/test/office/qrcodeCustom.jpg"), 100, 200);
        // 写出到文件
        writer.flush(FileKit.file("/test/office/writePic.docx"));
        // 关闭
        writer.close();
    }

    @Test
    @Disabled
    public void writeTableTest() {
        final Word07Writer writer = new Word07Writer();
        final Map<String, Object> map = new LinkedHashMap<>();
        map.put("姓名", "张三");
        map.put("年龄", "23");
        map.put("成绩", 88.32);
        map.put("是否合格", true);

        writer.addTable(ListKit.of(map));
        writer.flush(FileKit.file("/test/office/test.docx"));
    }

    @Test
    @Disabled
    public void writeMapAsTableTest() {
        final Word07Writer writer = new Word07Writer();

        final Map<String, Object> data = new LinkedHashMap<>();
        data.put("姓名", "张三");
        data.put("年龄", 23);
        data.put("成绩", 80.5);
        data.put("是否合格", true);
        data.put("考试日期", DateKit.now());

        final Map<String, Object> data2 = new LinkedHashMap<>();
        data2.put("姓名", "李四");
        data2.put("年龄", 4);
        data2.put("成绩", 59);
        data2.put("是否合格", false);
        data2.put("考试日期", DateKit.now());

        final ArrayList<Map<String, Object>> mapArrayList = ListKit.of(data, data2);

        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 13), "我是正文第一部分");
        writer.addTable(mapArrayList);
        // 写出到文件
        writer.flush(FileKit.file("/test/office/a.docx"));
        // 关闭
        writer.close();
    }

    @Test
    public void overflowTest() {
        final Word07Writer word07Writer = new Word07Writer();
        final List<Object> list = ListKit.of(false);
        final List<Object> list2 = ListKit.of(false);
        list.add("溢出测试");
        list2.add(list);
        word07Writer.addTable(list);
        word07Writer.close();
    }

    @Test
    @Disabled
    public void writeBeanAsTableTest() {
        final List<Vo> of = ListKit.of(
                new Vo("测试1", new BigDecimal(12), new BigDecimal(2)),
                new Vo("测试2", new BigDecimal(13), new BigDecimal(2)),
                new Vo("测试3", new BigDecimal(15), new BigDecimal(3)),
                new Vo("测试4", new BigDecimal(112), new BigDecimal(5))
        );

        DocxKit.getWriter()
                .addTable(of)
                .flush(FileKit.file("/test/office/beanValueTest.docx"))
                .close();
    }

    @Data
    @AllArgsConstructor
    private static class Vo {
        private String name;
        private BigDecimal amount;
        private BigDecimal onYear;
    }

}

package org.miaixz.bus.office.excel.writer;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.*;
import org.miaixz.bus.office.excel.BigExcelWriter;
import org.miaixz.bus.office.excel.ExcelKit;
import org.miaixz.bus.office.excel.ExcelWriter;
import org.miaixz.bus.office.excel.TestBean;
import org.miaixz.bus.office.excel.style.DefaultStyleSet;
import org.miaixz.bus.office.excel.style.Styles;

import java.util.*;

/**
 * 写出Excel单元测试
 */
public class BigExcelWriteTest {

    @Test
    @Disabled
    public void writeTest2() {
        final List<String> row = ListKit.of("姓名", "加班日期", "下班时间", "加班时长", "餐补", "车补次数", "车补", "总计");
        final BigExcelWriter overtimeWriter = ExcelKit.getBigWriter("/test/office/single_line.xlsx");
        overtimeWriter.write(row);
        overtimeWriter.close();
    }

    @Test
    @Disabled
    public void writeTest() {
        final List<?> row1 = ListKit.of("aaaaa", "bb", "cc", "dd", DateKit.now(), 3.22676575765);
        final List<?> row2 = ListKit.of("aa1", "bb1", "cc1", "dd1", DateKit.now(), 250.7676);
        final List<?> row3 = ListKit.of("aa2", "bb2", "cc2", "dd2", DateKit.now(), 0.111);
        final List<?> row4 = ListKit.of("aa3", "bb3", "cc3", "dd3", DateKit.now(), 35);
        final List<?> row5 = ListKit.of("aa4", "bb4", "cc4", "dd4", DateKit.now(), 28.00);

        final List<List<?>> rows = ListKit.of(row1, row2, row3, row4, row5);
        for (int i = 0; i < 400000; i++) {
            //超大列表写出测试
            rows.add(ObjectKit.clone(row1));
        }

        final String filePath = "e:/bigWriteTest.xlsx";
        FileKit.remove(FileKit.file(filePath));
        // 通过工具类创建writer
        final BigExcelWriter writer = ExcelKit.getBigWriter(filePath);

//		// 跳过当前行，即第一行，非必须，在此演示用
//		writer.passCurrentRow();
//		// 合并单元格后的标题行，使用默认标题样式
//		writer.merge(row1.size() - 1, "大数据测试标题");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
//		writer.autoSizeColumn(0, true);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    @Disabled
    public void mergeTest() {
        final List<?> row1 = ListKit.of("aa", "bb", "cc", "dd", DateKit.now(), 3.22676575765);
        final List<?> row2 = ListKit.of("aa1", "bb1", "cc1", "dd1", DateKit.now(), 250.7676);
        final List<?> row3 = ListKit.of("aa2", "bb2", "cc2", "dd2", DateKit.now(), 0.111);
        final List<?> row4 = ListKit.of("aa3", "bb3", "cc3", "dd3", DateKit.now(), 35);
        final List<?> row5 = ListKit.of("aa4", "bb4", "cc4", "dd4", DateKit.now(), 28.00);

        final List<List<?>> rows = ListKit.of(row1, row2, row3, row4, row5);

        // 通过工具类创建writer
        final BigExcelWriter writer = ExcelKit.getBigWriter("e:/mergeTest.xlsx");
        final CellStyle style = ((DefaultStyleSet) writer.getStyleSet()).getHeadCellStyle();
        Styles.setColor(style, IndexedColors.RED, FillPatternType.SOLID_FOREGROUND);

        // 跳过当前行，即第一行，非必须，在此演示用
        writer.passCurrentRow();
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        // 一次性写出内容，使用默认样式
        writer.write(rows);

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(new CellRangeAddress(7, 10, 4, 10), "测试Merge", false);

        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    @Disabled
    public void writeMapTest() {
        final Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateKit.now());

        final Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateKit.now());

        final ArrayList<Map<String, Object>> rows = ListKit.of(row1, row2);

        // 通过工具类创建writer
        final String path = "/test/office/bigWriteMapTest.xlsx";
        FileKit.remove(FileKit.file(path));
        final BigExcelWriter writer = ExcelKit.getBigWriter(path);

        //设置内容字体
        final Font font = writer.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);
        ((DefaultStyleSet) writer.getStyleSet()).setFont(font, true);

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    @Disabled
    public void writeMapTest2() {
        final Map<String, Object> row1 = MapKit.newHashMap(true);
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateKit.now());

        // 通过工具类创建writer
        final String path = "/test/office/bigWriteMapTest2.xlsx";
        FileKit.remove(FileKit.file(path));
        final BigExcelWriter writer = ExcelKit.getBigWriter(path);

        // 一次性写出内容，使用默认样式
        writer.writeRow(row1, true);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    @Disabled
    public void writeBeanTest() {
        final TestBean bean1 = new TestBean();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(DateKit.now());

        final TestBean bean2 = new TestBean();
        bean2.setName("李四");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(DateKit.now());

        final List<TestBean> rows = ListKit.of(bean1, bean2);
        // 通过工具类创建writer
        final String file = "/test/office/bigWriteBeanTest.xlsx";
        FileKit.remove(FileKit.file(file));
        final BigExcelWriter writer = ExcelKit.getBigWriter(file);
        //自定义标题
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("score", "分数");
        writer.addHeaderAlias("isPass", "是否通过");
        writer.addHeaderAlias("examDate", "考试时间");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "一班成绩单");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        // 关闭writer，释放内存
        writer.close();
    }

    @Test
    @Disabled
    public void writeCellValueTest() {
        final String path = "/test/office/cellValueTest.xlsx";
        FileKit.remove(FileKit.file(path));
        final BigExcelWriter writer = new BigExcelWriter(path);
        writer.writeCellValue(3, 5, "aaa");
        writer.close();
    }

    @Test
    @Disabled
    public void closeTest() {
        final Map<String, ?> map1 = MapKit.of("id", "123456");
        final Map<String, ?> map2 = MapKit.of("id", "123457");
        final List<?> data = Arrays.asList(map1, map2);
        final String destFilePath = "/test/office/closeTest.xlsx";//略
        FileKit.remove(FileKit.file(destFilePath));
        try (final ExcelWriter writer = ExcelKit.getBigWriter(destFilePath)) {
            writer.write(data).flush();
        }
    }

    @Test
    @Disabled
    public void issuesTest() {
        // 通过工具类创建writer
        final String path = "/test/office/1210.xlsx";
        FileKit.remove(FileKit.file(path));
        final BigExcelWriter writer = ExcelKit.getBigWriter(path);
        writer.addHeaderAlias("id", "SN");
        writer.addHeaderAlias("userName", "User Name");

        final List<Map<String, Object>> list = new ArrayList<>();
        list.add(new HashMap<>() {
            private static final long serialVersionUID = 1L;

            {
                put("id", 1);
                put("userName", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        });

        list.add(new HashMap<>() {
            private static final long serialVersionUID = 1L;

            {
                put("id", 2);
                put("userName", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            }
        });
        writer.write(list, true);
        writer.autoSizeColumnAll();
        writer.close();
    }

}

package org.miaixz.bus.office.csv;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.annotation.Alias;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.CollKit;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ResourceKit;

import java.util.List;
import java.util.Map;

public class CsvReaderTest {

    @Test
    public void readTest() {
        final CsvReader reader = new CsvReader();
        final CsvData data = reader.read(
                ResourceKit.getReader("/test/office/test.csv", Charset.UTF_8), true);
        Assertions.assertEquals("sss,sss", data.getRow(0).get(0));
        Assertions.assertEquals(1, data.getRow(0).getOriginalLineNumber());
        Assertions.assertEquals("性别", data.getRow(0).get(2));
        Assertions.assertEquals("关注\"对象\"", data.getRow(0).get(3));
    }

    @Test
    public void readMapListTest() {
        final CsvReader reader = CsvKit.getReader();
        final List<Map<String, String>> result = reader.readMapList(
                ResourceKit.getReader("/test/office/test_bean.csv"), true);

        Assertions.assertEquals("张三", result.get(0).get("姓名"));
        Assertions.assertEquals("男", result.get(0).get("gender"));
        Assertions.assertEquals("无", result.get(0).get("focus"));
        Assertions.assertEquals("33", result.get(0).get("age"));

        Assertions.assertEquals("李四", result.get(1).get("姓名"));
        Assertions.assertEquals("男", result.get(1).get("gender"));
        Assertions.assertEquals("好对象", result.get(1).get("focus"));
        Assertions.assertEquals("23", result.get(1).get("age"));

        Assertions.assertEquals("王妹妹", result.get(2).get("姓名"));
        Assertions.assertEquals("女", result.get(2).get("gender"));
        Assertions.assertEquals("特别关注", result.get(2).get("focus"));
        Assertions.assertEquals("22", result.get(2).get("age"));
    }

    @Test
    public void readAliasMapListTest() {
        final CsvReadConfig csvReadConfig = CsvReadConfig.defaultConfig();
        csvReadConfig.addHeaderAlias("姓名", "name");

        final CsvReader reader = CsvKit.getReader(csvReadConfig);
        final List<Map<String, String>> result = reader.readMapList(
                ResourceKit.getReader("test_bean.csv"), true);

        Assertions.assertEquals("张三", result.get(0).get("name"));
        Assertions.assertEquals("男", result.get(0).get("gender"));
        Assertions.assertEquals("无", result.get(0).get("focus"));
        Assertions.assertEquals("33", result.get(0).get("age"));

        Assertions.assertEquals("李四", result.get(1).get("name"));
        Assertions.assertEquals("男", result.get(1).get("gender"));
        Assertions.assertEquals("好对象", result.get(1).get("focus"));
        Assertions.assertEquals("23", result.get(1).get("age"));

        Assertions.assertEquals("王妹妹", result.get(2).get("name"));
        Assertions.assertEquals("女", result.get(2).get("gender"));
        Assertions.assertEquals("特别关注", result.get(2).get("focus"));
        Assertions.assertEquals("22", result.get(2).get("age"));
    }

    @Test
    public void readBeanListTest() {
        final CsvReader reader = CsvKit.getReader();
        final List<TestBean> result = reader.read(
                ResourceKit.getReader("test_bean.csv"), true, TestBean.class);

        Assertions.assertEquals("张三", result.get(0).getName());
        Assertions.assertEquals("男", result.get(0).getGender());
        Assertions.assertEquals("无", result.get(0).getFocus());
        Assertions.assertEquals(Integer.valueOf(33), result.get(0).getAge());

        Assertions.assertEquals("李四", result.get(1).getName());
        Assertions.assertEquals("男", result.get(1).getGender());
        Assertions.assertEquals("好对象", result.get(1).getFocus());
        Assertions.assertEquals(Integer.valueOf(23), result.get(1).getAge());

        Assertions.assertEquals("王妹妹", result.get(2).getName());
        Assertions.assertEquals("女", result.get(2).getGender());
        Assertions.assertEquals("特别关注", result.get(2).getFocus());
        Assertions.assertEquals(Integer.valueOf(22), result.get(2).getAge());
    }

    @Test
    @Disabled
    public void readTest2() {
        final CsvReader reader = CsvKit.getReader();
        final CsvData read = reader.read(FileKit.file("/test/office/test.csv"));
        for (final CsvRow strings : read) {
            Console.log(strings);
        }
    }

    @Test
    @Disabled
    public void readTest3() {
        final CsvReadConfig csvReadConfig = CsvReadConfig.defaultConfig();
        csvReadConfig.setContainsHeader(true);
        final CsvReader reader = CsvKit.getReader(csvReadConfig);
        final CsvData read = reader.read(FileKit.file("/test/office/ceshi.csv"));
        for (final CsvRow row : read) {
            Console.log(row.getByName("案件ID"));
        }
    }

    @Test
    public void lineNoTest() {
        final CsvReader reader = new CsvReader();
        final CsvData data = reader.read(
                ResourceKit.getReader("/test/office/test_lines.csv", Charset.UTF_8), true);
        Assertions.assertEquals(1, data.getRow(0).getOriginalLineNumber());
        Assertions.assertEquals("a,b,c,d", CollKit.join(data.getRow(0), ","));

        Assertions.assertEquals(4, data.getRow(2).getOriginalLineNumber());
        Assertions.assertEquals("q,w,e,r,我是一段\n带换行的内容",
                CollKit.join(data.getRow(2), ",").replace("\r", ""));

        // 文件中第3行数据，对应原始行号是6（从0开始）
        Assertions.assertEquals(6, data.getRow(3).getOriginalLineNumber());
        Assertions.assertEquals("a,s,d,f", CollKit.join(data.getRow(3), ","));
    }

    @Test
    public void lineLimitTest() {
        // 从原始第2行开始读取
        final CsvReader reader = new CsvReader(CsvReadConfig.defaultConfig().setBeginLineNo(2));
        final CsvData data = reader.read(
                ResourceKit.getReader("/test/office/test_lines.csv"), true);

        Assertions.assertEquals(2, data.getRow(0).getOriginalLineNumber());
        Assertions.assertEquals("1,2,3,4", CollKit.join(data.getRow(0), ","));

        Assertions.assertEquals(4, data.getRow(1).getOriginalLineNumber());
        Assertions.assertEquals("q,w,e,r,我是一段\n带换行的内容",
                CollKit.join(data.getRow(1), ",").replace("\r", ""));

        // 文件中第3行数据，对应原始行号是6（从0开始）
        Assertions.assertEquals(6, data.getRow(2).getOriginalLineNumber());
        Assertions.assertEquals("a,s,d,f", CollKit.join(data.getRow(2), ","));
    }

    @Test
    public void lineLimitWithHeaderTest() {
        // 从原始第2行开始读取
        final CsvReader reader = new CsvReader(CsvReadConfig.defaultConfig().setBeginLineNo(2).setContainsHeader(true));
        final CsvData data = reader.read(
                ResourceKit.getReader("/test/office/test_lines.csv"), true);

        Assertions.assertEquals(4, data.getRow(0).getOriginalLineNumber());
        Assertions.assertEquals("q,w,e,r,我是一段\n带换行的内容",
                CollKit.join(data.getRow(0), ",").replace("\r", ""));

        // 文件中第3行数据，对应原始行号是6（从0开始）
        Assertions.assertEquals(6, data.getRow(1).getOriginalLineNumber());
        Assertions.assertEquals("a,s,d,f", CollKit.join(data.getRow(1), ","));
    }

    @Test
    public void customConfigTest() {
        final CsvReader reader = CsvKit.getReader(
                CsvReadConfig.defaultConfig()
                        .setTextDelimiter('\'')
                        .setFieldSeparator(';'));
        final CsvData csvRows = reader.readFromString("123;456;'789;0'abc;");
        final CsvRow row = csvRows.getRow(0);
        Assertions.assertEquals("123", row.get(0));
        Assertions.assertEquals("456", row.get(1));
        Assertions.assertEquals("'789;0'abc", row.get(2));
    }

    @Test
    public void readDisableCommentTest() {
        final CsvReader reader = CsvKit.getReader(CsvReadConfig.defaultConfig().disableComment());
        final CsvData read = reader.read(
                ResourceKit.getReader("/test/office/test.csv"), true);
        final CsvRow row = read.getRow(0);
        Assertions.assertEquals("# 这是一行注释，读取时应忽略", row.get(0));
    }

    @Test
    @Disabled
    public void streamTest() {
        final CsvReader reader = CsvKit.getReader(ResourceKit.getReader("/test/office/test_bean.csv"));
        reader.stream().limit(2).forEach(Console::log);
    }

    @Test
    @Disabled
    public void issuesTest() {
        final CsvReader reader = CsvKit.getReader(ResourceKit.getReader("/test/office/2306.csv"));
        final CsvData csvData = reader.read();
        for (final CsvRow csvRow : csvData) {
            Console.log(csvRow);
        }
    }

    @Data
    private static class TestBean {
        @Alias("姓名")
        private String name;
        private String gender;
        private String focus;
        private Integer age;
    }

}

package org.miaixz.bus.office.csv;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.BeanKit;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvKitTest {

    @Test
    public void readTest() {
        final CsvReader reader = CsvKit.getReader();
        //从文件中读取CSV数据
        final CsvData data = reader.read(FileKit.file("/test/office/test.csv"));
        final List<CsvRow> rows = data.getRows();
        final CsvRow row0 = rows.get(0);
        Assertions.assertEquals("sss,sss", row0.get(0));
        Assertions.assertEquals("姓名", row0.get(1));
        Assertions.assertEquals("性别", row0.get(2));
        Assertions.assertEquals("关注\"对象\"", row0.get(3));
        Assertions.assertEquals("年龄", row0.get(4));
        Assertions.assertEquals("", row0.get(5));
        Assertions.assertEquals("\"", row0.get(6));
    }

    @Test
    public void readTest2() {
        final CsvReader reader = CsvKit.getReader();
        reader.read(FileKit.getUtf8Reader("/test/office/test.csv"), true, (csvRow) -> {
            // 只有一行，所以直接判断
            Assertions.assertEquals("sss,sss", csvRow.get(0));
            Assertions.assertEquals("姓名", csvRow.get(1));
            Assertions.assertEquals("性别", csvRow.get(2));
            Assertions.assertEquals("关注\"对象\"", csvRow.get(3));
            Assertions.assertEquals("年龄", csvRow.get(4));
            Assertions.assertEquals("", csvRow.get(5));
            Assertions.assertEquals("\"", csvRow.get(6));
        });
    }

    @Test
    @Disabled
    public void readTest3() {
        final CsvReader reader = CsvKit.getReader();
        final String path = FileKit.isWindows() ? "/test/office/test.csv" : "/test/office/test.csv";
        reader.read(FileKit.getUtf8Reader(path), true, Console::log);
    }

    @Test
    public void readCsvStr1() {
        final CsvData data = CsvKit.getReader().readFromString("# 这是一行注释，读取时应忽略\n" +
                "\"sss,sss\",姓名,\"性别\",关注\"对象\",年龄,\"\",\"\"\"\n");
        final List<CsvRow> rows = data.getRows();
        final CsvRow row0 = rows.get(0);
        Assertions.assertEquals("sss,sss", row0.get(0));
        Assertions.assertEquals("姓名", row0.get(1));
        Assertions.assertEquals("性别", row0.get(2));
        Assertions.assertEquals("关注\"对象\"", row0.get(3));
        Assertions.assertEquals("年龄", row0.get(4));
        Assertions.assertEquals("", row0.get(5));
        Assertions.assertEquals("\"", row0.get(6));
    }

    @Test
    public void readCsvStr2() {
        CsvKit.getReader().readFromString("# 这是一行注释，读取时应忽略\n" +
                "\"sss,sss\",姓名,\"性别\",关注\"对象\",年龄,\"\",\"\"\"\n", (csvRow) -> {
            // 只有一行，所以直接判断
            Assertions.assertEquals("sss,sss", csvRow.get(0));
            Assertions.assertEquals("姓名", csvRow.get(1));
            Assertions.assertEquals("性别", csvRow.get(2));
            Assertions.assertEquals("关注\"对象\"", csvRow.get(3));
            Assertions.assertEquals("年龄", csvRow.get(4));
            Assertions.assertEquals("", csvRow.get(5));
            Assertions.assertEquals("\"", csvRow.get(6));
        });
    }

    @Test
    @Disabled
    public void writeTest() {
        final String path = FileKit.isWindows() ? "/test/office/testWrite.csv" : "/test/office/testWrite1.csv";
        final CsvWriter writer = CsvKit.getWriter(path, Charset.UTF_8);
        writer.write(
                new String[]{"a1", "b1", "c1", "123345346456745756756785656"},
                new String[]{"a2", "b2", "c2"},
                new String[]{"a3", "b3", "c3"}
        );
    }

    @Test
    @Disabled
    public void writeBeansTest() {

        @Data
        class Student {
            Integer id;
            String name;
            Integer age;
        }

        final String path = FileKit.isWindows() ? "/test/office/testWriteBeans.csv" : "/test/office/testWriteBeans1.csv";
        final CsvWriter writer = CsvKit.getWriter(path, Charset.UTF_8);
        final List<Student> students = new ArrayList<>();
        final Student student1 = new Student();
        student1.setId(1);
        student1.setName("张三");
        student1.setAge(18);

        final Student student2 = new Student();
        student2.setId(2);
        student2.setName("李四");
        student2.setAge(22);

        final Student student3 = new Student();
        student3.setId(3);
        student3.setName("王五");
        student3.setAge(31);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        writer.writeBeans(students);
        writer.close();
    }

    @Test
    @Disabled
    public void readLfTest() {
        final CsvReader reader = CsvKit.getReader();
        final String path = FileKit.isWindows() ? "/test/office/rw_test.csv" : "/test/office/rw_test1.csv";
        final CsvData read = reader.read(FileKit.file(path));
        for (final CsvRow row : read) {
            Console.log(row);
        }
    }

    @Test
    @Disabled
    public void writeWrapTest() {
        final List<List<Object>> resultList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        list.add("\"name\"");
        list.add("\"code\"");
        resultList.add(list);

        list = new ArrayList<>();
        list.add("\"wang\"");
        list.add(1);
        resultList.add(list);

        final String path = FileKit.isWindows() ? "/test/office/csvWrapTest.csv" : "/test/office/csvWrapTest1.csv";
        final CsvWriter writer = CsvKit.getWriter(path, Charset.UTF_8);
        writer.write(resultList);
    }

    @Test
    @Disabled
    public void writeDataTest() {
        @Data
        @AllArgsConstructor
        class User {
            Integer userId;
            String username;
            String mobile;
        }

        final List<String> header = ListKit.view("用户id", "用户名", "手机号");
        final List<CsvRow> row = new ArrayList<>();

        final List<User> datas = new ArrayList<>();
        datas.add(new User(1, "张三", "18800001111"));
        datas.add(new User(2, "李四", "18800001112"));
        datas.add(new User(3, "王五", "18800001113"));
        datas.add(new User(4, "赵六", "18800001114"));

        //可以为null
        //Map<String, Integer> headMap = null;
        final Map<String, Integer> headMap = new HashMap<>();
        headMap.put("userId", 0);
        headMap.put("username", 1);
        headMap.put("mobile", 2);

        for (final User user : datas) {
            // row.size() + 1, 表示从第2行开始，第一行是标题栏
            row.add(new CsvRow(row.size() + 1, headMap,
                    BeanKit.beanToMap(user).values().stream().map(Object::toString).collect(Collectors.toList())));
        }

        final CsvData csvData = new CsvData(header, row);
        final String path = FileKit.isWindows() ? "/test/office/csvWriteDataTest.csv" : "/test/office/csvWriteDataTest1.csv";
        final CsvWriter writer = CsvKit.getWriter(path, Charset.UTF_8);
        writer.write(csvData);
    }

}

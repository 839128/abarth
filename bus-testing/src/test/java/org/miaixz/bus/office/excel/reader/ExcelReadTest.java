package org.miaixz.bus.office.excel.reader;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.function.BiConsumerX;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.MapKit;
import org.miaixz.bus.core.xyz.ObjectKit;
import org.miaixz.bus.core.xyz.ResourceKit;
import org.miaixz.bus.office.excel.ExcelKit;
import org.miaixz.bus.office.excel.ExcelReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Excel读取单元测试
 */
public class ExcelReadTest {

    @Test
    public void aliasTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/alias.xlsx"));

        //读取单个单元格内容测试
        final Object value = reader.readCellValue(1, 2);
        Assertions.assertEquals("仓库", value);

        final Map<String, String> headerAlias = MapKit.newHashMap();
        headerAlias.put("用户姓名", "userName");
        headerAlias.put("库房", "storageName");
        headerAlias.put("盘点权限", "checkPerm");
        headerAlias.put("领料审批权限", "allotAuditPerm");
        reader.setHeaderAlias(headerAlias);

        // 读取list时默认首个非空行为标题
        final List<List<Object>> read = reader.read(0, Integer.MAX_VALUE, true);
        Assertions.assertEquals("userName", read.get(0).get(0));
        Assertions.assertEquals("storageName", read.get(0).get(1));
        Assertions.assertEquals("checkPerm", read.get(0).get(2));
        Assertions.assertEquals("allotAuditPerm", read.get(0).get(3));

        final List<Map<String, Object>> readAll = reader.readAll();
        for (final Map<String, Object> map : readAll) {
            Assertions.assertTrue(map.containsKey("userName"));
            Assertions.assertTrue(map.containsKey("storageName"));
            Assertions.assertTrue(map.containsKey("checkPerm"));
            Assertions.assertTrue(map.containsKey("allotAuditPerm"));
        }
    }

    @Test
    public void excelReadTestOfEmptyLine() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/priceIndex.xls"));
        final List<Map<String, Object>> readAll = reader.readAll();

        Assertions.assertEquals(4, readAll.size());
    }

    @Test
    public void excelReadTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xlsx"));
        final List<List<Object>> readAll = reader.read();

        // 标题
        Assertions.assertEquals("姓名", readAll.get(0).get(0));
        Assertions.assertEquals("性别", readAll.get(0).get(1));
        Assertions.assertEquals("年龄", readAll.get(0).get(2));
        Assertions.assertEquals("鞋码", readAll.get(0).get(3));

        // 第一行
        Assertions.assertEquals("张三", readAll.get(1).get(0));
        Assertions.assertEquals("男", readAll.get(1).get(1));
        Assertions.assertEquals(11L, readAll.get(1).get(2));
        Assertions.assertEquals(41.5D, readAll.get(1).get(3));
    }

    @Test
    public void excelReadAsTextTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xlsx"));
        Assertions.assertNotNull(reader.readAsText(false));
    }

    @Test
    public void excel03ReadTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xls"));
        final List<List<Object>> readAll = reader.read();

        // 标题
        Assertions.assertEquals("姓名", readAll.get(0).get(0));
        Assertions.assertEquals("性别", readAll.get(0).get(1));
        Assertions.assertEquals("年龄", readAll.get(0).get(2));
        Assertions.assertEquals("分数", readAll.get(0).get(3));

        // 第一行
        Assertions.assertEquals("张三", readAll.get(1).get(0));
        Assertions.assertEquals("男", readAll.get(1).get(1));
        Assertions.assertEquals(11L, readAll.get(1).get(2));
        Assertions.assertEquals(33.2D, readAll.get(1).get(3));
    }

    @Test
    public void excel03ReadTest2() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xls"), "校园入学");
        final List<List<Object>> readAll = reader.read();

        // 标题
        Assertions.assertEquals("班级", readAll.get(0).get(0));
        Assertions.assertEquals("年级", readAll.get(0).get(1));
        Assertions.assertEquals("学校", readAll.get(0).get(2));
        Assertions.assertEquals("入学时间", readAll.get(0).get(3));
        Assertions.assertEquals("更新时间", readAll.get(0).get(4));
    }

    @Test
    public void excelReadToMapListTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xlsx"));
        final List<Map<String, Object>> readAll = reader.readAll();

        Assertions.assertEquals("张三", readAll.get(0).get("姓名"));
        Assertions.assertEquals("男", readAll.get(0).get("性别"));
        Assertions.assertEquals(11L, readAll.get(0).get("年龄"));
    }

    @Test
    public void excelReadToBeanListTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xlsx"));
        reader.addHeaderAlias("姓名", "name");
        reader.addHeaderAlias("年龄", "age");
        reader.addHeaderAlias("性别", "gender");
        reader.addHeaderAlias("鞋码", "shoeSize");

        final List<Person> all = reader.readAll(Person.class);
        Assertions.assertEquals("张三", all.get(0).getName());
        Assertions.assertEquals("男", all.get(0).getGender());
        Assertions.assertEquals(Integer.valueOf(11), all.get(0).getAge());
        Assertions.assertEquals(new BigDecimal("41.5"), all.get(0).getShoeSize());
    }

    @Test
    @Disabled
    public void excelReadToBeanListTest2() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/toBean.xlsx");
        reader.addHeaderAlias("姓名", "name");
        reader.addHeaderAlias("年龄", "age");
        reader.addHeaderAlias("性别", "gender");

        final List<Person> all = reader.read(0, 2, Person.class);
        for (final Person person : all) {
            Console.log(person);
        }
    }

    @Test
    @Disabled
    public void readDoubleTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/doubleTest.xls");
        final List<List<Object>> read = reader.read();
        for (final List<Object> list : read) {
            Console.log(list.get(8));
        }
    }

    @Test
    public void mergeReadTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/merge_test.xlsx");
        final List<List<Object>> read = reader.read();
        // 验证合并单元格在两行中都可以取到值
        Assertions.assertEquals(11L, read.get(1).get(2));
        Assertions.assertEquals(11L, read.get(2).get(2));
    }

    @Test
    @Disabled
    public void readCellsTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/merge_test.xlsx");
        reader.read((cell, value) -> Console.log("{}, {} {}", cell.getRowIndex(), cell.getColumnIndex(), value));
    }

    @Test
    @Disabled
    public void readTest() {
        // 测试合并单元格是否可以正常读到第一个单元格的值
        final ExcelReader reader = ExcelKit.getReader("/test/office/人员体检信息表.xlsx");
        final List<List<Object>> read = reader.read();
        for (final List<Object> list : read) {
            Console.log(list);
        }
    }

    @Test
    public void nullValueEditTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/null_cell_test.xlsx");
        reader.setCellEditor((cell, value) -> ObjectKit.defaultIfNull(value, "#"));
        final List<List<Object>> read = reader.read();

        // 对于任意一个单元格有值的情况下，之前的单元格值按照null处理
        Assertions.assertEquals(1, read.get(1).size());
        Assertions.assertEquals(2, read.get(2).size());
        Assertions.assertEquals(3, read.get(3).size());

        Assertions.assertEquals("#", read.get(2).get(0));
        Assertions.assertEquals("#", read.get(3).get(0));
        Assertions.assertEquals("#", read.get(3).get(1));
    }

    @Test
    @Disabled
    public void readEmptyTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/issue.xlsx");
        final List<Map<String, Object>> maps = reader.readAll();
        Console.log(maps);
    }

    @Test
    @Disabled
    public void readNullRowTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/1.-.xls");
        reader.read((BiConsumerX<Cell, Object>) Console::log);
    }

    @Test
    public void readColumnTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/aaa.xlsx"));
        final List<Object> objects = reader.readColumn(0, 1);

        Assertions.assertEquals(3, objects.size());
        Assertions.assertEquals("张三", objects.get(0));
        Assertions.assertEquals("李四", objects.get(1));
        Assertions.assertEquals("", objects.get(2));
    }

    @Test
    public void readColumnNPETest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/read_row_npe.xlsx"));
        reader.readColumn(0, 1);
    }

    @Test
    public void readIssueTest() {
        final ExcelReader reader = ExcelKit.getReader(ResourceKit.getStream("/test/office/read.xlsx"));
        final List<Map<String, Object>> read = reader.read(1, 2, 2);
        for (Map<String, Object> map : read) {
            Console.log(map);
        }
        //超出lastIndex 抛出相应提示：startRowIndex row index 4 is greater than last row index 2.
        //而非:Illegal Capacity: -1
        try {
            final List<Map<String, Object>> readGreaterIndex = reader.read(1, 4, 4);
        } catch (Exception e) {
            Console.log(e.toString());
        }
    }

    @Data
    public static class Person {
        private String name;
        private String gender;
        private Integer age;
        private BigDecimal shoeSize;
    }

}

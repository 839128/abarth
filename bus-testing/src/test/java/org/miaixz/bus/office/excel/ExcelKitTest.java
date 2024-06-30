package org.miaixz.bus.office.excel;

import org.apache.poi.ss.util.CellReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.office.excel.cell.CellKit;

import java.util.List;
import java.util.Map;

public class ExcelKitTest {

    @Test
    public void indexToColNameTest() {
        Assertions.assertEquals("A", CellKit.indexToColName(0));
        Assertions.assertEquals("B", CellKit.indexToColName(1));
        Assertions.assertEquals("C", CellKit.indexToColName(2));

        Assertions.assertEquals("AA", CellKit.indexToColName(26));
        Assertions.assertEquals("AB", CellKit.indexToColName(27));
        Assertions.assertEquals("AC", CellKit.indexToColName(28));

        Assertions.assertEquals("AAA", CellKit.indexToColName(702));
        Assertions.assertEquals("AAB", CellKit.indexToColName(703));
        Assertions.assertEquals("AAC", CellKit.indexToColName(704));
    }

    @Test
    public void colNameToIndexTest() {
        Assertions.assertEquals(704, CellKit.colNameToIndex("AAC"));
        Assertions.assertEquals(703, CellKit.colNameToIndex("AAB"));
        Assertions.assertEquals(702, CellKit.colNameToIndex("AAA"));

        Assertions.assertEquals(28, CellKit.colNameToIndex("AC"));
        Assertions.assertEquals(27, CellKit.colNameToIndex("AB"));
        Assertions.assertEquals(26, CellKit.colNameToIndex("AA"));

        Assertions.assertEquals(2, CellKit.colNameToIndex("C"));
        Assertions.assertEquals(1, CellKit.colNameToIndex("B"));
        Assertions.assertEquals(0, CellKit.colNameToIndex("A"));
    }

    @Test
    public void cellReferenceTest() {
        final CellReference a11 = new CellReference("A11");
        Assertions.assertEquals(0, a11.getCol());
        Assertions.assertEquals(10, a11.getRow());
    }

    @Test
    public void readAndWriteTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/aaa.xlsx");
        final ExcelWriter writer = reader.getWriter();
        writer.writeCellValue(1, 2, "设置值");
        writer.close();
    }

    @Test
    public void getReaderByBookFilePathAndSheetNameTest() {
        final ExcelReader reader = ExcelKit.getReader("/test/office/aaa.xlsx", "12");
        final List<Map<String, Object>> list = reader.readAll();
        reader.close();
        Assertions.assertEquals(1L, list.get(1).get("鞋码"));
    }

}

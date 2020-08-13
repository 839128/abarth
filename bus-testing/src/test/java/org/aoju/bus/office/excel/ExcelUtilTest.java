package org.aoju.bus.office.excel;

import org.aoju.bus.office.support.excel.ExcelKit;
import org.aoju.bus.office.support.excel.cell.CellLocation;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ExcelUtilTest {

    @Test
    public void indexToColNameTest() {
        Assertions.assertEquals("A", ExcelKit.indexToColName(0));
        Assertions.assertEquals("B", ExcelKit.indexToColName(1));
        Assertions.assertEquals("C", ExcelKit.indexToColName(2));

        Assertions.assertEquals("AA", ExcelKit.indexToColName(26));
        Assertions.assertEquals("AB", ExcelKit.indexToColName(27));
        Assertions.assertEquals("AC", ExcelKit.indexToColName(28));

        Assertions.assertEquals("AAA", ExcelKit.indexToColName(702));
        Assertions.assertEquals("AAB", ExcelKit.indexToColName(703));
        Assertions.assertEquals("AAC", ExcelKit.indexToColName(704));
    }

    @Test
    public void colNameToIndexTest() {
        Assertions.assertEquals(704, ExcelKit.colNameToIndex("AAC"));
        Assertions.assertEquals(703, ExcelKit.colNameToIndex("AAB"));
        Assertions.assertEquals(702, ExcelKit.colNameToIndex("AAA"));

        Assertions.assertEquals(28, ExcelKit.colNameToIndex("AC"));
        Assertions.assertEquals(27, ExcelKit.colNameToIndex("AB"));
        Assertions.assertEquals(26, ExcelKit.colNameToIndex("AA"));

        Assertions.assertEquals(2, ExcelKit.colNameToIndex("C"));
        Assertions.assertEquals(1, ExcelKit.colNameToIndex("B"));
        Assertions.assertEquals(0, ExcelKit.colNameToIndex("A"));
    }

    @Test
    public void toLocationTest() {
        final CellLocation a11 = ExcelKit.toLocation("A11");
        Assertions.assertEquals(0, a11.getX());
        Assertions.assertEquals(10, a11.getY());
    }

}

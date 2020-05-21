package org.aoju.bus.office.support.excel;

import org.aoju.bus.office.support.excel.cell.CellLocation;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ExcelUtilTest {

    @Test
    public void indexToColNameTest() {
        Assertions.assertEquals("A", ExcelUtils.indexToColName(0));
        Assertions.assertEquals("B", ExcelUtils.indexToColName(1));
        Assertions.assertEquals("C", ExcelUtils.indexToColName(2));

        Assertions.assertEquals("AA", ExcelUtils.indexToColName(26));
        Assertions.assertEquals("AB", ExcelUtils.indexToColName(27));
        Assertions.assertEquals("AC", ExcelUtils.indexToColName(28));

        Assertions.assertEquals("AAA", ExcelUtils.indexToColName(702));
        Assertions.assertEquals("AAB", ExcelUtils.indexToColName(703));
        Assertions.assertEquals("AAC", ExcelUtils.indexToColName(704));
    }

    @Test
    public void colNameToIndexTest() {
        Assertions.assertEquals(704, ExcelUtils.colNameToIndex("AAC"));
        Assertions.assertEquals(703, ExcelUtils.colNameToIndex("AAB"));
        Assertions.assertEquals(702, ExcelUtils.colNameToIndex("AAA"));

        Assertions.assertEquals(28, ExcelUtils.colNameToIndex("AC"));
        Assertions.assertEquals(27, ExcelUtils.colNameToIndex("AB"));
        Assertions.assertEquals(26, ExcelUtils.colNameToIndex("AA"));

        Assertions.assertEquals(2, ExcelUtils.colNameToIndex("C"));
        Assertions.assertEquals(1, ExcelUtils.colNameToIndex("B"));
        Assertions.assertEquals(0, ExcelUtils.colNameToIndex("A"));
    }

    @Test
    public void toLocationTest() {
        final CellLocation a11 = ExcelUtils.toLocation("A11");
        Assertions.assertEquals(0, a11.getX());
        Assertions.assertEquals(10, a11.getY());
    }

}

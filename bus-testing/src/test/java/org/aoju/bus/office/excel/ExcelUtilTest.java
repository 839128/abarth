package org.aoju.bus.office.excel;

import org.aoju.bus.office.support.excel.ExcelKit;
import org.aoju.bus.office.support.excel.cell.CellLocation;
import org.junit.Test;
import org.junit.Assert;

public class ExcelUtilTest {

    @Test
    public void indexToColNameTest() {
        Assert.assertEquals("A", ExcelKit.indexToColName(0));
        Assert.assertEquals("B", ExcelKit.indexToColName(1));
        Assert.assertEquals("C", ExcelKit.indexToColName(2));

        Assert.assertEquals("AA", ExcelKit.indexToColName(26));
        Assert.assertEquals("AB", ExcelKit.indexToColName(27));
        Assert.assertEquals("AC", ExcelKit.indexToColName(28));

        Assert.assertEquals("AAA", ExcelKit.indexToColName(702));
        Assert.assertEquals("AAB", ExcelKit.indexToColName(703));
        Assert.assertEquals("AAC", ExcelKit.indexToColName(704));
    }

    @Test
    public void colNameToIndexTest() {
        Assert.assertEquals(704, ExcelKit.colNameToIndex("AAC"));
        Assert.assertEquals(703, ExcelKit.colNameToIndex("AAB"));
        Assert.assertEquals(702, ExcelKit.colNameToIndex("AAA"));

        Assert.assertEquals(28, ExcelKit.colNameToIndex("AC"));
        Assert.assertEquals(27, ExcelKit.colNameToIndex("AB"));
        Assert.assertEquals(26, ExcelKit.colNameToIndex("AA"));

        Assert.assertEquals(2, ExcelKit.colNameToIndex("C"));
        Assert.assertEquals(1, ExcelKit.colNameToIndex("B"));
        Assert.assertEquals(0, ExcelKit.colNameToIndex("A"));
    }

    @Test
    public void toLocationTest() {
        final CellLocation a11 = ExcelKit.toLocation("A11");
        Assert.assertEquals(0, a11.getX());
        Assert.assertEquals(10, a11.getY());
    }

}

package org.miaixz.bus.office.excel.reader;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.jupiter.api.Assertions;
import org.miaixz.bus.office.excel.ExcelKit;
import org.miaixz.bus.office.excel.ExcelReader;
import org.miaixz.bus.office.excel.cell.CellEditor;

import java.io.Serializable;
import java.util.List;

public class CellEditorTest {

    @org.junit.jupiter.api.Test
    public void readTest() {
        final ExcelReader excelReader = ExcelKit.getReader("/test/office/cell_editor_test.xlsx");
        excelReader.setCellEditor(new ExcelHandler());
        final List<Test> excelReaderObjects = excelReader.readAll(Test.class);

        Assertions.assertEquals("0", excelReaderObjects.get(0).getTest1());
        Assertions.assertEquals("b", excelReaderObjects.get(0).getTest2());
        Assertions.assertEquals("0", excelReaderObjects.get(1).getTest1());
        Assertions.assertEquals("b1", excelReaderObjects.get(1).getTest2());
        Assertions.assertEquals("0", excelReaderObjects.get(2).getTest1());
        Assertions.assertEquals("c2", excelReaderObjects.get(2).getTest2());
    }

    @AllArgsConstructor
    @Data
    public static class Test implements Serializable {
        private static final long serialVersionUID = 1L;

        private String test1;
        private String test2;
    }

    public static class ExcelHandler implements CellEditor {
        @Override
        public Object edit(final Cell cell, Object o) {
            if (cell.getColumnIndex() == 0 && cell.getRowIndex() != 0) {
                o = "0";
            }
            return o;
        }
    }

}

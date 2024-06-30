package org.miaixz.bus.office.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkbookKitTest {

    @Test
    public void createBookTest() {
        Workbook book = WorkbookKit.createBook(true);
        Assertions.assertNotNull(book);

        book = WorkbookKit.createBook(false);
        Assertions.assertNotNull(book);
    }

}

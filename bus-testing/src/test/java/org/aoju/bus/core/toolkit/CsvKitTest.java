package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Assert;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.text.csv.CsvData;
import org.aoju.bus.core.text.csv.CsvReader;
import org.aoju.bus.core.text.csv.CsvRow;
import org.aoju.bus.core.text.csv.CsvWriter;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CsvKitTest {

    @Test
    public void readTest() {
        CsvReader reader = CsvKit.getReader();
        //从文件中读取CSV数据
        CsvData data = reader.read(FileKit.file("test.csv"));
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            Assert.notEmpty(csvRow.getRawList());
        }
    }

    @Test
    public void readTest2() {
        CsvReader reader = CsvKit.getReader();
        reader.read(FileKit.getUtf8Reader("test.csv"), (csvRow) -> {
            Assert.notEmpty(csvRow.getRawList());
        });
    }

    @Test
    public void writeTest() {
        CsvWriter writer = CsvKit.getWriter("/data/testWrite.csv", Charset.UTF_8);
        writer.write(
                new String[]{"a1", "b1", "c1", "123345346456745756756785656"},
                new String[]{"a2", "b2", "c2"},
                new String[]{"a3", "b3", "c3"}
        );
    }

}

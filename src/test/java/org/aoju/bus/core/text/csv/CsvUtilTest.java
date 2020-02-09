package org.aoju.bus.core.text.csv;

import org.aoju.bus.core.lang.Assert;
import org.aoju.bus.core.lang.Charset;
import org.aoju.bus.core.utils.CsvUtils;
import org.aoju.bus.core.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CsvUtilTest {

    @Test
    public void readTest() {
        CsvReader reader = CsvUtils.getReader();
        //从文件中读取CSV数据
        CsvData data = reader.read(FileUtils.file("test.csv"));
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            Assert.notEmpty(csvRow.getRawList());
        }
    }

    @Test
    public void readTest2() {
        CsvReader reader = CsvUtils.getReader();
        reader.read(FileUtils.getUtf8Reader("test.csv"), (csvRow) -> {
            Assert.notEmpty(csvRow.getRawList());
        });
    }

    @Test
    public void writeTest() {
        CsvWriter writer = CsvUtils.getWriter("/data/testWrite.csv", Charset.UTF_8);
        writer.write(
                new String[]{"a1", "b1", "c1", "123345346456745756756785656"},
                new String[]{"a2", "b2", "c2"},
                new String[]{"a3", "b3", "c3"}
        );
    }

}

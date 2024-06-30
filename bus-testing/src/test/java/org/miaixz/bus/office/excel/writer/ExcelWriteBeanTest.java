package org.miaixz.bus.office.excel.writer;

import lombok.Getter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.office.excel.ExcelKit;
import org.miaixz.bus.office.excel.ExcelWriter;

public class ExcelWriteBeanTest {
    @Test
    @Disabled
    public void writeRowTest() {
        final MyBean bean = new MyBean("value1", "value2");

        final ExcelWriter writer = ExcelKit.getWriter("/test/office/writeRowTest.xlsx");
        writer.writeRow(bean, true);
        writer.close();
    }

    @Getter
    static class MyBean {
        private final String property1;
        private final String property2;

        public MyBean(final String property1, final String property2) {
            this.property1 = property1;
            this.property2 = property2;
        }
    }

}

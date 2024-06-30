package org.miaixz.bus.office;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.IoKit;

import java.io.InputStream;

public class BuilderTest {

    @Test
    public void xlsTest() {
        final InputStream in = FileKit.getInputStream("/test/office/aaa.xls");
        try {
            Assertions.assertTrue(Builder.isXls(in));
            Assertions.assertFalse(Builder.isXlsx(in));
        } finally {
            IoKit.closeQuietly(in);
        }
    }

    @Test
    public void xlsxTest() {
        final InputStream in = FileKit.getInputStream("/test/office/aaa.xlsx");
        try {
            Assertions.assertFalse(Builder.isXls(in));
            Assertions.assertTrue(Builder.isXlsx(in));
        } finally {
            IoKit.closeQuietly(in);
        }
    }

}

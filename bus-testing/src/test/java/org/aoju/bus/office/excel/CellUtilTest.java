package org.aoju.bus.office.excel;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.aoju.bus.core.lang.Console;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.junit.jupiter.api.Test;

public class CellUtilTest {

    @Test
    @Ignore
    public void isDateTest() {
        String[] all = BuiltinFormats.getAll();
        for (int i = 0; i < all.length; i++) {
            Console.log("{} {}", i, all[i]);
        }
    }

}

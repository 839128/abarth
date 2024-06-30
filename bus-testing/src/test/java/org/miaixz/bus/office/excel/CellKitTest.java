package org.miaixz.bus.office.excel;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;

public class CellKitTest {

    @Test
    @Disabled
    public void isDateTest() {
        final String[] all = BuiltinFormats.getAll();
        for (int i = 0; i < all.length; i++) {
            Console.log("{} {}", i, all[i]);
        }
    }

}

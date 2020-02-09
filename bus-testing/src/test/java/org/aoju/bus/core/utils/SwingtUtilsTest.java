package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SwingtUtilsTest {

    @Test
    public void captureScreenTest() {
        SwingUtils.captureScreen(FileUtils.file("/data/screen.jpg"));
    }

    @Test
    public void browseTest() {
        SwingUtils.browse("https://www.aoju.org");
    }

    @Test
    public void setAndGetStrTest() {
        try {
            SwingUtils.setStr("test");

            String test = SwingUtils.getStr();
            Assertions.assertEquals("test", test);
        } catch (java.awt.HeadlessException e) {
            // ignore
        }
    }

}

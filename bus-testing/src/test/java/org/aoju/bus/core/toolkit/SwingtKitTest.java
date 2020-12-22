package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;


public class SwingtKitTest {

    @Test
    public void captureScreenTest() {
        SwingKit.captureScreen(FileKit.file("/data/screen.jpg"));
    }

    @Test
    public void browseTest() {
        SwingKit.browse("https://www.aoju.org");
    }

    @Test
    public void setAndGetStrTest() {
        try {
            SwingKit.setStr("test");

            String test = SwingKit.getStr();
            Assert.assertEquals("test", test);
        } catch (java.awt.HeadlessException e) {
            // ignore
        }
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class ColorKitTest {

    @Test
    public void getColorTest() {
        final Color blue = ColorKit.getColor("blue");
        Assertions.assertEquals(Color.BLUE, blue);
    }

    @Test
    public void toCssRgbTest() {
        final String s = ColorKit.toCssRgb(Color.BLUE);
        Assertions.assertEquals("rgb(0,0,255)", s);
    }

    @Test
    public void toCssRgbaTest() {
        final String s = ColorKit.toCssRgba(Color.BLUE);
        Assertions.assertEquals("rgba(0,0,255,1.0)", s);
    }

}

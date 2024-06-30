package org.miaixz.bus.core.text.placeholder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringFormatterTest {

    @Test
    public void formatTest() {
        //通常使用
        final String result1 = StringFormatter.format("this is {} for {}", "a", "b");
        Assertions.assertEquals("this is a for b", result1);

        //转义{}
        final String result2 = StringFormatter.format("this is \\{} for {}", "a", "b");
        Assertions.assertEquals("this is {} for a", result2);

        //转义\
        final String result3 = StringFormatter.format("this is \\\\{} for {}", "a", "b");
        Assertions.assertEquals("this is \\a for b", result3);
    }

    @Test
    public void formatWithTest() {
        //通常使用
        final String result1 = StringFormatter.formatWith("this is ? for ?", "?", "a", "b");
        Assertions.assertEquals("this is a for b", result1);

        //转义?
        final String result2 = StringFormatter.formatWith("this is \\? for ?", "?", "a", "b");
        Assertions.assertEquals("this is ? for a", result2);

        //转义\
        final String result3 = StringFormatter.formatWith("this is \\\\? for ?", "?", "a", "b");
        Assertions.assertEquals("this is \\a for b", result3);
    }

    @Test
    public void formatWithTest2() {
        //通常使用
        final String result1 = StringFormatter.formatWith("this is $$$ for $$$", "$$$", "a", "b");
        Assertions.assertEquals("this is a for b", result1);

        //转义?
        final String result2 = StringFormatter.formatWith("this is \\$$$ for $$$", "$$$", "a", "b");
        Assertions.assertEquals("this is $$$ for a", result2);

        //转义\
        final String result3 = StringFormatter.formatWith("this is \\\\$$$ for $$$", "$$$", "a", "b");
        Assertions.assertEquals("this is \\a for b", result3);
    }

}

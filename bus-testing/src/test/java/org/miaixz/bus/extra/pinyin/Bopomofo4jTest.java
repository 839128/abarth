package org.miaixz.bus.extra.pinyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Bopomofo4jTest {

    final PinyinProvider engine = PinyinKit.createEngine("bopomofo4j");

    @Test
    public void getFirstLetterByBopomofo4jTest() {
        final String result = engine.getFirstLetter("大海", "");
        Assertions.assertEquals("dh", result);
    }

    @Test
    public void getPinyinByBopomofo4jTest() {
        final String pinyin = engine.getPinyin("你好m", " ");
        Assertions.assertEquals("ni haom", pinyin);
    }

}

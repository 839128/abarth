package org.miaixz.bus.extra.pinyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TinyPinyinTest {

    final PinyinProvider engine = PinyinKit.createEngine("tinypinyin");

    @Test
    public void getFirstLetterByPinyin4jTest() {
        final String result = engine.getFirstLetter("大海", "");
        Assertions.assertEquals("dh", result);
    }

    @Test
    public void getPinyinByPinyin4jTest() {
        final String pinyin = engine.getPinyin("你好m", " ");
        Assertions.assertEquals("ni hao m", pinyin);
    }

}

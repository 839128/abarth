package org.miaixz.bus.extra.pinyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HoubbPinyinTest {

    final PinyinProvider engine = PinyinKit.createEngine("houbb");

    @Test
    public void getFirstLetterTest() {
        final String result = engine.getFirstLetter("大海", "");
        Assertions.assertEquals("dh", result);
    }

    @Test
    public void getPinyinTest() {
        final String pinyin = engine.getPinyin("你好m", " ");
        Assertions.assertEquals("ni hao m", pinyin);
    }

}

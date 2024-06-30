package org.miaixz.bus.extra.pinyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PinyinUtilTest {

    @Test
    public void getPinyinTest() {
        final String pinyin = PinyinKit.getPinyin("你好呀", " ");
        Assertions.assertEquals("ni hao ya", pinyin);
    }

    @Test
    public void getFirstLetterTest() {
        final String result = PinyinKit.getFirstLetter("M是第一个", ", ");
        Assertions.assertEquals("m, s, d, y, g", result);
    }

    @Test
    public void getFirstLetterTest2() {
        final String result = PinyinKit.getFirstLetter("果阳", ", ");
        Assertions.assertEquals("g, y", result);
    }

}

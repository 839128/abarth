package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharUtilsTest {

    @Test
    public void trimTest() {
        //此字符串中的第一个字符为不可见字符: '\u202a'
        String str = "/data/aaa.txt";
        Assertions.assertEquals('\u202a', str.charAt(0));
        Assertions.assertTrue(CharUtils.isBlankChar(str.charAt(0)));
    }

    @Test
    public void isEmojiTest() {
        String a = "莉🌹";
        Assertions.assertFalse(CharUtils.isEmoji(a.charAt(0)));
        Assertions.assertTrue(CharUtils.isEmoji(a.charAt(1)));

    }
}

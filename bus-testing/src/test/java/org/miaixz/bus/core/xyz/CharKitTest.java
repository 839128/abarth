package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharKitTest {

    @Test
    public void trimTest() {
        //此字符串中的第一个字符为不可见字符: '\u202a'
        final String str = "/test/core/tone.txt";
        Assertions.assertEquals('\u202a', str.charAt(0));
        Assertions.assertTrue(CharKit.isBlankChar(str.charAt(0)));
    }

    @Test
    public void isEmojiTest() {
        final String a = "莉🌹";
        Assertions.assertFalse(CharKit.isEmoji(a.charAt(0)));
        Assertions.assertTrue(CharKit.isEmoji(a.charAt(1)));

    }

    @Test
    public void isCharTest() {
        final char a = 'a';
        Assertions.assertTrue(CharKit.isChar(a));
    }


    @Test
    public void isBlankCharTest() {
        final char a = '\u00A0';
        Assertions.assertTrue(CharKit.isBlankChar(a));

        final char a2 = '\u0020';
        Assertions.assertTrue(CharKit.isBlankChar(a2));

        final char a3 = '\u3000';
        Assertions.assertTrue(CharKit.isBlankChar(a3));

        final char a4 = '\u0000';
        Assertions.assertTrue(CharKit.isBlankChar(a4));
    }

    @Test
    public void toCloseCharTest() {
        Assertions.assertEquals('②', CharKit.toCloseChar('2'));
        Assertions.assertEquals('Ⓜ', CharKit.toCloseChar('M'));
        Assertions.assertEquals('ⓡ', CharKit.toCloseChar('r'));
    }

    @Test
    public void toCloseByNumberTest() {
        Assertions.assertEquals('②', CharKit.toCloseByNumber(2));
        Assertions.assertEquals('⑫', CharKit.toCloseByNumber(12));
        Assertions.assertEquals('⑳', CharKit.toCloseByNumber(20));
    }

    @Test
    public void issueTest() {
        char c = '\u3164';
        Assertions.assertTrue(CharKit.isBlankChar(c));

        c = '\u2800';
        Assertions.assertTrue(CharKit.isBlankChar(c));
    }

}

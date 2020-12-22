package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

public class CharKitTest {

    @Test
    public void trimTest() {
        //此字符串中的第一个字符为不可见字符: '\u202a'
        String str = "/data/aaa.txt";
        Assert.assertEquals('\u202a', str.charAt(0));
        Assert.assertTrue(CharKit.isBlankChar(str.charAt(0)));
    }

    @Test
    public void isEmojiTest() {
        String a = "莉🌹";
        Assert.assertFalse(CharKit.isEmoji(a.charAt(0)));
        Assert.assertTrue(CharKit.isEmoji(a.charAt(1)));

    }
}

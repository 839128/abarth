package org.aoju.bus.extra.emoji;

import org.junit.Assert;
import org.junit.Test;

public class EmojiKitTest {

    @Test
    public void toUnicodeTest() {
        String emoji = EmojiKit.toUnicode(":smile:");
        Assert.assertEquals("😄", emoji);
    }

    @Test
    public void toAliasTest() {
        String alias = EmojiKit.toAlias("😄");
        Assert.assertEquals(":smile:", alias);
    }

}

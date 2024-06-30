package org.miaixz.bus.extra.emoji;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmojiKitTest {

    @Test
    public void toUnicodeTest() {
        final String emoji = EmojiKit.toUnicode(":smile:");
        Assertions.assertEquals("😄", emoji);
    }

    @Test
    public void toAliasTest() {
        final String alias = EmojiKit.toAlias("😄");
        Assertions.assertEquals(":smile:", alias);
    }

    @Test
    public void containsEmojiTest() {
        final boolean containsEmoji = EmojiKit.containsEmoji("测试一下是否包含EMOJ:😄");
        Assertions.assertTrue(containsEmoji);
        final boolean notContainsEmoji = EmojiKit.containsEmoji("不包含EMOJ:^_^");
        Assertions.assertFalse(notContainsEmoji);
    }

}

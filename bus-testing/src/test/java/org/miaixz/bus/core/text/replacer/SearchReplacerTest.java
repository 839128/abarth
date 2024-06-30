package org.miaixz.bus.core.text.replacer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.text.CharsBacker;
import org.miaixz.bus.core.xyz.StringKit;

public class SearchReplacerTest {

    @Test
    public void replaceOnlyTest() {
        final String result = CharsBacker.replace(",", ",", "|");
        Assertions.assertEquals("|", result);
    }

    @Test
    public void replaceTestAtBeginAndEnd() {
        final String result = CharsBacker.replace(",abcdef,", ",", "|");
        Assertions.assertEquals("|abcdef|", result);
    }

    @Test
    public void replaceTest() {
        final String str = "AAABBCCCBBDDDBB";
        String replace = StringKit.replace(str, 0, "BB", "22", false);
        Assertions.assertEquals("AAA22CCC22DDD22", replace);

        replace = StringKit.replace(str, 3, "BB", "22", false);
        Assertions.assertEquals("AAA22CCC22DDD22", replace);

        replace = StringKit.replace(str, 4, "BB", "22", false);
        Assertions.assertEquals("AAABBCCC22DDD22", replace);

        replace = StringKit.replace(str, 4, "bb", "22", true);
        Assertions.assertEquals("AAABBCCC22DDD22", replace);

        replace = StringKit.replace(str, 4, "bb", "", true);
        Assertions.assertEquals("AAABBCCCDDD", replace);

        replace = StringKit.replace(str, 4, "bb", null, true);
        Assertions.assertEquals("AAABBCCCDDD", replace);
    }

}

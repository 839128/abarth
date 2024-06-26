package org.miaixz.bus.core.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.text.finder.CharFinder;
import org.miaixz.bus.core.text.finder.LengthFinder;
import org.miaixz.bus.core.text.finder.PatternFinder;
import org.miaixz.bus.core.text.finder.StringFinder;

import java.util.List;
import java.util.regex.Pattern;

public class StringSplitterTest {

    @Test
    public void splitByCharTest() {
        final String str1 = "a, ,,efedsfs,   ddf,";

        //不忽略""
        final StringSplitter splitIter = new StringSplitter(str1,
                new CharFinder(',', false),
                Integer.MAX_VALUE,
                false
        );
        Assertions.assertEquals(6, splitIter.toList(false).size());
    }

    @Test
    public void splitByCharIgnoreCaseTest() {
        final String str1 = "a, ,,eAedsas,   ddf,";

        //不忽略""
        final StringSplitter splitIter = new StringSplitter(str1,
                new CharFinder('a', true),
                Integer.MAX_VALUE,
                false
        );
        Assertions.assertEquals(4, splitIter.toList(false).size());
    }

    @Test
    public void splitByCharIgnoreEmptyTest() {
        final String str1 = "a, ,,efedsfs,   ddf,";

        final StringSplitter splitIter = new StringSplitter(str1,
                new CharFinder(',', false),
                Integer.MAX_VALUE,
                true
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    public void splitByCharTrimTest() {
        final String str1 = "a, ,,efedsfs,   ddf,";

        final StringSplitter splitIter = new StringSplitter(str1,
                new CharFinder(',', false),
                Integer.MAX_VALUE,
                true
        );

        final List<String> strings = splitIter.toList(true);
        Assertions.assertEquals(3, strings.size());
        Assertions.assertEquals("a", strings.get(0));
        Assertions.assertEquals("efedsfs", strings.get(1));
        Assertions.assertEquals("ddf", strings.get(2));
    }

    @Test
    public void splitByStrTest() {
        final String str1 = "a, ,,efedsfs,   ddf,";

        final StringSplitter splitIter = new StringSplitter(str1,
                StringFinder.of("e", false),
                Integer.MAX_VALUE,
                true
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(3, strings.size());
    }

    @Test
    public void splitByPatternTest() {
        final String str1 = "a, ,,efedsfs,   ddf,";

        final StringSplitter splitIter = new StringSplitter(str1,
                new PatternFinder(Pattern.compile("\\s")),
                Integer.MAX_VALUE,
                true
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(3, strings.size());
    }

    @Test
    public void splitByLengthTest() {
        final String text = "1234123412341234";
        final StringSplitter splitIter = new StringSplitter(text,
                new LengthFinder(4),
                Integer.MAX_VALUE,
                false
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(4, strings.size());
    }

    @Test
    public void splitLimitTest() {
        final String text = "55:02:18";
        final StringSplitter splitIter = new StringSplitter(text,
                new CharFinder(':'),
                3,
                false
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(3, strings.size());
    }

    @Test
    public void splitToSingleTest() {
        final String text = "";
        final StringSplitter splitIter = new StringSplitter(text,
                new CharFinder(':'),
                3,
                false
        );

        final List<String> strings = splitIter.toList(false);
        Assertions.assertEquals(1, strings.size());
    }

    // 切割字符串是空字符串时报错
    @Test
    public void splitByEmptyTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final String text = "aa,bb,cc";
            final StringSplitter splitIter = new StringSplitter(text,
                    StringFinder.of("", false),
                    3,
                    false
            );

            final List<String> strings = splitIter.toList(false);
            Assertions.assertEquals(1, strings.size());
        });
    }

}

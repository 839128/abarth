package org.miaixz.bus.core.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.Formatter;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.lang.Symbol;
import org.miaixz.bus.core.text.finder.PatternFinder;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.StringKit;

import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

public class CharsBackerTest {

    @Test
    public void splitByCharTest() {
        final String str1 = "a, ,efedsfs,   ddf";
        final List<String> split = CharsBacker.split(str1, ",", 0, true, true);

        Assertions.assertEquals("ddf", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitByStrTest() {
        final String str1 = "aabbccaaddaaee";
        final List<String> split = CharsBacker.split(str1, "aa", 0, true, true);
        Assertions.assertEquals("ee", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitByBlankTest() {
        final String str1 = "aa bbccaa     ddaaee";
        final List<String> split = CharsBacker.splitByBlank(str1);
        Assertions.assertEquals("ddaaee", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitPathTest() {
        final String str1 = "/use/local\\bin";
        final List<String> split = CharsBacker.splitPath(str1);
        Assertions.assertEquals("bin", split.get(2));
        Assertions.assertEquals(3, split.size());
    }

    @Test
    public void splitMappingTest() {
        final String str = "1.2.";
        final List<Long> split = CharsBacker.split(str, ".", 0, true, true, Long::parseLong);
        Assertions.assertEquals(2, split.size());
        Assertions.assertEquals(Long.valueOf(1L), split.get(0));
        Assertions.assertEquals(Long.valueOf(2L), split.get(1));
    }

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @Test
    public void splitEmptyTest() {
        final String str = "";
        final String[] split = str.split(",");
        final String[] strings = CharsBacker.split(str, ",", -1, false, false)
                .toArray(new String[0]);

        Assertions.assertNotNull(strings);
        Assertions.assertArrayEquals(split, strings);

        final String[] strings2 = CharsBacker.split(str, ",", -1, false, true)
                .toArray(new String[0]);
        Assertions.assertEquals(0, strings2.length);
    }


    @Test
    public void splitNullTest() {
        final String str = null;
        final String[] strings = CharsBacker.split(str, ",", -1, false, false)
                .toArray(new String[0]);
        Assertions.assertNotNull(strings);
        Assertions.assertEquals(0, strings.length);

        final String[] strings2 = CharsBacker.split(str, ",", -1, false, true)
                .toArray(new String[0]);
        Assertions.assertNotNull(strings2);
        Assertions.assertEquals(0, strings2.length);
    }

    @Test
    public void splitByRegexTest() {
        final String text = "01  821   34567890182345617821";
        List<String> strings = CharsBacker.splitByRegex(text, "21", 0, false, true);
        Assertions.assertEquals(2, strings.size());
        Assertions.assertEquals("01  8", strings.get(0));
        Assertions.assertEquals("   345678901823456178", strings.get(1));

        strings = CharsBacker.splitByRegex(text, "21", 0, false, false);
        Assertions.assertEquals(3, strings.size());
        Assertions.assertEquals("01  8", strings.get(0));
        Assertions.assertEquals("   345678901823456178", strings.get(1));
        Assertions.assertEquals("", strings.get(2));
    }

    @Test
    void issueTest() {
        List<String> strings = CharsBacker.splitByRegex("", "", 0, false, false);
        Assertions.assertEquals(ListKit.of(""), strings);

        strings = CharsBacker.splitByRegex("aaa", "", 0, false, false);
        Assertions.assertEquals(ListKit.of("aaa"), strings);

        strings = CharsBacker.splitByRegex("", "aaa", 0, false, false);
        Assertions.assertEquals(ListKit.of(""), strings);

        strings = CharsBacker.splitByRegex("", "", 0, false, true);
        Assertions.assertEquals(ListKit.of(), strings);
    }

    @Test
    void issueTest2() {
        // 测试在无前置判断时，是否死循环
        final StringSplitter splitIter = new StringSplitter("", new PatternFinder(Pattern.compile("")), -1, false);
        final List<String> list = splitIter.toList(false);
        Assertions.assertEquals(ListKit.of(""), list);
    }

    @Test
    public void replaceTest() {
        final String actual = CharsBacker.replace("SSM15930297701BeryAllen", Pattern.compile("[0-9]"), matcher -> "");
        Assertions.assertEquals("SSMBeryAllen", actual);
    }

    @Test
    public void replaceTest2() {
        final String replace = "#{A}";
        final String result = CharsBacker.replace(replace, "#{AAAAAAA}", "1");
        Assertions.assertEquals(replace, result);
    }

    @Test
    public void replaceByStrTest() {
        final String replace = "SSM15930297701BeryAllen";
        final String result = CharsBacker.replaceByCodePoint(replace, 5, 12, "***");
        Assertions.assertEquals("SSM15***01BeryAllen", result);

        final String emoji = StringKit.replaceByCodePoint("\uD83D\uDE00aabb\uD83D\uDE00ccdd", 2, 6, "***");
        Assertions.assertEquals("\uD83D\uDE00a***ccdd", emoji);
    }

    @Test
    public void addPrefixIfNotTest() {
        final String str = "bus";
        String result = CharsBacker.addPrefixIfNot(str, "hu");
        Assertions.assertEquals(str, result);

        result = CharsBacker.addPrefixIfNot(str, "Good");
        Assertions.assertEquals("Good" + str, result);
    }

    @Test
    public void addSuffixIfNotTest() {
        final String str = "bus";
        String result = CharsBacker.addSuffixIfNot(str, "tool");
        Assertions.assertEquals(str, result);

        result = CharsBacker.addSuffixIfNot(str, " is Good");
        Assertions.assertEquals(str + " is Good", result);

        result = CharsBacker.addSuffixIfNot("", "/");
        Assertions.assertEquals("/", result);
    }


    @Test
    public void normalizeTest() {
        String str1 = "\u00C1";
        String str2 = "\u0041\u0301";

        Assertions.assertNotEquals(str1, str2);

        str1 = CharsBacker.normalize(str1);
        str2 = CharsBacker.normalize(str2);
        Assertions.assertEquals(str1, str2);
    }

    @Test
    public void indexOfTest() {
        int index = CharsBacker.indexOf("abc123", '1');
        Assertions.assertEquals(3, index);
        index = CharsBacker.indexOf("abc123", '3');
        Assertions.assertEquals(5, index);
        index = CharsBacker.indexOf("abc123", 'a');
        Assertions.assertEquals(0, index);
    }

    @Test
    public void indexOfTest2() {
        int index = CharsBacker.indexOf("abc123", '1', 0, 3);
        Assertions.assertEquals(-1, index);

        index = CharsBacker.indexOf("abc123", 'b', 0, 3);
        Assertions.assertEquals(1, index);
    }

    @Test
    public void subPreGbkTest() {
        final String s = "华硕K42Intel酷睿i31代2G以下独立显卡不含机械硬盘固态硬盘120GB-192GB4GB-6GB";

        String v = CharsBacker.subPreGbk(s, 40, false);
        Assertions.assertEquals(39, v.getBytes(Charset.GBK).length);

        v = CharsBacker.subPreGbk(s, 40, true);
        Assertions.assertEquals(41, v.getBytes(Charset.GBK).length);
    }

    @Test
    void subPreTest() {
        final String pre = CharsBacker.subPre("abc", 0);
        Assertions.assertEquals(Normal.EMPTY, pre);
    }

    @Test
    public void startWithTest() {
        Assertions.assertFalse(CharsBacker.startWith("123", "123", false, true));
        Assertions.assertFalse(CharsBacker.startWith(null, null, false, true));
        Assertions.assertFalse(CharsBacker.startWith("abc", "abc", true, true));

        Assertions.assertTrue(CharsBacker.startWithIgnoreCase(null, null));
        Assertions.assertFalse(CharsBacker.startWithIgnoreCase(null, "abc"));
        Assertions.assertFalse(CharsBacker.startWithIgnoreCase("abcdef", null));
        Assertions.assertTrue(CharsBacker.startWithIgnoreCase("abcdef", "abc"));
        Assertions.assertTrue(CharsBacker.startWithIgnoreCase("ABCDEF", "abc"));
    }

    @Test
    public void endWithTest() {
        Assertions.assertFalse(CharsBacker.endWith("123", "123", false, true));
        Assertions.assertFalse(CharsBacker.endWith(null, null, false, true));
        Assertions.assertFalse(CharsBacker.endWith("abc", "abc", true, true));

        Assertions.assertTrue(CharsBacker.endWithIgnoreCase(null, null));
        Assertions.assertFalse(CharsBacker.endWithIgnoreCase(null, "abc"));
        Assertions.assertFalse(CharsBacker.endWithIgnoreCase("abcdef", null));
        Assertions.assertTrue(CharsBacker.endWithIgnoreCase("abcdef", "def"));
        Assertions.assertTrue(CharsBacker.endWithIgnoreCase("ABCDEF", "def"));
    }

    @Test
    public void removePrefixIgnoreCaseTest() {
        Assertions.assertEquals("de", CharsBacker.removePrefixIgnoreCase("ABCde", "abc"));
        Assertions.assertEquals("de", CharsBacker.removePrefixIgnoreCase("ABCde", "ABC"));
        Assertions.assertEquals("de", CharsBacker.removePrefixIgnoreCase("ABCde", "Abc"));
        Assertions.assertEquals("ABCde", CharsBacker.removePrefixIgnoreCase("ABCde", ""));
        Assertions.assertEquals("ABCde", CharsBacker.removePrefixIgnoreCase("ABCde", null));
        Assertions.assertEquals("", CharsBacker.removePrefixIgnoreCase("ABCde", "ABCde"));
        Assertions.assertEquals("ABCde", CharsBacker.removePrefixIgnoreCase("ABCde", "ABCdef"));
        Assertions.assertNull(CharsBacker.removePrefixIgnoreCase(null, "ABCdef"));
    }

    @Test
    public void removeSuffixIgnoreCaseTest() {
        Assertions.assertEquals("AB", CharsBacker.removeSuffixIgnoreCase("ABCde", "cde"));
        Assertions.assertEquals("AB", CharsBacker.removeSuffixIgnoreCase("ABCde", "CDE"));
        Assertions.assertEquals("AB", CharsBacker.removeSuffixIgnoreCase("ABCde", "Cde"));
        Assertions.assertEquals("ABCde", CharsBacker.removeSuffixIgnoreCase("ABCde", ""));
        Assertions.assertEquals("ABCde", CharsBacker.removeSuffixIgnoreCase("ABCde", null));
        Assertions.assertEquals("", CharsBacker.removeSuffixIgnoreCase("ABCde", "ABCde"));
        Assertions.assertEquals("ABCde", CharsBacker.removeSuffixIgnoreCase("ABCde", "ABCdef"));
        Assertions.assertNull(CharsBacker.removeSuffixIgnoreCase(null, "ABCdef"));
    }


    @Test
    public void trimToNullTest() {
        String a = "  ";
        Assertions.assertNull(CharsBacker.trimToNull(a));

        a = "";
        Assertions.assertNull(CharsBacker.trimToNull(a));

        a = null;
        Assertions.assertNull(CharsBacker.trimToNull(a));
    }

    @Test
    public void containsAllTest() {
        final String a = "2142342422423423";
        Assertions.assertTrue(StringKit.containsAll(a, "214", "234"));
    }

    @Test
    public void defaultIfEmptyTest() {
        final String emptyValue = "";
        final Instant result1 = CharsBacker.defaultIfEmpty(emptyValue,
                (v) -> DateKit.parse(v, Formatter.NORM_DATETIME_MINUTE_FORMAT).toInstant(), Instant::now);
        Assertions.assertNotNull(result1);

        final String dateStr = "2020-10-23 15:12:30";
        final Instant result2 = CharsBacker.defaultIfEmpty(dateStr,
                (v) -> DateKit.parse(v, Formatter.NORM_DATETIME_MINUTE_FORMAT).toInstant(), Instant::now);
        Assertions.assertNotNull(result2);
    }

    @Test
    public void defaultIfBlankTest() {
        final String emptyValue = " ";
        final Instant result1 = CharsBacker.defaultIfBlank(emptyValue,
                (v) -> DateKit.parse(v, Formatter.NORM_DATETIME_MINUTE_FORMAT).toInstant(), Instant::now);
        Assertions.assertNotNull(result1);

        final String dateStr = "2020-10-23 15:12:30";
        final Instant result2 = CharsBacker.defaultIfBlank(dateStr,
                (v) -> DateKit.parse(v, Formatter.NORM_DATETIME_MINUTE_FORMAT).toInstant(), Instant::now);
        Assertions.assertNotNull(result2);
    }

    @Test
    public void replaceLastTest() {
        final String str = "i am jack and jack";
        final String result = StringKit.replaceLast(str, "JACK", null, true);
        Assertions.assertEquals(result, "i am jack and ");
    }

    @Test
    public void replaceFirstTest() {
        final String str = "yes and yes i do";
        final String result = StringKit.replaceFirst(str, "YES", "", true);
        Assertions.assertEquals(result, " and yes i do");
    }

    @Test
    public void issueTest1() {
        final String str = "A5E6005700000000000000000000000000000000000000090D0100000000000001003830";
        Assertions.assertEquals("38", StringKit.subByLength(str, -2, 2));
    }

    @Test
    public void commonPrefixTest() {

        // -------------------------- None match -----------------------

        Assertions.assertEquals("", CharsBacker.commonPrefix("", "abc"));
        Assertions.assertEquals("", CharsBacker.commonPrefix(null, "abc"));
        Assertions.assertEquals("", CharsBacker.commonPrefix("abc", null));
        Assertions.assertEquals("", CharsBacker.commonPrefix("abc", ""));

        Assertions.assertEquals("", CharsBacker.commonPrefix("azzzj", "bzzzj"));

        Assertions.assertEquals("", CharsBacker.commonPrefix("english中文", "french中文"));

        // -------------------------- Matched -----------------------

        Assertions.assertEquals("name_", CharsBacker.commonPrefix("name_abc", "name_efg"));

        Assertions.assertEquals("zzzj", CharsBacker.commonPrefix("zzzja", "zzzjb"));

        Assertions.assertEquals("中文", CharsBacker.commonPrefix("中文english", "中文french"));

        // { space * 10 } + "abc"
        final String str1 = CharsBacker.repeat(Symbol.C_SPACE, 10) + "abc";

        // { space * 5 } + "efg"
        final String str2 = CharsBacker.repeat(Symbol.C_SPACE, 5) + "efg";

        // Expect common prefix: { space * 5 }
        Assertions.assertEquals(CharsBacker.repeat(Symbol.C_SPACE, 5), CharsBacker.commonPrefix(str1, str2));
    }

    @Test
    public void commonSuffixTest() {

        // -------------------------- None match -----------------------

        Assertions.assertEquals("", CharsBacker.commonSuffix("", "abc"));
        Assertions.assertEquals("", CharsBacker.commonSuffix(null, "abc"));
        Assertions.assertEquals("", CharsBacker.commonSuffix("abc", null));
        Assertions.assertEquals("", CharsBacker.commonSuffix("abc", ""));

        Assertions.assertEquals("", CharsBacker.commonSuffix("zzzja", "zzzjb"));

        Assertions.assertEquals("", CharsBacker.commonSuffix("中文english", "中文Korean"));

        // -------------------------- Matched -----------------------

        Assertions.assertEquals("_name", CharsBacker.commonSuffix("abc_name", "efg_name"));

        Assertions.assertEquals("zzzj", CharsBacker.commonSuffix("abczzzj", "efgzzzj"));

        Assertions.assertEquals("中文", CharsBacker.commonSuffix("english中文", "Korean中文"));

        // "abc" + { space * 10 }
        final String str1 = "abc" + CharsBacker.repeat(Symbol.C_SPACE, 10);

        // "efg" + { space * 15 }
        final String str2 = "efg" + CharsBacker.repeat(Symbol.C_SPACE, 15);

        // Expect common suffix: { space * 10 }
        Assertions.assertEquals(CharsBacker.repeat(Symbol.C_SPACE, 10), CharsBacker.commonSuffix(str1, str2));
    }

    @Test
    void codeLengthTest() {
        final String a = "🍒🐽";
        final int i = StringKit.codeLength(a);
        Assertions.assertEquals(4, a.length());
        Assertions.assertEquals(2, i);
    }

    @Test
    public void limitByteLengthUtf8Test() {
        final String str = "这是This一段中英文";
        String ret = StringKit.limitByteLengthUtf8(str, 12, true);
        Assertions.assertEquals("这是Thi...", ret);

        ret = StringKit.limitByteLengthUtf8(str, 13, true);
        Assertions.assertEquals("这是This...", ret);

        ret = StringKit.limitByteLengthUtf8(str, 14, true);
        Assertions.assertEquals("这是This...", ret);

        ret = StringKit.limitByteLengthUtf8(str, 999, true);
        Assertions.assertEquals(str, ret);
    }

    @Test
    public void limitByteLengthUtf8Test2() {
        final String str = "这是This一";
        final String ret = StringKit.limitByteLengthUtf8(str, 12, true);
        Assertions.assertEquals("这是Thi...", ret);
    }

    @Test
    public void limitByteLengthTest() {
        final String str = "This is English";
        final String ret = StringKit.limitByteLength(str, Charset.ISO_8859_1, 10, 1, false);
        Assertions.assertEquals("This is En", ret);

    }

    @Test
    void upperAtTest() {
        final StringBuilder sb = new StringBuilder("key");

        final String s1 = CharsBacker.upperAt(sb, 0);
        Assertions.assertEquals("Key", s1);

        final String s2 = CharsBacker.upperAt(sb, 1);
        Assertions.assertEquals("kEy", s2);

        final String s3 = CharsBacker.upperAt(sb, 2);
        Assertions.assertEquals("keY", s3);

    }

    @Test
    void lowerAtTest() {
        final StringBuilder sb = new StringBuilder("KEY");

        final String s1 = CharsBacker.lowerAt(sb, 0);
        Assertions.assertEquals("kEY", s1);

        final String s2 = CharsBacker.lowerAt(sb, 1);
        Assertions.assertEquals("KeY", s2);

        final String s3 = CharsBacker.lowerAt(sb, 2);
        Assertions.assertEquals("KEy", s3);
    }

    @Test
    public void testContainsOnly() {
        // 测试空字符串
        Assertions.assertTrue(CharsBacker.containsOnly("", 'a', 'b'));

        // 测试字符串只包含testChars中的字符
        Assertions.assertTrue(CharsBacker.containsOnly("asdf", 'a', 's', 'd', 'f'));

        // 测试字符串包含testChars中的字符和其它字符
        Assertions.assertFalse(CharsBacker.containsOnly("asdf123", 'a', 's', 'd', 'f'));

        // 测试字符串不包含testChars中的任何字符
        Assertions.assertFalse(CharsBacker.containsOnly("hello", 'a', 'b'));

        // 测试字符串为null
        Assertions.assertTrue(CharsBacker.containsOnly(null, 'a', 'b'));
    }

}

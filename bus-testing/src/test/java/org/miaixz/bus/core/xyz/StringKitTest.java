package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.text.CharsBacker;

import java.util.List;

/**
 * 字符串工具类单元测试
 */
public class StringKitTest {
    @Test
    public void isBlankTest() {
        final String blank = "	  　";
        Assertions.assertTrue(StringKit.isBlank(blank));
    }

    @Test
    public void trimTest() {
        final String blank = "	 哈哈 　";
        final String trim = StringKit.trim(blank);
        Assertions.assertEquals("哈哈", trim);
    }

    @Test
    public void trimNewLineTest() {
        String str = "\r\naaa";
        Assertions.assertEquals("aaa", StringKit.trim(str));
        str = "\raaa";
        Assertions.assertEquals("aaa", StringKit.trim(str));
        str = "\naaa";
        Assertions.assertEquals("aaa", StringKit.trim(str));
        str = "\r\n\r\naaa";
        Assertions.assertEquals("aaa", StringKit.trim(str));
    }

    @Test
    public void trimTabTest() {
        final String str = "\taaa";
        Assertions.assertEquals("aaa", StringKit.trim(str));
    }

    @Test
    public void cleanBlankTest() {
        // 包含：制表符、英文空格、不间断空白符、全角空格
        final String str = "	 你 好　";
        final String cleanBlank = StringKit.cleanBlank(str);
        Assertions.assertEquals("你好", cleanBlank);
    }

    @Test
    public void cutTest() {
        final String str = "aaabbbcccdddaadfdfsdfsdf0";
        final String[] cut = StringKit.cut(str, 4);
        Assertions.assertArrayEquals(new String[]{"aaab", "bbcc", "cddd", "aadf", "dfsd", "fsdf", "0"}, cut);
    }

    @Test
    public void splitTest2() {
        final String str = "a.b.";
        final List<String> split = CharsBacker.split(str, ".");
        Assertions.assertEquals(3, split.size());
        Assertions.assertEquals("b", split.get(1));
        Assertions.assertEquals("", split.get(2));
    }

    @Test
    public void splitNullTest() {
        Assertions.assertEquals(0, CharsBacker.split(null, ".").size());
    }

    @Test
    public void formatTest() {
        final String template = "你好，我是{name}，我的电话是：{phone}";
        final String result = StringKit.format(template, Dictionary.of().set("name", "张三").set("phone", "13888881111"));
        Assertions.assertEquals("你好，我是张三，我的电话是：13888881111", result);

        final String result2 = StringKit.format(template, Dictionary.of().set("name", "张三").set("phone", null));
        Assertions.assertEquals("你好，我是张三，我的电话是：{phone}", result2);
    }

    @Test
    public void stripTest() {
        String str = "abcd123";
        String strip = StringKit.strip(str, "ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringKit.strip(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringKit.strip(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringKit.strip(str, null, "567");
        Assertions.assertEquals("abcd123", strip);

        Assertions.assertEquals("", StringKit.strip("a", "a"));
        Assertions.assertEquals("", StringKit.strip("a", "a", "b"));
    }

    @Test
    public void stripIgnoreCaseTest() {
        String str = "abcd123";
        String strip = StringKit.stripIgnoreCase(str, "Ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringKit.stripIgnoreCase(str, "AB", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringKit.stripIgnoreCase(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringKit.stripIgnoreCase(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringKit.stripIgnoreCase(str, null, "567");
        Assertions.assertEquals("abcd123", strip);
    }

    @Test
    public void indexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase("balabala", null, 0));
        Assertions.assertEquals(0, StringKit.indexOfIgnoreCase("", "", 0));
        Assertions.assertEquals(0, StringKit.indexOfIgnoreCase("aabaabaa", "A", 0));
        Assertions.assertEquals(2, StringKit.indexOfIgnoreCase("aabaabaa", "B", 0));
        Assertions.assertEquals(1, StringKit.indexOfIgnoreCase("aabaabaa", "AB", 0));
        Assertions.assertEquals(5, StringKit.indexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(2, StringKit.indexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void lastIndexOfTest() {
        final String a = "aabbccddcc";
        final int lastIndexOf = StringKit.lastIndexOf(a, "c", 0, false);
        Assertions.assertEquals(-1, lastIndexOf);
    }

    @Test
    public void lastIndexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringKit.lastIndexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringKit.lastIndexOfIgnoreCase("balabala", null));
        Assertions.assertEquals(0, StringKit.lastIndexOfIgnoreCase("", ""));
        Assertions.assertEquals(7, StringKit.lastIndexOfIgnoreCase("aabaabaa", "A"));
        Assertions.assertEquals(5, StringKit.lastIndexOfIgnoreCase("aabaabaa", "B"));
        Assertions.assertEquals(4, StringKit.lastIndexOfIgnoreCase("aabaabaa", "AB"));
        Assertions.assertEquals(2, StringKit.lastIndexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(5, StringKit.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(-1, StringKit.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(-1, StringKit.lastIndexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(-1, StringKit.lastIndexOfIgnoreCase("abc", "", 9));
        Assertions.assertEquals(0, StringKit.lastIndexOfIgnoreCase("AAAcsd", "aaa"));
    }

    @Test
    public void replaceTest() {
        String string = StringKit.replaceByCodePoint("aabbccdd", 2, 6, '*');
        Assertions.assertEquals("aa****dd", string);
        string = StringKit.replaceByCodePoint("aabbccdd", 2, 12, '*');
        Assertions.assertEquals("aa******", string);

        final String emoji = StringKit.replaceByCodePoint("\uD83D\uDE00aabb\uD83D\uDE00ccdd", 2, 6, '*');
        Assertions.assertEquals("\uD83D\uDE00a****ccdd", emoji);
    }

    @Test
    public void replaceTest2() {
        final String result = StringKit.replace("123", "2", "3");
        Assertions.assertEquals("133", result);
    }

    @Test
    public void replaceTest4() {
        final String a = "1039";
        final String result = StringKit.padPre(a, 8, "0"); //在字符串1039前补4个0
        Assertions.assertEquals("00001039", result);

        final String aa = "1039";
        final String result1 = StringKit.padPre(aa, -1, "0"); //在字符串1039前补4个0
        Assertions.assertEquals("103", result1);
    }

    @Test
    public void replaceTest5() {
        final String a = "\uD853\uDC09秀秀";
        final String result = StringKit.replaceByCodePoint(a, 1, a.length(), '*');
        Assertions.assertEquals("\uD853\uDC09**", result);

        final String aa = "规划大师";
        final String result1 = StringKit.replaceByCodePoint(aa, 2, a.length(), '*');
        Assertions.assertEquals("规划**", result1);
    }

    @Test
    public void upperFirstTest() {
        final StringBuilder sb = new StringBuilder("KEY");
        final String s = StringKit.upperFirst(sb);
        Assertions.assertEquals(s, sb.toString());
    }

    @Test
    public void lowerFirstTest() {
        final StringBuilder sb = new StringBuilder("KEY");
        final String s = StringKit.lowerFirst(sb);
        Assertions.assertEquals("kEY", s);
    }

    @Test
    public void subTest() {
        final String a = "abcderghigh";
        final String pre = StringKit.sub(a, -5, a.length());
        Assertions.assertEquals("ghigh", pre);
    }

    @SuppressWarnings("SimplifiableAssertion")
    @Test
    public void subPreTest() {
        Assertions.assertEquals(StringKit.subPre(null, 3), null);
        Assertions.assertEquals(StringKit.subPre("ab", 3), "ab");
        Assertions.assertEquals(StringKit.subPre("abc", 3), "abc");
        Assertions.assertEquals(StringKit.subPre("abcd", 3), "abc");
        Assertions.assertEquals(StringKit.subPre("abcd", -3), "a");
        Assertions.assertEquals(StringKit.subPre("ab", 3), "ab");
    }

    @Test
    public void subByCodePointTest() {
        // 🤔👍🍓🤔
        final String test = "\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53\uD83E\uDD14";

        // 不正确的子字符串
        final String wrongAnswer = StringKit.sub(test, 0, 3);
        Assertions.assertNotEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", wrongAnswer);

        // 正确的子字符串
        final String rightAnswer = StringKit.subByCodePoint(test, 0, 3);
        Assertions.assertEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", rightAnswer);
    }

    @Test
    public void subBeforeTest() {
        final String a = "abcderghigh";
        String pre = StringKit.subBefore(a, "d", false);
        Assertions.assertEquals("abc", pre);
        pre = StringKit.subBefore(a, 'd', false);
        Assertions.assertEquals("abc", pre);
        pre = StringKit.subBefore(a, 'a', false);
        Assertions.assertEquals("", pre);

        //找不到返回原串
        pre = StringKit.subBefore(a, 'k', false);
        Assertions.assertEquals(a, pre);
        pre = StringKit.subBefore(a, 'k', true);
        Assertions.assertEquals(a, pre);
    }

    @Test
    public void subAfterTest() {
        final String a = "abcderghigh";
        String pre = StringKit.subAfter(a, "d", false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringKit.subAfter(a, 'd', false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringKit.subAfter(a, 'h', true);
        Assertions.assertEquals("", pre);

        //找不到字符返回空串
        pre = StringKit.subAfter(a, 'k', false);
        Assertions.assertEquals("", pre);
        pre = StringKit.subAfter(a, 'k', true);
        Assertions.assertEquals("", pre);
    }

    @Test
    public void subSufByLengthTest() {
        Assertions.assertEquals("cde", StringKit.subSufByLength("abcde", 3));
        Assertions.assertEquals("", StringKit.subSufByLength("abcde", -1));
        Assertions.assertEquals("", StringKit.subSufByLength("abcde", 0));
        Assertions.assertEquals("abcde", StringKit.subSufByLength("abcde", 5));
        Assertions.assertEquals("abcde", StringKit.subSufByLength("abcde", 10));
    }

    @Test
    public void repeatAndJoinTest() {
        String repeatAndJoin = StringKit.repeatAndJoin("?", 5, ",");
        Assertions.assertEquals("?,?,?,?,?", repeatAndJoin);

        repeatAndJoin = StringKit.repeatAndJoin("?", 0, ",");
        Assertions.assertEquals("", repeatAndJoin);

        repeatAndJoin = StringKit.repeatAndJoin("?", 5, null);
        Assertions.assertEquals("?????", repeatAndJoin);
    }

    @Test
    public void moveTest() {
        final String str = "aaaaaaa22222bbbbbbb";
        String result = StringKit.move(str, 7, 12, -3);
        Assertions.assertEquals("aaaa22222aaabbbbbbb", result);
        result = StringKit.move(str, 7, 12, -4);
        Assertions.assertEquals("aaa22222aaaabbbbbbb", result);
        result = StringKit.move(str, 7, 12, -7);
        Assertions.assertEquals("22222aaaaaaabbbbbbb", result);
        result = StringKit.move(str, 7, 12, -20);
        Assertions.assertEquals("aaaaaa22222abbbbbbb", result);

        result = StringKit.move(str, 7, 12, 3);
        Assertions.assertEquals("aaaaaaabbb22222bbbb", result);
        result = StringKit.move(str, 7, 12, 7);
        Assertions.assertEquals("aaaaaaabbbbbbb22222", result);
        result = StringKit.move(str, 7, 12, 20);
        Assertions.assertEquals("aaaaaaab22222bbbbbb", result);

        result = StringKit.move(str, 7, 12, 0);
        Assertions.assertEquals("aaaaaaa22222bbbbbbb", result);
    }

    @Test
    public void removePrefixIgnorecaseTest() {
        final String a = "aaabbb";
        String prefix = "aaa";
        Assertions.assertEquals("bbb", StringKit.removePrefixIgnoreCase(a, prefix));

        prefix = "AAA";
        Assertions.assertEquals("bbb", StringKit.removePrefixIgnoreCase(a, prefix));

        prefix = "AAABBB";
        Assertions.assertEquals("", StringKit.removePrefixIgnoreCase(a, prefix));
    }

    @Test
    public void limitLengthTest() {
        final String text = "我是一段正文，很长的正文，需要截取的正文";
        String str = StringKit.limitLength(text, 5);
        Assertions.assertEquals("我是一段正...", str);
        str = StringKit.limitLength(text, 21);
        Assertions.assertEquals(text, str);
        str = StringKit.limitLength(text, 50);
        Assertions.assertEquals(text, str);
    }

    @Test
    public void containsAnyTest() {
        //字符
        boolean containsAny = StringKit.containsAny("aaabbbccc", 'a', 'd');
        Assertions.assertTrue(containsAny);
        containsAny = StringKit.containsAny("aaabbbccc", 'e', 'd');
        Assertions.assertFalse(containsAny);
        containsAny = StringKit.containsAny("aaabbbccc", 'd', 'c');
        Assertions.assertTrue(containsAny);

        //字符串
        containsAny = StringKit.containsAny("aaabbbccc", "a", "d");
        Assertions.assertTrue(containsAny);
        containsAny = StringKit.containsAny("aaabbbccc", "e", "d");
        Assertions.assertFalse(containsAny);
        containsAny = StringKit.containsAny("aaabbbccc", "d", "c");
        Assertions.assertTrue(containsAny);

        containsAny = StringKit.containsAny("你好啊", "嗯", null);
        Assertions.assertFalse(containsAny);
    }

    @Test
    public void centerTest() {
        Assertions.assertNull(StringKit.center(null, 10));
        Assertions.assertEquals("    ", StringKit.center("", 4));
        Assertions.assertEquals("ab", StringKit.center("ab", -1));
        Assertions.assertEquals(" ab ", StringKit.center("ab", 4));
        Assertions.assertEquals("abcd", StringKit.center("abcd", 2));
        Assertions.assertEquals(" a  ", StringKit.center("a", 4));
    }

    @Test
    public void padPreTest() {
        Assertions.assertNull(StringKit.padPre(null, 10, ' '));
        Assertions.assertEquals("001", StringKit.padPre("1", 3, '0'));
        Assertions.assertEquals("12", StringKit.padPre("123", 2, '0'));

        Assertions.assertNull(StringKit.padPre(null, 10, "AA"));
        Assertions.assertEquals("AB1", StringKit.padPre("1", 3, "ABC"));
        Assertions.assertEquals("12", StringKit.padPre("123", 2, "ABC"));
    }

    @Test
    public void padAfterTest() {
        Assertions.assertNull(StringKit.padAfter(null, 10, ' '));
        Assertions.assertEquals("100", StringKit.padAfter("1", 3, '0'));
        Assertions.assertEquals("23", StringKit.padAfter("123", 2, '0'));
        Assertions.assertEquals("", StringKit.padAfter("123", -1, '0'));

        Assertions.assertNull(StringKit.padAfter(null, 10, "ABC"));
        Assertions.assertEquals("1AB", StringKit.padAfter("1", 3, "ABC"));
        Assertions.assertEquals("23", StringKit.padAfter("123", 2, "ABC"));
    }

    @Test
    public void subBetweenAllTest() {
        Assertions.assertArrayEquals(new String[]{"yz", "abc"}, StringKit.subBetweenAll("saho[yz]fdsadp[abc]a", "[", "]"));
        Assertions.assertArrayEquals(new String[]{"abc"}, StringKit.subBetweenAll("saho[yzfdsadp[abc]a]", "[", "]"));
        Assertions.assertArrayEquals(new String[]{"abc", "abc"}, StringKit.subBetweenAll("yabczyabcz", "y", "z"));
        Assertions.assertArrayEquals(new String[0], StringKit.subBetweenAll(null, "y", "z"));
        Assertions.assertArrayEquals(new String[0], StringKit.subBetweenAll("", "y", "z"));
        Assertions.assertArrayEquals(new String[0], StringKit.subBetweenAll("abc", null, "z"));
        Assertions.assertArrayEquals(new String[0], StringKit.subBetweenAll("abc", "y", null));
    }

    @Test
    public void subBetweenAllTest2() {
        // 起始不匹配的时候，应该直接空
        final String src1 = "/* \n* bus  */  asdas  /* \n* bus  */";
        final String src2 = "/ * bus  */  asdas  / * bus  */";

        final String[] results1 = StringKit.subBetweenAll(src1, "/**", "*/");
        Assertions.assertEquals(0, results1.length);

        final String[] results2 = StringKit.subBetweenAll(src2, "/*", "*/");
        Assertions.assertEquals(0, results2.length);
    }

    @Test
    public void subBetweenAllTest3() {
        final String src1 = "'abc'and'123'";
        String[] strings = StringKit.subBetweenAll(src1, "'", "'");
        Assertions.assertEquals(2, strings.length);
        Assertions.assertEquals("abc", strings[0]);
        Assertions.assertEquals("123", strings[1]);

        final String src2 = "'abc''123'";
        strings = StringKit.subBetweenAll(src2, "'", "'");
        Assertions.assertEquals(2, strings.length);
        Assertions.assertEquals("abc", strings[0]);
        Assertions.assertEquals("123", strings[1]);

        final String src3 = "'abc'123'";
        strings = StringKit.subBetweenAll(src3, "'", "'");
        Assertions.assertEquals(1, strings.length);
        Assertions.assertEquals("abc", strings[0]);
    }

    @Test
    public void subBetweenAllTest4() {
        final String str = "你好:1388681xxxx用户已开通,1877275xxxx用户已开通,无法发送业务开通短信";
        final String[] strings = StringKit.subBetweenAll(str, "1877275xxxx", ",");
        Assertions.assertEquals(1, strings.length);
        Assertions.assertEquals("用户已开通", strings[0]);
    }

    @Test
    public void briefTest() {
        // case: 1 至 str.length - 1
        final String str = RandomKit.randomStringLower(RandomKit.randomInt(1, 100));
        for (int maxLength = 1; maxLength < str.length(); maxLength++) {
            final String brief = StringKit.brief(str, maxLength);
            Assertions.assertEquals(brief.length(), maxLength);
        }

        // case: 不会格式化的值
        Assertions.assertEquals(str, StringKit.brief(str, 0));
        Assertions.assertEquals(str, StringKit.brief(str, -1));
        Assertions.assertEquals(str, StringKit.brief(str, str.length()));
        Assertions.assertEquals(str, StringKit.brief(str, str.length() + 1));
    }

    @Test
    public void briefTest2() {
        final String str = "123";
        int maxLength = 3;
        String brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("123", brief);

        maxLength = 2;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1.", brief);

        maxLength = 1;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1", brief);
    }

    @Test
    public void briefTest3() {
        final String str = "123abc";

        int maxLength = 6;
        String brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals(str, brief);

        maxLength = 5;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1...c", brief);

        maxLength = 4;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1..c", brief);

        maxLength = 3;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1.c", brief);

        maxLength = 2;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1.", brief);

        maxLength = 1;
        brief = StringKit.brief(str, maxLength);
        Assertions.assertEquals("1", brief);
    }

    @Test
    public void filterTest() {
        final String filterNumber = StringKit.filter("Bus678", CharKit::isNumber);
        Assertions.assertEquals("678", filterNumber);
        final String cleanBlank = StringKit.filter("	 你 好　", c -> !CharKit.isBlankChar(c));
        Assertions.assertEquals("你好", cleanBlank);
    }

    @Test
    public void wrapAllTest() {
        String[] strings = StringKit.wrapAll("`", "`", CharsBacker.splitToArray("1,2,3,4", ","));
        Assertions.assertEquals("[`1`, `2`, `3`, `4`]", StringKit.toString(strings));

        strings = StringKit.wrapAllWithPair("`", CharsBacker.splitToArray("1,2,3,4", ","));
        Assertions.assertEquals("[`1`, `2`, `3`, `4`]", StringKit.toString(strings));
    }

    @Test
    public void startWithTest() {
        final String a = "123";
        final String b = "123";

        Assertions.assertTrue(StringKit.startWith(a, b));
        Assertions.assertFalse(StringKit.startWithIgnoreEquals(a, b));
    }

    @Test
    public void indexedFormatTest() {
        final String ret = StringKit.indexedFormat("this is {0} for {1}", "a", 1000);
        Assertions.assertEquals("this is a for 1,000", ret);
    }

    @Test
    public void hideTest() {
        Assertions.assertNull(StringKit.hide(null, 1, 1));
        Assertions.assertEquals("", StringKit.hide("", 1, 1));
        Assertions.assertEquals("****duan@163.com", StringKit.hide("jackduan@163.com", -1, 4));
        Assertions.assertEquals("ja*kduan@163.com", StringKit.hide("jackduan@163.com", 2, 3));
        Assertions.assertEquals("jackduan@163.com", StringKit.hide("jackduan@163.com", 3, 2));
        Assertions.assertEquals("jackduan@163.com", StringKit.hide("jackduan@163.com", 16, 16));
        Assertions.assertEquals("jackduan@163.com", StringKit.hide("jackduan@163.com", 16, 17));
    }


    @Test
    public void isCharEqualsTest() {
        final String a = "aaaaaaaaa";
        Assertions.assertTrue(StringKit.isCharEquals(a));
    }

    @Test
    public void isNumericTest() {
        final String a = "2142342422423423";
        Assertions.assertTrue(StringKit.isNumeric(a));
    }

    @Test
    public void testReplace2() {
        final String replace = "#{A}";
        final String result = StringKit.replace(replace, "#{AAAAAAA}", "1");
        Assertions.assertEquals(replace, result);
    }

    @Test
    public void testReplaceByStr() {
        final String replace = "SSM15930297701BeryAllen";
        final String result = StringKit.replaceByCodePoint(replace, 5, 12, "***");
        Assertions.assertEquals("SSM15***01BeryAllen", result);

        final String emoji = StringKit.replaceByCodePoint("\uD83D\uDE00aabb\uD83D\uDE00ccdd", 2, 6, "***");
        Assertions.assertEquals("\uD83D\uDE00a***ccdd", emoji);
    }

    @Test
    public void testAddPrefixIfNot() {
        final String str = "bus";
        String result = StringKit.addPrefixIfNot(str, "hu");
        Assertions.assertEquals(str, result);

        result = StringKit.addPrefixIfNot(str, "Good");
        Assertions.assertEquals("Good" + str, result);
    }

    @Test
    public void testAddSuffixIfNot() {
        final String str = "bus";
        String result = StringKit.addSuffixIfNot(str, "tool");
        Assertions.assertEquals(str, result);

        result = StringKit.addSuffixIfNot(str, " is Good");
        Assertions.assertEquals(str + " is Good", result);

        result = StringKit.addSuffixIfNot("", "/");
        Assertions.assertEquals("/", result);
    }

    @Test
    public void issuesTest() {
        final String s = StringKit.indexedFormat("a{0,number,#}", 1234567);
        Assertions.assertEquals("a1234567", s);
    }

}

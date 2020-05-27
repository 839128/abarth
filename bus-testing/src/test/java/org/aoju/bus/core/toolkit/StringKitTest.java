package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Dict;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 字符串工具类单元测试
 */
public class StringKitTest {

    @Test
    public void isBlankTest() {
        String blank = "	  　";
        Assertions.assertTrue(StringKit.isBlank(blank));
    }

    @Test
    public void isBlankTest2() {
        String blank = "\u202a";
        Assertions.assertTrue(StringKit.isBlank(blank));
    }

    @Test
    public void trimTest() {
        String blank = "	 哈哈 　";
        String trim = StringKit.trim(blank);
        Assertions.assertEquals("哈哈", trim);
    }

    @Test
    public void cleanBlankTest() {
        // 包含：制表符、英文空格、不间断空白符、全角空格
        String str = "	 你 好　";
        String cleanBlank = StringKit.cleanBlank(str);
        Assertions.assertEquals("你好", cleanBlank);
    }

    @Test
    public void cutTest() {
        String str = "aaabbbcccdddaadfdfsdfsdf0";
        String[] cut = StringKit.cut(str, 4);
        Assertions.assertArrayEquals(new String[]{"aaab", "bbcc", "cddd", "aadf", "dfsd", "fsdf", "0"}, cut);
    }

    @Test
    public void splitTest() {
        String str = "a,b ,c,d,,e";
        List<String> split = StringKit.split(str, ',', -1, true, true);
        // 测试空是否被去掉
        Assertions.assertEquals(5, split.size());
        // 测试去掉两边空白符是否生效
        Assertions.assertEquals("b", split.get(1));

        final String[] strings = StringKit.splitToArray("abc/", '/');
        Assertions.assertEquals(2, strings.length);
    }

    @Test
    public void splitToLongTest() {
        String str = "1,2,3,4, 5";
        long[] longArray = StringKit.splitToLong(str, ',');
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5}, longArray);

        longArray = StringKit.splitToLong(str, ",");
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5}, longArray);
    }

    @Test
    public void splitToIntTest() {
        String str = "1,2,3,4, 5";
        int[] intArray = StringKit.splitToInt(str, ',');
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, intArray);

        intArray = StringKit.splitToInt(str, ",");
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, intArray);
    }

    @Test
    public void formatTest() {
        String template = "你好，我是{name}，我的电话是：{phone}";
        String result = StringKit.format(template, Dict.create().set("name", "张三").set("phone", "13888881111"));
        Assertions.assertEquals("你好，我是张三，我的电话是：13888881111", result);

        String result2 = StringKit.format(template, Dict.create().set("name", "张三").set("phone", null));
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
        Assertions.assertEquals(2, StringKit.indexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(-1, StringKit.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void lastIndexOfTest() {
        String a = "aabbccddcc";
        int lastIndexOf = StringKit.lastIndexOf(a, "c", 0, false);
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
        Assertions.assertEquals(2, StringKit.lastIndexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(3, StringKit.lastIndexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void replaceTest() {
        String string = StringKit.replace("aabbccdd", 2, 6, '*');
        Assertions.assertEquals("aa****dd", string);
        string = StringKit.replace("aabbccdd", 2, 12, '*');
        Assertions.assertEquals("aa******", string);
    }

    @Test
    public void replaceTest2() {
        String result = StringKit.replace("123", "2", "3");
        Assertions.assertEquals("133", result);
    }

    @Test
    public void replaceTest3() {
        String result = StringKit.replace(",abcdef,", ",", "|");
        Assertions.assertEquals("|abcdef|", result);
    }

    @Test
    public void upperFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringKit.upperFirst(sb);
        Assertions.assertEquals(s, sb.toString());
    }

    @Test
    public void lowerFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringKit.lowerFirst(sb);
        Assertions.assertEquals("kEY", s);
    }

    @Test
    public void subTest() {
        String a = "abcderghigh";
        String pre = StringKit.sub(a, -5, a.length());
        Assertions.assertEquals("ghigh", pre);
    }

    @Test
    public void subByCodePointTest() {
        // 🤔👍🍓🤔
        String test = "\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53\uD83E\uDD14";

        // 不正确的子字符串
        String wrongAnswer = StringKit.sub(test, 0, 3);
        Assertions.assertNotEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", wrongAnswer);

        // 正确的子字符串
        String rightAnswer = StringKit.subCodePoint(test, 0, 3);
        Assertions.assertEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", rightAnswer);
    }

    @Test
    public void subBeforeTest() {
        String a = "abcderghigh";
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
        String a = "abcderghigh";
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
    public void removePrefixIgnorecaseTest() {
        String a = "aaabbb";
        String prefix = "aaa";
        Assertions.assertEquals("bbb", StringKit.removePrefixIgnoreCase(a, prefix));

        prefix = "AAA";
        Assertions.assertEquals("bbb", StringKit.removePrefixIgnoreCase(a, prefix));

        prefix = "AAABBB";
        Assertions.assertEquals("", StringKit.removePrefixIgnoreCase(a, prefix));
    }

    @Test
    public void maxLengthTest() {
        String text = "我是一段正文，很长的正文，需要截取的正文";
        String str = StringKit.maxLength(text, 5);
        Assertions.assertEquals("我是一段正...", str);
        str = StringKit.maxLength(text, 21);
        Assertions.assertEquals(text, str);
        str = StringKit.maxLength(text, 50);
        Assertions.assertEquals(text, str);
    }

    @Test
    public void toCamelCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringKit.toCamelCase(str);
        Assertions.assertEquals("tableTestOfDay", result);

        String str1 = "TableTestOfDay";
        String result1 = StringKit.toCamelCase(str1);
        Assertions.assertEquals("TableTestOfDay", result1);
    }

    @Test
    public void toUnderLineCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringKit.toUnderlineCase(str);
        Assertions.assertEquals("table_test_of_day", result);

        String str1 = "_Table_Test_Of_day_";
        String result1 = StringKit.toUnderlineCase(str1);
        Assertions.assertEquals("_table_test_of_day_", result1);

        String str2 = "_Table_Test_Of_DAY_";
        String result2 = StringKit.toUnderlineCase(str2);
        Assertions.assertEquals("_table_test_of_DAY_", result2);

        String str3 = "_TableTestOfDAYtoday";
        String result3 = StringKit.toUnderlineCase(str3);
        Assertions.assertEquals("_table_test_of_DAY_today", result3);

        String str4 = "HelloWorld_test";
        String result4 = StringKit.toUnderlineCase(str4);
        Assertions.assertEquals("hello_world_test", result4);
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

        Assertions.assertNull(StringKit.padAfter(null, 10, "ABC"));
        Assertions.assertEquals("1AB", StringKit.padAfter("1", 3, "ABC"));
        Assertions.assertEquals("23", StringKit.padAfter("123", 2, "ABC"));
    }

}

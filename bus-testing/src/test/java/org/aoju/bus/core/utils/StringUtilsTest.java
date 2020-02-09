package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Dict;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 字符串工具类单元测试
 */
public class StringUtilsTest {

    @Test
    public void isBlankTest() {
        String blank = "	  　";
        Assertions.assertTrue(StringUtils.isBlank(blank));
    }

    @Test
    public void isBlankTest2() {
        String blank = "\u202a";
        Assertions.assertTrue(StringUtils.isBlank(blank));
    }

    @Test
    public void trimTest() {
        String blank = "	 哈哈 　";
        String trim = StringUtils.trim(blank);
        Assertions.assertEquals("哈哈", trim);
    }

    @Test
    public void cleanBlankTest() {
        // 包含：制表符、英文空格、不间断空白符、全角空格
        String str = "	 你 好　";
        String cleanBlank = StringUtils.cleanBlank(str);
        Assertions.assertEquals("你好", cleanBlank);
    }

    @Test
    public void cutTest() {
        String str = "aaabbbcccdddaadfdfsdfsdf0";
        String[] cut = StringUtils.cut(str, 4);
        Assertions.assertArrayEquals(new String[]{"aaab", "bbcc", "cddd", "aadf", "dfsd", "fsdf", "0"}, cut);
    }

    @Test
    public void splitTest() {
        String str = "a,b ,c,d,,e";
        List<String> split = StringUtils.split(str, ',', -1, true, true);
        // 测试空是否被去掉
        Assertions.assertEquals(5, split.size());
        // 测试去掉两边空白符是否生效
        Assertions.assertEquals("b", split.get(1));

        final String[] strings = StringUtils.splitToArray("abc/", '/');
        Assertions.assertEquals(2, strings.length);
    }

    @Test
    public void splitToLongTest() {
        String str = "1,2,3,4, 5";
        long[] longArray = StringUtils.splitToLong(str, ',');
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5}, longArray);

        longArray = StringUtils.splitToLong(str, ",");
        Assertions.assertArrayEquals(new long[]{1, 2, 3, 4, 5}, longArray);
    }

    @Test
    public void splitToIntTest() {
        String str = "1,2,3,4, 5";
        int[] intArray = StringUtils.splitToInt(str, ',');
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, intArray);

        intArray = StringUtils.splitToInt(str, ",");
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, intArray);
    }

    @Test
    public void formatTest() {
        String template = "你好，我是{name}，我的电话是：{phone}";
        String result = StringUtils.format(template, Dict.create().set("name", "张三").set("phone", "13888881111"));
        Assertions.assertEquals("你好，我是张三，我的电话是：13888881111", result);

        String result2 = StringUtils.format(template, Dict.create().set("name", "张三").set("phone", null));
        Assertions.assertEquals("你好，我是张三，我的电话是：{phone}", result2);
    }

    @Test
    public void stripTest() {
        String str = "abcd123";
        String strip = StringUtils.strip(str, "ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringUtils.strip(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtils.strip(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringUtils.strip(str, null, "567");
        Assertions.assertEquals("abcd123", strip);

        Assertions.assertEquals("", StringUtils.strip("a", "a"));
        Assertions.assertEquals("", StringUtils.strip("a", "a", "b"));
    }

    @Test
    public void stripIgnoreCaseTest() {
        String str = "abcd123";
        String strip = StringUtils.stripIgnoreCase(str, "Ab", "23");
        Assertions.assertEquals("cd1", strip);

        str = "abcd123";
        strip = StringUtils.stripIgnoreCase(str, "AB", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtils.stripIgnoreCase(str, "ab", "");
        Assertions.assertEquals("cd123", strip);

        str = "abcd123";
        strip = StringUtils.stripIgnoreCase(str, null, "");
        Assertions.assertEquals("abcd123", strip);

        str = "abcd123";
        strip = StringUtils.stripIgnoreCase(str, null, "567");
        Assertions.assertEquals("abcd123", strip);
    }

    @Test
    public void indexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringUtils.indexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringUtils.indexOfIgnoreCase("balabala", null, 0));
        Assertions.assertEquals(0, StringUtils.indexOfIgnoreCase("", "", 0));
        Assertions.assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0));
        Assertions.assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0));
        Assertions.assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0));
        Assertions.assertEquals(5, StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(-1, StringUtils.indexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void lastIndexOfTest() {
        String a = "aabbccddcc";
        int lastIndexOf = StringUtils.lastIndexOf(a, "c", 0, false);
        Assertions.assertEquals(-1, lastIndexOf);
    }

    @Test
    public void lastIndexOfIgnoreCaseTest() {
        Assertions.assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, "balabala", 0));
        Assertions.assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("balabala", null));
        Assertions.assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", ""));
        Assertions.assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A"));
        Assertions.assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B"));
        Assertions.assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB"));
        Assertions.assertEquals(2, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 3));
        Assertions.assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9));
        Assertions.assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1));
        Assertions.assertEquals(2, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "", 2));
        Assertions.assertEquals(3, StringUtils.lastIndexOfIgnoreCase("abc", "", 9));
    }

    @Test
    public void replaceTest() {
        String string = StringUtils.replace("aabbccdd", 2, 6, '*');
        Assertions.assertEquals("aa****dd", string);
        string = StringUtils.replace("aabbccdd", 2, 12, '*');
        Assertions.assertEquals("aa******", string);
    }

    @Test
    public void replaceTest2() {
        String result = StringUtils.replace("123", "2", "3");
        Assertions.assertEquals("133", result);
    }

    @Test
    public void replaceTest3() {
        String result = StringUtils.replace(",abcdef,", ",", "|");
        Assertions.assertEquals("|abcdef|", result);
    }

    @Test
    public void upperFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringUtils.upperFirst(sb);
        Assertions.assertEquals(s, sb.toString());
    }

    @Test
    public void lowerFirstTest() {
        StringBuilder sb = new StringBuilder("KEY");
        String s = StringUtils.lowerFirst(sb);
        Assertions.assertEquals("kEY", s);
    }

    @Test
    public void subTest() {
        String a = "abcderghigh";
        String pre = StringUtils.sub(a, -5, a.length());
        Assertions.assertEquals("ghigh", pre);
    }

    @Test
    public void subByCodePointTest() {
        // 🤔👍🍓🤔
        String test = "\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53\uD83E\uDD14";

        // 不正确的子字符串
        String wrongAnswer = StringUtils.sub(test, 0, 3);
        Assertions.assertNotEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", wrongAnswer);

        // 正确的子字符串
        String rightAnswer = StringUtils.subCodePoint(test, 0, 3);
        Assertions.assertEquals("\uD83E\uDD14\uD83D\uDC4D\uD83C\uDF53", rightAnswer);
    }

    @Test
    public void subBeforeTest() {
        String a = "abcderghigh";
        String pre = StringUtils.subBefore(a, "d", false);
        Assertions.assertEquals("abc", pre);
        pre = StringUtils.subBefore(a, 'd', false);
        Assertions.assertEquals("abc", pre);
        pre = StringUtils.subBefore(a, 'a', false);
        Assertions.assertEquals("", pre);

        //找不到返回原串
        pre = StringUtils.subBefore(a, 'k', false);
        Assertions.assertEquals(a, pre);
        pre = StringUtils.subBefore(a, 'k', true);
        Assertions.assertEquals(a, pre);
    }

    @Test
    public void subAfterTest() {
        String a = "abcderghigh";
        String pre = StringUtils.subAfter(a, "d", false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringUtils.subAfter(a, 'd', false);
        Assertions.assertEquals("erghigh", pre);
        pre = StringUtils.subAfter(a, 'h', true);
        Assertions.assertEquals("", pre);

        //找不到字符返回空串
        pre = StringUtils.subAfter(a, 'k', false);
        Assertions.assertEquals("", pre);
        pre = StringUtils.subAfter(a, 'k', true);
        Assertions.assertEquals("", pre);
    }


    @Test
    public void removePrefixIgnorecaseTest() {
        String a = "aaabbb";
        String prefix = "aaa";
        Assertions.assertEquals("bbb", StringUtils.removePrefixIgnoreCase(a, prefix));

        prefix = "AAA";
        Assertions.assertEquals("bbb", StringUtils.removePrefixIgnoreCase(a, prefix));

        prefix = "AAABBB";
        Assertions.assertEquals("", StringUtils.removePrefixIgnoreCase(a, prefix));
    }

    @Test
    public void maxLengthTest() {
        String text = "我是一段正文，很长的正文，需要截取的正文";
        String str = StringUtils.maxLength(text, 5);
        Assertions.assertEquals("我是一段正...", str);
        str = StringUtils.maxLength(text, 21);
        Assertions.assertEquals(text, str);
        str = StringUtils.maxLength(text, 50);
        Assertions.assertEquals(text, str);
    }

    @Test
    public void toCamelCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringUtils.toCamelCase(str);
        Assertions.assertEquals("tableTestOfDay", result);

        String str1 = "TableTestOfDay";
        String result1 = StringUtils.toCamelCase(str1);
        Assertions.assertEquals("TableTestOfDay", result1);
    }

    @Test
    public void toUnderLineCaseTest() {
        String str = "Table_Test_Of_day";
        String result = StringUtils.toUnderlineCase(str);
        Assertions.assertEquals("table_test_of_day", result);

        String str1 = "_Table_Test_Of_day_";
        String result1 = StringUtils.toUnderlineCase(str1);
        Assertions.assertEquals("_table_test_of_day_", result1);

        String str2 = "_Table_Test_Of_DAY_";
        String result2 = StringUtils.toUnderlineCase(str2);
        Assertions.assertEquals("_table_test_of_DAY_", result2);

        String str3 = "_TableTestOfDAYtoday";
        String result3 = StringUtils.toUnderlineCase(str3);
        Assertions.assertEquals("_table_test_of_DAY_today", result3);

        String str4 = "HelloWorld_test";
        String result4 = StringUtils.toUnderlineCase(str4);
        Assertions.assertEquals("hello_world_test", result4);
    }

    @Test
    public void containsAnyTest() {
        //字符
        boolean containsAny = StringUtils.containsAny("aaabbbccc", 'a', 'd');
        Assertions.assertTrue(containsAny);
        containsAny = StringUtils.containsAny("aaabbbccc", 'e', 'd');
        Assertions.assertFalse(containsAny);
        containsAny = StringUtils.containsAny("aaabbbccc", 'd', 'c');
        Assertions.assertTrue(containsAny);

        //字符串
        containsAny = StringUtils.containsAny("aaabbbccc", "a", "d");
        Assertions.assertTrue(containsAny);
        containsAny = StringUtils.containsAny("aaabbbccc", "e", "d");
        Assertions.assertFalse(containsAny);
        containsAny = StringUtils.containsAny("aaabbbccc", "d", "c");
        Assertions.assertTrue(containsAny);
    }

    @Test
    public void centerTest() {
        Assertions.assertNull(StringUtils.center(null, 10));
        Assertions.assertEquals("    ", StringUtils.center("", 4));
        Assertions.assertEquals("ab", StringUtils.center("ab", -1));
        Assertions.assertEquals(" ab ", StringUtils.center("ab", 4));
        Assertions.assertEquals("abcd", StringUtils.center("abcd", 2));
        Assertions.assertEquals(" a  ", StringUtils.center("a", 4));
    }

    @Test
    public void padPreTest() {
        Assertions.assertNull(StringUtils.padPre(null, 10, ' '));
        Assertions.assertEquals("001", StringUtils.padPre("1", 3, '0'));
        Assertions.assertEquals("12", StringUtils.padPre("123", 2, '0'));

        Assertions.assertNull(StringUtils.padPre(null, 10, "AA"));
        Assertions.assertEquals("AB1", StringUtils.padPre("1", 3, "ABC"));
        Assertions.assertEquals("12", StringUtils.padPre("123", 2, "ABC"));
    }

    @Test
    public void padAfterTest() {
        Assertions.assertNull(StringUtils.padAfter(null, 10, ' '));
        Assertions.assertEquals("100", StringUtils.padAfter("1", 3, '0'));
        Assertions.assertEquals("23", StringUtils.padAfter("123", 2, '0'));

        Assertions.assertNull(StringUtils.padAfter(null, 10, "ABC"));
        Assertions.assertEquals("1AB", StringUtils.padAfter("1", 3, "ABC"));
        Assertions.assertEquals("23", StringUtils.padAfter("123", 2, "ABC"));
    }

}

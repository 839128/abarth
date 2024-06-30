package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.miaixz.bus.core.center.regex.Pattern;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.Normal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatternKitTest {

    static final String content = "ZZZaaabbbccc中文1234";

    @Test
    public void getTest() {
        final String resultGet = PatternKit.get("\\w{2}", content, 0);
        assertEquals("ZZ", resultGet);
    }

    @Test
    public void extractMultiTest() {
        // 抽取多个分组然后把它们拼接起来
        final String resultExtractMulti = PatternKit.extractMulti("(\\w)aa(\\w)", content, "$1-$2");
        assertEquals("Z-a", resultExtractMulti);
    }

    @Test
    public void extractMultiTest2() {
        // 抽取多个分组然后把它们拼接起来
        final String resultExtractMulti = PatternKit.extractMulti("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", content, "$1-$2-$3-$4-$5-$6-$7-$8-$9-$10");
        assertEquals("Z-Z-Z-a-a-a-b-b-b-c", resultExtractMulti);
    }

    @Test
    public void delFirstTest() {
        // 删除第一个匹配到的内容
        final String resultDelFirst = PatternKit.delFirst("(\\w)aa(\\w)", content);
        assertEquals("ZZbbbccc中文1234", resultDelFirst);
    }

    @Test
    public void delLastTest() {
        final String blank = "";
        final String word = "180公斤";
        final String sentence = "10.商品KLS100021型号xxl适合身高180体重130斤的用户";
        //空字符串兼容
        assertEquals(blank, PatternKit.delLast("\\d+", blank));
        assertEquals(blank, PatternKit.delLast(Pattern.NUMBERS_PATTERN, blank));

        //去除数字
        assertEquals("公斤", PatternKit.delLast("\\d+", word));
        assertEquals("公斤", PatternKit.delLast(Pattern.NUMBERS_PATTERN, word));
        //去除汉字
        //noinspection UnnecessaryUnicodeEscape
        assertEquals("180", PatternKit.delLast("[\u4E00-\u9FFF]+", word));
        assertEquals("180", PatternKit.delLast(Pattern.CHINESES_PATTERN, word));

        //多个匹配删除最后一个 判断是否不在包含最后的数字
        String s = PatternKit.delLast("\\d+", sentence);
        assertEquals("10.商品KLS100021型号xxl适合身高180体重斤的用户", s);
        s = PatternKit.delLast(Pattern.NUMBERS_PATTERN, sentence);
        assertEquals("10.商品KLS100021型号xxl适合身高180体重斤的用户", s);

        //多个匹配删除最后一个 判断是否不在包含最后的数字
        //noinspection UnnecessaryUnicodeEscape
        Assertions.assertFalse(PatternKit.delLast("[\u4E00-\u9FFF]+", sentence).contains("斤的用户"));
        Assertions.assertFalse(PatternKit.delLast(Pattern.CHINESES_PATTERN, sentence).contains("斤的用户"));
    }

    @Test
    public void delAllTest() {
        // 删除所有匹配到的内容
        final String content = "发东方大厦eee![images]http://abc.com/2.gpg]好机会eee![images]http://abc.com/2.gpg]好机会";
        final String resultDelAll = PatternKit.delAll("!\\[images\\][^\\u4e00-\\u9fa5\\\\s]*", content);
        assertEquals("发东方大厦eee好机会eee好机会", resultDelAll);
    }

    @Test
    public void findAllTest() {
        // 查找所有匹配文本
        final List<String> resultFindAll = PatternKit.findAll("\\w{2}", content, 0, new ArrayList<>());
        final List<String> expected = ListKit.of("ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34");
        assertEquals(expected, resultFindAll);
    }

    @Test
    public void getFirstNumberTest() {
        // 找到匹配的第一个数字
        final Integer resultGetFirstNumber = PatternKit.getFirstNumber(content);
        assertEquals(Integer.valueOf(1234), resultGetFirstNumber);
    }

    @Test
    public void isMatchTest() {
        // 给定字符串是否匹配给定正则
        //noinspection UnnecessaryUnicodeEscape
        final boolean isMatch = PatternKit.isMatch("\\w+[\u4E00-\u9FFF]+\\d+", content);
        Assertions.assertTrue(isMatch);
    }

    @Test
    public void replaceAllTest() {
        //通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串
        //此处把1234替换为 ->1234<-
        final String replaceAll = PatternKit.replaceAll(content, "(\\d+)", "->$1<-");
        assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);
    }

    @Test
    public void replaceAllTest2() {
        //此处把1234替换为 ->1234<-
        final String replaceAll = PatternKit.replaceAll(content, "(\\d+)", parameters -> "->" + parameters.group(1) + "<-");
        assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);
    }

    @Test
    public void replaceAllTest3() {
        // 修改前：PatternKit.replaceAll()方法，当replacementTemplate为null对象时，出现空指针异常
        final String str = null;
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)");
        // Assertions.assertThrows(NullPointerException.class, () -> PatternKit.replaceAll(content, pattern, str));

        // 修改后：测试正常的方法访问是否有效
        final String replaceAll = PatternKit.replaceAll(content, pattern, parameters -> "->" + parameters.group(1) + "<-");
        assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);

        // 修改后：判断ReUtil.replaceAll()方法，当replacementTemplate为null对象时，提示为非法的参数异常：ReplacementTemplate must be not null !
        Assertions.assertThrows(IllegalArgumentException.class, () -> PatternKit.replaceAll(content, pattern, str));
    }

    @Test
    public void escapeTest() {
        //转义给定字符串，为正则相关的特殊符号转义
        final String escape = PatternKit.escape("我有个$符号{}");
        assertEquals("我有个\\$符号\\{\\}", escape);
    }

    @Test
    public void escapeTest2() {
        final String str = "a[bbbc";
        final String re = "[";
        final String s = PatternKit.get(PatternKit.escape(re), str, 0);
        assertEquals("[", s);
    }

    @Test
    public void escapeTest3() {
        final String context = "{prefix}_";
        final String regex = "{prefix}_";
        final boolean b = PatternKit.isMatch(PatternKit.escape(regex), context);
        Assertions.assertTrue(b);
    }

    @Test
    public void getAllGroupsTest() {
        //转义给定字符串，为正则相关的特殊符号转义
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
        List<String> allGroups = PatternKit.getAllGroups(pattern, "192-168-1-1");
        assertEquals("192-168-1", allGroups.get(0));
        assertEquals("192", allGroups.get(1));
        assertEquals("168", allGroups.get(2));
        assertEquals("1", allGroups.get(3));

        allGroups = PatternKit.getAllGroups(pattern, "192-168-1-1", false);
        assertEquals("192", allGroups.get(0));
        assertEquals("168", allGroups.get(1));
        assertEquals("1", allGroups.get(2));
    }

    @Test
    public void matchTest() {
        final boolean match = PatternKit.isMatch(
                "(.+?)省(.+?)市(.+?)区", "广东省深圳市南山区");
        Console.log(match);
    }

    @Test
    public void getByGroupNameTest() {
        final String content = "2021-10-11";
        final String regex = "(?<year>\\d+)-(?<month>\\d+)-(?<day>\\d+)";
        final String year = PatternKit.get(regex, content, "year");
        assertEquals("2021", year);
        final String month = PatternKit.get(regex, content, "month");
        assertEquals("10", month);
        final String day = PatternKit.get(regex, content, "day");
        assertEquals("11", day);
    }

    @Test
    @EnabledForJreRange(max = org.junit.jupiter.api.condition.JRE.JAVA_8)
    public void getAllGroupNamesTest() {
        final String content = "2021-10-11";
        final String regex = "(?<year>\\d+)-(?<month>\\d+)-(?<day>\\d+)";
        final Map<String, String> map = PatternKit.getAllGroupNames(Pattern.get(regex, java.util.regex.Pattern.DOTALL), content);
        assertEquals(map.get("year"), "2021");
        assertEquals(map.get("month"), "10");
        assertEquals(map.get("day"), "11");
    }

    @Test
    public void issuesTest() {
        final java.util.regex.Pattern patternIp = java.util.regex.Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})\\.((2(5[0-5]|[0-4]\\d))"
                + "|[0-1]?\\d{1,2})\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})");
        final String s = PatternKit.replaceAll("1.2.3.4", patternIp, "$1.**.**.$10");
        assertEquals("1.**.**.4", s);
    }

    @Test
    public void issuesTest1() {
        assertEquals(Normal.EMPTY, PatternKit.delAll("[\\s]*", " "));
    }

    @Test
    public void issuesTest2() {
        String regex = "^model";
        String content = "model-v";
        final String result = PatternKit.get(regex, content, 0);
        assertEquals("model", result);

        regex = "^model.*?";
        content = "model-v";
        final boolean match = PatternKit.isMatch(regex, content);
        assertTrue(match);
    }

}

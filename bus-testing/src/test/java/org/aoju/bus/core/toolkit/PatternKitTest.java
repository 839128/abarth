package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PatternKitTest {
    final String content = "ZZZaaabbbccc中文1234";

    @Test
    public void getTest() {
        String resultGet = PatternKit.get("\\w{2}", content, 0);
        Assertions.assertEquals("ZZ", resultGet);
    }

    @Test
    public void extractMultiTest() {
        // 抽取多个分组然后把它们拼接起来
        String resultExtractMulti = PatternKit.extractMulti("(\\w)aa(\\w)", content, "$1-$2");
        Assertions.assertEquals("Z-a", resultExtractMulti);
    }

    @Test
    public void extractMultiTest2() {
        // 抽取多个分组然后把它们拼接起来
        String resultExtractMulti = PatternKit.extractMulti("(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)(\\w)", content, "$1-$2-$3-$4-$5-$6-$7-$8-$9-$10");
        Assertions.assertEquals("Z-Z-Z-a-a-a-b-b-b-c", resultExtractMulti);
    }

    @Test
    public void delFirstTest() {
        // 删除第一个匹配到的内容
        String resultDelFirst = PatternKit.delFirst(Pattern.compile("(\\w)aa(\\w)"), content);
        Assertions.assertEquals("ZZbbbccc中文1234", resultDelFirst);
    }

    @Test
    public void delAllTest() {
        // 删除所有匹配到的内容
        String content = "发东方大厦eee![images]http://abc.com/2.gpg]好机会eee![images]http://abc.com/2.gpg]好机会";
        String resultDelAll = PatternKit.delAll("!\\[images\\][^\\u4e00-\\u9fa5\\\\s]*", content);
        Assertions.assertEquals("发东方大厦eee好机会eee好机会", resultDelAll);
    }

    @Test
    public void findAllTest() {
        // 查找所有匹配文本
        List<String> resultFindAll = PatternKit.findAll("\\w{2}", content, 0, new ArrayList<>());
        ArrayList<String> expected = CollKit.newArrayList("ZZ", "Za", "aa", "bb", "bc", "cc", "12", "34");
        Assertions.assertEquals(expected, resultFindAll);
    }

    @Test
    public void getFirstNumberTest() {
        // 找到匹配的第一个数字
        Integer resultGetFirstNumber = PatternKit.getFirstNumber(content);
        Assertions.assertEquals(Integer.valueOf(1234), resultGetFirstNumber);
    }

    @Test
    public void isMatchTest() {
        // 给定字符串是否匹配给定正则
        boolean isMatch = PatternKit.isMatch("\\w+[\u4E00-\u9FFF]+\\d+", content);
        Assertions.assertTrue(isMatch);
    }

    @Test
    public void replaceAllTest() {
        //通过正则查找到字符串，然后把匹配到的字符串加入到replacementTemplate中，$1表示分组1的字符串
        //此处把1234替换为 ->1234<-
        String replaceAll = PatternKit.replaceAll(content, "(\\d+)", "->$1<-");
        Assertions.assertEquals("ZZZaaabbbccc中文->1234<-", replaceAll);
    }

    @Test
    public void replaceTest() {
        String str = "AAABBCCCBBDDDBB";
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

        System.out.println(PatternKit.replaceAll("name:$3age:$2sex:$1", "name:(.*)age:(.+)sex:(.+)", "sex:$3 age:$2 name:$1"));
        System.out.println(PatternKit.replaceAll("2013年5月", "(.*?)年(.*?)月", "$1-$2"));
        System.out.println(PatternKit.extractMulti("name:(.*)age:(.+)sex:(.+)", "name:$3age:$2sex:$1", "sex:$3 age:$2 name:$1"));
        System.out.println(PatternKit.extractMulti("(.*?)年(.*?)月", "2013年5月", "$1-$2"));
    }

    @Test
    public void escapeTest() {
        //转义给定字符串，为正则相关的特殊符号转义
        String escape = PatternKit.escape("我有个$符号{}");
        Assertions.assertEquals("我有个\\$符号\\{\\}", escape);
    }

    @Test
    public void getAllGroupsTest() {
        //转义给定字符串，为正则相关的特殊符号转义
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)-(\\d+)");
        List<String> allGroups = PatternKit.getAllGroups(pattern, "192-168-1-1");
        Assertions.assertEquals("192-168-1", allGroups.get(0));
        Assertions.assertEquals("192", allGroups.get(1));
        Assertions.assertEquals("168", allGroups.get(2));
        Assertions.assertEquals("1", allGroups.get(3));

        allGroups = PatternKit.getAllGroups(pattern, "192-168-1-1", false);
        Assertions.assertEquals("192", allGroups.get(0));
        Assertions.assertEquals("168", allGroups.get(1));
        Assertions.assertEquals("1", allGroups.get(2));
    }

}

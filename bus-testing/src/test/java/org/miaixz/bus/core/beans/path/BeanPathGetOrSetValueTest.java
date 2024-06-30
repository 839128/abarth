package org.miaixz.bus.core.beans.path;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.lang.test.bean.ExamInfoDict;
import org.miaixz.bus.core.lang.test.bean.UserInfoDict;
import org.miaixz.bus.core.xyz.ArrayKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanPathGetOrSetValueTest {

    Map<String, Object> tempMap;

    @BeforeEach
    public void init() {
        // ------------------------------------------------- 考试信息列表
        final ExamInfoDict examInfoDict = new ExamInfoDict();
        examInfoDict.setId(1);
        examInfoDict.setExamType(0);
        examInfoDict.setAnswerIs(1);

        final ExamInfoDict examInfoDict1 = new ExamInfoDict();
        examInfoDict1.setId(2);
        examInfoDict1.setExamType(0);
        examInfoDict1.setAnswerIs(0);

        final ExamInfoDict examInfoDict2 = new ExamInfoDict();
        examInfoDict2.setId(3);
        examInfoDict2.setExamType(1);
        examInfoDict2.setAnswerIs(0);

        final List<ExamInfoDict> examInfoDicts = new ArrayList<>();
        examInfoDicts.add(examInfoDict);
        examInfoDicts.add(examInfoDict1);
        examInfoDicts.add(examInfoDict2);

        // ------------------------------------------------- 用户信息
        final UserInfoDict userInfoDict = new UserInfoDict();
        userInfoDict.setId(1);
        userInfoDict.setPhotoPath("yx.mm.com");
        userInfoDict.setRealName("张三");
        userInfoDict.setExamInfoDict(examInfoDicts);

        tempMap = new HashMap<>();
        tempMap.put("userInfo", userInfoDict);
        tempMap.put("flag", 1);
    }

    @Test
    public void getValueTest() {
        final BeanPath pattern = new BeanPath("$.userInfo.examInfoDict[0].id");
        final Object result = pattern.getValue(tempMap);
        Assertions.assertEquals(1, result);
    }

    @Test
    public void setValueTest() {
        final BeanPath pattern = new BeanPath("userInfo.examInfoDict[0].id");
        pattern.setValue(tempMap, 2);
        final Object result = pattern.getValue(tempMap);
        Assertions.assertEquals(2, result);
    }

    @Test
    public void getMapTest() {
        final BeanPath pattern = new BeanPath("userInfo[id, photoPath]");
        final Map<String, Object> result = (Map<String, Object>) pattern.getValue(tempMap);
        Assertions.assertEquals(1, result.get("id"));
        Assertions.assertEquals("yx.mm.com", result.get("photoPath"));
    }

    @Test
    public void getKeyWithDotTest() {
        final Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("aa", "value0");
        dataMap.put("aa.bb.cc", "value111111");//     key   是类名 格式 带 ' . '

        final BeanPath pattern = new BeanPath("'aa.bb.cc'");
        Assertions.assertEquals("value111111", pattern.getValue(dataMap));
    }

    @Test
    public void issueTest() {
        final Map<String, Object> map = new HashMap<>();

        BeanPath beanPath = BeanPath.of("list[0].name");
        beanPath.setValue(map, "张三");
        Assertions.assertEquals("{list=[{name=张三}]}", map.toString());

        map.clear();
        beanPath = BeanPath.of("list[1].name");
        beanPath.setValue(map, "张三");
        Assertions.assertEquals("{list=[null, {name=张三}]}", map.toString());

        map.clear();
        beanPath = BeanPath.of("list[0].1.name");
        beanPath.setValue(map, "张三");
        Assertions.assertEquals("{list=[[null, {name=张三}]]}", map.toString());
    }

    @Test
    public void putTest() {
        final Map<String, Object> map = new HashMap<>();

        final BeanPath beanPath = BeanPath.of("list[1].name");
        beanPath.setValue(map, "张三");
        Assertions.assertEquals("{list=[null, {name=张三}]}", map.toString());
    }

    @Test
    public void putByPathTest() {
        final Dictionary dict = new Dictionary();
        BeanPath.of("aa.bb").setValue(dict, "BB");
        Assertions.assertEquals("{aa={bb=BB}}", dict.toString());
    }

    @Test
    public void appendArrayTest() {
        final MyUser myUser = new MyUser();
        BeanPath.of("hobby[0]").setValue(myUser, "LOL");
        BeanPath.of("hobby[1]").setValue(myUser, "KFC");
        BeanPath.of("hobby[2]").setValue(myUser, "COFFE");

        Assertions.assertEquals("[LOL, KFC, COFFE]", ArrayKit.toString(myUser.getHobby()));
    }

    @Test
    public void appendArrayTest2() {
        final MyUser2 myUser = new MyUser2();
        BeanPath.of("myUser.hobby[0]").setValue(myUser, "LOL");
        BeanPath.of("myUser.hobby[1]").setValue(myUser, "KFC");
        BeanPath.of("myUser.hobby[2]").setValue(myUser, "COFFE");

        Assertions.assertEquals("[LOL, KFC, COFFE]", ArrayKit.toString(myUser.getMyUser().getHobby()));
    }

    @Data
    static class MyUser {
        private String[] hobby;
    }

    @Data
    static class MyUser2 {
        private MyUser myUser;
    }

}

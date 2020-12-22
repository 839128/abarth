package org.aoju.bus.core.beans;

import org.aoju.bus.core.lang.bean.ExamInfoDict;
import org.aoju.bus.core.lang.bean.UserInfoDict;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link BeanPath} 单元测试
 */
public class BeanPathTest {

    Map<String, Object> tempMap;

    @Before
    public void init() {
        ExamInfoDict examInfoDict = new ExamInfoDict();
        examInfoDict.setId(1);
        examInfoDict.setExamType(0);
        examInfoDict.setAnswerIs(1);

        ExamInfoDict examInfoDict1 = new ExamInfoDict();
        examInfoDict1.setId(2);
        examInfoDict1.setExamType(0);
        examInfoDict1.setAnswerIs(0);

        ExamInfoDict examInfoDict2 = new ExamInfoDict();
        examInfoDict2.setId(3);
        examInfoDict2.setExamType(1);
        examInfoDict2.setAnswerIs(0);

        List<ExamInfoDict> examInfoDicts = new ArrayList<>();
        examInfoDicts.add(examInfoDict);
        examInfoDicts.add(examInfoDict1);
        examInfoDicts.add(examInfoDict2);

        UserInfoDict userInfoDict = new UserInfoDict();
        userInfoDict.setId(1);
        userInfoDict.setPhotoPath("yx.mm.com");
        userInfoDict.setRealName("张三");
        userInfoDict.setExamInfoDict(examInfoDicts);

        tempMap = new HashMap<>();
        tempMap.put("userInfo", userInfoDict);
        tempMap.put("flag", 1);
    }

    @Test
    public void beanPathTest1() {
        BeanPath pattern = new BeanPath("userInfo.examInfoDict[0].id");
        Assert.assertEquals("userInfo", pattern.patternParts.get(0));
        Assert.assertEquals("examInfoDict", pattern.patternParts.get(1));
        Assert.assertEquals("0", pattern.patternParts.get(2));
        Assert.assertEquals("id", pattern.patternParts.get(3));

    }

    @Test
    public void beanPathTest2() {
        BeanPath pattern = new BeanPath("[userInfo][examInfoDict][0][id]");
        Assert.assertEquals("userInfo", pattern.patternParts.get(0));
        Assert.assertEquals("examInfoDict", pattern.patternParts.get(1));
        Assert.assertEquals("0", pattern.patternParts.get(2));
        Assert.assertEquals("id", pattern.patternParts.get(3));
    }

    @Test
    public void beanPathTest3() {
        BeanPath pattern = new BeanPath("['userInfo']['examInfoDict'][0]['id']");
        Assert.assertEquals("userInfo", pattern.patternParts.get(0));
        Assert.assertEquals("examInfoDict", pattern.patternParts.get(1));
        Assert.assertEquals("0", pattern.patternParts.get(2));
        Assert.assertEquals("id", pattern.patternParts.get(3));
    }

    @Test
    public void getTest() {
        BeanPath pattern = BeanPath.create("userInfo.examInfoDict[0].id");
        Object result = pattern.get(tempMap);
        Assert.assertEquals(1, result);
    }

    @Test
    public void setTest() {
        BeanPath pattern = BeanPath.create("userInfo.examInfoDict[0].id");
        pattern.set(tempMap, 2);
        Object result = pattern.get(tempMap);
        Assert.assertEquals(2, result);
    }

}

package org.miaixz.bus.core.text.dfa;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.List;

public class SensitiveUtilTest {

    @Test
    public void testSensitiveFilter() {
        final List<String> wordList = new ArrayList<>();
        wordList.add("大");
        wordList.add("大土豆");
        wordList.add("土豆");
        wordList.add("刚出锅");
        wordList.add("出锅");
        final TestBean bean = new TestBean();
        bean.setStr("我有一颗$大土^豆，刚出锅的");
        bean.setNum(100);
        Sensitive.init(wordList);
        final String beanStr = Sensitive.sensitiveFilter(bean.getStr(), true, null);
        Assertions.assertEquals("我有一颗$****，***的", beanStr);
    }

    @Test
    public void issue() {
        Sensitive.init(ListKit.view("赵", "赵阿", "赵阿三"));

        final String result = Sensitive.sensitiveFilter("赵阿三在做什么。", true, null);
        Assertions.assertEquals("***在做什么。", result);
    }

    @Data
    public static class TestBean {
        private String str;
        private Integer num;
    }

}

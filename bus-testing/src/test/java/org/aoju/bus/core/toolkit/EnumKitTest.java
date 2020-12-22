package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * EnumKit单元测试
 */
public class EnumKitTest {

    @Test
    public void getNamesTest() {
        List<String> names = EnumKit.getNames(TestEnum.class);
        Assert.assertEquals(CollKit.newArrayList("TEST1", "TEST2", "TEST3"), names);
    }

    @Test
    public void getFieldValuesTest() {
        List<Object> types = EnumKit.getFieldValues(TestEnum.class, "type");
        Assert.assertEquals(CollKit.newArrayList("type1", "type2", "type3"), types);
    }

    @Test
    public void getFieldNamesTest() {
        List<String> names = EnumKit.getFieldNames(TestEnum.class);
        Assert.assertEquals(CollKit.newArrayList("type", "name"), names);
    }

    @Test
    public void likeValueOfTest() {
        TestEnum value = EnumKit.likeValueOf(TestEnum.class, "type2");
        Assert.assertEquals(TestEnum.TEST2, value);
    }

    @Test
    public void getEnumMapTest() {
        Map<String, TestEnum> enumMap = EnumKit.getEnumMap(TestEnum.class);
        Assert.assertEquals(TestEnum.TEST1, enumMap.get("TEST1"));
    }

    @Test
    public void getNameFieldMapTest() {
        Map<String, Object> enumMap = EnumKit.getFieldNames(TestEnum.class, "type");
        Assert.assertEquals("type1", enumMap.get("TEST1"));
    }

    public enum TestEnum {
        TEST1("type1"), TEST2("type2"), TEST3("type3");

        private final String type;
        private String name;

        TestEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }
    }

}

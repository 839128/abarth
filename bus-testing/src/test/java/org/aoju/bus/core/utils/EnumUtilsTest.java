package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * EnumUtils单元测试
 */
public class EnumUtilsTest {

    @Test
    public void getNamesTest() {
        List<String> names = EnumUtils.getNames(TestEnum.class);
        Assertions.assertEquals(CollUtils.newArrayList("TEST1", "TEST2", "TEST3"), names);
    }

    @Test
    public void getFieldValuesTest() {
        List<Object> types = EnumUtils.getFieldValues(TestEnum.class, "type");
        Assertions.assertEquals(CollUtils.newArrayList("type1", "type2", "type3"), types);
    }

    @Test
    public void getFieldNamesTest() {
        List<String> names = EnumUtils.getFieldNames(TestEnum.class);
        Assertions.assertEquals(CollUtils.newArrayList("type", "name"), names);
    }

    @Test
    public void likeValueOfTest() {
        TestEnum value = EnumUtils.likeValueOf(TestEnum.class, "type2");
        Assertions.assertEquals(TestEnum.TEST2, value);
    }

    @Test
    public void getEnumMapTest() {
        Map<String, TestEnum> enumMap = EnumUtils.getEnumMap(TestEnum.class);
        Assertions.assertEquals(TestEnum.TEST1, enumMap.get("TEST1"));
    }

    @Test
    public void getNameFieldMapTest() {
        Map<String, Object> enumMap = EnumUtils.getFieldNames(TestEnum.class, "type");
        Assertions.assertEquals("type1", enumMap.get("TEST1"));
    }

    public enum TestEnum {
        TEST1("type1"), TEST2("type2"), TEST3("type3");

        private String type;
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

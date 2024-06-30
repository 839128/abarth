package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * EnumUtil单元测试
 */
public class EnumKitTest {

    @Test
    public void getNamesTest() {
        final List<String> names = EnumKit.getNames(TestEnum.class);
        Assertions.assertEquals(ListKit.of("TEST1", "TEST2", "TEST3"), names);
    }

    @Test
    public void getFieldValuesTest() {
        final List<Object> types = EnumKit.getFieldValues(TestEnum.class, "type");
        Assertions.assertEquals(ListKit.of("type1", "type2", "type3"), types);
    }

    @Test
    public void getFieldNamesTest() {
        final List<String> names = EnumKit.getFieldNames(TestEnum.class);
        Assertions.assertTrue(names.contains("type"));
        Assertions.assertTrue(names.contains("name"));
    }

    @Test
    public void getByTest() {
        // 枚举中字段互相映射使用
        final TestEnum testEnum = EnumKit.getBy(TestEnum::ordinal, 1);
        Assertions.assertEquals("TEST2", testEnum.name());
    }

    @Test
    public void getFieldByTest() {
        // 枚举中字段互相映射使用
        final String type = EnumKit.getFieldBy(TestEnum::getType, Enum::ordinal, 1);
        Assertions.assertEquals("type2", type);

        final int ordinal = EnumKit.getFieldBy(TestEnum::ordinal, Enum::ordinal, 1);
        Assertions.assertEquals(1, ordinal);
    }

    @Test
    public void likeValueOfTest() {
        final TestEnum value = EnumKit.likeValueOf(TestEnum.class, "type2");
        Assertions.assertEquals(TestEnum.TEST2, value);
    }

    @Test
    public void getEnumMapTest() {
        final Map<String, TestEnum> enumMap = EnumKit.getEnumMap(TestEnum.class);
        Assertions.assertEquals(TestEnum.TEST1, enumMap.get("TEST1"));
    }

    @Test
    public void getNameFieldMapTest() {
        final Map<String, Object> enumMap = EnumKit.getNameFieldMap(TestEnum.class, "type");
        assert enumMap != null;
        Assertions.assertEquals("type1", enumMap.get("TEST1"));
    }

    public enum TestEnum {
        TEST1("type1"), TEST2("type2"), TEST3("type3");

        private final String type;

        private String name;

        TestEnum(final String type) {
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

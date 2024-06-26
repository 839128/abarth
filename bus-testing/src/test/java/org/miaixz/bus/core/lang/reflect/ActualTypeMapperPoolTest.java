package org.miaixz.bus.core.lang.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Map;

public class ActualTypeMapperPoolTest {

    @Test
    public void getTypeArgumentTest() {
        final Map<Type, Type> typeTypeMap = ActualTypeMapperPool.get(FinalClass.class);
        typeTypeMap.forEach((key, value) -> {
            if ("A".equals(key.getTypeName())) {
                Assertions.assertEquals(Character.class, value);
            } else if ("B".equals(key.getTypeName())) {
                Assertions.assertEquals(Boolean.class, value);
            } else if ("C".equals(key.getTypeName())) {
                Assertions.assertEquals(String.class, value);
            } else if ("D".equals(key.getTypeName())) {
                Assertions.assertEquals(Double.class, value);
            } else if ("E".equals(key.getTypeName())) {
                Assertions.assertEquals(Integer.class, value);
            }
        });
    }

    @Test
    public void getTypeArgumentStrKeyTest() {
        final Map<String, Type> typeTypeMap = ActualTypeMapperPool.getStrKeyMap(FinalClass.class);
        typeTypeMap.forEach((key, value) -> {
            if ("A".equals(key)) {
                Assertions.assertEquals(Character.class, value);
            } else if ("B".equals(key)) {
                Assertions.assertEquals(Boolean.class, value);
            } else if ("C".equals(key)) {
                Assertions.assertEquals(String.class, value);
            } else if ("D".equals(key)) {
                Assertions.assertEquals(Double.class, value);
            } else if ("E".equals(key)) {
                Assertions.assertEquals(Integer.class, value);
            }
        });
    }

    public interface BaseInterface<A, B, C> {
    }

    public interface FirstInterface<A, B, D, E> extends BaseInterface<A, B, String> {
    }

    public interface SecondInterface<A, B, F> extends BaseInterface<A, B, String> {
    }

    public static class BaseClass<A, D> implements FirstInterface<A, Boolean, D, Integer> {
    }

    public static class FirstClass extends BaseClass<Character, Double> implements SecondInterface<Character, Boolean, FirstClass> {
    }

    public static class SecondClass extends FirstClass {
    }

    public static class FinalClass extends SecondClass {
    }
}

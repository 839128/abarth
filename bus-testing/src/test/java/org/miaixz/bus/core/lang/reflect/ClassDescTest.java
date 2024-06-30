package org.miaixz.bus.core.lang.reflect;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ReflectKit;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 来自：org.apache.dubbo.common.utils.ClassDescUtilTest
 */
public class ClassDescTest {

    @Test
    void descToClassTest() {
        assertEquals(void.class, ReflectKit.descToClass("V"));
        assertEquals(boolean.class, ReflectKit.descToClass("Z"));
        assertEquals(boolean[].class, ReflectKit.descToClass("[Z"));
        assertEquals(byte.class, ReflectKit.descToClass("B"));
        assertEquals(char.class, ReflectKit.descToClass("C"));
        assertEquals(double.class, ReflectKit.descToClass("D"));
        assertEquals(float.class, ReflectKit.descToClass("F"));
        assertEquals(int.class, ReflectKit.descToClass("I"));
        assertEquals(long.class, ReflectKit.descToClass("J"));
        assertEquals(short.class, ReflectKit.descToClass("S"));
        assertEquals(String.class, ReflectKit.descToClass("Ljava.lang.String;"));
        assertEquals(int[][].class, ReflectKit.descToClass(ReflectKit.getDesc(int[][].class)));
        assertEquals(ClassDescTest[].class, ReflectKit.descToClass(ReflectKit.getDesc(ClassDescTest[].class)));
    }

    @Test
    void getDescTest() {
        // getDesc
        assertEquals("Z", ReflectKit.getDesc(boolean.class));
        assertEquals("[[[I", ReflectKit.getDesc(int[][][].class));
        assertEquals("[[Ljava/lang/Object;", ReflectKit.getDesc(Object[][].class));
    }

    @Test
    void nameToClassTest() {
        Class<?> aClass = ReflectKit.nameToClass("java.lang.Object[]", true, null);
        assertEquals(Object[].class, aClass);

        aClass = ReflectKit.nameToClass("java.lang.Object", true, null);
        assertEquals(Object.class, aClass);
    }

    @Test
    void descToNameTest() {
        assertEquals("short[]", ReflectKit.descToName(ReflectKit.getDesc(short[].class)));
        assertEquals("boolean[]", ReflectKit.descToName(ReflectKit.getDesc(boolean[].class)));
        assertEquals("byte[]", ReflectKit.descToName(ReflectKit.getDesc(byte[].class)));
        assertEquals("char[]", ReflectKit.descToName(ReflectKit.getDesc(char[].class)));
        assertEquals("double[]", ReflectKit.descToName(ReflectKit.getDesc(double[].class)));
        assertEquals("float[]", ReflectKit.descToName(ReflectKit.getDesc(float[].class)));
        assertEquals("int[]", ReflectKit.descToName(ReflectKit.getDesc(int[].class)));
        assertEquals("long[]", ReflectKit.descToName(ReflectKit.getDesc(long[].class)));
        assertEquals("int", ReflectKit.descToName(ReflectKit.getDesc(int.class)));
        assertEquals("void", ReflectKit.descToName(ReflectKit.getDesc(void.class)));
        assertEquals("java.lang.Object[][]", ReflectKit.descToName(ReflectKit.getDesc(Object[][].class)));
    }

    @Test
    void nameToDescTest() {
        // name2desc
        assertEquals("Z", ReflectKit.nameToDesc(ReflectKit.getName(boolean.class)));
        assertEquals("[[[I", ReflectKit.nameToDesc(ReflectKit.getName(int[][][].class)));
        assertEquals("[[Ljava/lang/Object;",
                ReflectKit.nameToDesc(ReflectKit.getName(Object[][].class)));
    }

    @Test
    @SneakyThrows
    public void testGetDescriptor() {
        // methods
        Assertions.assertEquals("()I", ReflectKit.getDesc(
                Object.class.getMethod("hashCode"), false));
        Assertions.assertEquals("()Ljava/lang/String;", ReflectKit.getDesc(
                Object.class.getMethod("toString"), false));
        Assertions.assertEquals("(Ljava/lang/Object;)Z", ReflectKit.getDesc(
                Object.class.getMethod("equals", Object.class), false));
        Assertions.assertEquals("(II)I", ReflectKit.getDesc(
                Integer.class.getDeclaredMethod("compare", int.class, int.class), false));
        Assertions.assertEquals("([Ljava/lang/Object;)Ljava/util/List;", ReflectKit.getDesc(
                Arrays.class.getMethod("asList", Object[].class), false));
        Assertions.assertEquals("()V", ReflectKit.getDesc(
                Object.class.getConstructor(), false));

        // clazz
        Assertions.assertEquals("Z", ReflectKit.getDesc(boolean.class));
        Assertions.assertEquals("Ljava/lang/Boolean;", ReflectKit.getDesc(Boolean.class));
        Assertions.assertEquals("[[[D", ReflectKit.getDesc(double[][][].class));
        Assertions.assertEquals("I", ReflectKit.getDesc(int.class));
        Assertions.assertEquals("Ljava/lang/Integer;", ReflectKit.getDesc(Integer.class));
        Assertions.assertEquals("V", ReflectKit.getDesc(void.class));
        Assertions.assertEquals("Ljava/lang/Void;", ReflectKit.getDesc(Void.class));
        Assertions.assertEquals("Ljava/lang/Object;", ReflectKit.getDesc(Object.class));
    }

}

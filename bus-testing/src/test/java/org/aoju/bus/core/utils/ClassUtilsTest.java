package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * {@link ClassUtils} 单元测试
 */
public class ClassUtilsTest {

    @Test
    public void getClassNameTest() {
        String className = ClassUtils.getClassName(ClassUtils.class, false);
        Assertions.assertEquals("org.aoju.bus.core.utils.ClassUtils", className);

        String simpleClassName = ClassUtils.getClassName(ClassUtils.class, true);
        Assertions.assertEquals("ClassUtils", simpleClassName);
    }

    @Test
    public void getPublicMethod() {
        Method superPublicMethod = ClassUtils.getPublicMethod(TestSubClass.class, "publicMethod");
        Assertions.assertNotNull(superPublicMethod);
        Method superPrivateMethod = ClassUtils.getPublicMethod(TestSubClass.class, "privateMethod");
        Assertions.assertNull(superPrivateMethod);

        Method publicMethod = ClassUtils.getPublicMethod(TestSubClass.class, "publicSubMethod");
        Assertions.assertNotNull(publicMethod);
        Method privateMethod = ClassUtils.getPublicMethod(TestSubClass.class, "privateSubMethod");
        Assertions.assertNull(privateMethod);
    }

    @Test
    public void getDeclaredMethod() {
        Method noMethod = ClassUtils.getDeclaredMethod(TestSubClass.class, "noMethod");
        Assertions.assertNull(noMethod);

        Method privateMethod = ClassUtils.getDeclaredMethod(TestSubClass.class, "privateMethod");
        Assertions.assertNotNull(privateMethod);
        Method publicMethod = ClassUtils.getDeclaredMethod(TestSubClass.class, "publicMethod");
        Assertions.assertNotNull(publicMethod);

        Method publicSubMethod = ClassUtils.getDeclaredMethod(TestSubClass.class, "publicSubMethod");
        Assertions.assertNotNull(publicSubMethod);
        Method privateSubMethod = ClassUtils.getDeclaredMethod(TestSubClass.class, "privateSubMethod");
        Assertions.assertNotNull(privateSubMethod);

    }

    @Test
    public void getDeclaredField() {
        Field noField = ClassUtils.getDeclaredField(TestSubClass.class, "noField");
        Assertions.assertNull(noField);

        // 获取不到父类字段
        Field field = ClassUtils.getDeclaredField(TestSubClass.class, "field");
        Assertions.assertNull(field);

        Field subField = ClassUtils.getDeclaredField(TestSubClass.class, "subField");
        Assertions.assertNotNull(subField);
    }

    @Test
    public void getClassPathTest() {
        String classPath = ClassUtils.getClassPath();
        Assertions.assertNotNull(classPath);
    }

    @Test
    public void getShortClassNameTest() {
        String className = "org.aoju.bus.core.utils.StringUtils";
        String result = ClassUtils.getShortClassName(className);
        Assertions.assertEquals("o.a.b.c.u.StringUtils", result);
    }

    @Test
    public void loadClassTest() {
        String name = ClassUtils.loadClass("java.lang.Thread.State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);

        name = ClassUtils.loadClass("java.lang.Thread$State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);
    }

    static class TestClass {
        protected String field;
        private String privateField;

        private void privateMethod() {
        }

        public void publicMethod() {
        }
    }

    class TestSubClass extends TestClass {
        private String subField;

        private void privateSubMethod() {
        }

        public void publicSubMethod() {
        }

    }

}

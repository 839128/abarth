package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * {@link ClassKit} 单元测试
 */
public class ClassKitTest {

    @Test
    public void getClassNameTest() {
        String className = ClassKit.getClassName(ClassKit.class, false);
        Assert.assertEquals("org.aoju.bus.core.utils.ClassKit", className);

        String simpleClassName = ClassKit.getClassName(ClassKit.class, true);
        Assert.assertEquals("ClassKit", simpleClassName);
    }

    @Test
    public void getPublicMethod() {
        Method superPublicMethod = ClassKit.getPublicMethod(TestSubClass.class, "publicMethod");
        Assert.assertNotNull(superPublicMethod);
        Method superPrivateMethod = ClassKit.getPublicMethod(TestSubClass.class, "privateMethod");
        Assert.assertNull(superPrivateMethod);

        Method publicMethod = ClassKit.getPublicMethod(TestSubClass.class, "publicSubMethod");
        Assert.assertNotNull(publicMethod);
        Method privateMethod = ClassKit.getPublicMethod(TestSubClass.class, "privateSubMethod");
        Assert.assertNull(privateMethod);
    }

    @Test
    public void getDeclaredMethod() {
        Method noMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "noMethod");
        Assert.assertNull(noMethod);

        Method privateMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "privateMethod");
        Assert.assertNotNull(privateMethod);
        Method publicMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "publicMethod");
        Assert.assertNotNull(publicMethod);

        Method publicSubMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "publicSubMethod");
        Assert.assertNotNull(publicSubMethod);
        Method privateSubMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "privateSubMethod");
        Assert.assertNotNull(privateSubMethod);

    }

    @Test
    public void getDeclaredField() {
        Field noField = ClassKit.getDeclaredField(TestSubClass.class, "noField");
        Assert.assertNull(noField);

        // 获取不到父类字段
        Field field = ClassKit.getDeclaredField(TestSubClass.class, "field");
        Assert.assertNull(field);

        Field subField = ClassKit.getDeclaredField(TestSubClass.class, "subField");
        Assert.assertNotNull(subField);
    }

    @Test
    public void getClassPathTest() {
        String classPath = ClassKit.getClassPath();
        Assert.assertNotNull(classPath);
    }

    @Test
    public void getShortClassNameTest() {
        String className = "org.aoju.bus.core.utils.StringKit";
        String result = ClassKit.getShortClassName(className);
        Assert.assertEquals("o.a.b.c.u.StringKit", result);
    }

    @Test
    public void loadClassTest() {
        String name = ClassKit.loadClass("java.lang.Thread.State").getName();
        Assert.assertEquals("java.lang.Thread$State", name);

        name = ClassKit.loadClass("java.lang.Thread$State").getName();
        Assert.assertEquals("java.lang.Thread$State", name);
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

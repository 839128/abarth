package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class LookupKitTest {

    @Test
    void lookupTest() {
        final MethodHandles.Lookup lookup = LookupKit.lookup();
        Assertions.assertNotNull(lookup);
    }

    @Test
    void findMethodTest() throws Throwable {
        MethodHandle handle = LookupKit.findMethod(Duck.class, "quack",
                MethodType.methodType(String.class));
        Assertions.assertNotNull(handle);
        // 对象方法自行需要绑定对象或者传入对象参数
        final String invoke = (String) handle.invoke(new BigDuck());
        Assertions.assertEquals("Quack", invoke);

        // 对象的方法获取
        handle = LookupKit.findMethod(BigDuck.class, "getSize",
                MethodType.methodType(int.class));
        Assertions.assertNotNull(handle);
        final int invokeInt = (int) handle.invoke(new BigDuck());
        Assertions.assertEquals(36, invokeInt);
    }

    @Test
    void findStaticMethodTest() throws Throwable {
        final MethodHandle handle = LookupKit.findMethod(Duck.class, "getDuck",
                MethodType.methodType(String.class, int.class));
        Assertions.assertNotNull(handle);

        // static 方法执行不需要绑定或者传入对象，直接传入参数即可
        final String invoke = (String) handle.invoke(12);
        Assertions.assertEquals("Duck 12", invoke);
    }

    @Test
    void findPrivateMethodTest() throws Throwable {
        final MethodHandle handle = LookupKit.findMethod(BigDuck.class, "getPrivateValue",
                MethodType.methodType(String.class));
        Assertions.assertNotNull(handle);

        final String invoke = (String) handle.invoke(new BigDuck());
        Assertions.assertEquals("private value", invoke);
    }

    @Test
    void findSuperMethodTest() throws Throwable {
        // 查找父类的方法
        final MethodHandle handle = LookupKit.findMethod(BigDuck.class, "quack",
                MethodType.methodType(String.class));
        Assertions.assertNotNull(handle);

        final String invoke = (String) handle.invoke(new BigDuck());
        Assertions.assertEquals("Quack", invoke);
    }

    @Test
    void findPrivateStaticMethodTest() throws Throwable {
        final MethodHandle handle = LookupKit.findMethod(BigDuck.class, "getPrivateStaticValue",
                MethodType.methodType(String.class));
        Assertions.assertNotNull(handle);

        final String invoke = (String) handle.invoke();
        Assertions.assertEquals("private static value", invoke);
    }

    @Test
    void unreflectTest() throws Throwable {
        final MethodHandle handle = LookupKit.unreflect(
                MethodKit.getMethodByName(BigDuck.class, "getSize"));

        final int invoke = (int) handle.invoke(new BigDuck());
        Assertions.assertEquals(36, invoke);
    }

    @Test
    void unreflectPrivateTest() throws Throwable {
        final MethodHandle handle = LookupKit.unreflect(
                MethodKit.getMethodByName(BigDuck.class, "getPrivateValue"));

        final String invoke = (String) handle.invoke(new BigDuck());
        Assertions.assertEquals("private value", invoke);
    }

    @Test
    void unreflectPrivateStaticTest() throws Throwable {
        final MethodHandle handle = LookupKit.unreflect(
                MethodKit.getMethodByName(BigDuck.class, "getPrivateStaticValue"));

        final String invoke = (String) handle.invoke();
        Assertions.assertEquals("private static value", invoke);
    }

    @Test
    void unreflectDefaultTest() throws Throwable {
        final MethodHandle handle = LookupKit.unreflect(
                MethodKit.getMethodByName(BigDuck.class, "quack"));

        final String invoke = (String) handle.invoke(new BigDuck());
        Assertions.assertEquals("Quack", invoke);
    }

    @Test
    void unreflectStaticInInterfaceTest() throws Throwable {
        final MethodHandle handle = LookupKit.unreflect(
                MethodKit.getMethodByName(BigDuck.class, "getDuck"));

        final String invoke = (String) handle.invoke(1);
        Assertions.assertEquals("Duck 1", invoke);
    }

    interface Duck {
        static String getDuck(final int count) {
            return "Duck " + count;
        }

        default String quack() {
            return "Quack";
        }
    }

    static class BigDuck implements Duck {

        private static String getPrivateStaticValue() {
            return "private static value";
        }

        public int getSize() {
            return 36;
        }


        private String getPrivateValue() {
            return "private value";
        }
    }

}

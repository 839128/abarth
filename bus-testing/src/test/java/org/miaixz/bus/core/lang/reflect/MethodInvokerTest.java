package org.miaixz.bus.core.lang.reflect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.reflect.method.MethodInvoker;
import org.miaixz.bus.core.xyz.ClassKit;
import org.miaixz.bus.core.xyz.MethodKit;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MethodInvokerTest {

    @Test
    public void invokeDefaultTest() {
        final Duck duck = (Duck) Proxy.newProxyInstance(
                ClassKit.getClassLoader(),
                new Class[]{Duck.class},
                MethodInvoker::invoke);

        Assertions.assertEquals("Quack", duck.quack());

        // 测试子类执行default方法
        final Method quackMethod = MethodKit.getMethod(Duck.class, "quack");
        String quack = MethodInvoker.invoke(new BigDuck(), quackMethod);
        Assertions.assertEquals("Quack", quack);

        // 测试反射执行默认方法
        quack = MethodKit.invoke(new Duck() {
        }, quackMethod);
        Assertions.assertEquals("Quack", quack);
    }

    @Test
    public void invokeDefaultByReflectTest() {
        final Duck duck = (Duck) Proxy.newProxyInstance(
                ClassKit.getClassLoader(),
                new Class[]{Duck.class},
                MethodKit::invoke);

        Assertions.assertEquals("Quack", duck.quack());
    }

    @Test
    public void invokeStaticByProxyTest() {
        final Duck duck = (Duck) Proxy.newProxyInstance(
                ClassKit.getClassLoader(),
                new Class[]{Duck.class},
                MethodKit::invoke);

        Assertions.assertEquals("Quack", duck.quack());
    }

    @Test
    public void invokeTest() {
        // 测试执行普通方法
        final int size = MethodInvoker.invoke(new BigDuck(),
                MethodKit.getMethod(BigDuck.class, "getSize"));
        Assertions.assertEquals(36, size);
    }

    @Test
    public void invokeStaticTest() {
        // 测试执行普通方法
        final String result = MethodInvoker.invoke(null,
                MethodKit.getMethod(Duck.class, "getDuck", int.class), 78);
        Assertions.assertEquals("Duck 78", result);
    }

    @Test
    public void testInvokeExact() {
        final Duck duck = new BigDuck();

        final Method method = MethodKit.getMethod(BigDuck.class, "toString");
        final Object[] args = {};

        // 执行测试方法
        final String result = MethodInvoker.invokeExact(duck, method, args);

        // 验证结果
        Assertions.assertEquals(duck.toString(), result);
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

        @Override
        public String toString() {
            return super.toString();
        }
    }

}

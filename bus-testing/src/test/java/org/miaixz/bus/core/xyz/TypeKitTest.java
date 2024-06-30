package org.miaixz.bus.core.xyz;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeKitTest {

    @Test
    public void getEleTypeTest() {
        final Method method = MethodKit.getMethod(TestClass.class, "getList");
        final Type type = TypeKit.getReturnType(method);
        Assertions.assertEquals("java.util.List<java.lang.String>", type.toString());

        final Type type2 = TypeKit.getTypeArgument(type);
        Assertions.assertEquals(String.class, type2);
    }

    @Test
    public void getParamTypeTest() {
        final Method method = MethodKit.getMethod(TestClass.class, "intTest", Integer.class);
        final Type type = TypeKit.getParamType(method, 0);
        Assertions.assertEquals(Integer.class, type);

        final Type returnType = TypeKit.getReturnType(method);
        Assertions.assertEquals(Integer.class, returnType);
    }

    @Test
    public void getTypeArgumentTest() {
        // 测试不继承父类，而是实现泛型接口时是否可以获取成功。
        final Type typeArgument = TypeKit.getTypeArgument(IPService.class);
        Assertions.assertEquals(String.class, typeArgument);
    }

    @Test
    public void getActualTypesTest() {
        // 测试多层级泛型参数是否能获取成功
        final Type idType = TypeKit.getActualType(Level3.class,
                FieldKit.getField(Level3.class, "id"));

        Assertions.assertEquals(Long.class, idType);
    }

    @Test
    public void getClasses() {
        Method method = MethodKit.getMethod(Parent.class, "getLevel");
        Type returnType = TypeKit.getReturnType(method);
        Class<?> clazz = TypeKit.getClass(returnType);
        Assertions.assertEquals(Level1.class, clazz);

        method = MethodKit.getMethod(Level1.class, "getId");
        returnType = TypeKit.getReturnType(method);
        clazz = TypeKit.getClass(returnType);
        Assertions.assertEquals(Object.class, clazz);
    }

    public interface OperateService<T> {
        void service(T t);
    }

    public static class TestClass {
        public List<String> getList() {
            return new ArrayList<>();
        }

        public Integer intTest(final Integer integer) {
            return 1;
        }
    }

    public static class IPService implements OperateService<String> {
        @Override
        public void service(final String string) {
        }
    }

    public static class Level3 extends Level2<Level3> {

    }

    public static class Level2<E> extends Level1<Long> {

    }

    @Data
    public static class Level1<T> {
        private T id;
    }

    @Data
    public static class Parent<T extends Level1<B>, B extends Long> {
        private T level;
    }

}

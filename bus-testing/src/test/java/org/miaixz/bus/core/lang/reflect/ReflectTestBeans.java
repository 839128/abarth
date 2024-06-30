package org.miaixz.bus.core.lang.reflect;

import lombok.Data;

/**
 * 反射工具用于测试的类
 */
public class ReflectTestBeans {

    interface TestInterface1 {

        void getA();


        void getB();


        default void getC() {

        }
    }

    interface TestInterface2 extends TestInterface1 {
        @Override
        void getB();
    }

    protected interface TestInterface3 extends TestInterface2 {
        void get3();
    }

    @Data
    public static class AClass {
        private int a;
    }

    @Data
    protected static class TestBenchClass {
        private int a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
    }


    static class TestClass {
        protected String field;
        private String privateField;

        private void privateMethod() {
        }

        public void publicMethod() {
        }
    }

    @Data
    public class NoneStaticClass {
        private int a = 2;
    }


    class C1 implements TestInterface2 {

        @Override
        public void getA() {

        }

        @Override
        public void getB() {

        }
    }

    protected class C2 extends C1 {
        @Override
        public void getA() {

        }
    }

    public class TestSubClass extends TestClass {
        private String subField;

        private void privateSubMethod() {
        }

        public void publicSubMethod() {
        }

    }

}

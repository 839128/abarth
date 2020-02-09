package org.aoju.bus.core.beans;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link DynaBean}单元测试
 */
public class DynaBeanTest {

    @Test
    public void beanTest() {
        User user = new User();
        DynaBean bean = DynaBean.create(user);
        bean.set("name", "李华");
        bean.set("age", 12);

        String name = bean.get("name");
        Assertions.assertEquals(user.getName(), name);
        int age = bean.get("age");
        Assertions.assertEquals(user.getAge(), age);

        //重复包装测试
        DynaBean bean2 = new DynaBean(bean);
        User user2 = bean2.getBean();
        Assertions.assertEquals(user, user2);

        //执行指定方法
        Object invoke = bean2.invoke("test");
        Assertions.assertEquals("test for 李华", invoke);
    }

    @Data
    public static class User {
        private String name;
        private int age;

        public String test() {
            return "test for " + this.name;
        }

        @Override
        public String toString() {
            return "User [name=" + name + ", age=" + age + "]";
        }

    }

}

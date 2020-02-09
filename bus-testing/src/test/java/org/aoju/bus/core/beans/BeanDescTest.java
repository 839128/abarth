package org.aoju.bus.core.beans;

import lombok.Data;
import org.aoju.bus.core.utils.BeanUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link BeanDesc} 单元测试类
 */
public class BeanDescTest {

    @Test
    public void propDescTes() {
        BeanDesc desc = BeanUtils.getBeanDesc(User.class);
        Assertions.assertEquals("User", desc.getSimpleName());
        Assertions.assertEquals("age", desc.getField("age").getName());
        Assertions.assertEquals("getAge", desc.getGetter("age").getName());
        Assertions.assertEquals("setAge", desc.getSetter("age").getName());
        Assertions.assertEquals(1, desc.getSetter("age").getParameterTypes().length);
        Assertions.assertEquals(int.class, desc.getSetter("age").getParameterTypes()[0]);

    }

    @Test
    public void propDescTes2() {
        BeanDesc desc = BeanUtils.getBeanDesc(User.class);

        BeanDesc.PropDesc prop = desc.getProp("name");
        Assertions.assertEquals("name", prop.getFieldName());
        Assertions.assertEquals("getName", prop.getGetter().getName());
        Assertions.assertEquals("setName", prop.getSetter().getName());
        Assertions.assertEquals(1, prop.getSetter().getParameterTypes().length);
        Assertions.assertEquals(String.class, prop.getSetter().getParameterTypes()[0]);
    }

    @Test
    public void propDescOfBooleanTest() {
        BeanDesc desc = BeanUtils.getBeanDesc(User.class);

        Assertions.assertEquals("isAdmin", desc.getGetter("isAdmin").getName());
        Assertions.assertEquals("setAdmin", desc.getSetter("isAdmin").getName());
        Assertions.assertEquals("isGender", desc.getGetter("gender").getName());
        Assertions.assertEquals("setGender", desc.getSetter("gender").getName());
    }

    @Test
    public void getSetTest() {
        BeanDesc desc = BeanUtils.getBeanDesc(User.class);

        User user = new User();
        desc.getProp("name").setValue(user, "张三");
        Assertions.assertEquals("张三", user.getName());

        Object value = desc.getProp("name").getValue(user);
        Assertions.assertEquals("张三", value);
    }

    @Data
    public static class User {
        private String name;
        private int age;
        private boolean isAdmin;
        private boolean isSuper;
        private boolean gender;
    }

}

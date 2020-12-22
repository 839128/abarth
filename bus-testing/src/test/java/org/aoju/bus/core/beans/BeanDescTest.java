package org.aoju.bus.core.beans;

import lombok.Data;
import org.aoju.bus.core.toolkit.BeanKit;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@link BeanDesc} 单元测试类
 */
public class BeanDescTest {

    @Test
    public void propDescTes() {
        BeanDesc desc = BeanKit.getBeanDesc(User.class);
        Assert.assertEquals("User", desc.getSimpleName());
        Assert.assertEquals("age", desc.getField("age").getName());
        Assert.assertEquals("getAge", desc.getGetter("age").getName());
        Assert.assertEquals("setAge", desc.getSetter("age").getName());
        Assert.assertEquals(1, desc.getSetter("age").getParameterTypes().length);
        Assert.assertEquals(int.class, desc.getSetter("age").getParameterTypes()[0]);

    }

    @Test
    public void propDescTes2() {
        BeanDesc desc = BeanKit.getBeanDesc(User.class);

        BeanDesc.PropDesc prop = desc.getProp("name");
        Assert.assertEquals("name", prop.getFieldName());
        Assert.assertEquals("getName", prop.getGetter().getName());
        Assert.assertEquals("setName", prop.getSetter().getName());
        Assert.assertEquals(1, prop.getSetter().getParameterTypes().length);
        Assert.assertEquals(String.class, prop.getSetter().getParameterTypes()[0]);
    }

    @Test
    public void propDescOfBooleanTest() {
        BeanDesc desc = BeanKit.getBeanDesc(User.class);

        Assert.assertEquals("isAdmin", desc.getGetter("isAdmin").getName());
        Assert.assertEquals("setAdmin", desc.getSetter("isAdmin").getName());
        Assert.assertEquals("isGender", desc.getGetter("gender").getName());
        Assert.assertEquals("setGender", desc.getSetter("gender").getName());
    }

    @Test
    public void getSetTest() {
        BeanDesc desc = BeanKit.getBeanDesc(User.class);

        User user = new User();
        desc.getProp("name").setValue(user, "张三");
        Assert.assertEquals("张三", user.getName());

        Object value = desc.getProp("name").getValue(user);
        Assert.assertEquals("张三", value);
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

package org.miaixz.bus.core.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.BeanKit;

/**
 * {@link StrictBeanDesc} 单元测试类
 */
public class BeanDescTest {

    @Test
    public void propDescTes() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);
        Assertions.assertEquals("User", desc.getSimpleName());

        Assertions.assertEquals("age", desc.getField("age").getName());
        Assertions.assertEquals("getAge", desc.getGetter("age").getName());
        Assertions.assertEquals("setAge", desc.getSetter("age").getName());
        Assertions.assertEquals(1, desc.getSetter("age").getParameterTypes().length);
        Assertions.assertSame(int.class, desc.getSetter("age").getParameterTypes()[0]);

    }

    @Test
    public void propDescTes2() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);

        final PropDesc prop = desc.getProp("name");
        Assertions.assertEquals("name", prop.getFieldName());
        Assertions.assertEquals("getName", prop.getGetter().getName());
        Assertions.assertEquals("setName", prop.getSetter().getName());
        Assertions.assertEquals(1, prop.getSetter().getParameterTypes().length);
        Assertions.assertSame(String.class, prop.getSetter().getParameterTypes()[0]);
    }

    @Test
    public void propDescOfBooleanTest() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);

        Assertions.assertEquals("isAdmin", desc.getGetter("isAdmin").getName());
        Assertions.assertEquals("setAdmin", desc.getSetter("isAdmin").getName());
        Assertions.assertEquals("isGender", desc.getGetter("gender").getName());
        Assertions.assertEquals("setGender", desc.getSetter("gender").getName());
    }

    @Test
    public void propDescOfBooleanTest2() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);

        Assertions.assertEquals("isIsSuper", desc.getGetter("isSuper").getName());
        Assertions.assertEquals("setIsSuper", desc.getSetter("isSuper").getName());
    }

    @Test
    public void propDescOfBooleanTest3() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);

        Assertions.assertEquals("setLastPage", desc.getSetter("lastPage").getName());
        Assertions.assertEquals("setIsLastPage", desc.getSetter("isLastPage").getName());
    }

    @Test
    public void getSetTest() {
        final StrictBeanDesc desc = BeanKit.getBeanDesc(User.class);

        final User user = new User();
        desc.getProp("name").setValue(user, "张三");
        Assertions.assertEquals("张三", user.getName());

        final Object value = desc.getProp("name").getValue(user);
        Assertions.assertEquals("张三", value);
    }

    public static class User {
        private String name;
        private int age;
        private boolean isAdmin;
        private boolean isSuper;
        private boolean gender;
        private Boolean lastPage;
        private Boolean isLastPage;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public User setAge(final int age) {
            this.age = age;
            return this;
        }

        public String testMethod() {
            return "test for " + this.name;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(final boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public boolean isIsSuper() {
            return isSuper;
        }

        public void setIsSuper(final boolean isSuper) {
            this.isSuper = isSuper;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(final boolean gender) {
            this.gender = gender;
        }

        public Boolean getIsLastPage() {
            return this.isLastPage;
        }

        public void setIsLastPage(final Boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public Boolean getLastPage() {
            return this.lastPage;
        }

        public void setLastPage(final Boolean lastPage) {
            this.lastPage = lastPage;
        }

        @Override
        public String toString() {
            return "User [name=" + name + ", age=" + age + ", isAdmin=" + isAdmin + ", gender=" + gender + "]";
        }
    }
}

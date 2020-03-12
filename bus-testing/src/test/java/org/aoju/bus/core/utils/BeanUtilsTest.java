package org.aoju.bus.core.utils;

import lombok.Data;
import org.aoju.bus.core.beans.copier.CopyOptions;
import org.aoju.bus.core.beans.copier.ValueProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

/**
 * Bean工具单元测试
 */
public class BeanUtilsTest {

    @Test
    public void isBeanTest() {

        // HashMap不包含setXXX方法，不是bean
        boolean isBean = BeanUtils.isBean(HashMap.class);
        Assertions.assertFalse(isBean);
    }

    @Test
    public void fillBeanTest() {
        Person person = BeanUtils.fillBean(new Person(), new ValueProvider<String>() {

            @Override
            public Object value(String key, Type valueType) {
                switch (key) {
                    case "name":
                        return "张三";
                    case "age":
                        return 18;
                }
                return null;
            }

            @Override
            public boolean containsKey(String key) {
                // 总是存在key
                return true;
            }

        }, CopyOptions.create());

        Assertions.assertEquals(person.getName(), "张三");
        Assertions.assertEquals(person.getAge(), 18);
    }

    @Test
    public void fillBeanWithMapIgnoreCaseTest() {
        HashMap<String, Object> map = CollUtils.newHashMap();
        map.put("Name", "Joe");
        map.put("aGe", 12);
        map.put("openId", "DFDFSDFWERWER");
        SubPerson person = BeanUtils.fillBeanWithMapIgnoreCase(map, new SubPerson(), false);
        Assertions.assertEquals(person.getName(), "Joe");
        Assertions.assertEquals(person.getAge(), 12);
        Assertions.assertEquals(person.getOpenid(), "DFDFSDFWERWER");
    }

    @Test
    public void mapToBeanIgnoreCaseTest() {
        HashMap<String, Object> map = CollUtils.newHashMap();
        map.put("Name", "Joe");
        map.put("aGe", 12);

        Person person = BeanUtils.mapToBeanIgnoreCase(map, Person.class, false);
        Assertions.assertEquals("Joe", person.getName());
        Assertions.assertEquals(12, person.getAge());
    }

    @Test
    public void mapToBeanTest() {
        HashMap<String, Object> map = CollUtils.newHashMap();
        map.put("a_name", "Joe");
        map.put("b_age", 12);

        // 别名，用于对应bean的字段名
        HashMap<String, String> mapping = CollUtils.newHashMap();
        mapping.put("a_name", "name");
        mapping.put("b_age", "age");

        Person person = BeanUtils.mapToBean(map, Person.class, CopyOptions.create().setFieldMapping(mapping));
        Assertions.assertEquals("Joe", person.getName());
        Assertions.assertEquals(12, person.getAge());
    }

    /**
     * 测试public类型的字段注入是否成功
     */
    @Test
    public void mapToBeanTest2() {
        HashMap<String, Object> map = CollUtils.newHashMap();
        map.put("name", "Joe");
        map.put("age", 12);

        Person2 person = BeanUtils.mapToBean(map, Person2.class, CopyOptions.create());
        Assertions.assertEquals("Joe", person.name);
        Assertions.assertEquals(12, person.age);
    }

    @Test
    public void beanToMapTest() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<String, Object> map = BeanUtils.beanToMap(person);

        Assertions.assertEquals("测试A11", map.get("name"));
        Assertions.assertEquals(14, map.get("age"));
        Assertions.assertEquals("11213232", map.get("openid"));
        // static属性应被忽略
        Assertions.assertFalse(map.containsKey("SUBNAME"));
    }

    @Test
    public void beanToMapTest2() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<String, Object> map = BeanUtils.beanToMap(person, true, true);
        Assertions.assertEquals("sub名字", map.get("sub_name"));
    }

    @Test
    public void beanToMapWithLocalDateTimeTest() {
        final LocalDateTime now = LocalDateTime.now();

        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");
        person.setDate(now);
        person.setDate2(now.toLocalDate());

        Map<String, Object> map = BeanUtils.beanToMap(person, false, true);
        Assertions.assertEquals(now, map.get("date"));
        Assertions.assertEquals(now.toLocalDate(), map.get("date2"));
    }

    @Test
    public void getPropertyTest() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Object name = BeanUtils.getProperty(person, "name");
        Assertions.assertEquals("测试A11", name);
        Object subName = BeanUtils.getProperty(person, "subName");
        Assertions.assertEquals("sub名字", subName);
    }

    @Test
    public void getPropertyDescriptorsTest() {
        HashSet<Object> set = CollUtils.newHashSet();
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(SubPerson.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            set.add(propertyDescriptor.getName());
        }
        Assertions.assertTrue(set.contains("age"));
        Assertions.assertTrue(set.contains("id"));
        Assertions.assertTrue(set.contains("name"));
        Assertions.assertTrue(set.contains("openid"));
        Assertions.assertTrue(set.contains("slow"));
        Assertions.assertTrue(set.contains("subName"));
    }

    @Test
    public void copyPropertiesHasBooleanTest() {
        SubPerson p1 = new SubPerson();
        p1.setSlow(true);

        // 测试boolean参数值isXXX形式
        SubPerson p2 = new SubPerson();
        BeanUtils.copyProperties(p1, p2);
        Assertions.assertTrue(p2.getSlow());

        // 测试boolean参数值非isXXX形式
        SubPerson2 p3 = new SubPerson2();
        BeanUtils.copyProperties(p1, p3);
        Assertions.assertTrue(p3.getSlow());
    }

    @Test
    public void copyPropertiesBeanToMapTest() {
        // 测试BeanToMap
        SubPerson p1 = new SubPerson();
        p1.setSlow(true);
        p1.setName("测试");
        p1.setSubName("sub测试");

        Map<String, Object> map = MapUtils.newHashMap();
        BeanUtils.copyProperties(p1, map);
        Assertions.assertTrue((Boolean) map.get("slow"));
        Assertions.assertEquals("测试", map.get("name"));
        Assertions.assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void copyPropertiesMapToMapTest() {
        // 测试MapToMap
        Map<String, Object> p1 = new HashMap<>();
        p1.put("isSlow", true);
        p1.put("name", "测试");
        p1.put("subName", "sub测试");

        Map<String, Object> map = MapUtils.newHashMap();
        BeanUtils.copyProperties(p1, map);
        Assertions.assertTrue((Boolean) map.get("isSlow"));
        Assertions.assertEquals("测试", map.get("name"));
        Assertions.assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void trimBeanStrFieldsTest() {
        Person person = new Person();
        person.setAge(1);
        person.setName("  张三 ");
        person.setOpenid(null);
        Person person2 = BeanUtils.trimStrField(person);

        // 是否改变原对象
        Assertions.assertEquals("张三", person.getName());
        Assertions.assertEquals("张三", person2.getName());
    }

    @Data
    public static class SubPerson extends Person {

        public static final String SUBNAME = "TEST";

        private UUID id;
        private String subName;
        private Boolean slow;
        private LocalDateTime date;
        private LocalDate date2;
    }

    @Data
    public static class SubPerson2 extends Person {
        private String subName;
        private Boolean slow;
    }

    @Data
    public static class Person {
        private String name;
        private int age;
        private String openid;
    }

    @Data
    public static class Person2 {
        public String name;
        public int age;
        public String openid;
    }

}

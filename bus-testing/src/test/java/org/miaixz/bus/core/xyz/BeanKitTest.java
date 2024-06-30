package org.miaixz.bus.core.xyz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.annotation.Alias;
import org.miaixz.bus.core.beans.copier.CopyOptions;
import org.miaixz.bus.core.beans.copier.ValueProvider;
import org.miaixz.bus.core.beans.path.BeanPath;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.center.map.MapBuilder;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Bean工具单元测试
 */
public class BeanKitTest {

    @Test
    public void isBeanTest() {

        // HashMap不包含setXXX方法，不是bean
        final boolean isBean = BeanKit.isWritableBean(HashMap.class);
        assertFalse(isBean);
    }

    @Test
    public void issueTest() {
        final boolean bean = BeanKit.isWritableBean(Dictionary.class);
        assertFalse(bean);
    }

    @Test
    public void fillBeanTest() {
        final Person person = BeanKit.fillBean(new Person(), new ValueProvider<>() {

            @Override
            public Object value(final String key, final Type valueType) {
                switch (key) {
                    case "name":
                        return "张三";
                    case "age":
                        return 18;
                }
                return null;
            }

            @Override
            public boolean containsKey(final String key) {
                // 总是存在key
                return true;
            }

        }, CopyOptions.of());

        assertEquals("张三", person.getName());
        assertEquals(18, person.getAge());
    }

    @Test
    public void fillBeanWithMapIgnoreCaseTest() {
        final Map<String, Object> map = MapBuilder.<String, Object>of()
                .put("Name", "Joe")
                .put("aGe", 12)
                .put("openId", "DFDFSDFWERWER")
                .build();
        final SubPerson person = BeanKit.fillBeanWithMap(
                map, new SubPerson(), CopyOptions.of().setIgnoreCase(true));
        assertEquals("Joe", person.getName());
        assertEquals(12, person.getAge());
        assertEquals("DFDFSDFWERWER", person.getOpenid());
    }

    @Test
    public void toBeanTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Map<?, ?> map = BeanKit.toBean(person, Map.class);
        assertEquals("测试A11", map.get("name"));
        assertEquals(14, map.get("age"));
        assertEquals("11213232", map.get("openid"));
        // static属性应被忽略
        assertFalse(map.containsKey("SUBNAME"));
    }

    /**
     * 忽略转换错误测试
     */
    @Test
    public void toBeanIgnoreErrorTest() {
        final HashMap<String, Object> map = MapKit.newHashMap();
        map.put("name", "Joe");
        // 错误的类型，此处忽略
        map.put("age", "aaaaaa");

        final Person person = BeanKit.toBean(map, Person.class, CopyOptions.of().setIgnoreError(true));
        assertEquals("Joe", person.getName());
        // 错误的类型，不copy这个字段，使用对象创建的默认值
        assertEquals(0, person.getAge());
    }

    @Test
    public void mapToBeanIgnoreCaseTest() {
        final HashMap<String, Object> map = MapKit.newHashMap();
        map.put("Name", "Joe");
        map.put("aGe", 12);

        final Person person = BeanKit.toBean(map, Person.class, CopyOptions.of().setIgnoreCase(true));
        assertEquals("Joe", person.getName());
        assertEquals(12, person.getAge());
    }

    @Test
    public void mapToBeanTest() {
        final HashMap<String, Object> map = MapKit.newHashMap();
        map.put("a_name", "Joe");
        map.put("b_age", 12);

        // 别名，用于对应bean的字段名
        final HashMap<String, String> mapping = MapKit.newHashMap();
        mapping.put("a_name", "name");
        mapping.put("b_age", "age");

        final Person person = BeanKit.toBean(map, Person.class, CopyOptions.of().setFieldMapping(mapping));
        assertEquals("Joe", person.getName());
        assertEquals(12, person.getAge());
    }

    /**
     * 测试public类型的字段注入是否成功
     */
    @Test
    public void mapToBeanTest2() {
        final HashMap<String, Object> map = MapKit.newHashMap();
        map.put("name", "Joe");
        map.put("age", 12);

        // 非空构造也可以实例化成功
        final Person2 person = BeanKit.toBean(map, Person2.class, CopyOptions.of());
        assertEquals("Joe", person.name);
        assertEquals(12, person.age);
    }

    /**
     * 测试在不忽略错误情况下，转换失败需要报错。
     */
    @Test
    public void mapToBeanWinErrorTest() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            final Map<String, String> map = new HashMap<>();
            map.put("age", "哈哈");
            BeanKit.toBean(map, Person.class);
        });
    }

    @Test
    public void beanToMapTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Map<String, Object> map = BeanKit.beanToMap(person);

        assertEquals("测试A11", map.get("name"));
        assertEquals(14, map.get("age"));
        assertEquals("11213232", map.get("openid"));
        // static属性应被忽略
        assertFalse(map.containsKey("SUBNAME"));
    }

    @Test
    public void beanToMapNullPropertiesTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Map<String, Object> map = BeanKit.beanToMap(person, (String[]) null);

        assertEquals("测试A11", map.get("name"));
        assertEquals(14, map.get("age"));
        assertEquals("11213232", map.get("openid"));
        // static属性应被忽略
        assertFalse(map.containsKey("SUBNAME"));
    }

    @Test
    public void beanToMapTest2() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Map<String, Object> map = BeanKit.beanToMap(person, true, true);
        assertEquals("sub名字", map.get("sub_name"));
    }

    @Test
    public void beanToMapWithValueEditTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Map<String, Object> map = BeanKit.beanToMap(person, new LinkedHashMap<>(),
                CopyOptions.of().setFieldEditor((entry) -> {
                    entry.setValue(entry.getKey() + "_" + entry.getValue());
                    return entry;
                }));
        assertEquals("subName_sub名字", map.get("subName"));
    }

    @Test
    public void beanToMapWithAliasTest() {
        final SubPersonWithAlias person = new SubPersonWithAlias();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");
        person.setSlow(true);
        person.setBooleana(true);
        person.setBooleanb(true);

        final Map<String, Object> map = BeanKit.beanToMap(person);
        assertEquals("sub名字", map.get("aliasSubName"));
    }

    @Test
    public void mapToBeanWithAliasTest() {
        final Map<String, Object> map = MapKit.newHashMap();
        map.put("aliasSubName", "sub名字");
        map.put("slow", true);
        map.put("is_booleana", "1");
        map.put("is_booleanb", true);

        final SubPersonWithAlias subPersonWithAlias = BeanKit.toBean(map, SubPersonWithAlias.class);
        assertEquals("sub名字", subPersonWithAlias.getSubName());

        // is_booleana并不匹配booleana字段
        assertFalse(subPersonWithAlias.isBooleana());
        Assertions.assertNull(subPersonWithAlias.getBooleanb());
    }

    @Test
    public void beanToMapWithLocalDateTimeTest() {
        final LocalDateTime now = LocalDateTime.now();

        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");
        person.setDate(now);
        person.setDate2(now.toLocalDate());

        final Map<String, Object> map = BeanKit.beanToMap(person, false, true);
        assertEquals(now, map.get("date"));
        assertEquals(now.toLocalDate(), map.get("date2"));
    }

    @Test
    public void getPropertyTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final Object name = BeanKit.getProperty(person, "name");
        assertEquals("测试A11", name);
        final Object subName = BeanKit.getProperty(person, "subName");
        assertEquals("sub名字", subName);
    }

    @Test
    public void getNullPropertyTest() {
        final Object property = BeanKit.getProperty(null, "name");
        Assertions.assertNull(property);
    }

    @Test
    public void getPropertyDescriptorsTest() {
        final HashSet<Object> set = SetKit.of();
        final PropertyDescriptor[] propertyDescriptors = BeanKit.getPropertyDescriptors(SubPerson.class);
        for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            set.add(propertyDescriptor.getName());
        }
        assertTrue(set.contains("age"));
        assertTrue(set.contains("id"));
        assertTrue(set.contains("name"));
        assertTrue(set.contains("openid"));
        assertTrue(set.contains("slow"));
        assertTrue(set.contains("subName"));
    }

    @Test
    public void copyPropertiesTest() {
        final SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        final SubPerson person1 = BeanKit.copyProperties(person, SubPerson.class);
        assertEquals(14, person1.getAge());
        assertEquals("11213232", person1.getOpenid());
        assertEquals("测试A11", person1.getName());
        assertEquals("sub名字", person1.getSubName());
    }

    @Test
    @Disabled
    public void multiThreadTest() {
        final Student student = new Student();
        student.setName("张三");
        student.setAge(123);
        student.setNo(3158L);

        final Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(125);
        student2.setNo(8848L);

        final List<Student> studentList = ListKit.view(student, student2);

        for (int i = 0; i < 5000; i++) {
            new Thread(() -> {
                final List<Student> list = ObjectKit.clone(studentList);
                final List<Student> listReps = list.stream().map(s1 -> {
                    final Student s2 = new Student();
                    BeanKit.copyProperties(s1, s2);
                    return s2;
                }).collect(Collectors.toList());

                System.out.println(listReps);
            }).start();
        }

        ThreadKit.waitForDie();
    }

    @Test
    public void copyPropertiesHasBooleanTest() {
        final SubPerson p1 = new SubPerson();
        p1.setSlow(true);

        // 测试boolean参数值isXXX形式
        final SubPerson p2 = new SubPerson();
        BeanKit.copyProperties(p1, p2);
        assertTrue(p2.getSlow());

        // 测试boolean参数值非isXXX形式
        final SubPerson2 p3 = new SubPerson2();
        BeanKit.copyProperties(p1, p3);
        assertTrue(p3.getSlow());
    }

    @Test
    public void copyPropertiesIgnoreNullTest() {
        final SubPerson p1 = new SubPerson();
        p1.setSlow(true);
        p1.setName(null);

        final SubPerson2 p2 = new SubPerson2();
        p2.setName("oldName");

        // null值不覆盖目标属性
        BeanKit.copyProperties(p1, p2, CopyOptions.of().ignoreNullValue());
        assertEquals("oldName", p2.getName());

        // null覆盖目标属性
        BeanKit.copyProperties(p1, p2);
        Assertions.assertNull(p2.getName());
    }

    @Test
    public void copyPropertiesBeanToMapTest() {
        // 测试BeanToMap
        final SubPerson p1 = new SubPerson();
        p1.setSlow(true);
        p1.setName("测试");
        p1.setSubName("sub测试");

        final Map<String, Object> map = MapKit.newHashMap();
        BeanKit.copyProperties(p1, map);
        assertTrue((Boolean) map.get("slow"));
        assertEquals("测试", map.get("name"));
        assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void copyPropertiesMapToMapTest() {
        // 测试MapToMap
        final Map<String, Object> p1 = new HashMap<>();
        p1.put("isSlow", true);
        p1.put("name", "测试");
        p1.put("subName", "sub测试");

        final Map<String, Object> map = MapKit.newHashMap();
        BeanKit.copyProperties(p1, map);
        assertTrue((Boolean) map.get("isSlow"));
        assertEquals("测试", map.get("name"));
        assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void trimBeanStrFieldsTest() {
        final Person person = new Person();
        person.setAge(1);
        person.setName("  张三 ");
        person.setOpenid(null);
        final Person person2 = BeanKit.trimStringField(person);

        // 是否改变原对象
        assertEquals("张三", person.getName());
        assertEquals("张三", person2.getName());
    }

    @Test
    public void beanToBeanOverlayFieldTest() {
        final SubPersonWithOverlayTransientField source = new SubPersonWithOverlayTransientField();
        source.setName("zhangsan");
        source.setAge(20);
        source.setOpenid("1");
        final SubPersonWithOverlayTransientField dest = new SubPersonWithOverlayTransientField();
        BeanKit.copyProperties(source, dest);

        assertEquals(source.getName(), dest.getName());
        assertEquals(source.getAge(), dest.getAge());
        assertEquals(source.getOpenid(), dest.getOpenid());
    }

    @Test
    public void beanToBeanTest() {
        // 修复对象无getter方法导致报错的问题
        final Page page1 = new Page();
        BeanKit.toBean(page1, Page.class);
    }

    @Test
    public void copyBeanToBeanTest() {
        // 测试在copyProperties方法中alias是否有效
        final Food info = new Food();
        info.setBookID("0");
        info.setCode("123");
        final HllFoodEntity entity = new HllFoodEntity();
        BeanKit.copyProperties(info, entity);
        assertEquals(info.getBookID(), entity.getBookId());
        assertEquals(info.getCode(), entity.getCode2());
    }

    @Test
    public void copyBeanTest() {
        final Food info = new Food();
        info.setBookID("0");
        info.setCode("123");
        final Food newFood = BeanKit.copyProperties(info, Food.class, "code");
        assertEquals(info.getBookID(), newFood.getBookID());
        Assertions.assertNull(newFood.getCode());
    }

    @Test
    public void copyNullTest() {
        Assertions.assertNull(BeanKit.copyProperties(null, Food.class));
    }

    @Test
    public void copyPropertiesMapToMapIgnoreNullTest() {
        // 测试MapToMap
        final Map<String, Object> p1 = new HashMap<>();
        p1.put("isSlow", true);
        p1.put("name", "测试");
        p1.put("subName", null);

        final Map<String, Object> map = MapKit.newHashMap();
        BeanKit.copyProperties(p1, map, CopyOptions.of().setIgnoreNullValue(true));
        assertTrue((Boolean) map.get("isSlow"));
        assertEquals("测试", map.get("name"));
        assertFalse(map.containsKey("subName"));
    }

    @Test
    public void copyBeanPropertiesFilterTest() {
        final Food info = new Food();
        info.setBookID("0");
        info.setCode("");
        final Food newFood = new Food();
        final CopyOptions copyOptions = CopyOptions.of().setPropertiesFilter((f, v) -> !(v instanceof CharSequence) || StringKit.isNotBlank(v.toString()));
        BeanKit.copyProperties(info, newFood, copyOptions);
        assertEquals(info.getBookID(), newFood.getBookID());
        Assertions.assertNull(newFood.getCode());
    }

    @Test
    public void copyBeanPropertiesFunctionFilterTest() {
        final Person o = new Person();
        o.setName("asd");
        o.setAge(123);
        o.setOpenid("asd");

        final CopyOptions copyOptions = CopyOptions.of().setIgnoreProperties(Person::getAge, Person::getOpenid);
        final Person n = new Person();
        BeanKit.copyProperties(o, n, copyOptions);

        // 是否忽略拷贝属性
        Assertions.assertNotEquals(o.getAge(), n.getAge());
        Assertions.assertNotEquals(o.getOpenid(), n.getOpenid());
    }

    @Test
    public void setPropertiesTest() {
        final Map<String, Object> resultMap = MapKit.newHashMap();
        BeanKit.setProperty(resultMap, "codeList[0].name", "张三");
        assertEquals("{codeList=[{name=张三}]}", resultMap.toString());
    }

    @Test
    public void beanCopyTest() {
        final Station station = new Station();
        station.setId(123456L);

        final Station station2 = new Station();

        BeanKit.copyProperties(station, station2);
        assertEquals(Long.valueOf(123456L), station2.getId());
    }

    @Test
    public void copyListTest() {
        final Student student = new Student();
        student.setName("张三");
        student.setAge(123);
        student.setNo(3158L);

        final Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(125);
        student2.setNo(8848L);

        final List<Student> studentList = ListKit.view(student, student2);
        final List<Person> people = BeanKit.copyToList(studentList, Person.class);

        assertEquals(studentList.size(), people.size());
        for (int i = 0; i < studentList.size(); i++) {
            assertEquals(studentList.get(i).getName(), people.get(i).getName());
            assertEquals(studentList.get(i).getAge(), people.get(i).getAge());
        }

    }

    @Test
    public void copyListTest2() {
        final Student student = new Student();
        student.setName("张三");
        student.setAge(123);
        student.setNo(3158L);

        final Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(125);
        student2.setNo(8848L);

        final List<Student> studentList = ListKit.view(student, student2);
        final List<Person> people = BeanKit.copyToList(studentList, Person.class, CopyOptions.of().setFieldMapping(MapKit.of("no", "openid")));

        assertEquals(studentList.size(), people.size());
        for (int i = 0; i < studentList.size(); i++) {
            assertEquals(studentList.get(i).getName(), people.get(i).getName());
            assertEquals(studentList.get(i).getAge(), people.get(i).getAge());
            assertEquals(studentList.get(i).getNo().toString(), people.get(i).getOpenid());
        }

    }

    @Test
    public void toMapTest() {
        // 测试转map的时候返回key
        final PrivilegeIClassification a = new PrivilegeIClassification();
        a.setId("1");
        a.setName("2");
        a.setCode("3");
        a.setCreateTime(new Date());
        a.setSortOrder(9L);

        final Map<String, Object> f = BeanKit.beanToMap(
                a,
                new LinkedHashMap<>(),
                false,
                entry -> {
                    if (!Arrays.asList("id", "name", "code", "sortOrder").contains(entry.getKey())) {
                        entry.setKey(null);
                    }
                    return entry;
                });
        assertFalse(f.containsKey(null));
    }

    @Test
    public void getFieldValue() {
        final TestPojo testPojo = new TestPojo();
        testPojo.setName("名字");

        final TestPojo2 testPojo2 = new TestPojo2();
        testPojo2.setAge(2);
        final TestPojo2 testPojo3 = new TestPojo2();
        testPojo3.setAge(3);


        testPojo.setTestPojo2List(new TestPojo2[]{testPojo2, testPojo3});

        final BeanPath beanPath = BeanPath.of("testPojo2List.age");
        final Object o = beanPath.getValue(testPojo);

        assertEquals(Integer.valueOf(2), ArrayKit.get(o, 0));
        assertEquals(Integer.valueOf(3), ArrayKit.get(o, 1));
    }

    @Test
    void hasNullFieldTest() {
        assertTrue(BeanKit.hasNullField(null));


        final TestPojo testPojo = new TestPojo();
        assertTrue(BeanKit.hasNullField(testPojo));

        testPojo.setName("test");
        assertTrue(BeanKit.hasNullField(testPojo));
        // 忽略testPojo2List，则只剩下name属性，非空，返回false
        assertFalse(BeanKit.hasNullField(testPojo, "testPojo2List"));

        testPojo.setTestPojo2List(new TestPojo2[0]);
        // 所有字段都有值
        assertFalse(BeanKit.hasNullField(testPojo));
    }

    @Test
    void hasEmptyFieldTest() {
        assertTrue(BeanKit.hasEmptyField(null));


        final TestPojo testPojo = new TestPojo();
        assertTrue(BeanKit.hasEmptyField(testPojo));

        testPojo.setName("test");
        assertTrue(BeanKit.hasEmptyField(testPojo));
        // 忽略testPojo2List，则只剩下name属性，非空，返回false
        assertFalse(BeanKit.hasEmptyField(testPojo, "testPojo2List"));

        testPojo.setTestPojo2List(new TestPojo2[0]);
        // 所有字段都有值
        assertFalse(BeanKit.hasEmptyField(testPojo));

        // 给空字段值
        testPojo.setName("");
        assertTrue(BeanKit.hasEmptyField(testPojo));
    }

    @Test
    void isEmptyTest() {
        assertTrue(BeanKit.isEmpty(null));

        final TestPojo testPojo = new TestPojo();
        assertTrue(BeanKit.isEmpty(testPojo));

        testPojo.setName("test");
        assertFalse(BeanKit.isEmpty(testPojo));
        // 忽略name属性判断
        assertTrue(BeanKit.isEmpty(testPojo, "name"));

        testPojo.setTestPojo2List(new TestPojo2[0]);
        // 所有字段都有值
        assertFalse(BeanKit.isEmpty(testPojo));
    }

    /**
     * copyProperties(Object source, Object target, CopyOptions copyOptions)
     * 当：copyOptions的 setFieldNameEditor 不为空的时候，有bug,这里进行修复；
     */
    @Test
    public void beanToBeanCopyOptionsTest() {
        final ChildVo1 childVo1 = new ChildVo1();
        childVo1.setChild_address("中国北京五道口");
        childVo1.setChild_name("张三");
        childVo1.setChild_father_name("张无忌");
        childVo1.setChild_mother_name("赵敏敏");

        final CopyOptions copyOptions = CopyOptions.of().
                //setIgnoreNullValue(true).
                //setIgnoreCase(false).
                        setFieldEditor(entry -> {
                    entry.setKey(StringKit.toCamelCase(entry.getKey()));
                    return entry;
                });

        final ChildVo2 childVo2 = new ChildVo2();
        BeanKit.copyProperties(childVo1, childVo2, copyOptions);

        assertEquals(childVo1.getChild_address(), childVo2.getChildAddress());
        assertEquals(childVo1.getChild_name(), childVo2.getChildName());
        assertEquals(childVo1.getChild_father_name(), childVo2.getChildFatherName());
        assertEquals(childVo1.getChild_mother_name(), childVo2.getChildMotherName());
    }

    @Test
    public void issueTest1() {
        final Test1 t1 = new Test1().setStrList(ListKit.of("list"));
        final Test2 t2_hu = new Test2();
        BeanKit.copyProperties(t1, t2_hu, CopyOptions.of().setIgnoreError(true));
        Assertions.assertNull(t2_hu.getStrList());
    }

    @Test
    public void issuesTest() {
        final Map<String, String> map = new HashMap<>();
        map.put("statusIdUpdateTime", "");

        final WkCrmCustomer customer = new WkCrmCustomer();
        BeanKit.copyProperties(map, customer);

        Assertions.assertNull(customer.getStatusIdUpdateTime());
    }

    @Test
    public void valueProviderToBeanTest() {
        final CopyOptions copyOptions = CopyOptions.of();
        final Map<String, String> filedMap = new HashMap<>();
        filedMap.put("name", "sourceId");
        copyOptions.setFieldMapping(filedMap);
        final TestPojo pojo = BeanKit.fillBean(new TestPojo(), new ValueProvider<>() {
            final HashMap<String, Object> map = new HashMap<>();

            {
                map.put("sourceId", "123");
            }

            @Override
            public Object value(final String key, final Type valueType) {
                return map.get(key);
            }

            @Override
            public boolean containsKey(final String key) {
                return map.containsKey(key);
            }
        }, copyOptions);
        assertEquals("123", pojo.getName());
    }

    @Test
    public void hasGetterTest() {
        final boolean b = BeanKit.hasGetter(Object.class);
        assertFalse(b);
    }

    @Getter
    @Setter
    public static class SubPerson extends Person {

        public static final String SUBNAME = "TEST";

        private UUID id;
        private String subName;
        private Boolean slow;
        private LocalDateTime date;
        private LocalDate date2;
    }

    @Getter
    @Setter
    public static class SubPerson2 extends Person {
        private String subName;
        // boolean参数值非isXXX形式
        private Boolean slow;
    }

    @Getter
    @Setter
    @ToString
    public static class SubPersonWithAlias extends Person {
        // boolean参数值非isXXX形式
        @Alias("aliasSubName")
        private String subName;
        private Boolean slow;
        private boolean booleana;
        private Boolean booleanb;
    }

    @Getter
    @Setter
    public static class SubPersonWithOverlayTransientField extends PersonWithTransientField {
        // 覆盖父类中 transient 属性
        private String name;
    }

    @Getter
    @Setter
    public static class Person {
        private String name;
        private int age;
        private String openid;
    }

    @Getter
    @Setter
    public static class PersonWithTransientField {
        private transient String name;
        private int age;
        private String openid;
    }

    public static class Person2 {

        public String name;
        public int age;
        public String openid;

        public Person2(final String name, final int age, final String openid) {
            this.name = name;
            this.age = age;
            this.openid = openid;
        }
    }

    public static class Page {
        private boolean optimizeCountSql = true;

        public boolean optimizeCountSql() {
            return optimizeCountSql;
        }

        public Page setOptimizeCountSql(final boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
            return this;
        }
    }

    @Data
    public static class Food {
        @Alias("bookId")
        private String bookID;
        private String code;
    }

    @Data
    public static class HllFoodEntity implements Serializable {
        private static final long serialVersionUID = 1L;

        private String bookId;
        @Alias("code")
        private String code2;
    }

    static class Station extends Tree<Long> {
    }

    static class Tree<T> extends Entity<T> {
    }

    @Data
    public static class Entity<T> {
        private T id;
    }

    @Data
    public static class PrivilegeIClassification implements Serializable {
        private static final long serialVersionUID = 1L;

        private String id;
        private String name;
        private String code;
        private Long rowStatus;
        private Long sortOrder;
        private Date createTime;
    }

    @Data
    public static class TestPojo {
        private String name;
        private TestPojo2[] testPojo2List;
    }

    @Data
    public static class TestPojo2 {
        private int age;
    }

    @Data
    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        String name;
        int age;
        Long no;
    }

    @Data
    public static class ChildVo1 {
        String child_name;
        String child_address;
        String child_mother_name;
        String child_father_name;
    }

    @Data
    public static class ChildVo2 {
        String childName;
        String childAddress;
        String childMotherName;
        String childFatherName;
    }

    @Data
    @Accessors(chain = true)
    public static class Test1 {
        private List<String> strList;
    }

    @Data
    @Accessors(chain = true)
    public static class Test2 {
        private List<Integer> strList;
    }

    @Data
    public static class WkCrmCustomer {
        private LocalDateTime statusIdUpdateTime;
    }
}

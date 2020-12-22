package org.aoju.bus.core.convert;

import org.aoju.bus.core.lang.Console;
import org.aoju.bus.core.toolkit.BeanKitTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类型转换工具单元测试
 * 转换为数组
 */
public class ConvertToBeanTest {

    @Test
    public void beanToMapTest() {
        BeanKitTest.SubPerson person = new BeanKitTest.SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<?, ?> map = Convert.convert(Map.class, person);
        Assert.assertEquals(map.get("name"), "测试A11");
        Assert.assertEquals(map.get("age"), 14);
        Assert.assertEquals("11213232", map.get("openid"));
    }

    @Test
    public void beanToMapTest2() {
        BeanKitTest.SubPerson person = new BeanKitTest.SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<String, String> map = Convert.toMap(String.class, String.class, person);
        Assert.assertEquals("测试A11", map.get("name"));
        Assert.assertEquals("14", map.get("age"));
        Assert.assertEquals("11213232", map.get("openid"));
    }

    @Test
    public void mapToMapTest() {
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("key1", 1);
        map1.put("key2", 2);
        map1.put("key3", 3);
        map1.put("key4", 4);

        Map<String, String> map2 = Convert.toMap(String.class, String.class, map1);
        Console.log(map2);

        Assert.assertEquals("1", map2.get("key1"));
        Assert.assertEquals("2", map2.get("key2"));
        Assert.assertEquals("3", map2.get("key3"));
        Assert.assertEquals("4", map2.get("key4"));
    }

    @Test
    public void mapToBeanTest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "88dc4b28-91b1-4a1a-bab5-444b795c7ecd");
        map.put("age", 14);
        map.put("openid", "11213232");
        map.put("name", "测试A11");
        map.put("subName", "sub名字");

        BeanKitTest.SubPerson subPerson = Convert.convert(BeanKitTest.SubPerson.class, map);
        Assert.assertEquals("88dc4b28-91b1-4a1a-bab5-444b795c7ecd", subPerson.getId().toString());
        Assert.assertEquals(14, subPerson.getAge());
        Assert.assertEquals("11213232", subPerson.getOpenid());
        Assert.assertEquals("测试A11", subPerson.getName());
        Assert.assertEquals("11213232", subPerson.getOpenid());
    }

    @Test
    public void nullStrToBeanTest() {
        String nullStr = "null";
        final BeanKitTest.SubPerson subPerson = Convert.convert(BeanKitTest.SubPerson.class, nullStr);
        Assert.assertNull(subPerson);
    }

}

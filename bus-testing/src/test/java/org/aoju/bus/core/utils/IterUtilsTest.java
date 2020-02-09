package org.aoju.bus.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@link IterUtils} 单元测试
 */
public class IterUtilsTest {

    @Test
    public void countMapTest() {
        ArrayList<String> list = CollUtils.newArrayList("a", "b", "c", "c", "a", "b", "d");
        Map<String, Integer> countMap = IterUtils.countMap(list);

        Assertions.assertEquals(Integer.valueOf(2), countMap.get("a"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("b"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("c"));
        Assertions.assertEquals(Integer.valueOf(1), countMap.get("d"));
    }

    @Test
    public void fieldValueMapTest() {
        ArrayList<Car> carList = CollUtils.newArrayList(new Car("123", "大众"), new Car("345", "奔驰"), new Car("567", "路虎"));
        Map<String, Car> carNameMap = IterUtils.fieldValueMap(carList, "carNumber");

        Assertions.assertEquals("大众", carNameMap.get("123").getCarName());
        Assertions.assertEquals("奔驰", carNameMap.get("345").getCarName());
        Assertions.assertEquals("路虎", carNameMap.get("567").getCarName());
    }

    @Test
    public void joinTest() {
        ArrayList<String> list = CollUtils.newArrayList("1", "2", "3", "4");
        String join = IterUtils.join(list, ":");
        Assertions.assertEquals("1:2:3:4", join);

        ArrayList<Integer> list1 = CollUtils.newArrayList(1, 2, 3, 4);
        String join1 = IterUtils.join(list1, ":");
        Assertions.assertEquals("1:2:3:4", join1);

        ArrayList<String> list2 = CollUtils.newArrayList("1", "2", "3", "4");
        String join2 = IterUtils.join(list2, ":", "\"", "\"");
        Assertions.assertEquals("\"1\":\"2\":\"3\":\"4\"", join2);
    }

    @Data
    @AllArgsConstructor
    public static class Car {
        private String carNumber;
        private String carName;
    }

}

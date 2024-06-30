package org.miaixz.bus.core.compare;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.List;

public class PropertyCompareTest {

    @Test
    public void sortNullTest() {
        final ArrayList<User> users = ListKit.of(
                new User("1", "d"),
                new User("2", null),
                new User("3", "a")
        );

        // 默认null在末尾
        final List<User> sortedList1 = ListKit.sort(users, new PropertyCompare<>("b"));
        Assertions.assertEquals("a", sortedList1.get(0).getB());
        Assertions.assertEquals("d", sortedList1.get(1).getB());
        Assertions.assertNull(sortedList1.get(2).getB());

        // null在首
        final List<User> sortedList2 = ListKit.sort(users, new PropertyCompare<>("b", false));
        Assertions.assertNull(sortedList2.get(0).getB());
        Assertions.assertEquals("a", sortedList2.get(1).getB());
        Assertions.assertEquals("d", sortedList2.get(2).getB());
    }

    @Test
    public void reversedTest() {
        final ArrayList<User> users = ListKit.of(
                new User("1", "d"),
                new User("2", null),
                new User("3", "a")
        );

        // 反序
        final List<User> sortedList = ListKit.sort(users, new PropertyCompare<>("b").reversed());
        Assertions.assertNull(sortedList.get(0).getB());
        Assertions.assertEquals("d", sortedList.get(1).getB());
        Assertions.assertEquals("a", sortedList.get(2).getB());
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String a;
        private String b;
    }
}

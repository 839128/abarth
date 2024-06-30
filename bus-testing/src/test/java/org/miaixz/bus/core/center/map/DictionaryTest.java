package org.miaixz.bus.core.center.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.builder.GenericBuilder;
import org.miaixz.bus.core.center.date.DateTime;

import java.util.HashMap;
import java.util.Map;

public class DictionaryTest {

    @Test
    public void dictTest() {
        final Dictionary dict = Dictionary.of()
                .set("key1", 1)//int
                .set("key2", 1000L)//long
                .set("key3", DateTime.now());//Date

        final Long v2 = dict.getLong("key2");
        Assertions.assertEquals(Long.valueOf(1000L), v2);
    }

    @Test
    public void dictTest2() {
        final Dictionary dict = new Dictionary(true);
        final Map<String, Object> map = new HashMap<>();
        map.put("A", 1);

        dict.putAll(map);

        Assertions.assertEquals(1, dict.get("A"));
        Assertions.assertEquals(1, dict.get("a"));
    }

    @Test
    public void ofTest() {
        final Dictionary dict = Dictionary.ofKvs(
                "RED", "#FF0000",
                "GREEN", "#00FF00",
                "BLUE", "#0000FF"
        );

        Assertions.assertEquals("#FF0000", dict.get("RED"));
        Assertions.assertEquals("#00FF00", dict.get("GREEN"));
        Assertions.assertEquals("#0000FF", dict.get("BLUE"));
    }

    @Test
    public void removeEqualTest() {
        final Dictionary dict = Dictionary.ofKvs(
                "key1", null
        );

        final Dictionary dict2 = Dictionary.ofKvs(
                "key1", null
        );

        dict.removeEqual(dict2);

        Assertions.assertTrue(dict.isEmpty());
    }

    @Test
    public void setFieldsTest() {
        final User user = GenericBuilder.of(User::new).with(User::setUsername, "bus").build();
        final Dictionary dict = Dictionary.of();
        dict.setFields(user::getNickname, user::getUsername);
        Assertions.assertEquals("bus", dict.get("username"));
        Assertions.assertNull(dict.get("nickname"));

        // get by lambda
        Assertions.assertEquals("bus", dict.get(User::getUsername));
    }

    @Test
    public void removeNewTest() {
        final Dictionary dict = Dictionary.of()
                .set("key1", 1)//int
                .set("key2", 1000L)//long
                .set("key3", DateTime.now());//Date

        final Dictionary dict2 = dict.removeNew("key1");
        Assertions.assertEquals(3, dict.size());
        Assertions.assertEquals(2, dict2.size());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String username;
        private String nickname;
    }

}

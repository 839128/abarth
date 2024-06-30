package org.miaixz.bus.setting.metric.toml;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ArrayKit;
import org.miaixz.bus.core.xyz.ResourceKit;
import org.miaixz.bus.setting.metric.props.Props;

import java.util.Map;

public class TomlTest {

    @Test
    public void readTest() {
        final Map<String, Object> read = Toml.read(ResourceKit.getResource("/test/setting/test.toml"));
        Assertions.assertEquals(5, read.size());
    }


    /**
     * 数组字段追加后生成新的数组，造成赋值丢失
     * 修复见：BeanKit.setFieldValue
     */
    @Test
    public void toBeanTest() {
        final Props props = Props.get("/test/setting/issue");
        final MyUser user = props.toBean(MyUser.class, "person");
        Assertions.assertEquals("[LOL, KFC, COFFE]", ArrayKit.toString(user.getHobby()));
    }

    @Data
    static class MyUser {
        private String[] hobby;
    }

}

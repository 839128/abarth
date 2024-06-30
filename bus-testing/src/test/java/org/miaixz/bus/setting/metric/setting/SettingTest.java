package org.miaixz.bus.setting.metric.setting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SettingTest {

    @Test
    public void getTest() {
        final String driver = Setting.get("/test/setting/test").getStrByGroup("driver", "demo");
        Assertions.assertEquals("com.mysql.jdbc.Driver", driver);
    }

    @Test
    public void getTest2() {
        final String driver = Setting.get("/test/setting/example/example").getStrByGroup("key", "demo");
        Assertions.assertEquals("value", driver);
    }

    @Test
    public void getFirstFoundTest() {
        final String driver = Setting.getFirstFound("test2", "/test/setting/test")
                .getStrByGroup("driver", "demo");
        Assertions.assertEquals("com.mysql.jdbc.Driver", driver);
    }

}

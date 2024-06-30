package org.miaixz.bus.setting.metric.yaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.FileKit;

import java.util.List;

public class YamlTest {

    @Test
    public void loadByPathTest() {
        final Dictionary result = Yaml.load("/test/setting/test.yaml");

        Assertions.assertEquals("John", result.getString("firstName"));

        final List<Integer> numbers = result.getByPath("contactDetails.number");
        Assertions.assertEquals(123456789, (int) numbers.get(0));
        Assertions.assertEquals(456786868, (int) numbers.get(1));
    }

    @Test
    @Disabled
    public void dumpTest() {
        final Dictionary dict = Dictionary.of()
                .set("name", "bus")
                .set("count", 1000);

        Yaml.dump(
                dict
                , FileKit.getWriter("/test/setting/dump.yaml", Charset.UTF_8, false));
    }

}

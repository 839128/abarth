package org.miaixz.bus.extra.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.extra.template.provider.beetl.BeetlProvider;

public class BeetlTest {

    @Test
    public void beetlProviderTest() {
        // 字符串模板
        TemplateProvider engine = new BeetlProvider(new TemplateConfig("/test/extra/template/templates"));
        final Template template = engine.getTemplate("hello,${name}");
        final String result = template.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result);

        // classpath中获取模板
        engine = new BeetlProvider(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        final Template template2 = engine.getTemplate("/test/extra/template/beetl_test.btl");
        final String result2 = template2.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result2);
    }

}

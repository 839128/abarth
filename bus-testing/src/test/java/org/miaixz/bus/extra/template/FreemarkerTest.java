package org.miaixz.bus.extra.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.extra.template.provider.freemarker.FreemarkerProvider;

public class FreemarkerTest {

    @Test
    public void freemarkerProviderTest() {
        // 字符串模板
        TemplateProvider engine = TemplateFactory.create(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.STRING).setProvider(FreemarkerProvider.class));
        Template template = engine.getTemplate("hello,${name}");
        String result = template.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result);

        //ClassPath模板
        engine = TemplateFactory.create(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH).setProvider(FreemarkerProvider.class));
        template = engine.getTemplate("/test/extra/template/freemarker_test.ftl");
        result = template.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result);
    }

}

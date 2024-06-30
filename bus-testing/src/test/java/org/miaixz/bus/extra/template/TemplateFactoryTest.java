package org.miaixz.bus.extra.template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.extra.template.provider.thymeleaf.ThymeleafProvider;

/**
 * 模板引擎单元测试
 */
public class TemplateFactoryTest {

    @Test
    public void createTest() {
        // 字符串模板, 默认模板引擎，此处为Beetl
        TemplateProvider engine = TemplateFactory.create(new TemplateConfig());
        final Template template = engine.getTemplate("hello,${name}");
        final String result = template.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result);

        // classpath中获取模板
        engine = TemplateFactory.create(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        final Template template2 = engine.getTemplate("/test/extra/template/beetl_test.btl");
        final String result2 = template2.render(Dictionary.of().set("name", "bus"));
        Assertions.assertEquals("hello,bus", result2);
    }

    @Test
    public void thymeleafProviderTest() {
        // 字符串模板
        TemplateProvider engine = TemplateFactory.create(
                new TemplateConfig("templates").setProvider(ThymeleafProvider.class));
        Template template = engine.getTemplate("<h3 th:text=\"${message}\"></h3>");
        String result = template.render(Dictionary.of().set("message", "bus"));
        Assertions.assertEquals("<h3>bus</h3>", result);

        //ClassPath模板
        engine = TemplateFactory.create(
                new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH).setProvider(ThymeleafProvider.class));
        template = engine.getTemplate("/test/extra/template/thymeleaf_test.ttl");
        result = template.render(Dictionary.of().set("message", "bus"));
        Assertions.assertEquals("<h3>bus</h3>", result);
    }

}

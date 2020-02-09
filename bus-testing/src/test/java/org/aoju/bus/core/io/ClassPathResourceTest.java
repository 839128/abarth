package org.aoju.bus.core.io;

import org.aoju.bus.core.io.resource.ClassPathResource;
import org.aoju.bus.core.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassPath资源读取测试
 */
public class ClassPathResourceTest {

    @Test
    public void readStringTest() {
        ClassPathResource resource = new ClassPathResource("test.properties");
        String content = resource.readUtf8Str();
        Assertions.assertTrue(StringUtils.isNotEmpty(content));
    }

    @Test
    public void readStringTest2() {
        ClassPathResource resource = new ClassPathResource("/");
        String content = resource.readUtf8Str();
        Assertions.assertTrue(StringUtils.isNotEmpty(content));
    }

    @Test
    public void readTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("test.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());

        Assertions.assertEquals("1", properties.get("a"));
        Assertions.assertEquals("2", properties.get("b"));
    }

    @Test
    public void readFromJarTest() {
        final ClassPathResource resource = new ClassPathResource("test.properties");

        String result = resource.readUtf8Str();
        Assertions.assertNotNull(result);

        //二次读取测试，用于测试关闭流对再次读取的影响
        result = resource.readUtf8Str();
        Assertions.assertNotNull(result);
    }

    @Test
    public void getAbsTest() {
        final ClassPathResource resource = new ClassPathResource("test.properties");
        String absPath = resource.getAbsolutePath();
        Assertions.assertTrue(absPath.contains("test.properties"));
    }

}

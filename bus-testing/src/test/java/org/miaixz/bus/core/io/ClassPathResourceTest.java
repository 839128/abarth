package org.miaixz.bus.core.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.miaixz.bus.core.io.resource.ClassPathResource;
import org.miaixz.bus.core.xyz.StringKit;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassPath资源读取测试
 */
public class ClassPathResourceTest {

    @Test
    public void readStringTest() {
        final ClassPathResource resource = new ClassPathResource("test.properties");
        final String content = resource.readString();
        Assertions.assertTrue(StringKit.isNotEmpty(content));
    }

    @Test()
    @EnabledForJreRange(max = JRE.JAVA_8)
    public void readStringTest2() {
        // JDK9+中因为模块的加入，根路径读取可能为空
        // 读取classpath根目录测试
        final ClassPathResource resource = new ClassPathResource("/");
        final String content = resource.readString();
        Assertions.assertTrue(StringKit.isNotEmpty(content));
    }

    @Test()
    @EnabledForJreRange(min = JRE.JAVA_9)
    public void readStringTestForJdk9() {
        // JDK9+中因为模块的加入，根路径读取可能为空
        // 读取classpath根目录测试
        final ClassPathResource resource = new ClassPathResource("/");
        final String content = resource.readString();
        Assertions.assertNotNull(content);
    }

    @Test
    public void readTest() throws IOException {
        final ClassPathResource resource = new ClassPathResource("test.properties");
        final Properties properties = new Properties();
        properties.load(resource.getStream());

        Assertions.assertEquals("1", properties.get("a"));
        Assertions.assertEquals("2", properties.get("b"));
    }

    @Test
    public void readFromJarTest() {
        //测试读取junit的jar包下的LICENSE-junit.txt文件
        final ClassPathResource resource = new ClassPathResource("META-INF/LICENSE.md");

        String result = resource.readString();
        Assertions.assertNotNull(result);

        //二次读取测试，用于测试关闭流对再次读取的影响
        result = resource.readString();
        Assertions.assertNotNull(result);
    }

    @Test
    public void getAbsTest() {
        final ClassPathResource resource = new ClassPathResource("META-INF/LICENSE.md");
        final String absPath = resource.getAbsolutePath();
        Assertions.assertTrue(absPath.contains("LICENSE"));
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.io.resource.ClassPathResource;
import org.miaixz.bus.core.io.resource.FileResource;
import org.miaixz.bus.core.io.resource.Resource;
import org.miaixz.bus.core.io.resource.StringResource;

public class ResourceKitTest {

    @Test
    public void readXmlTest() {
        final String str = ResourceKit.readString("test.xml");
        Assertions.assertNotNull(str);

        final Resource resource = new ClassPathResource("test.xml");
        final String xmlStr = resource.readString();

        Assertions.assertEquals(str, xmlStr);
    }

    @Test
    public void stringResourceTest() {
        final StringResource stringResource = new StringResource("testData", "test");
        Assertions.assertEquals("test", stringResource.getName());
        Assertions.assertArrayEquals("testData".getBytes(), stringResource.readBytes());
        Assertions.assertArrayEquals("testData".getBytes(), IoKit.readBytes(stringResource.getStream()));
    }

    @Test
    public void fileResourceTest() {
        final FileResource resource = new FileResource(FileKit.file("test.xml"));
        Assertions.assertEquals("test.xml", resource.getName());
        Assertions.assertTrue(StringKit.isNotEmpty(resource.readString()));
    }

}

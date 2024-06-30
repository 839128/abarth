package org.miaixz.bus.core.io.resource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ResourceKit;

public class ResourceFinderTest {

    @Test
    @Disabled
    void findAllTest() {
        final MultiResource resources = ResourceFinder.of()
                .find("**/*");

        Console.log("===== result =====");
        for (final Resource resource : resources) {
            Console.log(resource);
        }
    }

    @Test
    @Disabled
    void getResourcesTest() {
        final MultiResource resources = ResourceKit.getResources("");
        for (final Resource resource : resources) {
            Console.log(resource);
        }
    }

}

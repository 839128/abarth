package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.jar.Manifest;

public class ManifestKitTest {

    @Test
    public void getManiFestTest() {
        final Manifest manifest = ManifestKit.getManifest(Test.class);
        Assertions.assertNotNull(manifest);
    }

}

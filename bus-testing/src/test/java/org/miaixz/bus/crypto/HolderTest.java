package org.miaixz.bus.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.Provider;

/**
 * 单元测试，只针对bouncycastle引入有效
 */
public class HolderTest {

    @Test
    void getProviderTest() {
        final Provider provider = Holder.getProvider();
        Assertions.assertNotNull(provider);
        Assertions.assertEquals(
                "org.bouncycastle.jce.provider.BouncyCastleProvider",
                provider.getClass().getName());
    }

}

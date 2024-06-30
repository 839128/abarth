package org.miaixz.bus.core.lang.loader.spi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.xyz.SPIKit;

public class ListServiceLoaderTest {

    @Test
    void loadFirstAvailableTest() {
        final ListServiceLoader<TestSPI1> serviceLoader = ListServiceLoader.of(TestSPI1.class);
        final TestSPI1 testSPI1 = SPIKit.loadFirstAvailable(serviceLoader);
        Assertions.assertNotNull(testSPI1);
        Assertions.assertEquals("test service 1", testSPI1.doSth());
    }

    @Test
    void getServiceClassTest() {
        final ListServiceLoader<TestSPI1> serviceLoader = ListServiceLoader.of(TestSPI1.class);
        final Class<TestSPI1> service1 = serviceLoader.getServiceClass(1);
        Assertions.assertEquals(TestService1.class, service1);
    }

    @Test
    void getServiceClassNotExistTest() {
        final ListServiceLoader<TestSPI1> serviceLoader = ListServiceLoader.of(TestSPI1.class);
        Assertions.assertThrows(InternalException.class, () -> {
            serviceLoader.getServiceClass(0);
        });
    }

    public interface TestSPI1 {
        String doSth();
    }

    public static class TestService1 implements TestSPI1 {
        @Override
        public String doSth() {
            return "test service 1";
        }
    }

}

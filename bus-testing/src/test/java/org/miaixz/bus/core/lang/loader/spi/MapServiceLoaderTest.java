package org.miaixz.bus.core.lang.loader.spi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.xyz.SPIKit;

public class MapServiceLoaderTest {

    @Test
    void loadFirstAvailableTest() {
        final MapServiceLoader<TestSPI1> serviceLoader = MapServiceLoader.of(TestSPI1.class);
        final TestSPI1 testSPI1 = SPIKit.loadFirstAvailable(serviceLoader);
        Assertions.assertNotNull(testSPI1);
        Assertions.assertEquals("test service 1", testSPI1.doSth());
    }

    @Test
    void getServiceClassTest() {
        final MapServiceLoader<TestSPI1> serviceLoader = MapServiceLoader.of(TestSPI1.class);
        final Class<TestSPI1> service1 = serviceLoader.getServiceClass("service1");
        Assertions.assertEquals(TestService1.class, service1);
    }

    @Test
    void getServiceClassNotExistTest() {
        final MapServiceLoader<TestSPI1> serviceLoader = MapServiceLoader.of(TestSPI1.class);
        Assertions.assertThrows(InternalException.class, () -> {
            serviceLoader.getServiceClass("service2");
        });
    }

    @Test
    void getServiceClassEmptyTest() {
        final MapServiceLoader<TestSPI1> serviceLoader = MapServiceLoader.of(TestSPI1.class);
        final Class<TestSPI1> serviceEmpty = serviceLoader.getServiceClass("serviceEmpty");
        Assertions.assertNull(serviceEmpty);
    }

    @Test
    void getServiceNotDefineTest() {
        final MapServiceLoader<TestSPI1> serviceLoader = MapServiceLoader.of(TestSPI1.class);
        final Class<TestSPI1> service1 = serviceLoader.getServiceClass("serviceNotDefine");
        Assertions.assertNull(service1);
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

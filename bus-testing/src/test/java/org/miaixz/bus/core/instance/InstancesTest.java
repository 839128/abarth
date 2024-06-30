package org.miaixz.bus.core.instance;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InstancesTest {

    @Test
    public void getTest() {
        // 此测试中使用1000个线程获取单例对象，其间对象只被创建一次
        ThreadKit.concurrencyTest(1000, () -> Instances.get(TestBean.class));
    }

    /**
     * 测试单例构建属性锁死问题
     * C构建单例时候，同时构建B，此时在SimpleCache中会有写锁竞争（写入C时获取了写锁，此时要写入B，也要获取写锁）
     */
    @Test
    public void reentrantTest() {
        Assertions.assertTimeout(Duration.ofMillis(1000L), () -> {
            final C c = Instances.get(C.class);
            Assertions.assertEquals("aaa", c.getB().getA());
        });
    }

    @Test
    @Disabled
    void issueTest() {
        final String key = "123";
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> {
                Instances.get(key, () -> {
                    System.out.println(key);
                    return "123";
                });
            });
        }

        ThreadKit.sleep(5000);
    }

    @Data
    static class TestBean {
        private static volatile TestBean testSingleton;
        private String name;
        private String age;

        public TestBean() {
            if (null != testSingleton) {
                throw new InternalException("单例测试中，对象被创建了两次！");
            }
            testSingleton = this;
        }
    }

    @Data
    static class B {
        private String a = "aaa";
    }

    @Data
    static class C {
        private B b = Instances.get(B.class);
    }

}

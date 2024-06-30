package org.miaixz.bus.core.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.cache.provider.FIFOCache;
import org.miaixz.bus.core.cache.provider.LRUCache;
import org.miaixz.bus.core.cache.provider.WeakCache;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.thread.ConcurrencyTester;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存单元测试
 */
public class CacheConcurrentTest {

    @Test
    @Disabled
    public void fifoCacheTest() {
        final int threadCount = 4000;
        final Cache<String, String> cache = new FIFOCache<>(3);

        // 由于缓存容量只有3，当加入第四个元素的时候，根据FIFO规则，最先放入的对象将被移除

        for (int i = 0; i < threadCount; i++) {
            ThreadKit.execute(() -> {
                cache.put("key1", "value1", System.currentTimeMillis() * 3);
                cache.put("key2", "value2", System.currentTimeMillis() * 3);
                cache.put("key3", "value3", System.currentTimeMillis() * 3);
                cache.put("key4", "value4", System.currentTimeMillis() * 3);
                ThreadKit.sleep(1000);
                cache.put("key5", "value5", System.currentTimeMillis() * 3);
                cache.put("key6", "value6", System.currentTimeMillis() * 3);
                cache.put("key7", "value7", System.currentTimeMillis() * 3);
                cache.put("key8", "value8", System.currentTimeMillis() * 3);
                Console.log("put all");
            });
        }

        for (int i = 0; i < threadCount; i++) {
            ThreadKit.execute(() -> show(cache));
        }

        System.out.println("==============================");
        ThreadKit.sleep(10000);
    }

    @Test
    @Disabled
    public void lruCacheTest() {
        final int threadCount = 40000;
        final Cache<String, String> cache = new LRUCache<>(1000);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            ThreadKit.execute(() -> {
                cache.put("key1" + index, "value1");
                cache.put("key2" + index, "value2", System.currentTimeMillis() * 3);

                int size = cache.size();
                int capacity = cache.capacity();
                if (size > capacity) {
                    Console.log("{} {}", size, capacity);
                }
                ThreadKit.sleep(1000);
                size = cache.size();
                capacity = cache.capacity();
                if (size > capacity) {
                    Console.log("## {} {}", size, capacity);
                }
            });
        }

        ThreadKit.sleep(5000);
    }

    private void show(final Cache<String, String> cache) {

        for (final Object tt : cache) {
            Console.log(tt);
        }
    }

    @Test
    public void effectiveTest() {
        // 模拟耗时操作消耗时间
        final int delay = 2000;
        final AtomicInteger ai = new AtomicInteger(0);
        final WeakCache<Integer, Integer> weakCache = new WeakCache<>(60 * 1000);
        final ConcurrencyTester concurrencyTester = ThreadKit.concurrencyTest(32, () -> {
            final int i = ai.incrementAndGet() % 4;
            weakCache.get(i, () -> {
                ThreadKit.sleep(delay);
                return i;
            });
        });
        final long interval = concurrencyTester.getInterval();
        // 总耗时应与单次操作耗时在同一个数量级
        Assertions.assertTrue(interval < delay * 2);
    }

}

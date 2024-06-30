package org.miaixz.bus.core.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.cache.provider.LRUCache;
import org.miaixz.bus.core.xyz.CacheKit;
import org.miaixz.bus.core.xyz.RandomKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LRUCache存在并发问题，多线程get后，map结构变更，导致null的位置不确定，
 * 并可能引起死锁。
 */
public class LRUCacheTest {

    @Test
    @Disabled
    public void putTest() {
        final LRUCache<String, String> cache = CacheKit.newLRUCache(100, 10);
        for (int i = 0; i < 10000; i++) {
            ThreadKit.execute(() -> cache.get(RandomKit.randomStringLower(5), () -> RandomKit.randomStringLower(10)));
        }
        ThreadKit.sleep(3000);
    }

    @Test
    public void readWriteTest() throws InterruptedException {
        final LRUCache<Integer, Integer> cache = CacheKit.newLRUCache(10);
        for (int i = 0; i < 10; i++) {
            cache.put(i, i);
        }

        final CountDownLatch countDownLatch = new CountDownLatch(10);
        // 10个线程分别读0-9 10000次
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    cache.get(finalI);
                }
                countDownLatch.countDown();
            }).start();
        }
        // 等待读线程结束
        countDownLatch.await();
        // 按顺序读0-9
        final StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb1.append(cache.get(i));
        }
        Assertions.assertEquals("0123456789", sb1.toString());

        // 新加11，此时0最久未使用，应该淘汰0
        cache.put(11, 11);

        final StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb2.append(cache.get(i));
        }
        Assertions.assertEquals("null123456789", sb2.toString());
    }

    @Test
    public void issueTest() {
        final AtomicInteger removeCount = new AtomicInteger();

        final LRUCache<String, Integer> cache = CacheKit.newLRUCache(3, 1);
        cache.setListener((key, value) -> {
            // 共移除7次
            removeCount.incrementAndGet();
        });

        for (int i = 0; i < 10; i++) {
            cache.put(StringKit.format("key-{}", i), i);
        }

        Assertions.assertEquals(7, removeCount.get());
        Assertions.assertEquals(3, cache.size());
    }

}

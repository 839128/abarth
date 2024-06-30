package org.miaixz.bus.core.data.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.ConcurrentHashSet;
import org.miaixz.bus.core.center.date.StopWatch;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * {@link ID} 单元测试
 */
public class IDTest {

    @Test
    public void randomUUIDTest() {
        final String simpleUUID = ID.simpleUUID();
        Assertions.assertEquals(32, simpleUUID.length());

        final String randomUUID = ID.randomUUID();
        Assertions.assertEquals(36, randomUUID.length());
    }

    @Test
    public void fastUUIDTest() {
        final String simpleUUID = ID.fastSimpleUUID();
        Assertions.assertEquals(32, simpleUUID.length());

        final String randomUUID = ID.fastUUID();
        Assertions.assertEquals(36, randomUUID.length());
    }

    /**
     * UUID的性能测试
     */
    @Test
    @Disabled
    public void benchTest() {
        final StopWatch timer = DateKit.createStopWatch();
        timer.start();
        for (int i = 0; i < 1000000; i++) {
            ID.simpleUUID();
        }
        timer.stop();
        Console.log(timer.getLastTaskTimeMillis());

        timer.start();
        for (int i = 0; i < 1000000; i++) {
            //noinspection ResultOfMethodCallIgnored
            UUID.randomUUID().toString().replace("-", "");
        }
        timer.stop();
        Console.log(timer.getLastTaskTimeMillis());
    }

    @Test
    public void objectIdTest() {
        final String id = ID.objectId();
        Assertions.assertEquals(24, id.length());
    }

    @Test
    public void getSnowflakeTest() {
        final Snowflake snowflake = ID.getSnowflake(1, 1);
        final long id = snowflake.next();
        Assertions.assertTrue(id > 0);
    }

    @Test
    @Disabled
    public void snowflakeBenchTest() {
        final Set<Long> set = new ConcurrentHashSet<>();
        final Snowflake snowflake = ID.getSnowflake(1, 1);

        //线程数
        final int threadCount = 100;
        //每个线程生成的ID数
        final int idCountPerThread = 10000;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadKit.execute(() -> {
                for (int i1 = 0; i1 < idCountPerThread; i1++) {
                    final long id = snowflake.next();
                    set.add(id);
//						Console.log("Add new id: {}", id);
                }
                latch.countDown();
            });
        }

        //等待全部线程结束
        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new InternalException(e);
        }
        Assertions.assertEquals(threadCount * idCountPerThread, set.size());
    }

    @Test
    @Disabled
    public void snowflakeBenchTest2() {
        final Set<Long> set = new ConcurrentHashSet<>();

        //线程数
        final int threadCount = 100;
        //每个线程生成的ID数
        final int idCountPerThread = 10000;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            ThreadKit.execute(() -> {
                for (int i1 = 0; i1 < idCountPerThread; i1++) {
                    final long id = ID.getSnowflake(1, 1).next();
                    set.add(id);
//						Console.log("Add new id: {}", id);
                }
                latch.countDown();
            });
        }

        //等待全部线程结束
        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new InternalException(e);
        }
        Assertions.assertEquals(threadCount * idCountPerThread, set.size());
    }

    @Test
    public void getDataCenterIdTest() {
        //按照mac地址算法拼接的算法，maxDatacenterId应该是0xffffffffL>>6-1此处暂时按照0x7fffffffffffffffL-1，防止最后取模溢出
        final long dataCenterId = ID.getDataCenterId(Long.MAX_VALUE);
        Assertions.assertTrue(dataCenterId >= 0);
    }

}

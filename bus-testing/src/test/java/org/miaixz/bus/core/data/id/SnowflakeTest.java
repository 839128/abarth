package org.miaixz.bus.core.data.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.ConcurrentHashSet;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.core.xyz.RandomKit;
import org.miaixz.bus.core.xyz.StringKit;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.HashSet;
import java.util.Set;

/**
 * Snowflake单元测试
 */
public class SnowflakeTest {

    @Test
    public void snowflakeTest1() {
        //构建Snowflake，提供终端ID和数据中心ID
        final Snowflake idWorker = new Snowflake(0, 0);
        final long nextId = idWorker.next();
        Assertions.assertTrue(nextId > 0);
    }

    @Test
    public void snowflakeTest() {
        final HashSet<Long> hashSet = new HashSet<>();

        //构建Snowflake，提供终端ID和数据中心ID
        final Snowflake idWorker = new Snowflake(0, 0);
        for (int i = 0; i < 1000; i++) {
            final long id = idWorker.next();
            hashSet.add(id);
        }
        Assertions.assertEquals(1000L, hashSet.size());
    }

    @Test
    public void snowflakeGetTest() {
        //构建Snowflake，提供终端ID和数据中心ID
        final Snowflake idWorker = new Snowflake(1, 2);
        final long nextId = idWorker.next();

        Assertions.assertEquals(1, idWorker.getWorkerId(nextId));
        Assertions.assertEquals(2, idWorker.getDataCenterId(nextId));
        Assertions.assertTrue(idWorker.getGenerateDateTime(nextId) - System.currentTimeMillis() < 10);
    }

    @Test
    @Disabled
    public void uniqueTest() {
        // 测试并发环境下生成ID是否重复
        final Snowflake snowflake = ID.getSnowflake(0, 0);

        final Set<Long> ids = new ConcurrentHashSet<>();
        ThreadKit.concurrencyTest(100, () -> {
            for (int i = 0; i < 50000; i++) {
                if (!ids.add(snowflake.next())) {
                    throw new InternalException("重复ID！");
                }
            }
        });
    }

    @Test
    @Disabled
    public void uniqueTest2() {
        // 测试并发环境下生成ID是否重复
        final Snowflake snowflake = ID.getSnowflake();

        final Set<Long> ids = new ConcurrentHashSet<>();
        ThreadKit.concurrencyTest(100, () -> {
            for (int i = 0; i < 50000; i++) {
                if (!ids.add(snowflake.next())) {
                    throw new InternalException("重复ID！");
                }
            }
        });
    }

    @Test
    public void getSnowflakeLengthTest() {
        for (int i = 0; i < 1000; i++) {
            final long l = ID.getSnowflake(0, 0).next();
            Assertions.assertEquals(19, StringKit.toString(l).length());
        }
    }

    @Test
    @Disabled
    public void snowflakeRandomSequenceTest() {
        final Snowflake snowflake = new Snowflake(null, 0, 0,
                false, 2);
        for (int i = 0; i < 1000; i++) {
            final long id = snowflake.next();
            Console.log(id);
            ThreadKit.sleep(10);
        }
    }

    @Test
    @Disabled
    public void uniqueOfRandomSequenceTest() {
        // 测试并发环境下生成ID是否重复
        final Snowflake snowflake = new Snowflake(null, 0, 0,
                false, 100);

        final Set<Long> ids = new ConcurrentHashSet<>();
        ThreadKit.concurrencyTest(100, () -> {
            for (int i = 0; i < 50000; i++) {
                if (!ids.add(snowflake.next())) {
                    throw new InternalException("重复ID！");
                }
            }
        });
    }

    /**
     * 测试-根据传入时间戳-计算ID起终点
     */
    @Test
    public void snowflakeTestGetIdScope() {
        final long workerId = RandomKit.randomLong(31);
        final long dataCenterId = RandomKit.randomLong(31);
        final Snowflake idWorker = new Snowflake(workerId, dataCenterId);
        final long generatedId = idWorker.next();
        // 随机忽略数据中心和工作机器的占位
        final boolean ignore = RandomKit.randomBoolean();
        final long createTimestamp = idWorker.getGenerateDateTime(generatedId);
        final Pair<Long, Long> idScope = idWorker.getIdScopeByTimestamp(createTimestamp, createTimestamp, ignore);
        final long startId = idScope.getLeft();
        final long endId = idScope.getRight();

        // 起点终点相差比较
        final long trueOffSet = endId - startId;
        // 忽略数据中心和工作机器时差值为22个1，否则为12个1
        final long expectedOffSet = ignore ? ~(-1 << 22) : ~(-1 << 12);
        Assertions.assertEquals(trueOffSet, expectedOffSet);
    }

}

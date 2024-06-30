package org.miaixz.bus.core.center.function;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.StopWatch;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.RandomKit;

import java.util.ArrayList;

public class FunctionPoolTest {

    @Test
    public void createStringTest() {
        // 预热
        FunctionPool.createString("123".toCharArray());

        // 测试数据
        final ArrayList<char[]> list = ListKit.of();
        for (int i = 0; i < 100000; i++) {
            list.add(RandomKit.randomStringLower(100).toCharArray());
        }

        final StopWatch stopWatch = DateKit.createStopWatch();
        stopWatch.start("copy creator");
        for (final char[] value : list) {
            new String(value);
        }
        stopWatch.stop();

        stopWatch.start("zero copy creator");
        for (final char[] value : list) {
            FunctionPool.createString(value);
        }
        stopWatch.stop();

        //Console.log(stopWatch.prettyPrint());
    }

}

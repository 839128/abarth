package org.miaixz.bus.cron;

import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.concurrent.TimeUnit;

/**
 * 测试定时任务，当触发到定时的时间点时，执行doTest方法
 */
public class TestJob2 {

    /**
     * 执行定时任务内容
     */
    public void doTest() {
        Console.log("TestJob2.doTest开始执行…… at [{}]", DateKit.formatNow());
        ThreadKit.sleep(20, TimeUnit.SECONDS);
        Console.log("延迟20s打印testJob2");
    }

}

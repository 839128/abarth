package org.miaixz.bus.core.lang.thread;

import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.RuntimeKit;
import org.miaixz.bus.core.xyz.ThreadKit;

/**
 * 简单定时任务测试
 */
public class SimpleSchedulerTest {

    public static void main(final String[] args) {

        // 新建一个定时任务，定时获取内存信息
        final SimpleScheduler<Long> scheduler = new SimpleScheduler<>(new SimpleScheduler.Job<>() {
            private volatile long maxAvailable;

            @Override
            public Long getResult() {
                return this.maxAvailable;
            }

            @Override
            public void run() {
                this.maxAvailable = RuntimeKit.getFreeMemory();
            }
        }, 50);

        // 另一个线程不停获取内存结果计算值
        ThreadKit.execAsync(() -> {
            while (true) {
                Console.log(FileKit.readableFileSize(scheduler.getResult()));
                ThreadKit.sleep(1000);
            }
        });
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadKitTest {

    @Test
    public void executeTest() {
        final boolean isValid = true;

        ThreadKit.execute(() -> Assertions.assertTrue(isValid));
    }

    @Test
    @Disabled
    public void phaserTest() {
        final LocalDateTime now = DateKit.parseTime("2022-08-04T22:59:59+08:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        Assertions.assertNotNull(now);

        final int repeat = 30; // 执行的轮数配置
        final Phaser phaser = new Phaser() {  // 进行一些处理方法的覆写
            //返回ture: 移相器终止，false: 移相器继续执行
            @Override
            protected boolean onAdvance(final int phase, final int registeredParties) { // 回调处理
                System.out.printf("【onAdvance()处理】进阶处理操作，phase = %s、registeredParties = %s%n",
                        phase, registeredParties);
                return phase + 1 >= repeat || registeredParties == 0; // 终止处理
            }
        };
        for (int x = 0; x < 2; x++) {   // 循环创建2个线程
            phaser.register(); // 注册参与者的线程
            new Thread(() -> { // 每一个线程都在持续的执行之中
                while (!phaser.isTerminated()) { // 现在没有终止Phaser执行
                    try {
                        TimeUnit.SECONDS.sleep(RandomKit.randomInt(1, 10)); // 增加操作延迟,模拟各个线程执行时间不多。阿超、阿珍准备爱果的时间不同
                    } catch (final InterruptedException e) {
                        throw new InternalException(e);
                    }
                    phaser.arriveAndAwaitAdvance(); // 等待其他的线程就位； 准备就绪，并等待其他线程就绪; 阿超、阿珍准备好了爱果，相互等待见面共度美好的一天
                    final String date = DateKit.format(now.plusYears(phaser.getPhase() - 1)); // 增加一年
                    System.out.printf("【%s】%s 阿超和阿珍共度了一个美好的七夕。%n", date, Thread.currentThread().getName());
                    ThreadKit.sleep(3, TimeUnit.SECONDS);
                }
            }, "子线程 - " + (x == 0 ? "阿超" : "阿珍")).start();
        }

        ThreadKit.waitForDie();
    }

    @Test
    public void cyclicBarrierTest() {
        //示例：7个同学，集齐7个龙珠，7个同学一起召唤神龙；前后集齐了2次
        final AtomicInteger times = new AtomicInteger();
        final CyclicBarrier barrier = new CyclicBarrier(7, () -> {
            System.out.println("    ");
            System.out.println("    ");
            System.out.println("【循环栅栏业务处理】7个子线程 都收集了一颗龙珠，七颗龙珠已经收集齐全，开始召唤神龙。" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            times.getAndIncrement();
        }); // 现在设置的栅栏的数量为2
        for (int x = 0; x < 7; x++) {   // 创建7个线程, 当然也可以使用线程池替换。
            new Thread(() -> {
                while (times.get() < 2) {
                    try {
                        System.out.printf("【Barrier - 收集龙珠】当前的线程名称：%s%n", Thread.currentThread().getName());
                        final int time = ThreadLocalRandom.current().nextInt(1, 10); // 等待一段时间，模拟线程的执行时间
                        TimeUnit.SECONDS.sleep(time); // 模拟业务延迟，收集龙珠的时间
                        barrier.await(); // 等待，凑够了7个等待的线程
                        System.err.printf("〖Barrier - 举起龙珠召唤神龙〗当前的线程名称：%s\t%s%n", Thread.currentThread().getName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        if (barrier.getParties() >= 7) {
                            barrier.reset(); // 重置栅栏，等待下一次的召唤。
                        }
                    } catch (final Exception e) {
                        throw new InternalException(e);
                    }
                }
            }, "线程 - " + x).start();
        }
    }

    @Test
    void safeSleepTest() {
        final long sleepMillis = RandomKit.randomLong(1, 1000);
        // 随机sleep时长，确保sleep时间足够
        final long l = System.currentTimeMillis();
        ThreadKit.safeSleep(sleepMillis);
        Assertions.assertTrue(System.currentTimeMillis() - l >= sleepMillis);
    }

}

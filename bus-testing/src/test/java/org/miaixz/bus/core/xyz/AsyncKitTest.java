package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 工具类测试
 */
public class AsyncKitTest {

    @Test
    @Disabled
    public void waitAndGetTest() {
        final CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(1, TimeUnit.SECONDS);
            return "bus";
        });
        final CompletableFuture<String> sweater = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(2, TimeUnit.SECONDS);
            return "卫衣";
        });
        final CompletableFuture<String> warm = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(3, TimeUnit.SECONDS);
            return "真暖和";
        });
        // 等待完成
        AsyncKit.waitAll(bus, sweater, warm);
        // 获取结果
        Assertions.assertEquals("卫衣真暖和", AsyncKit.get(bus) + AsyncKit.get(sweater) + AsyncKit.get(warm));
    }

    @Test
    @Disabled
    public void allGetTest() {
        final CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(1, TimeUnit.SECONDS);
            return "bus";
        });
        final CompletableFuture<String> sweater = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(2, TimeUnit.SECONDS);
            return "卫衣";
        });
        final CompletableFuture<String> warm = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(3, TimeUnit.SECONDS);
            return "真暖和";
        });
        // 等待完成
        final List<String> list = AsyncKit.allOfGet(ListKit.of(bus, sweater, warm));
        // 获取结果
        Assertions.assertEquals(Arrays.asList("bus", "卫衣", "真暖和"), list);
    }

    @Test
    @Disabled
    public void allGetTestException() {
        final CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(1, TimeUnit.SECONDS);
            return "bus";
        });
        final CompletableFuture<String> sweater = CompletableFuture.supplyAsync(() -> {
            final int a = 1 / 0;
            ThreadKit.sleep(2, TimeUnit.SECONDS);
            return "卫衣";
        });
        final CompletableFuture<String> warm = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(3, TimeUnit.SECONDS);
            return "真暖和";
        });
        // 等待完成
        final List<String> list = AsyncKit.allOfGet(ListKit.of(bus, sweater, warm), (e) -> "出错了");
        // 获取结果
        Assertions.assertEquals(Arrays.asList("bus", "卫衣", "真暖和"), list);
    }

    @Test
    @Disabled
    public void parallelAllOfGetTest() {
        final CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(1, TimeUnit.SECONDS);
            return "bus";
        });
        final CompletableFuture<String> sweater = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(2, TimeUnit.SECONDS);
            return "卫衣";
        });
        final CompletableFuture<String> warm = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(3, TimeUnit.SECONDS);
            return "真暖和";
        });
        // 等待完成
        final List<String> list = AsyncKit.parallelAllOfGet(ListKit.of(bus, sweater, warm));
        // 获取结果
        Assertions.assertEquals(Arrays.asList("bus", "卫衣", "真暖和"), list);
    }

    @Test
    @Disabled
    public void parallelAllOfGetTestException() {
        final CompletableFuture<String> bus = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(1, TimeUnit.SECONDS);
            return "bus";
        });
        final CompletableFuture<String> sweater = CompletableFuture.supplyAsync(() -> {
            final int a = 1 / 0;
            ThreadKit.sleep(2, TimeUnit.SECONDS);
            return "卫衣";
        });
        final CompletableFuture<String> warm = CompletableFuture.supplyAsync(() -> {
            ThreadKit.sleep(3, TimeUnit.SECONDS);
            return "真暖和";
        });
        // 等待完成
        final List<String> list = AsyncKit.parallelAllOfGet(ListKit.of(bus, sweater, warm), (e) -> "出错了");
        Assertions.assertEquals(Arrays.asList("bus", "出错了", "真暖和"), list);
    }

}

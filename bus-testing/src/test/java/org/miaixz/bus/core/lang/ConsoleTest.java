package org.miaixz.bus.core.lang;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ThreadKit;

/**
 * 控制台单元测试
 */
public class ConsoleTest {

    @Test
    public void logTest() {
        Console.log();

        final String[] a = {"abc", "bcd", "def"};
        Console.log(a);

        Console.log("This is Console log for {}.", "test");
    }

    @Test
    public void logTest2() {
        Console.log("a", "b", "c");
        Console.log((Object) "a", "b", "c");
    }

    @Test
    public void printTest() {
        final String[] a = {"abc", "bcd", "def"};
        Console.print(a);

        Console.log("This is Console print for {}.", "test");
    }

    @Test
    public void printTest2() {
        Console.print("a", "b", "c");
        Console.print((Object) "a", "b", "c");
    }

    @Test
    public void errorTest() {
        Console.error();

        final String[] a = {"abc", "bcd", "def"};
        Console.error(a);

        Console.error("This is Console error for {}.", "test");
    }

    @Test
    public void errorTest2() {
        Console.error("a", "b", "c");
        Console.error((Object) "a", "b", "c");
    }

    @Test
    @Disabled
    public void inputTest() {
        Console.log("Please input something: ");
        final String input = Console.input();
        Console.log(input);
    }

    @Test
    @Disabled
    public void printProgressTest() {
        for (int i = 0; i < 100; i++) {
            Console.printProgress('#', 100, i / 100D);
            ThreadKit.sleep(200);
        }
    }

    @Test
    public void printColorTest() {
        System.out.print("\33[30;1m A \u001b[31;2m B \u001b[32;1m C \u001b[33;1m D \u001b[0m");
    }

    @Test
    @Disabled
    public void printSBCTest() {
        Console.Table t = Console.Table.of();
        t.addHeader("姓名", "年龄");
        t.addBody("张三", "15");
        t.addBody("李四", "29");
        t.addBody("王二麻子", "37");
        t.print();

        Console.log();

        t = Console.Table.of();
        t.addHeader("体温", "占比");
        t.addHeader("℃", "%");
        t.addBody("36.8", "10");
        t.addBody("37", "5");
        t.print();

        Console.log();

        t = Console.Table.of();
        t.addHeader("标题1", "标题2");
        t.addBody("12345", "混合321654asdfcSDF");
        t.addBody("sd   e3ee  ff22", "ff值");
        t.print();
    }

    @Test
    @Disabled
    public void printDBCTest() {
        Console.Table t = Console.Table.of().setSBCMode(false);
        t.addHeader("姓名", "年龄");
        t.addBody("张三", "15");
        t.addBody("李四", "29");
        t.addBody("王二麻子", "37");
        t.print();

        Console.log();

        t = Console.Table.of().setSBCMode(false);
        t.addHeader("体温", "占比");
        t.addHeader("℃", "%");
        t.addBody("36.8", "10");
        t.addBody("37", "5");
        t.print();
    }

}

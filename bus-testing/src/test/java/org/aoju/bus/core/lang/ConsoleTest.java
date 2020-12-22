package org.aoju.bus.core.lang;

import org.aoju.bus.core.toolkit.ThreadKit;
import org.junit.Test;

/**
 * 控制台单元测试
 */
public class ConsoleTest {

    @Test
    public void logTest() {
        Console.log();

        String[] a = {"abc", "bcd", "def"};
        Console.log(a);

        Console.log("This is Console log for {}.", "test");
    }

    @Test
    public void printTest() {
        String[] a = {"abc", "bcd", "def"};
        Console.print(a);

        Console.log("This is Console print for {}.", "test");
    }

    @Test
    public void errorTest() {
        Console.error();

        String[] a = {"abc", "bcd", "def"};
        Console.error(a);

        Console.error("This is Console error for {}.", "test");
    }

    @Test
    public void printProgressTest() {
        for (int i = 0; i < 100; i++) {
            Console.printProgress('#', 100, i / 100D);
            ThreadKit.sleep(200);
        }
    }

}

package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;

/**
 * 命令行单元测试
 */
public class RuntimeKitTest {

    @Test
    @Disabled
    public void execTest() {
        final String str = RuntimeKit.execForString("ipconfig");
        Console.log(str);
    }

    @Test
    @Disabled
    public void execCmdTest() {
        final String str = RuntimeKit.execForString("cmd /c dir");
        Console.log(str);
    }

    @Test
    @Disabled
    public void execCmdTest2() {
        final String str = RuntimeKit.execForString("cmd /c", "cd \"C:\\Program Files (x86)\"", "chdir");
        Console.log(str);
    }

    @Test
    public void getUsableMemoryTest() {
        Assertions.assertTrue(RuntimeKit.getUsableMemory() > 0);
    }

    @Test
    public void getPidTest() {
        final int pid = RuntimeKit.getPid();
        Assertions.assertTrue(pid > 0);
    }

    @Test
    public void getProcessorCountTest() {
        final int cpu = RuntimeKit.getProcessorCount();
        Assertions.assertTrue(cpu > 0);
    }

    @Test
    @Disabled
    void pingTest() {
        final String s = RuntimeKit.execForString("cmd /c ping " + "www.miaixz.org");
        Console.log(s);
    }

}

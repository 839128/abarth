package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Console;
import org.junit.jupiter.api.Test;

/**
 * 命令行单元测试
 */
public class RuntimeKitTest {

    @Test
    public void execTest() {
        String str = RuntimeKit.execForStr("ipconfig");
        Console.log(str);
    }

    @Test
    public void execCmdTest() {
        String str = RuntimeKit.execForStr("cmd /c dir");
        Console.log(str);
    }

}

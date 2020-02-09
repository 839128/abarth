package org.aoju.bus.core.utils;

import org.aoju.bus.core.lang.Console;
import org.junit.jupiter.api.Test;

/**
 * 命令行单元测试
 */
public class RuntimeUtilTest {

    @Test
    public void execTest() {
        String str = RuntimeUtils.execForStr("ipconfig");
        Console.log(str);
    }

    @Test
    public void execCmdTest() {
        String str = RuntimeUtils.execForStr("cmd /c dir");
        Console.log(str);
    }

}

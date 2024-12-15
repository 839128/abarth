package org.miaixz.bus.example.entity;

import org.miaixz.bus.core.basic.normal.ErrorCode;

public class AbarthErrorCode extends ErrorCode {

    public static final String EM_500100 = "500100";

    static {
        register(EM_500100, "错误提示信息");
    }

}

package org.aoju.bus.core.lang;

import org.aoju.bus.core.text.Simhash;
import org.aoju.bus.core.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimhashTest {

    @Test
    public void simTest() {
        String text1 = "我是 一个 普通 字符串";
        String text2 = "我是 一个 普通 字符串";

        Simhash simhash = new Simhash();
        long hash = simhash.hash(StringUtils.split(text1, ' '));
        Assertions.assertTrue(hash != 0);

        simhash.store(hash);
        boolean duplicate = simhash.equals(StringUtils.split(text2, ' '));
        Assertions.assertTrue(duplicate);
    }

}

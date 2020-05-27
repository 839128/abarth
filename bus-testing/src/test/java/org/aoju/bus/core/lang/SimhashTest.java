package org.aoju.bus.core.lang;

import org.aoju.bus.core.text.Similarity;
import org.aoju.bus.core.toolkit.StringKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimhashTest {

    @Test
    public void simTest() {
        String text1 = "我是 一个 普通 字符串";
        String text2 = "我是 一个 普通 字符串";

        Similarity simhash = new Similarity();
        long hash = simhash.hash(StringKit.split(text1, ' '));
        Assertions.assertTrue(hash != 0);

        simhash.store(hash);
        boolean duplicate = simhash.equals(StringKit.split(text2, ' '));
        Assertions.assertTrue(duplicate);
    }

}

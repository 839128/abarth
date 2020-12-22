package org.aoju.bus.core.lang;

import org.aoju.bus.core.text.Similarity;
import org.aoju.bus.core.toolkit.StringKit;
import org.junit.Assert;
import org.junit.Test;

public class SimhashTest {

    @Test
    public void simTest() {
        String text1 = "我是 一个 普通 字符串";
        String text2 = "我是 一个 普通 字符串";

        Similarity simhash = new Similarity();
        long hash = simhash.hash(StringKit.split(text1, ' '));
        Assert.assertTrue(hash != 0);

        simhash.store(hash);
        boolean duplicate = simhash.equals(StringKit.split(text2, ' '));
        Assert.assertTrue(duplicate);
    }

}

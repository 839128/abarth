package org.miaixz.bus.core.codec.hash;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Symbol;
import org.miaixz.bus.core.text.CharsBacker;

public class SimhashTest {

    @Test
    public void simTest() {
        final String text1 = "我是 一个 普通 字符串";
        final String text2 = "我是 一个 普通 字符串";

        final Simhash simhash = new Simhash();
        final long hash = simhash.hash64(CharsBacker.split(text1, Symbol.SPACE));
        Assertions.assertTrue(hash != 0);

        simhash.store(hash);
        final boolean duplicate = simhash.equals(CharsBacker.split(text2, Symbol.SPACE));
        Assertions.assertTrue(duplicate);
    }

}

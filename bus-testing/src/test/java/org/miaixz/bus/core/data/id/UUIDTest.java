package org.miaixz.bus.core.data.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.ConcurrentHashSet;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.Set;

public class UUIDTest {

    /**
     * 测试UUID是否存在重复问题
     */
    @Test
    public void fastUUIDTest() {
        final Set<String> set = new ConcurrentHashSet<>(100);
        ThreadKit.concurrencyTest(100, () -> set.add(UUID.fastUUID().toString()));
        Assertions.assertEquals(100, set.size());
        //Console.log(set);
    }

}

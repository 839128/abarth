package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.intern.Intern;

public class InternKitTest {

    /**
     * 检查规范字符串是否相同
     */
    @Test
    public void weakTest() {
        final Intern<String> intern = InternKit.ofWeak();
        final String a1 = RandomKit.randomStringLower(RandomKit.randomInt(100));
        final String a2 = new String(a1);

        Assertions.assertNotSame(a1, a2);

        Assertions.assertSame(intern.intern(a1), intern.intern(a2));
    }

}

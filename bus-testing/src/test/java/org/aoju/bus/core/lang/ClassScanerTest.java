package org.aoju.bus.core.lang;

import org.junit.jupiter.api.Test;

import java.util.Set;

public class ClassScanerTest {

    @Test
    public void scanTest() {
        Scaner scaner = new Scaner("org.aoju.bus.utils", null);
        Set<Class<?>> set = scaner.scan();
        for (Class<?> clazz : set) {
            Console.log(clazz.getName());
        }
    }

}

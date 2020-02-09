package org.aoju.bus.core.utils;

import org.aoju.bus.core.clone.Support;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ObjectUtilsTest {

    @Test
    public void cloneTest() {
        Obj obj = new Obj();
        Obj obj2 = ObjectUtils.clone(obj);
        Assertions.assertEquals("OK", obj2.doSomeThing());
    }

    static class Obj extends Support<Obj> {
        public String doSomeThing() {
            return "OK";
        }
    }

    @Test
    public void toStringTest() {
        ArrayList<String> strings = CollUtils.newArrayList("1", "2");
        String result = ObjectUtils.toString(strings);
        Assertions.assertEquals("[1, 2]", result);
    }

}

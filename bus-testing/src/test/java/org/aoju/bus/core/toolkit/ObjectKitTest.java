package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.clone.Support;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ObjectKitTest {

    @Test
    public void cloneTest() {
        Obj obj = new Obj();
        Obj obj2 = ObjectKit.clone(obj);
        Assert.assertEquals("OK", obj2.doSomeThing());
    }

    @Test
    public void toStringTest() {
        ArrayList<String> strings = CollKit.newArrayList("1", "2");
        String result = ObjectKit.toString(strings);
        Assert.assertEquals("[1, 2]", result);
    }

    static class Obj extends Support<Obj> {
        public String doSomeThing() {
            return "OK";
        }
    }

}

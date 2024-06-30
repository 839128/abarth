package org.miaixz.bus.core.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.BeanKit;

public class BeanWithReturnThisTest {

    @Test
    public void setValueTest() {
        final BeanWithRetuenThis bean = new BeanWithRetuenThis();
        final StrictBeanDesc beanDesc = BeanKit.getBeanDesc(BeanWithRetuenThis.class);
        final PropDesc prop = beanDesc.getProp("a");
        prop.setValue(bean, "123");

        Assertions.assertEquals("123", bean.getA());
    }

    static class BeanWithRetuenThis {
        private String a;

        public String getA() {
            return a;
        }

        public BeanWithRetuenThis setA(final String a) {
            this.a = a;
            return this;
        }
    }

}

package org.miaixz.bus.core.xyz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.culture.en.Week;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.mutable.MutableObject;
import org.miaixz.bus.core.lang.ref.ReferenceType;
import org.miaixz.bus.core.lang.reflect.ReflectTestBeans;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class ReferenceKitTest {

    @Test
    public void noneStaticInnerClassTest() {
        final ReflectTestBeans.NoneStaticClass testAClass = ReflectKit.newInstanceIfPossible(ReflectTestBeans.NoneStaticClass.class);
        Assertions.assertNotNull(testAClass);
        Assertions.assertEquals(2, testAClass.getA());
    }

    @Test
    public void newInstanceIfPossibleTest() {
        //noinspection ConstantConditions
        final int intValue = ReflectKit.newInstanceIfPossible(int.class);
        Assertions.assertEquals(0, intValue);

        final Integer integer = ReflectKit.newInstanceIfPossible(Integer.class);
        Assertions.assertEquals(Integer.valueOf(0), integer);

        final Map<?, ?> map = ReflectKit.newInstanceIfPossible(Map.class);
        Assertions.assertNotNull(map);

        final Collection<?> collection = ReflectKit.newInstanceIfPossible(Collection.class);
        Assertions.assertNotNull(collection);

        final Week week = ReflectKit.newInstanceIfPossible(Week.class);
        Assertions.assertEquals(Week.SUNDAY, week);

        final int[] intArray = ReflectKit.newInstanceIfPossible(int[].class);
        Assertions.assertArrayEquals(new int[0], intArray);
    }

    @Test
    void newInstanceTest() {
        final TestBean testBean = ReflectKit.newInstance(TestBean.class);
        Assertions.assertNull(testBean.getA());
        Assertions.assertEquals(0, testBean.getB());
    }

    @Test
    void newInstanceAllArgsTest() {
        final TestBean testBean = ReflectKit.newInstance(TestBean.class, "aValue", 1);
        Assertions.assertEquals("aValue", testBean.getA());
        Assertions.assertEquals(1, testBean.getB());
    }

    @Test
    void newInstanceHashtableTest() {
        final Hashtable<?, ?> testBean = ReflectKit.newInstance(Hashtable.class);
        Assertions.assertNotNull(testBean);
    }

    @Test
    public void createWeakTest() {
        final Reference<Integer> integerReference = ReferKit.of(ReferenceType.WEAK, 1);
        Assertions.assertSame(WeakReference.class, integerReference);
        Assertions.assertEquals(Integer.valueOf(1), integerReference.get());
    }

    @Test
    public void createSoftTest() {
        final Reference<Integer> integerReference = ReferKit.of(ReferenceType.SOFT, 1);
        Assertions.assertSame(SoftReference.class, integerReference);
        Assertions.assertEquals(Integer.valueOf(1), integerReference.get());
    }

    @Test
    public void createPhantomTest() {
        final Reference<Integer> integerReference = ReferKit.of(ReferenceType.PHANTOM, 1);
        Assertions.assertSame(PhantomReference.class, integerReference);
        // get方法永远都返回null，PhantomReference只能用来监控对象的GC状况
        Assertions.assertNull(integerReference.get());
    }

    @Test
    @Disabled
    public void gcTest() {
        // https://blog.csdn.net/zmx729618/article/details/54093532
        // 弱引用的对象必须使用可变对象，不能使用常量对象（比如String）
        final WeakReference<MutableObject<String>> reference = new WeakReference<>(new MutableObject<>("abc"));
        int i = 0;
        while (true) {
            if (reference.get() != null) {
                i++;
                Console.log("Object is alive for {} loops - ", i);
                System.gc();
            } else {
                Console.log("Object has been collected.");
                break;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TestBean {
        private String a;
        private int b;
    }

}

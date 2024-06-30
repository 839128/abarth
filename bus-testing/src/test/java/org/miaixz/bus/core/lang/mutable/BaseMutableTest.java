package org.miaixz.bus.core.lang.mutable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Normal;

import java.util.Objects;

/**
 * base test for {@link Mutable} implementations.
 */
abstract class BaseMutableTest<V, M extends Mutable<V>> {

    /**
     * 创建一个值，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    abstract V getValue1();

    /**
     * 创建一个值，与{@link #getValue1()}不同，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    abstract V getValue2();

    /**
     * 创建一个{@link Mutable}
     *
     * @param value 值
     * @return 值
     */
    abstract M getMutable(V value);

    @Test
    void testOf() {
        Assertions.assertSame(MutableBool.class, Mutable.of(true));
        Assertions.assertSame(MutableByte.class, Mutable.of((byte) 1));
        Assertions.assertSame(MutableDouble.class, Mutable.of(1.0D));
        Assertions.assertSame(MutableFloat.class, Mutable.of(1.0F));
        Assertions.assertSame(MutableInt.class, Mutable.of(1));
        Assertions.assertSame(MutableLong.class, Mutable.of(1L));
        Assertions.assertSame(MutableObject.class, Mutable.of(new Object()));
        Assertions.assertSame(MutableShort.class, Mutable.of((short) 1));
    }

    @Test
    void testGet() {
        final Mutable<V> mutableObj = getMutable(getValue1());
        final V value = mutableObj.get();
        Assertions.assertEquals(getValue1(), value);
    }

    @Test
    void testSet() {
        final Mutable<V> mutableObj = getMutable(getValue2());
        mutableObj.set(getValue2());
        final V value = mutableObj.get();
        Assertions.assertEquals(getValue2(), value);
    }

    @Test
    void testTo() {
        final Mutable<V> mutableObj = getMutable(getValue1());
        final String value = mutableObj.to(String::valueOf);
        Assertions.assertEquals(String.valueOf(getValue1()), value);
    }

    @Test
    void testToOpt() {
        final Mutable<V> mutableObj = getMutable(getValue1());
        final V value = mutableObj.toOpt().get();
        Assertions.assertEquals(getValue1(), value);
    }

    @Test
    void testMap() {
        final Mutable<V> mutableObj = getMutable(getValue1());
        final V value = mutableObj.map(v -> getValue2()).get();
        Assertions.assertEquals(getValue2(), value);
    }

    @Test
    void testTest() {
        final Mutable<V> mutableObj = getMutable(getValue1());
        Assertions.assertTrue(mutableObj.test(Objects::nonNull));
    }

    @Test
    void testPeek() {
        final Mutable<V> m1 = getMutable(getValue1());
        final Mutable<V> m2 = getMutable(getValue2());
        m1.peek(m2::set);
        Assertions.assertEquals(getValue1(), m2.get());
    }

    @Test
    void testHashCode() {
        final V value = getValue1();
        final Mutable<V> mutableObj = new MutableObject<>(value);
        Assertions.assertEquals(value.hashCode(), mutableObj.hashCode());
        mutableObj.set(null);
        Assertions.assertEquals(0, mutableObj.hashCode());
    }

    @Test
    void testEquals() {
        final V value = getValue1();
        final Mutable<V> mutableObj = new MutableObject<>(value);
        Assertions.assertNotEquals(value, mutableObj);
        Assertions.assertEquals(mutableObj, new MutableObject<>(value));
        Assertions.assertNotEquals(mutableObj, new MutableObject<>(null));
        Assertions.assertNotEquals(mutableObj, null);
        Assertions.assertNotEquals(mutableObj, value);
    }

    @Test
    void testToString() {
        final V value = getValue1();
        final Mutable<V> mutableObj = new MutableObject<>(value);
        Assertions.assertEquals(value.toString(), mutableObj.toString());
        mutableObj.set(null);
        Assertions.assertEquals(Normal.NULL, mutableObj.toString());
    }

}

package org.miaixz.bus.core.xyz;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * test for {@link ObjectKit}
 */
public class ObjectKitTest {

    @Test
    public void equalsTest() {
        Object a = null;
        Object b = null;
        Assertions.assertTrue(ObjectKit.equals(a, b));

        a = new BigDecimal("1.1");
        b = new BigDecimal("1.10");
        Assertions.assertTrue(ObjectKit.equals(a, b));

        a = 127;
        b = 127;
        Assertions.assertTrue(ObjectKit.equals(a, b));

        a = 128;
        b = 128;
        Assertions.assertTrue(ObjectKit.equals(a, b));

        a = LocalDateTime.of(2022, 5, 29, 22, 11);
        b = LocalDateTime.of(2022, 5, 29, 22, 11);
        Assertions.assertTrue(ObjectKit.equals(a, b));

        a = 1;
        b = 1.0;
        Assertions.assertFalse(ObjectKit.equals(a, b));
    }

    @Test
    public void lengthTest() {
        final int[] array = new int[]{1, 2, 3, 4, 5};
        int length = ObjectKit.length(array);
        Assertions.assertEquals(5, length);

        final Map<String, String> map = new HashMap<>();
        map.put("a", "a1");
        map.put("b", "b1");
        map.put("c", "c1");
        length = ObjectKit.length(map);
        Assertions.assertEquals(3, length);

        final Iterable<Integer> list = ListKit.of(1, 2, 3);
        Assertions.assertEquals(3, ObjectKit.length(list));
        Assertions.assertEquals(3, ObjectKit.length(Arrays.asList(1, 2, 3).iterator()));
    }

    @Test
    public void containsTest() {
        Assertions.assertTrue(ObjectKit.contains(new int[]{1, 2, 3, 4, 5}, 1));
        Assertions.assertFalse(ObjectKit.contains(null, 1));
        Assertions.assertTrue(ObjectKit.contains("123", "3"));
        final Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        Assertions.assertTrue(ObjectKit.contains(map, 1));
        Assertions.assertTrue(ObjectKit.contains(Arrays.asList(1, 2, 3).iterator(), 2));
    }

    @Test
    public void isNullTest() {
        Assertions.assertTrue(ObjectKit.isNull(null));
    }

    @Test
    public void isNotNullTest() {
        Assertions.assertFalse(ObjectKit.isNotNull(null));
    }

    @Test
    public void isEmptyTest() {
        Assertions.assertTrue(ObjectKit.isEmpty(null));
        Assertions.assertTrue(ObjectKit.isEmpty(new int[0]));
        Assertions.assertTrue(ObjectKit.isEmpty(""));
        Assertions.assertTrue(ObjectKit.isEmpty(Collections.emptyList()));
        Assertions.assertTrue(ObjectKit.isEmpty(Collections.emptyMap()));
        Assertions.assertTrue(ObjectKit.isEmpty(Collections.emptyIterator()));
    }

    @Test
    public void isNotEmptyTest() {
        Assertions.assertFalse(ObjectKit.isNotEmpty(null));
        Assertions.assertFalse(ObjectKit.isNotEmpty(new int[0]));
        Assertions.assertFalse(ObjectKit.isNotEmpty(""));
        Assertions.assertFalse(ObjectKit.isNotEmpty(Collections.emptyList()));
        Assertions.assertFalse(ObjectKit.isNotEmpty(Collections.emptyMap()));
        Assertions.assertFalse(ObjectKit.isNotEmpty(Collections.emptyIterator()));
    }


    @Test
    public void defaultIfNullTest() {
        final Object val1 = new Object();
        final Object val2 = new Object();

        Assertions.assertSame(val1, ObjectKit.defaultIfNull(val1, () -> val2));
        Assertions.assertSame(val2, ObjectKit.defaultIfNull(null, () -> val2));

        Assertions.assertSame(val1, ObjectKit.defaultIfNull(val1, val2));
        Assertions.assertSame(val2, ObjectKit.defaultIfNull(null, val2));

        Assertions.assertSame(val1, ObjectKit.defaultIfNull(val1, Function.identity(), () -> val2));
        Assertions.assertSame(val2, ObjectKit.defaultIfNull(null, Function.identity(), () -> val2));

        Assertions.assertSame(val1, ObjectKit.defaultIfNull(val1, Function.identity(), val2));
        Assertions.assertSame(val2, ObjectKit.defaultIfNull(null, Function.identity(), val2));

        final SerializableBean obj = new SerializableBean(null);
        final SerializableBean objNull = null;
        final String result3 = ObjectKit.defaultIfNull(obj, Object::toString, "fail");
        Assertions.assertNotNull(result3);

        final String result4 = ObjectKit.defaultIfNull(objNull, Object::toString, () -> "fail");
        Assertions.assertNotNull(result4);
    }

    @Test
    void cloneListTest() {
        final ArrayList<Integer> list = ListKit.of(1, 2);
        final ArrayList<Integer> clone = ObjectKit.clone(list);
        Assertions.assertEquals(list, clone);
    }

    @Test
    public void cloneTest() {
        Assertions.assertNull(ObjectKit.clone(null));

        final CloneableBean cloneableBean1 = new CloneableBean(1);
        final CloneableBean cloneableBean2 = ObjectKit.clone(cloneableBean1);
        Assertions.assertEquals(cloneableBean1, cloneableBean2);

        final SerializableBean serializableBean1 = new SerializableBean(2);
        final SerializableBean serializableBean2 = ObjectKit.clone(serializableBean1);
        Assertions.assertEquals(serializableBean1, serializableBean2);

        final Bean bean1 = new Bean(3);
        Assertions.assertNull(ObjectKit.clone(bean1));
    }

    @Test
    public void cloneIfPossibleTest() {
        Assertions.assertNull(ObjectKit.clone(null));

        final CloneableBean cloneableBean1 = new CloneableBean(1);
        Assertions.assertEquals(cloneableBean1, ObjectKit.cloneIfPossible(cloneableBean1));

        final SerializableBean serializableBean1 = new SerializableBean(2);
        Assertions.assertEquals(serializableBean1, ObjectKit.cloneIfPossible(serializableBean1));

        final Bean bean1 = new Bean(3);
        Assertions.assertSame(bean1, ObjectKit.cloneIfPossible(bean1));

        final ExceptionCloneableBean exceptionBean1 = new ExceptionCloneableBean(3);
        Assertions.assertSame(exceptionBean1, ObjectKit.cloneIfPossible(exceptionBean1));
    }

    @Test
    public void cloneByStreamTest() {
        Assertions.assertNull(ObjectKit.cloneByStream(null));
        Assertions.assertNull(ObjectKit.cloneByStream(new CloneableBean(1)));
        final SerializableBean serializableBean1 = new SerializableBean(2);
        Assertions.assertEquals(serializableBean1, ObjectKit.cloneByStream(serializableBean1));
        Assertions.assertNull(ObjectKit.cloneByStream(new Bean(1)));
    }

    @Test
    public void isBasicTypeTest() {
        final int a = 1;
        final boolean basicType = ObjectKit.isBasicType(a);
        Assertions.assertTrue(basicType);
    }

    @Test
    public void isValidIfNumberTest() {
        Assertions.assertTrue(ObjectKit.isValidIfNumber(null));
        Assertions.assertFalse(ObjectKit.isValidIfNumber(Double.NEGATIVE_INFINITY));
        Assertions.assertFalse(ObjectKit.isValidIfNumber(Double.NaN));
        Assertions.assertTrue(ObjectKit.isValidIfNumber(Double.MIN_VALUE));
        Assertions.assertFalse(ObjectKit.isValidIfNumber(Float.NEGATIVE_INFINITY));
        Assertions.assertFalse(ObjectKit.isValidIfNumber(Float.NaN));
        Assertions.assertTrue(ObjectKit.isValidIfNumber(Float.MIN_VALUE));
    }

    @Test
    public void getTypeArgumentTest() {
        final Bean bean = new Bean(1);
        Assertions.assertEquals(Integer.class, ObjectKit.getTypeArgument(bean));
        Assertions.assertEquals(String.class, ObjectKit.getTypeArgument(bean, 1));
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("null", ObjectKit.toString(null));
        Assertions.assertEquals(Collections.emptyMap().toString(), ObjectKit.toString(Collections.emptyMap()));
        Assertions.assertEquals("[1, 2]", Arrays.asList("1", "2").toString());
    }


    private interface TypeArgument<A, B> {
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class ExceptionCloneableBean implements Cloneable {
        private final Integer id;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("can not clone this object");
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class CloneableBean implements Cloneable {
        private final Integer id;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class SerializableBean implements Serializable {
        private static final long serialVersionUID = -7759522980793544334L;
        private final Integer id;
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class Bean implements TypeArgument<Integer, String> {
        private final Integer id;
    }

}

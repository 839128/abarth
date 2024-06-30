package org.miaixz.bus.core.xyz;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.miaixz.bus.core.annotation.Alias;
import org.miaixz.bus.core.annotation.resolve.elements.CombinationAnnotatedElement;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * test for {@link AnnoKit}
 */
public class AnnoKitTest {

    @Test
    public void testGetDeclaredAnnotations() {
        final Annotation[] annotations = AnnoKit.getDeclaredAnnotations(ClassForTest.class);
        Assertions.assertArrayEquals(annotations, ClassForTest.class.getDeclaredAnnotations());
        Assertions.assertSame(annotations, AnnoKit.getDeclaredAnnotations(ClassForTest.class));

        AnnoKit.clearCaches();
        Assertions.assertNotSame(annotations, AnnoKit.getDeclaredAnnotations(ClassForTest.class));
    }

    @Test
    public void testToCombination() {
        final CombinationAnnotatedElement element = AnnoKit.toCombination(ClassForTest.class);
        Assertions.assertEquals(2, element.getAnnotations().length);
    }

    @Test
    public void testGetAnnotations() {
        Annotation[] annotations = AnnoKit.getAnnotations(ClassForTest.class, true);
        Assertions.assertEquals(2, annotations.length);
        annotations = AnnoKit.getAnnotations(ClassForTest.class, false);
        Assertions.assertEquals(1, annotations.length);
    }

    @Test
    public void testGetCombinationAnnotations() {
        final MetaAnnotationForTest[] annotations = AnnoKit.getCombinationAnnotations(ClassForTest.class, MetaAnnotationForTest.class);
        Assertions.assertEquals(1, annotations.length);
    }

    @Test
    public void testAnnotations() {
        MetaAnnotationForTest[] annotations1 = AnnoKit.getAnnotations(ClassForTest.class, false, MetaAnnotationForTest.class);
        Assertions.assertEquals(0, annotations1.length);
        annotations1 = AnnoKit.getAnnotations(ClassForTest.class, true, MetaAnnotationForTest.class);
        Assertions.assertEquals(1, annotations1.length);

        Annotation[] annotations2 = AnnoKit.getAnnotations(
                ClassForTest.class, false, t -> ObjectKit.equals(t.annotationType(), MetaAnnotationForTest.class)
        );
        Assertions.assertEquals(0, annotations2.length);
        annotations2 = AnnoKit.getAnnotations(
                ClassForTest.class, true, t -> ObjectKit.equals(t.annotationType(), MetaAnnotationForTest.class)
        );
        Assertions.assertEquals(1, annotations2.length);
    }

    @Test
    public void testGetAnnotation() {
        final MetaAnnotationForTest annotation = AnnoKit.getAnnotation(ClassForTest.class, MetaAnnotationForTest.class);
        Assertions.assertNotNull(annotation);
    }

    @Test
    public void testHasAnnotation() {
        Assertions.assertTrue(AnnoKit.hasAnnotation(ClassForTest.class, MetaAnnotationForTest.class));
    }

    @Test
    public void testGetAnnotationValue() {
        final AnnotationForTest annotation = ClassForTest.class.getAnnotation(AnnotationForTest.class);
        Assertions.assertEquals(annotation.value(), AnnoKit.getAnnotationValue(ClassForTest.class, AnnotationForTest.class));
        Assertions.assertEquals(annotation.value(), AnnoKit.getAnnotationValue(ClassForTest.class, AnnotationForTest.class, "value"));
        Assertions.assertNull(AnnoKit.getAnnotationValue(ClassForTest.class, AnnotationForTest.class, "property"));
    }

    @Test
    public void testGetAnnotationValueMap() {
        final AnnotationForTest annotation = ClassForTest.class.getAnnotation(AnnotationForTest.class);
        final Map<String, Object> valueMap = AnnoKit.getAnnotationValueMap(ClassForTest.class, AnnotationForTest.class);
        Assertions.assertNotNull(valueMap);
        Assertions.assertEquals(2, valueMap.size());
        Assertions.assertEquals(annotation.value(), valueMap.get("value"));
    }

    @Test
    public void testGetRetentionPolicy() {
        final RetentionPolicy policy = AnnotationForTest.class.getAnnotation(Retention.class).value();
        Assertions.assertEquals(policy, AnnoKit.getRetentionPolicy(AnnotationForTest.class));
    }

    @Test
    public void testGetTargetType() {
        final ElementType[] types = AnnotationForTest.class.getAnnotation(Target.class).value();
        Assertions.assertArrayEquals(types, AnnoKit.getTargetType(AnnotationForTest.class));
    }

    @Test
    public void testIsDocumented() {
        Assertions.assertFalse(AnnoKit.isDocumented(AnnotationForTest.class));
    }

    @Test
    public void testIsInherited() {
        Assertions.assertFalse(AnnoKit.isInherited(AnnotationForTest.class));
    }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_8)
    public void testSetValue() {
        // jdk9+中抛出异常，须添加`--add-opens=java.base/java.lang=ALL-UNNAMED`启动参数
        final AnnotationForTest annotation = ClassForTest.class.getAnnotation(AnnotationForTest.class);
        final String newValue = "is a new value";
        Assertions.assertNotEquals(newValue, annotation.value());
        AnnoKit.setValue(annotation, "value", newValue);
        Assertions.assertEquals(newValue, annotation.value());
    }

    @Test
    public void testGetAnnotationAlias() {
        final MetaAnnotationForTest annotation = AnnoKit.getAnnotationAlias(AnnotationForTest.class, MetaAnnotationForTest.class);
        Assertions.assertNotNull(annotation);
        Assertions.assertEquals(annotation.value(), annotation.alias());
        Assertions.assertEquals(MetaAnnotationForTest.class, annotation.annotationType());
    }

    @Test
    public void testGetAnnotationAttributes() {
        final Method[] methods = AnnoKit.getAnnotationAttributes(AnnotationForTest.class);
        Assertions.assertArrayEquals(methods, AnnoKit.getAnnotationAttributes(AnnotationForTest.class));
        Assertions.assertEquals(2, methods.length);
        Assertions.assertArrayEquals(AnnotationForTest.class.getDeclaredMethods(), methods);
    }

    @SneakyThrows
    @Test
    public void testIsAnnotationAttribute() {
        Assertions.assertFalse(AnnoKit.isAnnotationAttribute(AnnotationForTest.class.getMethod("equals", Object.class)));
        Assertions.assertTrue(AnnoKit.isAnnotationAttribute(AnnotationForTest.class.getMethod("value")));
    }

    @Test
    public void getAnnotationValueTest2() {
        final String[] names = AnnoKit.getAnnotationValue(ClassForTest.class, AnnotationForTest::names);
        Assertions.assertTrue(ArrayKit.equals(names, new String[]{"测试1", "测试2"}));
    }

    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface MetaAnnotationForTest {
        @Alias(value = "alias")
        String value() default "";

        String alias() default "";
    }

    @MetaAnnotationForTest("foo")
    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface AnnotationForTest {
        String value() default "";

        String[] names() default "";
    }

    @AnnotationForTest(value = "foo", names = {"测试1", "测试2"})
    private static class ClassForTest {
    }

}

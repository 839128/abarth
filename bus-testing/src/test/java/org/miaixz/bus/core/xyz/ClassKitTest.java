package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.loader.classloader.JarClassLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link ClassKit} 单元测试
 */
public class ClassKitTest {

    @Test
    void getCallerClassLoaderTest() {
        final ClassLoader callerClassLoader = ClassKit.getCallerClassLoader();
        Assertions.assertEquals(ClassKitTest.class.getClassLoader(), callerClassLoader);
    }

    @Test
    public void isPresentTest() {
        final boolean present = ClassKit.isPresent("org.miaixz.bus.core.xyz.ClassKit");
        Assertions.assertTrue(present);
    }

    @Test
    public void loadClassTest() {
        String name = ClassKit.loadClass("java.lang.Thread.State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);

        name = ClassKit.loadClass("java.lang.Thread$State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);
    }

    @Test
    public void loadArrayClassTest() {
        final String s = Dictionary[].class.getName();

        final Class<Object> objectClass = ClassKit.loadClass(s);
        Assertions.assertEquals(Dictionary[].class, objectClass);
    }

    @Test
    public void loadInnerClassTest() {
        String name = ClassKit.loadClass("org.miaixz.bus.core.xyz.ClassKitTest.A").getName();
        Assertions.assertEquals("org.miaixz.bus.core.xyz.ClassKitTest$A", name);
        name = ClassKit.loadClass("org.miaixz.bus.core.xyz.ClassKitTest.A.B").getName();
        Assertions.assertEquals("org.miaixz.bus.core.xyz.ClassKitTest$A$B", name);
    }

    @Test
    @Disabled
    void loadClassFromJarTest() {
        final JarClassLoader classLoader = ClassKit.getJarClassLoader(
                FileKit.file("D:\\m2_repo\\com\\sap\\cloud\\db\\jdbc\\ngdbc\\2.18.13\\ngdbc-2.18.13.jar"));

        final Class<?> aClass = ClassKit.forName("com.sap.db.jdbc.Driver", true, classLoader);
        final Field instance = FieldKit.getField(aClass, "INSTANCE");
        Console.log(FieldKit.getFieldValue(null, instance));

        final Field version = FieldKit.getField(aClass, "JAVA_VERSION");
        Console.log(FieldKit.getFieldValue(null, version));
    }

    @Test
    public void getClassNameTest() {
        final String className = ClassKit.getClassName(ClassKit.class, false);
        Assertions.assertEquals("org.miaixz.bus.core.xyz.ClassKit", className);

        final String simpleClassName = ClassKit.getClassName(ClassKit.class, true);
        Assertions.assertEquals("ClassKit", simpleClassName);
    }

    @Test
    public void getClassPathTest() {
        final String classPath = ClassKit.getClassPath();
        Assertions.assertNotNull(classPath);
    }

    @Test
    public void getShortClassNameTest() {
        final String className = "org.miaixz.bus.core.xyz.StringKit";
        final String result = ClassKit.getShortClassName(className);
        Assertions.assertEquals("o.d.h.c.t.StringKit", result);
    }

    @Test
    public void getLocationPathTest() {
        final String classDir = ClassKit.getLocationPath(ClassKitTest.class);
        Assertions.assertTrue(Objects.requireNonNull(classDir).endsWith("/bus-core/target/test-classes/"));
    }

    @Test
    public void isAssignableTest() {
        Assertions.assertTrue(ClassKit.isAssignable(int.class, int.class));
        Assertions.assertTrue(ClassKit.isAssignable(int.class, Integer.class));
        Assertions.assertFalse(ClassKit.isAssignable(int.class, String.class));
    }

    @Test
    void testGetSuperClasses() {
        // if root is null
        List<Class<?>> superclasses = ClassKit.getSuperClasses(null);
        Assertions.assertEquals(0, superclasses.size());
        // if root not null
        superclasses = ClassKit.getSuperClasses(Child.class);
        Assertions.assertEquals(3, superclasses.size());
        Assertions.assertEquals(Parent.class, superclasses.get(0));
        Assertions.assertEquals(GrandParent.class, superclasses.get(1));
        Assertions.assertEquals(Object.class, superclasses.get(2));
    }

    @Test
    void testGetInterface() {
        // if root is null
        List<Class<?>> interfaces = ClassKit.getInterfaces(null);
        Assertions.assertEquals(0, interfaces.size());
        // if root not null
        interfaces = ClassKit.getInterfaces(Child.class);
        Assertions.assertEquals(4, interfaces.size());
        Assertions.assertEquals(Mother.class, interfaces.get(0));
        Assertions.assertEquals(Father.class, interfaces.get(1));
        Assertions.assertEquals(GrandMother.class, interfaces.get(2));
        Assertions.assertEquals(GrandFather.class, interfaces.get(3));
    }

    @Test
    void testTraverseTypeHierarchy() {
        // collect all superclass of child by bfs (include child)
        final List<Class<?>> superclasses = new ArrayList<>();
        ClassKit.traverseTypeHierarchy(
                Child.class, t -> !t.isInterface(), superclasses::add, true
        );
        Assertions.assertEquals(4, superclasses.size());
        Assertions.assertEquals(Child.class, superclasses.get(0));
        Assertions.assertEquals(Parent.class, superclasses.get(1));
        Assertions.assertEquals(GrandParent.class, superclasses.get(2));
        Assertions.assertEquals(Object.class, superclasses.get(3));

        // collect all superclass of child by bfs (exclude child)
        superclasses.clear();
        ClassKit.traverseTypeHierarchy(
                Child.class, t -> !t.isInterface(), superclasses::add, false
        );
        Assertions.assertEquals(3, superclasses.size());
        Assertions.assertEquals(Parent.class, superclasses.get(0));
        Assertions.assertEquals(GrandParent.class, superclasses.get(1));
        Assertions.assertEquals(Object.class, superclasses.get(2));
    }

    @Test
    void testTraverseTypeHierarchyWithTerminator() {
        // collect all superclass of child until Parent by bfs (include child)
        final List<Class<?>> superclasses = new ArrayList<>();
        ClassKit.traverseTypeHierarchyWhile(
                Child.class, t -> !t.isInterface(), t -> {
                    if (!Objects.equals(t, GrandParent.class)) {
                        superclasses.add(t);
                        return true;
                    }
                    return false;
                }
        );
        Assertions.assertEquals(2, superclasses.size());
        Assertions.assertEquals(Child.class, superclasses.get(0));
        Assertions.assertEquals(Parent.class, superclasses.get(1));

        // collect all class of child until GrandMother or GrandFather by bfs (include child)
        superclasses.clear();
        ClassKit.traverseTypeHierarchyWhile(
                Child.class, t -> {
                    if (!Objects.equals(t, GrandMother.class) && !Objects.equals(t, GrandFather.class)) {
                        superclasses.add(t);
                        return true;
                    }
                    return false;
                }
        );
        Assertions.assertEquals(6, superclasses.size());
        Assertions.assertEquals(Child.class, superclasses.get(0));
        Assertions.assertEquals(Parent.class, superclasses.get(1));
        Assertions.assertEquals(GrandParent.class, superclasses.get(2));
        Assertions.assertEquals(Mother.class, superclasses.get(3));
        Assertions.assertEquals(Father.class, superclasses.get(4));
        Assertions.assertEquals(Object.class, superclasses.get(5));
    }

    private interface Mother {
    }

    private interface Father {
    }

    private interface GrandMother extends Mother {
    }

    private interface GrandFather extends Father {
    }

    private static class GrandParent implements GrandMother, GrandFather {
    }

    private static class Parent extends GrandParent implements Mother, Father {
    }

    private static class Child extends Parent {
    }

}

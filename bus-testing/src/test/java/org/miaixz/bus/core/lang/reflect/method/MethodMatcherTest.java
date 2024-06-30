package org.miaixz.bus.core.lang.reflect.method;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * test for {@link MethodMatcher}
 */
class MethodMatcherTest {

    private final Predicate<Method> matchToString = t -> "toString".equals(t.getName());
    private Method noneReturnNoArgs;
    private Method noneReturnOneArgs;
    private Method noneReturnTwoArgs;
    private Method noneReturnTwoArgs2;
    private Method returnNoArgs;
    private Method returnOneArgs;
    private Method returnTwoArgs;

    private static void noneReturnOneArgs(final String arg1) {
    }

    public static void noneReturnTwoArgs(final String arg1, final List<String> stringList) {
    }

    public static void noneReturnTwoArgs(final CharSequence arg1, final Collection<String> stringList) {
    }

    @SneakyThrows
    @Test
    void test() {
        final Method toString = Object.class.getDeclaredMethod("toString");
        Assertions.assertTrue(matchToString.test(toString));
        final Method hashCode = Object.class.getDeclaredMethod("hashCode");
        Assertions.assertFalse(matchToString.test(hashCode));
    }

    @SneakyThrows
    @Test
    void and() {
        final Method toString = Object.class.getDeclaredMethod("toString");
        Assertions.assertTrue(matchToString.test(toString));
        final Predicate<Method> newMatcher = matchToString.and(t -> t.getReturnType() == String.class);
        Assertions.assertTrue(newMatcher.test(toString));
    }

    @SneakyThrows
    @Test
    void negate() {
        final Method toString = Object.class.getDeclaredMethod("toString");
        Assertions.assertTrue(matchToString.test(toString));
        final Predicate<Method> newMatcher = matchToString.negate();
        Assertions.assertFalse(newMatcher.test(toString));
    }

    @SneakyThrows
    @Test
    void or() {
        final Predicate<Method> newMatcher = matchToString.or(t -> "hashCode".equals(t.getName()));
        final Method toString = Object.class.getDeclaredMethod("toString");
        final Method hashCode = Object.class.getDeclaredMethod("hashCode");
        Assertions.assertTrue(newMatcher.test(toString));
        Assertions.assertTrue(newMatcher.test(hashCode));
    }

    @SneakyThrows
    @Test
    void inspect() {
        final Method toString = Object.class.getDeclaredMethod("toString");
        Assertions.assertTrue(matchToString.test(toString));
        final Method hashCode = Object.class.getDeclaredMethod("hashCode");
        Assertions.assertFalse(matchToString.test(hashCode));
    }

    @SneakyThrows
    @BeforeEach
    void init() {
        this.noneReturnNoArgs = MethodMatcherTest.class.getDeclaredMethod("noneReturnNoArgs");
        this.noneReturnOneArgs = MethodMatcherTest.class.getDeclaredMethod("noneReturnOneArgs", String.class);
        this.noneReturnTwoArgs = MethodMatcherTest.class.getDeclaredMethod("noneReturnTwoArgs", String.class, List.class);
        this.noneReturnTwoArgs2 = MethodMatcherTest.class.getDeclaredMethod("noneReturnTwoArgs", CharSequence.class, Collection.class);
        this.returnNoArgs = MethodMatcherTest.class.getDeclaredMethod("returnNoArgs");
        this.returnOneArgs = MethodMatcherTest.class.getDeclaredMethod("returnOneArgs", String.class);
        this.returnTwoArgs = MethodMatcherTest.class.getDeclaredMethod("returnTwoArgs", String.class, List.class);
    }

    @Test
    void testForName() {
        final Predicate<Method> methodMatcher = MethodMatcher.forName("noneReturnNoArgs");
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
    }

    @Test
    void forNameIgnoreCase() {
        Predicate<Method> methodMatcher = MethodMatcher.forNameIgnoreCase("noneReturnNoArgs");
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        // if name is upper case, it will be ignored
        methodMatcher = MethodMatcher.forNameIgnoreCase("NONERETURNNOARGS");
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
    }

    @Test
    void forNoneReturnType() {
        final Predicate<Method> methodMatcher = MethodMatcher.forNoneReturnType();
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forReturnType() {
        final Predicate<Method> methodMatcher = MethodMatcher.forReturnType(Collection.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertTrue(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forStrictReturnType() {
        Predicate<Method> methodMatcher = MethodMatcher.forStrictReturnType(Collection.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        // only match return type is strict equal to parameter type
        methodMatcher = MethodMatcher.forStrictReturnType(List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forParameterCount() {
        final Predicate<Method> methodMatcher = MethodMatcher.forParameterCount(2);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
    }

    @Test
    void forMostSpecificParameterTypes() {
        // match none args method
        Predicate<Method> methodMatcher = MethodMatcher.forMostSpecificParameterTypes();
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));

        // match all args types
        methodMatcher = MethodMatcher.forMostSpecificParameterTypes(null, null);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));

        // match first arg type
        methodMatcher = MethodMatcher.forMostSpecificParameterTypes(CharSequence.class, null);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));

        // match second arg type
        methodMatcher = MethodMatcher.forMostSpecificParameterTypes(null, Collection.class);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));

        // match two arg type
        methodMatcher = MethodMatcher.forMostSpecificParameterTypes(CharSequence.class, Collection.class);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
    }

    @Test
    void forMostSpecificStrictParameterTypes() {
        // match none args method
        Predicate<Method> methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes();
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));

        // match all args types
        methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes(null, null);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));

        // match first arg type
        methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes(CharSequence.class, null);
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes(String.class, null);
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));

        // match second arg type
        methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes(null, Collection.class);
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));

        // match two arg type
        methodMatcher = MethodMatcher.forMostSpecificStrictParameterTypes(CharSequence.class, Collection.class);
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
    }

    @Test
    void forParameterTypes() {
        Predicate<Method> methodMatcher = MethodMatcher.forParameterTypes();
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        // match parameter types is empty
        methodMatcher = MethodMatcher.forParameterTypes(CharSequence.class, Collection.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forStrictParameterTypes() {
        Predicate<Method> methodMatcher = MethodMatcher.forStrictParameterTypes();
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        // cannot match assignable parameter types
        methodMatcher = MethodMatcher.forStrictParameterTypes(CharSequence.class, Collection.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        // only match parameter types is strict equal to parameter type
        methodMatcher = MethodMatcher.forStrictParameterTypes(String.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void noneMatch() {
        Predicate<Method> methodMatcher = MethodMatcher.noneMatch();
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertTrue(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));

        // combine with other matchers
        methodMatcher = MethodMatcher.noneMatch(
                MethodMatcher.forName("noneReturnNoArgs"),
                MethodMatcher.forReturnType(Collection.class)
        );
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnOneArgs));
    }

    @Test
    void anyMatch() {
        Predicate<Method> methodMatcher = MethodMatcher.anyMatch();
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        // combine with other matchers
        methodMatcher = MethodMatcher.anyMatch(
                MethodMatcher.forName("noneReturnNoArgs"),
                MethodMatcher.forReturnType(Collection.class)
        );
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void allMatch() {
        Predicate<Method> methodMatcher = MethodMatcher.allMatch();
        Assertions.assertTrue(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertTrue(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));

        // combine with other matchers
        methodMatcher = MethodMatcher.allMatch(
                MethodMatcher.forName("noneReturnNoArgs"),
                MethodMatcher.forReturnType(Collection.class)
        );
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void isPublic() {
        final Predicate<Method> methodMatcher = MethodMatcher.isPublic();
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(returnNoArgs));
        Assertions.assertTrue(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void isStatic() {
        final Predicate<Method> methodMatcher = MethodMatcher.isStatic();
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void isPublicStatic() {
        final Predicate<Method> methodMatcher = MethodMatcher.isPublicStatic();
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forModifiers() {
        final Predicate<Method> methodMatcher = MethodMatcher.forModifiers(Modifier.PUBLIC, Modifier.STATIC);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forNameAndParameterTypes() {
        final Predicate<Method> methodMatcher = MethodMatcher.forNameAndParameterTypes("noneReturnTwoArgs", CharSequence.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forNameAndStrictParameterTypes() {
        Predicate<Method> methodMatcher = MethodMatcher.forNameAndStrictParameterTypes("noneReturnTwoArgs", CharSequence.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        methodMatcher = MethodMatcher.forNameAndStrictParameterTypes("returnTwoArgs", String.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forNameIgnoreCaseAndParameterTypes() {
        Predicate<Method> methodMatcher = MethodMatcher.forNameIgnoreCaseAndParameterTypes("NONEReturnTWOArgs", CharSequence.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        methodMatcher = MethodMatcher.forNameIgnoreCaseAndParameterTypes("ReturnTWOArgs", String.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forNameIgnoreCaseAndStrictParameterTypes() {
        Predicate<Method> methodMatcher = MethodMatcher.forNameIgnoreCaseAndStrictParameterTypes("NONEReturnTWOArgs", CharSequence.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        methodMatcher = MethodMatcher.forNameIgnoreCaseAndStrictParameterTypes("ReturnTWOArgs", String.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forStrictMethodSignature() {
        Predicate<Method> methodMatcher = MethodMatcher.forStrictMethodSignature("noneReturnTwoArgs", null, CharSequence.class, Collection.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs2));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        methodMatcher = MethodMatcher.forStrictMethodSignature("noneReturnTwoArgs", null, String.class, List.class);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs2));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forStrictMethodSignatureWithMethod() {
        Predicate<Method> methodMatcher = MethodMatcher.forStrictMethodSignature(noneReturnTwoArgs);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));

        methodMatcher = MethodMatcher.forStrictMethodSignature(returnTwoArgs);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertTrue(methodMatcher.test(returnTwoArgs));
    }

    @Test
    void forMethodSignatureWithMethod() {
        final Predicate<Method> methodMatcher = MethodMatcher.forMethodSignature(noneReturnTwoArgs2);
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs2));
    }

    @Test
    void forMethodSignature() {
        final Predicate<Method> methodMatcher = MethodMatcher.forMethodSignature(
                "noneReturnTwoArgs", null, CharSequence.class, Collection.class
        );
        Assertions.assertFalse(methodMatcher.test(noneReturnNoArgs));
        Assertions.assertFalse(methodMatcher.test(noneReturnOneArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs));
        Assertions.assertFalse(methodMatcher.test(returnNoArgs));
        Assertions.assertFalse(methodMatcher.test(returnOneArgs));
        Assertions.assertFalse(methodMatcher.test(returnTwoArgs));
        Assertions.assertTrue(methodMatcher.test(noneReturnTwoArgs2));
    }

    @Test
    @SneakyThrows
    void forGetterMethodWithField() {
        final Field nameField = Foo.class.getDeclaredField("name");
        Predicate<Method> methodMatcher = MethodMatcher.forGetterMethod(nameField);
        final Method getName = Foo.class.getMethod("getName");
        Assertions.assertTrue(methodMatcher.test(getName));

        final Field flagField = Foo.class.getDeclaredField("flag");
        methodMatcher = MethodMatcher.forGetterMethod(flagField);
        final Method isFlag = Foo.class.getMethod("isFlag");
        Assertions.assertTrue(methodMatcher.test(isFlag));

        final Field objectField = Foo.class.getDeclaredField("object");
        methodMatcher = MethodMatcher.forGetterMethod(objectField);
        final Method object = Foo.class.getMethod("object");
        Assertions.assertTrue(methodMatcher.test(object));
    }

    @Test
    @SneakyThrows
    void forGetterMethod() {
        Predicate<Method> methodMatcher = MethodMatcher.forGetterMethod("name", String.class);
        final Method getName = Foo.class.getMethod("getName");
        Assertions.assertTrue(methodMatcher.test(getName));

        methodMatcher = MethodMatcher.forGetterMethod("flag", boolean.class);
        final Method isFlag = Foo.class.getMethod("isFlag");
        Assertions.assertTrue(methodMatcher.test(isFlag));

        methodMatcher = MethodMatcher.forGetterMethod("object", Object.class);
        final Method object = Foo.class.getMethod("object");
        Assertions.assertTrue(methodMatcher.test(object));
    }

    @Test
    @SneakyThrows
    void forSetterMethodWithField() {
        final Field nameField = Foo.class.getDeclaredField("name");
        Predicate<Method> methodMatcher = MethodMatcher.forSetterMethod(nameField);
        final Method setName = Foo.class.getMethod("setName", String.class);
        Assertions.assertTrue(methodMatcher.test(setName));

        final Field flagField = Foo.class.getDeclaredField("flag");
        methodMatcher = MethodMatcher.forSetterMethod(flagField);
        final Method setFlag = Foo.class.getMethod("setFlag", boolean.class);
        Assertions.assertTrue(methodMatcher.test(setFlag));

        final Field objectField = Foo.class.getDeclaredField("object");
        methodMatcher = MethodMatcher.forSetterMethod(objectField);
        final Method object = Foo.class.getMethod("object", Object.class);
        Assertions.assertTrue(methodMatcher.test(object));
    }

    @Test
    @SneakyThrows
    void forSetterMethod() {
        Predicate<Method> methodMatcher = MethodMatcher.forSetterMethod("name", String.class);
        final Method setName = Foo.class.getMethod("setName", String.class);
        Assertions.assertTrue(methodMatcher.test(setName));

        methodMatcher = MethodMatcher.forSetterMethod("flag", boolean.class);
        final Method setFlag = Foo.class.getMethod("setFlag", boolean.class);
        Assertions.assertTrue(methodMatcher.test(setFlag));

        methodMatcher = MethodMatcher.forSetterMethod("object", Object.class);
        final Method object = Foo.class.getMethod("object", Object.class);
        Assertions.assertTrue(methodMatcher.test(object));
    }

    @Test
    @SneakyThrows
    void hasDeclaredAnnotation() {
        final Predicate<Method> methodMatcher = MethodMatcher.hasDeclaredAnnotation(GrandParentAnnotation.class);
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));
    }

    @Test
    @SneakyThrows
    void hasAnnotation() {
        Predicate<Method> methodMatcher = MethodMatcher.hasAnnotation(GrandParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));

        methodMatcher = MethodMatcher.hasAnnotation(ParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));
    }

    @Test
    @SneakyThrows
    void hasAnnotationOnDeclaringClass() {
        Predicate<Method> methodMatcher = MethodMatcher.hasAnnotationOnDeclaringClass(GrandParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));

        methodMatcher = MethodMatcher.hasAnnotationOnDeclaringClass(ParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));

        methodMatcher = MethodMatcher.hasAnnotationOnDeclaringClass(ChildAnnotation.class);
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));
    }

    @Test
    @SneakyThrows
    void hasAnnotationOnMethodOrDeclaringClass() {
        Predicate<Method> methodMatcher = MethodMatcher.hasAnnotationOnMethodOrDeclaringClass(GrandParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));

        methodMatcher = MethodMatcher.hasAnnotationOnMethodOrDeclaringClass(ParentAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));

        methodMatcher = MethodMatcher.hasAnnotationOnMethodOrDeclaringClass(ChildAnnotation.class);
        Assertions.assertTrue(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByChildAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("annotatedByGrandParentAnnotation")));
        Assertions.assertFalse(methodMatcher.test(AnnotatedClass.class.getDeclaredMethod("noneAnnotated")));
    }

    private void noneReturnNoArgs() {
    }

    public List<String> returnNoArgs() {
        return null;
    }

    public Set<String> returnOneArgs(final String arg1) {
        return null;
    }

    public List<String> returnTwoArgs(final String arg1, final List<String> stringList) {
        return null;
    }

    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface GrandParentAnnotation {
    }

    @GrandParentAnnotation
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ParentAnnotation {
    }

    @ParentAnnotation
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ChildAnnotation {
    }

    @ParentAnnotation
    private static class AnnotatedClass {

        @ParentAnnotation
        private static void annotatedByParentAnnotation() {
        }

        @GrandParentAnnotation
        public static void annotatedByGrandParentAnnotation() {
        }

        public static void noneAnnotated() {
        }

        @ChildAnnotation
        private void annotatedByChildAnnotation() {
        }
    }

    private static class Foo {
        @Setter
        @Getter
        private String name;
        @Setter
        @Getter
        private boolean flag;
        private Object object;

        public void setName(final String name, final Void none) {
        }

        public Object object() {
            return object;
        }

        public Foo object(final Object object) {
            this.object = object;
            return this;
        }
    }
}

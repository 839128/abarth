package org.miaixz.bus.core.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.CollKit;

import javax.management.monitor.MonitorSettingException;
import java.util.*;
import java.util.stream.Stream;

/**
 * {@link Optional}的单元测试
 */
public class OptionalTest {

    @Test
    public void ofTest() {
        Assertions.assertTrue(Optional.of(Optional.empty()).isEmpty());
        Assertions.assertTrue(Optional.of(Optional.of(1)).isPresent());
    }

    @Test
    public void ofBlankAbleTest() {
        // ofBlankAble相对于ofNullable考虑了字符串为空串的情况
        final String bus = Optional.ofBlankAble("").orElse("bus");
        Assertions.assertEquals("bus", bus);
    }

    @Test
    public void getTest() {
        // 和原版Optional有区别的是，get不会抛出NoSuchElementException
        // 如果想使用原版Optional中的get这样，获取一个一定不为空的值，则应该使用orElseThrow
        final Object opt = Optional.ofNullable(null).get();
        Assertions.assertNull(opt);
    }

    @Test
    public void isEmptyTest() {
        // 这是jdk11 Optional中的新函数，直接照搬了过来
        // 判断包裹内元素是否为空，注意并没有判断空字符串的情况
        final boolean isEmpty = Optional.empty().isEmpty();
        Assertions.assertTrue(isEmpty);
    }

    @Test
    public void peekTest() {
        final User user = new User();
        // 相当于ifPresent的链式调用
        Optional.ofNullable("bus").peek(user::setUsername).peek(user::setNickname);
        Assertions.assertEquals("bus", user.getNickname());
        Assertions.assertEquals("bus", user.getUsername());

        // 注意，传入的lambda中，对包裹内的元素执行赋值操作并不会影响到原来的元素
        final String name = Optional.ofNullable("bus").peek(username -> username = "123").peek(username -> username = "456").get();
        Assertions.assertEquals("bus", name);
    }

    @Test
    public void peeksTest() {
        final User user = new User();
        // 相当于上面peek的动态参数调用，更加灵活，你可以像操作数组一样去动态设置中间的步骤，也可以使用这种方式去编写你的代码
        // 可以一行搞定
        Optional.ofNullable("bus").peeks(user::setUsername, user::setNickname);
        // 也可以在适当的地方换行使得代码的可读性提高
        Optional.of(user).peeks(
                u -> Assertions.assertEquals("bus", u.getNickname()),
                u -> Assertions.assertEquals("bus", u.getUsername())
        );
        Assertions.assertEquals("bus", user.getNickname());
        Assertions.assertEquals("bus", user.getUsername());

        // 注意，传入的lambda中，对包裹内的元素执行赋值操作并不会影响到原来的元素,这是java语言的特性。。。
        // 这也是为什么我们需要getter和setter而不直接给bean中的属性赋值中的其中一个原因
        final String name = Optional.ofNullable("bus").peeks(
                username -> username = "123", username -> username = "456",
                n -> Assertions.assertEquals("bus", n)).get();
        Assertions.assertEquals("bus", name);

        // 当然，以下情况不会抛出NPE，但也没什么意义
        Optional.ofNullable("bus").peeks().peeks().peeks();
        Optional.ofNullable(null).peeks(i -> {
        });

    }

    @Test
    public void orTest() {
        // 这是jdk9 Optional中的新函数，直接照搬了过来
        // 给一个替代的Opt
        final String str = Optional.<String>ofNullable(null).or(() -> Optional.ofNullable("Hello bus!")).map(String::toUpperCase).orElseThrow();
        Assertions.assertEquals("HELLO BUS!", str);

        final User user = User.builder().username("bus").build();
        final Optional<User> userOpt = Optional.of(user);
        // 获取昵称，获取不到则获取用户名
        final String name = userOpt.map(User::getNickname).or(() -> userOpt.map(User::getUsername)).get();
        Assertions.assertEquals("bus", name);
    }

    @Test
    public void orElseThrowTest() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            // 获取一个不可能为空的值，否则抛出NoSuchElementException异常
            final Object obj = Optional.ofNullable(null).orElseThrow();
            Assertions.assertNull(obj);
        });
    }

    @Test
    public void orElseThrowTest2() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // 获取一个不可能为空的值，否则抛出自定义异常
            final Object assignException = Optional.ofNullable(null).orElseThrow(IllegalStateException::new);
            Assertions.assertNull(assignException);
        });
    }

    @Test
    public void orElseRunTest() {
        // 判断一个值是否为空，为空执行一段逻辑,否则执行另一段逻辑
        final Map<String, Integer> map = new HashMap<>();
        final String key = "key";
        map.put(key, 1);
        Optional.ofNullable(map.get(key))
                .ifPresent(v -> map.put(key, v + 1))
                .orElseRun(() -> map.remove(key));
        Assertions.assertEquals((Object) 2, map.get(key));
    }

    @Test
    public void flattedMapTest() {
        // 和Optional兼容的flatMap
        final List<User> userList = new ArrayList<>();
        // 以前，不兼容
//		Optional.ofNullable(userList).map(List::stream).flatMap(Stream::findFirst);
        // 现在，兼容
        final User user = Optional.ofNullable(userList).map(List::stream)
                .flattedMap(Stream::findFirst).orElseGet(User.builder()::build);
        Assertions.assertNull(user.getUsername());
        Assertions.assertNull(user.getNickname());
    }

    @Test
    public void ofEmptyAbleTest() {
        // 以前，输入一个CollectionUtil感觉要命，类似前缀的类一大堆，代码补全形同虚设(在项目中起码要输入完CollectionUtil才能在第一个调出这个函数)
        // 关键它还很常用，判空和判空集合真的太常用了...
        final List<String> past = Optional.ofNullable(Collections.<String>emptyList()).filter(CollKit::isNotEmpty).orElseGet(() -> Collections.singletonList("bus"));
        // 现在，一个ofEmptyAble搞定
        final List<String> bus = Optional.ofEmptyAble(Collections.<String>emptyList()).orElseGet(() -> Collections.singletonList("bus"));
        Assertions.assertEquals(past, bus);
        Assertions.assertEquals(Collections.singletonList("bus"), bus);
        Assertions.assertFalse(Optional.ofEmptyAble(Arrays.asList(null, null, null)).isEmpty());
    }

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "ConstantConditions"})
    @Test
    public void execTest() {
        // 有一些资深的程序员跟我说你这个lambda，双冒号语法糖看不懂...
        // 为了尊重资深程序员的意见，并且提升代码可读性，封装了一下 "try catch NPE 和 数组越界"的情况

        // 以前这种写法，简洁但可读性稍低，对资深程序员不太友好
        final List<String> last = null;
        final String npeSituation = Optional.ofEmptyAble(last).flattedMap(l -> l.stream().findFirst()).orElse("bus");
        final String indexOutSituation = Optional.ofEmptyAble(last).map(l -> l.get(0)).orElse("bus");

        // 现在代码整洁度降低，但可读性up，如果再人说看不懂这代码...
        final String npe = Optional.ofTry(() -> last.get(0)).exceptionOrElse("bus");
        final String indexOut = Optional.ofTry(() -> {
            final List<String> list = new ArrayList<>();
            // 你可以在里面写一长串调用链 list.get(0).getUser().getId()
            return list.get(0);
        }).exceptionOrElse("bus");

        Assertions.assertSame(AssertionError.class, Optional.ofTry(() -> {
            throw new AssertionError("");
        }).getThrowable());
        Assertions.assertEquals(npe, npeSituation);
        Assertions.assertEquals(indexOut, indexOutSituation);
        Assertions.assertEquals("bus", npe);
        Assertions.assertEquals("bus", indexOut);

        // 多线程下情况测试
        Stream.iterate(0, i -> ++i).limit(20000).parallel().forEach(i -> {
            final Optional<Object> opt = Optional.ofTry(() -> {
                if (i % 2 == 0) {
                    throw new IllegalStateException(String.valueOf(i));
                } else {
                    throw new NullPointerException(String.valueOf(i));
                }
            });
            Assertions.assertTrue(
                    (i % 2 == 0 && opt.getThrowable() instanceof IllegalStateException) ||
                            (i % 2 != 0 && opt.getThrowable() instanceof NullPointerException)
            );
        });
    }

    @SuppressWarnings({"NumericOverflow", "divzero"})
    @Test
    @Disabled
    void testFail() {
        Optional.ofTry(() -> 1 / 0)
                .ifFail(Console::log)
                .ifFail(Console::log, ArithmeticException.class)
                .ifFail(Console::log, NullPointerException.class)
                .ifFail(Console::log, NullPointerException.class, MonitorSettingException.class)
        ;
    }

    @SuppressWarnings({"NumericOverflow", "divzero"})
    @Test
    @Disabled
    void testFail1() {
        final Integer i = Optional.ofTry(() -> 1 / 0)
                .map(e -> 666)
                .ifFail(Console::log)
                .orElseGet(() -> 1);
        Assertions.assertEquals(i, 1);
    }

    @SuppressWarnings({"NumericOverflow", "divzero"})
    @Test
    @Disabled
    void testToEasyStream() {
        final List<Integer> list = Optional.ofTry(() -> 1).toEasyStream().toList();
        Assertions.assertArrayEquals(list.toArray(), new Integer[]{1});
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String username;
        private String nickname;
    }

}

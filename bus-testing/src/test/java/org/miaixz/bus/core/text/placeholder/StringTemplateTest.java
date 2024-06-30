package org.miaixz.bus.core.text.placeholder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;
import org.miaixz.bus.core.text.placeholder.template.NamedStringTemplate;
import org.miaixz.bus.core.text.placeholder.template.SingleStringTemplate;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.MapKit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * test for {@link StringTemplate}
 */
public class StringTemplateTest {

    @Test
    public void singlePlaceholderFormatTest() {
        //  默认值
        testSinglePlaceholderFormat("{}", '\\');

        // 自定义占位符
        testSinglePlaceholderFormat("?", '\\');

        //  自定义多个占位符
        testSinglePlaceholderFormat("$$$", '\\');

        // 自定义多个占位符和转义符
        testSinglePlaceholderFormat("$$$", '/');
    }

    @Test
    public void namedPlaceholderFormatSequenceTest() {
        final String text = "select * from #[tableName] where id = #[id]";
        final NamedStringTemplate strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatSequence("user", 1001)
        );
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatArraySequence(new String[]{"user", "1001"})
        );
        Assertions.assertEquals(
                "select * from 123 where id = 456",
                strTemplate.formatArraySequence(new int[]{123, 456})
        );
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatSequence(ListKit.of("user", 1001))
        );
    }

    @Test
    public void namedPlaceholderFormatIndexedTest() {
        final String text = "select * from #[1] where id = #[2]";
        final NamedStringTemplate strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();

        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatIndexed("bus", "user", 1001)
        );
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatArrayIndexed(new String[]{"bus", "user", "1001"})
        );
        Assertions.assertEquals(
                "select * from 123 where id = 456",
                strTemplate.formatArrayIndexed(new int[]{666, 123, 456})
        );
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.formatIndexed(ListKit.of("bus", "user", 1001))
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.formatIndexed(ListKit.of("bus", "user"), idx -> "?")
        );
    }

    @Test
    public void namedPlaceholderFormatTest() {
        final String text = "select * from #[tableName] where id = #[id]";
        final NamedStringTemplate strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();

        final Map<String, Object> map = MapKit.<String, Object>builder().put("tableName", "user").put("id", 1001).build();
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.format(map)
        );
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.format((Object) map)
        );

        FormatEntity entity = new FormatEntity().setTableName("user").setId(1001);
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.format(entity)
        );
        entity = new FormatEntity().setTableName("user").setId(1001);
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.format(entity)
        );
    }

    @Test
    public void namedPlaceholderFormatDefaultValueTest() {
        String text = "i {a}{m} a {jvav} programmer";
        NamedStringTemplate.Builder strTemplate = StringTemplate.ofNamed(text)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE);
        Assertions.assertEquals(
                "i  a  programmer",
                strTemplate.defaultValue(s -> "")
                        .build()
                        .formatSequence()
        );
        Assertions.assertEquals(
                "i ?? a ? programmer",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatSequence()
        );
        Assertions.assertEquals(
                "i $$$$$$ a $$$ programmer",
                strTemplate.defaultValue(s -> "$$$")
                        .build()
                        .formatSequence()
        );

        text = "select * from #[tableName] where id = #[id]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]");
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatSequence("user", 1001)
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE)
                        .build()
                        .formatSequence("user")
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatArraySequence(new String[]{"user"})
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatSequence(ListKit.of("user"))
        );

        text = "select * from #[1] where id = #[2]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE);
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatIndexed("bus", "user")
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatArrayIndexed(new String[]{"bus", "user"})
        );
        Assertions.assertEquals(
                "select * from user where id = ?",
                strTemplate.defaultValue(s -> "?")
                        .build()
                        .formatIndexed(ListKit.of("bus", "user"))
        );
    }

    @Test
    public void namedPlaceholderEscapeTest() {
        final Map<String, Object> map = MapKit.<String, Object>builder().put("tableName", "user").put("id", 1001).build();
        // 转义符
        String text = "select * from \\#[tableName] where id = \\#[id]";
        NamedStringTemplate strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from #[tableName] where id = #[id]",
                strTemplate.format(map)
        );
        text = "select * from \\#[tableName] where id = #[id\\]]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from #[tableName] where id = 1001",
                strTemplate.format(MapKit.<String, Object>builder().put("tableName", "user").put("id]", 1001).build())
        );

        // 转义 转义符
        text = "select * from \\\\#[tableName] where id = #[id]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from \\user where id = 1001",
                strTemplate.format(map)
        );
        text = "select * from \\\\#[tableName] where id = \\\\#[id]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from \\user where id = \\1001",
                strTemplate.format(map)
        );
        text = "select * from \\\\#[tableName] where id = #[id\\\\]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from \\user where id = 1001",
                strTemplate.format(MapKit.<String, Object>builder().put("tableName", "user").put("id\\", 1001).build())
        );
        text = "select * from #[tableName\\\\] where id = #[id\\\\]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").build();
        Assertions.assertEquals(
                "select * from user where id = 1001",
                strTemplate.format(MapKit.<String, Object>builder().put("tableName\\", "user").put("id\\", 1001).build())
        );

        // 自定义 转义符
        text = "select * from /#[tableName] where id = /#[id]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").escape('/').build();
        Assertions.assertEquals(
                "select * from #[tableName] where id = #[id]",
                strTemplate.format(map)
        );
        text = "select * from //#[tableName] where id = //#[id]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").escape('/').build();
        Assertions.assertEquals(
                "select * from /user where id = /1001",
                strTemplate.format(map)
        );
        text = "select * from /#[tableName] where id = #[id/]]";
        strTemplate = StringTemplate.ofNamed(text).prefix("#[").suffix("]").escape('/').build();
        Assertions.assertEquals(
                "select * from #[tableName] where id = 1001",
                strTemplate.format(MapKit.<String, Object>builder().put("tableName", "user").put("id]", 1001).build())
        );
    }

    private void testSinglePlaceholderFormat(final String placeholder, final char escape) {
        // 通常使用
        final String commonTemplate = "this is " + placeholder + " for " + placeholder;
        final SingleStringTemplate template = StringTemplate.of(commonTemplate)
                .placeholder(placeholder)
                .escape(escape)
                .build();


        // 普通使用
        Assertions.assertEquals("this is a for 666",
                template.format("a", 666)
        );
        Assertions.assertEquals("this is a for 666",
                template.format(ListKit.of("a", 666))
        );
        Assertions.assertEquals("this is 123 for 456",
                template.formatArray(new int[]{123, 456})
        );
        Assertions.assertEquals("this is 123 for 456",
                template.formatArray(new Integer[]{123, 456})
        );

        // 转义占位符
        Assertions.assertEquals("this is " + placeholder + " for a",
                StringTemplate.of("this is " + escape + placeholder + " for " + placeholder)
                        .placeholder(placeholder)
                        .escape(escape)
                        .build()
                        .format("a", "b")
        );
        // 转义"转义符"
        Assertions.assertEquals("this is " + escape + "a for b",
                StringTemplate.of("this is " + escape + escape + placeholder + " for " + placeholder)
                        .placeholder(placeholder)
                        .escape(escape)
                        .build()
                        .format("a", "b")
        );
        // 填充null值
        Assertions.assertEquals("this is " + null + " for b",
                template.format(null, "b")
        );
        Assertions.assertEquals("this is a for null",
                template.format("a", null)
        );

        // 序列化参数 小于 占位符数量
        Assertions.assertEquals("this is a for " + placeholder,
                template.format("a")
        );


        SingleStringTemplate.Builder builder = StringTemplate.of(commonTemplate)
                .placeholder(placeholder)
                .escape(escape)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE);

        Assertions.assertEquals("this is a for ",
                builder.defaultValue("")
                        .build()
                        .format("a")
        );
        Assertions.assertEquals("this is a for 666",
                builder.defaultValue("666")
                        .build()
                        .format("a")
        );

        builder = StringTemplate.of(commonTemplate)
                .placeholder(placeholder)
                .escape(escape)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE);
        Assertions.assertEquals("this is a for ",
                builder.defaultValue(s -> "")
                        .build()
                        .format("a")
        );

        Assertions.assertEquals("this is a for 666",
                builder.defaultValue(s -> "666")
                        .build()
                        .format("a")
        );

        // 序列化参数 超过 占位符数量
        Assertions.assertEquals("this is a for b",
                builder.build()
                        .format("a", "b", "c")
        );

        // 残缺的占位符
        if (placeholder.length() >= 2) {
            Assertions.assertEquals("this is " + placeholder.charAt(0) + " for a",
                    StringTemplate.of("this is " + placeholder.charAt(0) + " for " + placeholder)
                            .placeholder(placeholder)
                            .escape(escape)
                            .build()
                            .format("a")
            );
            Assertions.assertEquals("this is " + placeholder.charAt(1) + " for a",
                    StringTemplate.of("this is " + placeholder.charAt(1) + " for " + placeholder)
                            .placeholder(placeholder)
                            .escape(escape)
                            .build()
                            .format("a")
            );
            Assertions.assertEquals("this is " + placeholder.charAt(0) + ' ' + placeholder.charAt(1) + " for a",
                    StringTemplate.of("this is " + placeholder.charAt(0) + ' ' + placeholder.charAt(1) + " for " + placeholder)
                            .placeholder(placeholder)
                            .escape(escape)
                            .build()
                            .format("a")
            );
        }
    }

    @Test
    public void isMatchesTest() {
        SingleStringTemplate strTemplate = StringTemplate.of("this is {} for {}").build();
        Assertions.assertTrue(strTemplate.isMatches("this is a for b"));
        Assertions.assertTrue(strTemplate.isMatches("this is aaa for 666"));
        Assertions.assertTrue(strTemplate.isMatches("this is a for b "));
        Assertions.assertTrue(strTemplate.isMatches("this is a x for b"));
        Assertions.assertTrue(strTemplate.isMatches("this is {} for {}"));
        Assertions.assertTrue(strTemplate.isMatches("this is { } for {}"));
        Assertions.assertTrue(strTemplate.isMatches("this is { } for { }"));
        Assertions.assertTrue(strTemplate.isMatches("this is  a for b"));
        Assertions.assertTrue(strTemplate.isMatches("this is  a for  b"));
        Assertions.assertTrue(strTemplate.isMatches("this is a  for b"));
        Assertions.assertTrue(strTemplate.isMatches("this is a for "));
        Assertions.assertTrue(strTemplate.isMatches("this is  for b"));
        Assertions.assertTrue(strTemplate.isMatches("this is  for "));

        Assertions.assertFalse(strTemplate.isMatches(""));
        Assertions.assertFalse(strTemplate.isMatches(" "));
        Assertions.assertFalse(strTemplate.isMatches("  \r\n \n "));
        Assertions.assertFalse(strTemplate.isMatches(" this is a for b"));
        Assertions.assertFalse(strTemplate.isMatches("this is a forb"));
        Assertions.assertFalse(strTemplate.isMatches("this  is a for b"));
        Assertions.assertFalse(strTemplate.isMatches("this are a for b"));
        Assertions.assertFalse(strTemplate.isMatches("that is a for b"));

        // 占位符在最前和最后
        strTemplate = StringTemplate.of("{}, this is for {}").build();
        Assertions.assertTrue(strTemplate.isMatches("Cleveland, this is for you"));
        Assertions.assertTrue(strTemplate.isMatches("Cleveland, this is for you "));
        Assertions.assertTrue(strTemplate.isMatches(" Cleveland, this is for you"));
        Assertions.assertTrue(strTemplate.isMatches("Cleveland, this is for  you "));
        Assertions.assertTrue(strTemplate.isMatches("Cleveland, this is for you ?"));
        Assertions.assertTrue(strTemplate.isMatches("Cleveland , this is for you"));
        Assertions.assertTrue(strTemplate.isMatches(":)Cleveland, this is for you"));

        Assertions.assertFalse(strTemplate.isMatches("Cleveland,  this is for you"));
        Assertions.assertFalse(strTemplate.isMatches("Cleveland, this  is for you"));
        Assertions.assertFalse(strTemplate.isMatches("Cleveland, this is  for you"));
        Assertions.assertFalse(strTemplate.isMatches("Cleveland, this is four you"));
        Assertions.assertFalse(strTemplate.isMatches("Cleveland, this are for you"));
        Assertions.assertFalse(strTemplate.isMatches("Cleveland, that is for you"));
    }

    @Test
    public void singlePlaceholderMatchesTest() {
        SingleStringTemplate strTemplate = StringTemplate.of("this is {} for {}").build();
        Assertions.assertEquals(ListKit.of("a", "b"), strTemplate.matches("this is a for b"));
        Assertions.assertEquals(ListKit.of("aaa", "666"), strTemplate.matches("this is aaa for 666"));
        Assertions.assertEquals(ListKit.of("a", "b "), strTemplate.matches("this is a for b "));
        Assertions.assertEquals(ListKit.of("a x", "b"), strTemplate.matches("this is a x for b"));
        Assertions.assertEquals(ListKit.of("{}", "{}"), strTemplate.matches("this is {} for {}"));
        Assertions.assertEquals(ListKit.of("{ }", "{}"), strTemplate.matches("this is { } for {}"));
        Assertions.assertEquals(ListKit.of("{ }", "{ }"), strTemplate.matches("this is { } for { }"));
        Assertions.assertEquals(ListKit.of(" a", "b"), strTemplate.matches("this is  a for b"));
        Assertions.assertEquals(ListKit.of(" a", " b"), strTemplate.matches("this is  a for  b"));
        Assertions.assertEquals(ListKit.of("a ", "b"), strTemplate.matches("this is a  for b"));
        Assertions.assertEquals(ListKit.of("a", null), strTemplate.matches("this is a for "));
        Assertions.assertEquals(ListKit.of(null, "b"), strTemplate.matches("this is  for b"));
        Assertions.assertEquals(ListKit.of(null, null), strTemplate.matches("this is  for "));

        final List<Object> emptyList = Collections.emptyList();
        Assertions.assertEquals(emptyList, strTemplate.matches(""));
        Assertions.assertEquals(emptyList, strTemplate.matches(" "));
        Assertions.assertEquals(emptyList, strTemplate.matches("  \r\n \n "));
        Assertions.assertEquals(emptyList, strTemplate.matches(" this is a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matches("this is a forb"));
        Assertions.assertEquals(emptyList, strTemplate.matches("this  is a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matches("this are a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matches("that is a for b"));

        strTemplate = StringTemplate.of("{}, this is for {}").build();
        Assertions.assertEquals(ListKit.of("Cleveland", "you"), strTemplate.matches("Cleveland, this is for you"));
        Assertions.assertEquals(ListKit.of(" Cleveland", "you"), strTemplate.matches(" Cleveland, this is for you"));
        Assertions.assertEquals(ListKit.of("Cleveland ", "you"), strTemplate.matches("Cleveland , this is for you"));
        Assertions.assertEquals(ListKit.of("Cleveland", "you "), strTemplate.matches("Cleveland, this is for you "));
        Assertions.assertEquals(ListKit.of("Cleveland", " you"), strTemplate.matches("Cleveland, this is for  you"));
        Assertions.assertEquals(ListKit.of("Cleveland", " you "), strTemplate.matches("Cleveland, this is for  you "));
        Assertions.assertEquals(ListKit.of("Cleveland", "you ?"), strTemplate.matches("Cleveland, this is for you ?"));
        Assertions.assertEquals(ListKit.of(":)Cleveland", "you:("), strTemplate.matches(":)Cleveland, this is for you:("));

        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland,  this is for you"));
        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland, this  is for you"));
        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland, this is  for you"));
        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland, this is four you"));
        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland, this are for you"));
        Assertions.assertEquals(emptyList, strTemplate.matches("Cleveland, that is for you"));
    }

    @Test
    public void namedPlaceholderMatchesSequenceTest() {
        NamedStringTemplate strTemplate = StringTemplate.ofNamed("this is {a} for {b}").build();
        Assertions.assertEquals(ListKit.of("a", "b"), strTemplate.matchesSequence("this is a for b"));
        Assertions.assertEquals(ListKit.of("aaa", "666"), strTemplate.matchesSequence("this is aaa for 666"));
        Assertions.assertEquals(ListKit.of("a", "b "), strTemplate.matchesSequence("this is a for b "));
        Assertions.assertEquals(ListKit.of("a x", "b"), strTemplate.matchesSequence("this is a x for b"));
        Assertions.assertEquals(ListKit.of("{}", "{}"), strTemplate.matchesSequence("this is {} for {}"));
        Assertions.assertEquals(ListKit.of("{ }", "{}"), strTemplate.matchesSequence("this is { } for {}"));
        Assertions.assertEquals(ListKit.of("{ }", "{ }"), strTemplate.matchesSequence("this is { } for { }"));
        Assertions.assertEquals(ListKit.of(" a", "b"), strTemplate.matchesSequence("this is  a for b"));
        Assertions.assertEquals(ListKit.of(" a", " b"), strTemplate.matchesSequence("this is  a for  b"));
        Assertions.assertEquals(ListKit.of("a ", "b"), strTemplate.matchesSequence("this is a  for b"));
        Assertions.assertEquals(ListKit.of("a", null), strTemplate.matchesSequence("this is a for "));
        Assertions.assertEquals(ListKit.of(null, "b"), strTemplate.matchesSequence("this is  for b"));
        Assertions.assertEquals(ListKit.of(null, null), strTemplate.matchesSequence("this is  for "));

        final List<Object> emptyList = Collections.emptyList();
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence(""));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence(" "));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("  \r\n \n "));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence(" this is a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("this is a forb"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("this  is a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("this are a for b"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("that is a for b"));

        strTemplate = StringTemplate.ofNamed("{a}, this is for {b}").build();
        Assertions.assertEquals(ListKit.of("Cleveland", "you"), strTemplate.matchesSequence("Cleveland, this is for you"));
        Assertions.assertEquals(ListKit.of(" Cleveland", "you"), strTemplate.matchesSequence(" Cleveland, this is for you"));
        Assertions.assertEquals(ListKit.of("Cleveland ", "you"), strTemplate.matchesSequence("Cleveland , this is for you"));
        Assertions.assertEquals(ListKit.of("Cleveland", "you "), strTemplate.matchesSequence("Cleveland, this is for you "));
        Assertions.assertEquals(ListKit.of("Cleveland", " you"), strTemplate.matchesSequence("Cleveland, this is for  you"));
        Assertions.assertEquals(ListKit.of("Cleveland", " you "), strTemplate.matchesSequence("Cleveland, this is for  you "));
        Assertions.assertEquals(ListKit.of("Cleveland", "you ?"), strTemplate.matchesSequence("Cleveland, this is for you ?"));
        Assertions.assertEquals(ListKit.of(":)Cleveland", "you:("), strTemplate.matchesSequence(":)Cleveland, this is for you:("));

        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland,  this is for you"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland, this  is for you"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland, this is  for you"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland, this is four you"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland, this are for you"));
        Assertions.assertEquals(emptyList, strTemplate.matchesSequence("Cleveland, that is for you"));
    }

    @Test
    public void namedPlaceholderMatchesIndexedTest() {
        NamedStringTemplate strTemplate = StringTemplate.ofNamed("this is {2} for {1}").build();
        Assertions.assertEquals(ListKit.of(null, "b", "a"), strTemplate.matchesIndexed("this is a for b", null));
        Assertions.assertEquals(ListKit.of(null, "666", "aaa"), strTemplate.matchesIndexed("this is aaa for 666", null));
        Assertions.assertEquals(ListKit.of(null, "b", null), strTemplate.matchesIndexed("this is  for b", null));
        Assertions.assertEquals(ListKit.of(null, null, "aaa"), strTemplate.matchesIndexed("this is aaa for ", null));
        Assertions.assertEquals(ListKit.of(null, null, null), strTemplate.matchesIndexed("this is  for ", null));

        strTemplate = StringTemplate.ofNamed("this is {2} for {1}")
                .addFeatures(StringTemplate.Feature.MATCH_EMPTY_VALUE_TO_DEFAULT_VALUE)
                .build();
        Assertions.assertEquals(ListKit.of(null, "b", "a"), strTemplate.matchesIndexed("this is a for b", idx -> "?"));
        Assertions.assertEquals(ListKit.of(null, "666", "aaa"), strTemplate.matchesIndexed("this is aaa for 666", idx -> "?"));
        Assertions.assertEquals(ListKit.of(null, "b", "?"), strTemplate.matchesIndexed("this is  for b", idx -> "?"));
        Assertions.assertEquals(ListKit.of(null, "?", "aaa"), strTemplate.matchesIndexed("this is aaa for ", idx -> "?"));
        Assertions.assertEquals(ListKit.of(null, "?", "?"), strTemplate.matchesIndexed("this is  for ", idx -> "?"));

        strTemplate = StringTemplate.ofNamed("this is {2} for {1}").build();
        final List<Object> emptyList = Collections.emptyList();
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed(" ", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("  \r\n \n ", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed(" this is a for b", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this is a forb", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this  is a for b", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this are a for b", null));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("that is a for b", null));

        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed(" this is a for b", idx -> "?"));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this is a forb", idx -> "?"));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this  is a for b", idx -> "?"));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("this are a for b", idx -> "?"));
        Assertions.assertEquals(emptyList, strTemplate.matchesIndexed("that is a for b", idx -> "?"));
    }

    @Test
    public void namedPlaceholderMatchesTest() {
        final NamedStringTemplate strTemplate = StringTemplate.ofNamed("this is {tableName} for {id}").build();
        final Supplier<Map<String, String>> mapSupplier = HashMap::new;
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", "666").build(), strTemplate.matches("this is aaa for 666", mapSupplier));
        Assertions.assertEquals(MapKit.builder("tableName", null).put("id", "666").build(), strTemplate.matches("this is  for 666", mapSupplier));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", null).build(), strTemplate.matches("this is aaa for ", mapSupplier));
        Assertions.assertEquals(MapKit.builder("tableName", null).put("id", null).build(), strTemplate.matches("this is  for ", mapSupplier));
        Assertions.assertEquals(Collections.emptyMap(), strTemplate.matches("", mapSupplier));


        final Supplier<FormatEntity> beanSupplier = FormatEntity::new;
        Assertions.assertEquals(new FormatEntity("aaa", 666), strTemplate.matches("this is aaa for 666", beanSupplier));
        Assertions.assertEquals(new FormatEntity(null, 666), strTemplate.matches("this is  for 666", beanSupplier));
        Assertions.assertEquals(new FormatEntity("aaa", null), strTemplate.matches("this is aaa for ", beanSupplier));
        Assertions.assertEquals(new FormatEntity(null, null), strTemplate.matches("this is  for ", beanSupplier));
        Assertions.assertEquals(new FormatEntity(), strTemplate.matches("", beanSupplier));
    }

    @Test
    public void featureTest() {
        // 通常使用
        final String commonTemplate = "this is {tableName} for {id}";
        // ##### 使用新的策略 替换 默认策略 #####
        NamedStringTemplate template = StringTemplate.ofNamed(commonTemplate)
                .features(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_EMPTY, StringTemplate.Feature.MATCH_IGNORE_EMPTY_VALUE)
                .build();
        testFeature(template);

        // 添加新策略，互斥的策略则算作设置新策略，旧策略失效
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_EMPTY, StringTemplate.Feature.MATCH_IGNORE_DEFAULT_VALUE, StringTemplate.Feature.MATCH_IGNORE_EMPTY_VALUE)
                .build();
        testFeature(template);

        // ##### 删除策略 #####
        final NamedStringTemplate template2 = StringTemplate.ofNamed(commonTemplate)
                .removeFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_WHOLE_PLACEHOLDER)
                .build();
        Assertions.assertEquals("this is aaa for 666", template2.format(MapKit.builder("tableName", "aaa").put("id", "666").build()));
        Assertions.assertThrows(InternalException.class, () -> template2.format(MapKit.builder("tableName", "aaa").build()));

        // ##### 空字符串策略 #####
        template = StringTemplate.ofNamed(commonTemplate)
                // 解析时，空字符串 转为 null值
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_EMPTY, StringTemplate.Feature.MATCH_EMPTY_VALUE_TO_NULL)
                .build();
        Assertions.assertEquals("this is aaa for ", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", null).build(), template.matches("this is aaa for null"));

        // 解析时，空字符串 转为 默认值
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_EMPTY, StringTemplate.Feature.MATCH_EMPTY_VALUE_TO_DEFAULT_VALUE)
                .defaultValue("?")
                .build();
        Assertions.assertEquals("this is aaa for ", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", "?").build(), template.matches("this is aaa for "));

        // 默认值 为 空字符串，解析时，空字符串 转为 默认值
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_EMPTY, StringTemplate.Feature.MATCH_EMPTY_VALUE_TO_DEFAULT_VALUE)
                .defaultValue("")
                .build();
        Assertions.assertEquals("this is aaa for ", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", "").build(), template.matches("this is aaa for "));

        // ##### null值策略 #####
        // 解析时，null字符串 转为 null值
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_NULL, StringTemplate.Feature.MATCH_NULL_STR_TO_NULL)
                .build();
        Assertions.assertEquals("this is aaa for null", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", null).build(), template.matches("this is aaa for null"));
        // 格式化时，null值 转为 默认值 ；解析时，null字符串 转为 null值
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE, StringTemplate.Feature.MATCH_NULL_STR_TO_NULL)
                .defaultValue("null")
                .build();
        Assertions.assertEquals("this is aaa for null", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", null).build(), template.matches("this is aaa for null"));

        // 解析时，忽略 null字符串
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_NULL, StringTemplate.Feature.MATCH_IGNORE_NULL_STR)
                .build();
        Assertions.assertEquals("this is aaa for null", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").build(), template.matches("this is aaa for null"));
        // 格式化时，null值 转为 默认值 ；解析时，忽略 null字符串
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_DEFAULT_VALUE, StringTemplate.Feature.MATCH_IGNORE_NULL_STR)
                .defaultValue("null")
                .build();
        Assertions.assertEquals("this is aaa for null", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").build(), template.matches("this is aaa for null"));

        // 解析时，null字符串 依然为 "null"字符串
        template = StringTemplate.ofNamed(commonTemplate)
                .addFeatures(StringTemplate.Feature.FORMAT_MISSING_KEY_PRINT_NULL, StringTemplate.Feature.MATCH_KEEP_NULL_STR)
                .build();
        Assertions.assertEquals("this is aaa for null", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", "null").build(), template.matches("this is aaa for null"));
    }

    private void testFeature(final NamedStringTemplate template) {
        // 格式化
        Assertions.assertEquals("this is aaa for 666", template.format(MapKit.builder("tableName", "aaa").put("id", "666").build()));
        Assertions.assertEquals("this is aaa for ", template.format(MapKit.builder("tableName", "aaa").build()));
        Assertions.assertEquals("this is  for 666", template.format(MapKit.builder("id", "666").build()));
        Assertions.assertEquals("this is  for ", template.format(MapKit.builder().build()));

        // 解析
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").put("id", "666").build(), template.matches("this is aaa for 666"));
        Assertions.assertEquals(MapKit.builder("tableName", "aaa").build(), template.matches("this is aaa for "));
        Assertions.assertEquals(MapKit.builder("id", "666").build(), template.matches("this is  for 666"));
        Assertions.assertEquals(MapKit.builder().build(), template.matches("this is  for "));
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FormatEntity {
        private String tableName;
        private Integer id;
    }

}

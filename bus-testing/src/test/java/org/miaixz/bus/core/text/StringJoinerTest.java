package org.miaixz.bus.core.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ListKit;
import org.miaixz.bus.core.xyz.SetKit;

import java.util.ArrayList;
import java.util.List;

public class StringJoinerTest {

    @Test
    public void joinIntArrayTest() {
        final int[] a = {1, 2, 3, 4, 5};
        final StringJoiner append = StringJoiner.of(",").append(a);
        Assertions.assertEquals("1,2,3,4,5", append.toString());
    }

    @Test
    public void joinEmptyTest() {
        final List<String> list = new ArrayList<>();
        final StringJoiner append = StringJoiner.of(",").append(list);
        Assertions.assertEquals("", append.toString());
    }

    @Test
    public void noJoinTest() {
        final StringJoiner append = StringJoiner.of(",");
        Assertions.assertEquals("", append.toString());
    }

    @Test
    public void joinMultiArrayTest() {
        final StringJoiner append = StringJoiner.of(",");
        append.append(new Object[]{ListKit.view("1", "2"),
                SetKit.ofLinked("3", "4")
        });
        Assertions.assertEquals("1,2,3,4", append.toString());
    }

    @Test
    public void joinNullModeTest() {
        StringJoiner append = StringJoiner.of(",")
                .setNullMode(StringJoiner.NullMode.IGNORE)
                .append("1")
                .append((Object) null)
                .append("3");
        Assertions.assertEquals("1,3", append.toString());

        append = StringJoiner.of(",")
                .setNullMode(StringJoiner.NullMode.TO_EMPTY)
                .append("1")
                .append((Object) null)
                .append("3");
        Assertions.assertEquals("1,,3", append.toString());

        append = StringJoiner.of(",")
                .setNullMode(StringJoiner.NullMode.NULL_STRING)
                .append("1")
                .append((Object) null)
                .append("3");
        Assertions.assertEquals("1,null,3", append.toString());
    }

    @Test
    public void joinWrapTest() {
        StringJoiner append = StringJoiner.of(",", "[", "]")
                .append("1")
                .append("2")
                .append("3");
        Assertions.assertEquals("[1,2,3]", append.toString());

        append = StringJoiner.of(",", "[", "]")
                .setWrapElement(true)
                .append("1")
                .append("2")
                .append("3");
        Assertions.assertEquals("[1],[2],[3]", append.toString());
    }

    @Test
    public void lengthTest() {
        final StringJoiner joiner = StringJoiner.of(",", "[", "]");
        Assertions.assertEquals(joiner.toString().length(), joiner.length());

        joiner.append("123");
        Assertions.assertEquals(joiner.toString().length(), joiner.length());
    }

    @Test
    public void mergeTest() {
        final StringJoiner joiner1 = StringJoiner.of(",", "[", "]");
        joiner1.append("123");
        final StringJoiner joiner2 = StringJoiner.of(",", "[", "]");
        joiner1.append("456");
        joiner1.append("789");

        final StringJoiner merge = joiner1.merge(joiner2);
        Assertions.assertEquals("[123,456,789]", merge.toString());
    }

    @Test
    void issueTest() {
        final StringJoiner strJoinerEmpty = StringJoiner.of(",");
        Assertions.assertEquals(0, strJoinerEmpty.length());

        final StringJoiner strJoinerWithContent = StringJoiner.of(",").append("haha");
        Assertions.assertEquals(4, strJoinerWithContent.length());
    }

}

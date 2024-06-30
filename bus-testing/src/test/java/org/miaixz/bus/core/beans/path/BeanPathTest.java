package org.miaixz.bus.core.beans.path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BeanPathTest {

    @Test
    void parseDotTest() {
        BeanPath beanPath = new BeanPath("userInfo.examInfoDict[0].id");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("examInfoDict[0].id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0].id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals(".id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void parseDotWithQuoteTest() {
        BeanPath beanPath = new BeanPath("'userInfo'.examInfoDict[0].'id'");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("examInfoDict[0].'id'", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0].'id'", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals(".'id'", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void parseDotWithQuoteTest2() {
        BeanPath beanPath = new BeanPath("userInfo.'examInfoDict'[0].id");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("'examInfoDict'[0].id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0].id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals(".id", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void parseBucketTest() {
        BeanPath beanPath = new BeanPath("[userInfo][examInfoDict][0][id]");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("[examInfoDict][0][id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0][id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals("[id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void parseBucketWithQuoteTest() {
        BeanPath beanPath = new BeanPath("['userInfo']['examInfoDict'][0][id]");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("['examInfoDict'][0][id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0][id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals("[id]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void parseBucketWithQuoteTest2() {
        BeanPath beanPath = new BeanPath("[userInfo][examInfoDict][0]['id']");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("[examInfoDict][0]['id']", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("examInfoDict", beanPath.getNode().toString());
        Assertions.assertEquals("[0]['id']", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("0", beanPath.getNode().toString());
        Assertions.assertEquals("['id']", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("id", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void rangePathTest() {
        BeanPath beanPath = new BeanPath("[userInfo][2:3]");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("[2:3]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("[2:3:1]", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void listPathTest() {
        BeanPath beanPath = new BeanPath("[userInfo][1,2,3]");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("[1,2,3]", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("[1, 2, 3]", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }

    @Test
    void listKeysPathTest() {
        BeanPath beanPath = new BeanPath("[userInfo]['a', 'b', 'c']");
        Assertions.assertEquals("userInfo", beanPath.getNode().toString());
        Assertions.assertEquals("['a', 'b', 'c']", beanPath.getChild());

        beanPath = beanPath.next();
        Assertions.assertEquals("[a, b, c]", beanPath.getNode().toString());
        Assertions.assertNull(beanPath.getChild());
    }
}

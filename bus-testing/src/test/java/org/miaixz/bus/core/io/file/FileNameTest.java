package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileNameTest {

    @Test
    public void cleanInvalidTest() {
        String name = FileName.cleanInvalid("1\n2\n");
        Assertions.assertEquals("12", name);

        name = FileName.cleanInvalid("\r1\r\n2\n");
        Assertions.assertEquals("12", name);
    }

    @Test
    public void mainNameTest() {
        final String s = FileName.mainName("abc.tar.gz");
        Assertions.assertEquals("abc", s);
    }

    @Test
    public void normalizeTest() {
        Assertions.assertEquals("/foo/", FileName.normalize("/foo//"));
        Assertions.assertEquals("/foo/", FileName.normalize("/foo/./"));
        Assertions.assertEquals("/bar", FileName.normalize("/foo/../bar"));
        Assertions.assertEquals("/bar/", FileName.normalize("/foo/../bar/"));
        Assertions.assertEquals("/baz", FileName.normalize("/foo/../bar/../baz"));
        Assertions.assertEquals("/", FileName.normalize("/../"));
        Assertions.assertEquals("foo", FileName.normalize("foo/bar/.."));
        Assertions.assertEquals("../bar", FileName.normalize("foo/../../bar"));
        Assertions.assertEquals("bar", FileName.normalize("foo/../bar"));
        Assertions.assertEquals("/server/bar", FileName.normalize("//server/foo/../bar"));
        Assertions.assertEquals("/bar", FileName.normalize("//server/../bar"));
        Assertions.assertEquals("/core/bar", FileName.normalize("/core\\foo\\..\\bar"));
        //
        Assertions.assertEquals("/core/bar", FileName.normalize("/core\\..\\bar"));
        Assertions.assertEquals("../../bar", FileName.normalize("../../bar"));
        Assertions.assertEquals("/core/bar", FileName.normalize("/core/bar"));
        Assertions.assertEquals("/core", FileName.normalize("C:"));

        // smb保留格式
        Assertions.assertEquals("\\\\192.168.1.1\\Share\\", FileName.normalize("\\\\192.168.1.1\\Share\\"));
    }

    @Test
    public void normalizeBlankTest() {
        Assertions.assertEquals("/core/core/aaa ", FileName.normalize("/test/core/aaa "));
    }

}

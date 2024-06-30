package org.miaixz.bus.core.center.iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.InternalException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * test for {@link LineIterator}
 */
public class LineIteratorTest {

    private static LineIterator getItrFromClasspathFile() {
        final URL url = LineIteratorTest.class.getClassLoader().getResource("text.txt");
        Assertions.assertNotNull(url);
        final FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(url.getFile());
        } catch (final FileNotFoundException e) {
            throw new InternalException(e);
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return new LineIterator(bufferedReader);
    }

    @Test
    public void testHasNext() {
        final LineIterator iter = getItrFromClasspathFile();
        Assertions.assertTrue(iter.hasNext());
    }

    @Test
    public void testNext() {
        final LineIterator iter = getItrFromClasspathFile();
        Assertions.assertEquals("is first line", iter.next());
        Assertions.assertEquals("is second line", iter.next());
        Assertions.assertEquals("is third line", iter.next());
    }

    @Test
    public void testRemove() {
        final LineIterator iter = getItrFromClasspathFile();
        iter.next();
        Assertions.assertThrows(UnsupportedOperationException.class, iter::remove);
    }

    @Test
    public void testFinish() {
        final LineIterator iter = getItrFromClasspathFile();
        iter.finish();
        Assertions.assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    public void testClose() throws IOException {
        final URL url = LineIteratorTest.class.getClassLoader().getResource("text.txt");
        Assertions.assertNotNull(url);
        final FileInputStream inputStream = new FileInputStream(url.getFile());
        final LineIterator iter = new LineIterator(inputStream, StandardCharsets.UTF_8);
        iter.close();
        Assertions.assertThrows(NoSuchElementException.class, iter::next);
        Assertions.assertThrows(IOException.class, inputStream::read);
    }

}

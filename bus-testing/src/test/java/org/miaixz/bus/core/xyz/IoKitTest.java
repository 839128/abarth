package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.function.ConsumerX;
import org.miaixz.bus.core.center.iterator.LineIterator;
import org.miaixz.bus.core.io.StreamProgress;
import org.miaixz.bus.core.io.stream.EmptyOutputStream;
import org.miaixz.bus.core.io.stream.StringInputStream;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.Normal;
import org.miaixz.bus.core.lang.exception.InternalException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;

public class IoKitTest {


    @Test
    public void copyByNIOTest() {
        final File file = FileKit.file("LOGO.svg");
        final long size = IoKit.copyNio(ResourceKit.getStream("LOGO.svg"), EmptyOutputStream.INSTANCE, Normal.DEFAULT_MIDDLE_BUFFER_SIZE, null);

        // 确认写出
        Assertions.assertEquals(file.length(), size);
        Assertions.assertEquals(22807, size);
    }

    @Test
    @Disabled
    public void copyByNIOTest2() {
        final File file = FileKit.file("/test/core/logo.jpg");
        final BufferedInputStream in = FileKit.getInputStream(file);
        final BufferedOutputStream out = FileKit.getOutputStream("/test/core/2logo.jpg");

        final long copySize = IoKit.copy(in, out, Normal.DEFAULT_BUFFER_SIZE, new StreamProgress() {
            @Override
            public void start() {
                org.miaixz.bus.core.lang.Console.log("start");
            }

            @Override
            public void progress(final long total, final long progressSize) {
                org.miaixz.bus.core.lang.Console.log("{} {}", total, progressSize);
            }

            @Override
            public void finish() {
                Console.log("finish");
            }
        });
        Assertions.assertEquals(file.length(), copySize);
    }

    @Test
    public void readUtf8Test() throws IOException {
        final String s = IoKit.readNio(FileChannel.open(FileKit.file("text.txt").toPath()));
        Assertions.assertTrue(StringKit.isNotBlank(s));
    }


    @Test
    public void readBytesTest() {
        final byte[] bytes = IoKit.readBytes(ResourceKit.getStream("LOGO.svg"));
        Assertions.assertEquals(22807, bytes.length);
    }

    @Test
    public void readBytesWithLengthTest() {
        // 读取固定长度
        final int limit = RandomKit.randomInt(22807);
        final byte[] bytes = IoKit.readBytes(ResourceKit.getStream("LOGO.svg"), limit);
        Assertions.assertEquals(limit, bytes.length);
    }

    @Test
    public void readLinesTest() {
        try (final BufferedReader reader = ResourceKit.getReader("test_lines.csv")) {
            IoKit.readLines(reader, (ConsumerX<String>) Assertions::assertNotNull);
        } catch (final IOException e) {
            throw new InternalException(e);
        }
    }

    @Test
    public void readUtf8LinesTest() {
        final List<String> strings = IoKit.readUtf8Lines(ResourceKit.getStream("text.txt"), ListKit.of());
        Assertions.assertEquals(3, strings.size());
    }

    @Test
    public void readUtf8LinesTest2() {
        IoKit.readUtf8Lines(ResourceKit.getStream("text.txt"),
                (ConsumerX<String>) Assertions::assertNotNull);
    }

    @Test
    public void toBufferedTest() {
        final BufferedInputStream stream = IoKit.toBuffered(
                new ByteArrayInputStream("bus".getBytes()), Normal.DEFAULT_BUFFER_SIZE);

        Assertions.assertNotNull(stream);
        Assertions.assertEquals("bus", IoKit.readUtf8(stream));
    }

    @Test
    public void toBufferedOfOutTest() {
        final BufferedOutputStream stream = IoKit.toBuffered(
                EmptyOutputStream.INSTANCE, 512);

        Assertions.assertNotNull(stream);
    }

    @Test
    public void toBufferedOfReaderTest() {
        final BufferedReader reader = IoKit.toBuffered(
                new StringReader("bus"), 512);

        Assertions.assertNotNull(reader);

        Assertions.assertEquals("bus", IoKit.read(reader));
    }

    @Test
    public void toBufferedOfWriterTest() throws IOException {
        final StringWriter stringWriter = new StringWriter();
        final BufferedWriter writer = IoKit.toBuffered(
                stringWriter, 512);

        Assertions.assertNotNull(writer);
        writer.write("bus");
        writer.flush();

        Assertions.assertEquals("bus", stringWriter.toString());
    }

    @Test
    public void toBufferedOfWriterTest2() throws IOException {
        final StringWriter stringWriter = new StringWriter();
        final BufferedWriter writer = IoKit.toBuffered(stringWriter);

        Assertions.assertNotNull(writer);
        writer.write("bus");
        writer.flush();

        Assertions.assertEquals("bus", stringWriter.toString());
    }

    @Test
    public void toPushBackReaderTest() throws IOException {
        final PushbackReader reader = IoKit.toPushBackReader(new StringReader("bus"), 12);
        final int read = reader.read();
        Assertions.assertEquals('h', read);
        reader.unread(read);

        Assertions.assertEquals("bus", IoKit.read(reader));
    }

    @Test
    public void toAvailableStreamTest() {
        final InputStream in = IoKit.toAvailableStream(StringInputStream.of("bus"));
        final String read = IoKit.readUtf8(in);
        Assertions.assertEquals("bus", read);
    }

    @Test
    public void writeCloseTest() {
        IoKit.writeClose(EmptyOutputStream.INSTANCE, "bus".getBytes());
    }

    @Test
    public void writeUtf8Test() {
        IoKit.writeUtf8(EmptyOutputStream.INSTANCE, false, "bus");
    }

    @Test
    public void closeIfPossibleTest() {
        IoKit.close(new Object());
    }

    @Test
    public void contentEqualsTest() {
        final boolean b = IoKit.contentEquals(new StringReader("bus"), new StringReader("bus"));
        Assertions.assertTrue(b);
    }

    @Test
    public void lineIterTest() {
        final LineIterator strings = IoKit.lineIter(ResourceKit.getStream("text.txt"), Charset.UTF_8);
        strings.forEach(Assertions::assertNotNull);
    }

}

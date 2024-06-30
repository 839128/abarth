package org.miaixz.bus.office.csv;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.FileKit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CsvWriterTest {

    @Test
    @Disabled
    public void writeWithAliasTest() {
        final CsvWriteConfig csvWriteConfig = CsvWriteConfig.defaultConfig()
                .addHeaderAlias("name", "姓名")
                .addHeaderAlias("gender", "性别");

        final CsvWriter writer = CsvKit.getWriter(
                FileKit.file("/test/office/csvAliasTest.csv"),
                Charset.GBK, false, csvWriteConfig);

        writer.writeHeaderLine("name", "gender", "address");
        writer.writeLine("张三", "男", "XX市XX区");
        writer.writeLine("李四", "男", "XX市XX区,01号");
        writer.close();
    }

    @Test
    @Disabled
    public void issuesTest() {
        final String fileName = "/test/office/" + new Random().nextInt(100) + "-a.csv";
        final CsvWriter writer = CsvKit.getWriter(fileName, Charset.UTF_8);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(String.valueOf(i));
        }
        Console.log("{} : {}", fileName, list.size());
        for (final String s : list) {
            writer.writeLine(s);
        }
        writer.close();
    }

    @Test
    @Disabled
    public void issuesTest1() {
        // https://blog.csdn.net/weixin_42522167/article/details/112241143
        final File tmp = new File("/test/office/dde_safe.csv");
        final CsvWriter writer = CsvKit.getWriter(tmp, Charset.UTF_8);
        //设置 dde 安全模式
        writer.setDdeSafe(true);
        writer.write(
                new String[]{"=12+23"},
                new String[]{"%0A=12+23"},
                new String[]{"=;=12+23"},
                new String[]{"-3+2+cmd |' /C calc' !A0"},
                new String[]{"@SUM(cmd|'/c calc'!A0)"}
        );
        writer.close();
    }

    @Test
    @Disabled
    public void writeAppendTest() {
        final CsvWriter writer = CsvKit.getWriter(
                FileKit.file("/test/office/writeAppendTest.csv"),
                Charset.GBK, true);

        writer.writeHeaderLine("name", "gender", "address");
        writer.writeLine("张三", "男", "XX市XX区");
        writer.writeLine("李四", "男", "XX市XX区,01号");

        writer.writeLine("张三2", "男", "XX市XX区");
        writer.writeLine("李四2", "男", "XX市XX区,01号");
        writer.close();
    }

}

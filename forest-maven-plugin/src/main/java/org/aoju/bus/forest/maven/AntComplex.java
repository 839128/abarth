package org.aoju.bus.forest.maven;

import org.aoju.bus.forest.Complex;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;

import java.util.regex.Pattern;

/**
 * Ant表达式过滤器
 *
 * @author Kimi Liu
 * @version 3.0.9
 * @since JDK 1.8
 */
public abstract class AntComplex implements Complex<JarArchiveEntry> {

    private static final String[] SYMBOLS = {"\\", "$", "(", ")", "+", ".", "[", "]", "^", "{", "}", "|"};
    private final Pattern pattern;

    protected AntComplex(String ant) {
        String regex = ant;
        for (String symbol : SYMBOLS) regex = regex.replace(symbol, '\\' + symbol);
        regex = regex.replace("?", ".{1}");
        regex = regex.replace("**/", "(.{0,}?/){0,}?");
        regex = regex.replace("**", ".{0,}?");
        regex = regex.replace("*", "[^/]{0,}?");
        while (regex.startsWith("/")) regex = regex.substring(1);
        while (regex.endsWith("/")) regex = regex.substring(0, regex.length() - 1);
        this.pattern = Pattern.compile(regex);
    }

    protected boolean matches(String text) {
        return pattern.matcher(text).matches();
    }

}

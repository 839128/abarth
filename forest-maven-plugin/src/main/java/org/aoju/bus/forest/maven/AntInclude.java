package org.aoju.bus.forest.maven;

import org.aoju.bus.forest.Complex;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;

/**
 * 包含过滤器
 *
 * @author Kimi Liu
 * @version 3.0.9
 * @since JDK 1.8
 */
public class AntInclude extends AntComplex implements Complex<JarArchiveEntry> {

    public AntInclude(String ant) {
        super(ant);
    }

    @Override
    public boolean on(JarArchiveEntry entry) {
        return matches(entry.getName());
    }
}

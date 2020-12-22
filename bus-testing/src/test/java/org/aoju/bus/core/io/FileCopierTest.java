package org.aoju.bus.core.io;

import org.aoju.bus.core.io.file.FileCopier;
import org.aoju.bus.core.toolkit.FileKit;
import org.junit.Test;

/**
 * 文件拷贝单元测试
 */
public class FileCopierTest {

    @Test
    public void dirCopyTest() {
        FileCopier copier = FileCopier.create("/data/eclipse", "/data/eclipse/eclipse2.zip");
        copier.copy();
    }

    @Test
    public void dirCopyTest2() {
        //测试带.的文件夹复制
        FileCopier copier = FileCopier.create("/data/eclipse/.metadata", "/data/eclipse/temp");
        copier.copy();

        FileKit.copy("/data/eclipse/bus/.git", "/data/eclipse/temp", true);
    }

    @Test
    public void dirCopySubTest() {
        //测试父目录复制到子目录报错
        FileCopier copier = FileCopier.create("/data/eclipse/.metadata", "/data/eclipse/temp");
        copier.copy();
    }

}

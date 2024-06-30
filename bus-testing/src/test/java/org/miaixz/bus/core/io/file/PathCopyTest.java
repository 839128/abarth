package org.miaixz.bus.core.io.file;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件或目录拷贝测试
 */
public class PathCopyTest {

    @Test
    @Disabled
    public void copySameFileTest() {
        final Path path = Paths.get("/test/core/dir1/test1.txt");
        //src路径和target路径相同时，不执行操作
        PathResolve.copy(
                path,
                path);
    }

    @Test
    @Disabled
    public void copySameDirTest() {
        final Path path = Paths.get("/test/core/dir1");
        //src路径和target路径相同时，不执行操作
        PathResolve.copyContent(
                path,
                path);
    }

    @Test
    @Disabled
    public void copyFileToDirTest() {
        // src为文件，target为已存在目录，则拷贝到目录下，文件名不变。
        PathResolve.copy(
                Paths.get("/test/core/dir1/test1.txt"),
                Paths.get("/test/core/dir2"));
    }

    @Test
    @Disabled
    public void copyFileToPathNotExistTest() {
        // src为文件，target为不存在路径，则目标以文件对待（自动创建父级目录）
        // 相当于拷贝后重命名
        PathResolve.copy(
                Paths.get("/test/core/dir1/test1.txt"),
                Paths.get("/test/core/test2"));
    }

    @Test
    @Disabled
    public void copyFileToFileTest() {
        //src为文件，target是一个已存在的文件，则当{@link CopyOption}设为覆盖时会被覆盖，默认不覆盖。
        PathResolve.copy(
                Paths.get("/test/core/dir1/test1.txt"),
                Paths.get("/test/core/test2"));
    }

    @Test
    @Disabled
    public void copyDirToDirTest() {
        //src为目录，target为已存在目录，整个src目录连同其目录拷贝到目标目录中
        PathResolve.copy(
                Paths.get("/test/core/dir1/"),
                Paths.get("/test/core/dir2"));
    }

    @Test
    @Disabled
    public void copyDirToPathNotExistTest() {
        //src为目录，target为不存在路径，则自动创建目标为新目录，整个src目录连同其目录拷贝到目标目录中
        PathResolve.copy(
                Paths.get("/test/core/dir1/"),
                Paths.get("/test/core/dir3"));
    }

    @Test
    @Disabled
    public void copyDirToFileTest() {
        //src为目录，target为文件，抛出IllegalArgumentException
        PathResolve.copy(
                Paths.get("/test/core/dir1/"),
                Paths.get("/test/core/exist.txt"));
    }
}

# Forest-Maven-Plugin
#### Forest-Maven-Plugin 是对 [bus-forest](https://github.com/aoju/bus.git) 的一个Maven Plugin封装，实现可通过Maven命令或绑定在Maven构建的生命周期之中执行，用以更加便捷的方式集成 。

## 环境依赖
JDK 1.8 +

## 集成步骤
```xml
<project>

    <build>
        <plugins>
            <plugin>
                <groupId>org.aoju</groupId>
                <artifactId>forest-maven-plugin</artifactId>
                <version>1.0.5</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                        </goals>
                        <phase>package</phase>
                        <configuration>
                            <password>forest</password>
                            <!-- optional
                            <algorithm/>
                            <keySize/>
                            <ivSize/>
                            <mode/>
                            <includes>
                                <include/>
                            </includes>
                            <excludes>
                                <exclude/>
                            </excludes>
                            <sourceDir/>
                            <sourceJar/>
                            <targetDir/>
                            <targetJar/>
                            -->
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>

```
#### 也可以通过Maven命令单独执行 XJar 插件
```text
mvn xjar:build -Dxjar.password=forest
mvn xjar:build -Dxjar.password=forest -Dxjar.targetDir=/directory/to/save/target.xjar
```

#### 但通常情况下是让XJar插件绑定到指定的phase中自动执行，这样就能在项目构建的时候自动构建出加密的包。
```text
mvn clean package -Dxjar.password=forest
mvn clean install -Dxjar.password=forest -Dxjar.targetDir=/directory/to/save/target.xjar
```

## 强烈建议
强烈建议不要在 pom.xml 的 forest-maven-plugin 配置中写上密码，这样会导致打包出来的 xjar 包中的 pom.xml 文件保留着密码，极其容易暴露密码！强烈推荐通过 mvn 命令来指定加密密钥！

## 注意事项
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <!-- 需要将executable和embeddedLaunchScript参数删除，目前还不能支持对该模式Jar的加密！后面将会支持该方式的打包。 
    <configuration>
        <executable>true</executable>
        <embeddedLaunchScript>...</embeddedLaunchScript>
    </configuration>
    -->
</plugin>
```

## 参数说明
| 参数名称 | 命令参数名称 | 参数说明 | 参数类型 | 缺省值 | 示例值 |
| :------ | :----------- | :------ | :------ | :----- | :----- |
| password | -Dxjar.password | 密码字符串 | String | 必须 | 任意字符串，forest |
| algorithm | -Dxjar.algorithm | 加密算法名称 | String | AES | JDK内置加密算法，如：AES / DES |
| keySize | -Dxjar.keySize | 密钥长度 | int | 128 | 根据加密算法而定，56，128，256 |
| ivSize | -Dxjar.ivSize | 密钥向量长度 | int | 128 | 根据加密算法而定，128 |
| mode | -Dxjar.mode | 加密模式 | int | 0 | 0：普通模式 1：危险模式（免密码启动）|
| sourceDir | -Dxjar.sourceDir | 源jar所在目录 | File | ${project.build.directory} | 文件目录 |
| sourceJar | -Dxjar.sourceJar | 源jar名称 | String | ${project.build.finalName}.jar | 文件名称 |
| targetDir | -Dxjar.targetDir | 目标jar存放目录 | File | ${project.build.directory} | 文件目录 |
| targetJar | -Dxjar.targetJar | 目标jar名称 | String | ${project.build.finalName}.xjar | 文件名称 |
| includes | -Dxjar.includes | 需要加密的资源路径表达式 | String[] | 无 | com/company/project/** , mapper/*Mapper.xml , 支持Ant表达式 |
| excludes | -Dxjar.excludes | 无需加密的资源路径表达式 | String[] | 无 | static/** , META-INF/resources/** , 支持Ant表达式 |

#### 注意：
* 当 includes 和 excludes 同时使用时即加密在includes的范围内且排除了excludes的资源。
* mode 设置为 1 时表示危险加密模式，但同时也是免密码启动，请谨慎使用！

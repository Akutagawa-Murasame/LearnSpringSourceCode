package org.mura.springframework.core.io;

import cn.hutool.core.lang.Assert;
import org.mura.springframework.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:12
 *
 * 获取classpath中的配置文件
 */
public class ClassPathResource implements Resource {
    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
//        使用了hutool
        Assert.notNull(path, "Path must not be null");

        this.path = path;
        this.classLoader = (classLoader != null ?
                classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(path);

        if (inputStream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }

        return inputStream;
    }
}

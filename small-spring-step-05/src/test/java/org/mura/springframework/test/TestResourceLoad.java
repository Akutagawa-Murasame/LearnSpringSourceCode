package org.mura.springframework.test;

import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;
import org.mura.springframework.core.io.DefaultResourceLoader;
import org.mura.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/25 23:47
 * <p>
 * 测试资源加载
 */
public class TestResourceLoad {
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    /**
     * 测试类路径
     */
    @Test
    public void testClassPath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        System.out.println(resource.getClass());
        InputStream inputStream = resource.getInputStream();
        System.out.println(IoUtil.readUtf8(inputStream));
    }

    /**
     * 测试文件路径
     */
    @Test
    public void testFilePath() throws IOException {
        Resource resource = resourceLoader.getResource("src/main/resources/important.properties");
        System.out.println(resource.getClass());
        InputStream inputStream = resource.getInputStream();
        System.out.println(IoUtil.readUtf8(inputStream));
    }

    /**
     * 测试网络路径
     */
    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/Akutagawa-Murasame/LearnSpringSourceCode/blob/main/.gitignore");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

}

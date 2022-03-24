package org.mura.springframework.beans.factory.support;

import org.mura.springframework.core.io.DefaultResourceLoader;
import org.mura.springframework.core.io.ResourceLoader;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:03
 *
 * 配置bean的注册器和bean文件资源的加载器
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private final ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this(beanDefinitionRegistry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry,
                                        ResourceLoader resourceLoader) {
        this.registry = beanDefinitionRegistry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}

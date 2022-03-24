package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.core.io.Resource;
import org.mura.springframework.core.io.ResourceLoader;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:04
 *
 * Bean定义读取接口
 *
 * getRegistry()、getResourceLoader()，都是用于提供给后面三个方法
 * 的工具，加载和注册，这两个方法的实现会包装到抽象类中，以免污染具体的接口
 * 实现方法。
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;
}

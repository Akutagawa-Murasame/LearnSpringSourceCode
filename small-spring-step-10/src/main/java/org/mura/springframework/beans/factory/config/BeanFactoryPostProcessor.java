package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:44
 *
 * BeanFactoryPostProcessor，是由 Spring 框架组建提供的容器扩展机制，允许在
 * Bean 对象注册后但未实例化之前，对 Bean 的定义信息 BeanDefinition 执
 * 行修改操作。
 *
 * BeanFactoryPostProcessor针对的是全部
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修
     * 改 BeanDefinition属性的机制
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}

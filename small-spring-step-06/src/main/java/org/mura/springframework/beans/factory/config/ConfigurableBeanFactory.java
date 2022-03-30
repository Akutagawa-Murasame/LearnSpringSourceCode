package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:02
 *
 * ConfigurableBeanFactory，可获取 BeanPostProcessor、BeanClassLoader 等的一个
 * 配置化接口。
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}

package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Akutagawa Murasame
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注册 BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}

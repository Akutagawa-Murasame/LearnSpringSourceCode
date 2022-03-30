package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Akutagawa Murasame
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注册 BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 使用Bean名称查询BeanDefinition
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 判断是否包含指定名称的BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回注册表中所有的Bean名称
     */
    String[] getBeanDefinitionNames();
}

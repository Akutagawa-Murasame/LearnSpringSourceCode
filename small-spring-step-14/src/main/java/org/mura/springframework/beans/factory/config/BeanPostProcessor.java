package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:45
 *
 * BeanPostProcessor，也是 Spring 提供的扩展机制，不过 BeanPostProcessor 是在
 * Bean 对象实例化之后修改 Bean 对象，也可以替换 Bean 对象。这部分与后面要
 * 实现的 AOP 有着密切的关系。
 *
 * BeanPostProcessor针对的是某一个
 */
public interface BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
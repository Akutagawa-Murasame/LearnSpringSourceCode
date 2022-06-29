package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValues;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:20
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法，顺便感知
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在工厂将给定的属性值应用于给定的 bean 之前对其进行后处理。
     * 允许检查是否所有依赖项都已满足，例如基于 bean 属性注解上的“required”属性。
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;
}

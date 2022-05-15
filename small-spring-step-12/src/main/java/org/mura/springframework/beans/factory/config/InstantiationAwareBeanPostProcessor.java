package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/14 18:20
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法，顺便感知
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}

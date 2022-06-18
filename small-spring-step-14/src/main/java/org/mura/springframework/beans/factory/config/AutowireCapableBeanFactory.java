package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.BeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 20:56
 *
 * 自动化处理 Bean 工厂配置的接口
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
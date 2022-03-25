package org.mura.springframework.beans.factory;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/24 21:09
 *
 * 提供分析和修改 Bean 以及预先实例化的操作接
 * 口，不过目前只有一个 getBeanDefinition 方法
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory,
        AutowireCapableBeanFactory,
        ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}

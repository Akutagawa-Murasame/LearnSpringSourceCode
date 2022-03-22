package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.BeanFactory;
import org.mura.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Akutagawa Murasame
 * AbstractBeanFactory 首先继承了 DefaultSingletonBeanRegistry，也就具备了使用
 * 单例注册类方法。
 * - 接下来很重要的一点是关于接口 BeanFactory 的实现，在方法 getBean 的实现过
 * 程中可以看到，主要是对单例 Bean 对象的获取以及在获取不到时需要拿到 Bean
 * 的定义做相应 Bean 实例化操作。那么 getBean 并没有自身的去实现这些方法，
 * 而是只定义了调用过程以及提供了抽象方法，由实现此抽象类的其他类做相应实
 * 现。
 * - 后续继承抽象类 AbstractBeanFactory 的类有两个，包括：
 * AbstractAutowireCapableBeanFactory、DefaultListableBeanFactory，这两个类分别
 * 做了相应的实现处理
 */
public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry
        implements BeanFactory {
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);

//        有该单例对象，返回单例对象
        if (bean != null) {
            return bean;
        }

//        否则，按照方法创建bean
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
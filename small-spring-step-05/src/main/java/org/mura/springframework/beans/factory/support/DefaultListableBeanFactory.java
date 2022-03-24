package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * 核心类
 * DefaultListableBeanFactory 在 Spring 源码中也是一个非常核心的类，在我们目前
 * 的实现中也是逐步贴近于源码，与源码类名保持一致
 *
 *  DefaultListableBeanFactory 继承了 AbstractAutowireCapableBeanFactory 类，也
 * 就具备了接口 BeanFactory 和 AbstractBeanFactory 等一连串的功能实现。所以有
 * 时候你会看到一些类的强转，调用某些方法，也是因为你强转的类实现接口或继承
 * 了某些类。
 *
 * 除此之外这个类还实现了接口 BeanDefinitionRegistry 中的
 * registerBeanDefinition(String beanName, BeanDefinition beanDefinition) 方法，当
 * 然你还会看到一个 getBeanDefinition 的实现，这个方法我们文中提到过它是抽象
 * 类 AbstractBeanFactory 中定义的抽象方法。现在注册 Bean 定义与获取 Bean 定义
 *
 * 就可以同时使用了，是不感觉这个套路还蛮深的。接口定义了注册，抽象类定义了
 * 获取，都集中在 DefaultListableBeanFactory 中的 beanDefinitionMap 里
 */
public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory
        implements BeanDefinitionRegistry,
        ConfigurableListableBeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }



    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }
}

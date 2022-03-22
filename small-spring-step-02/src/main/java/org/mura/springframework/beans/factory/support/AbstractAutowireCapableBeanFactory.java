package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Akutagawa Murasame
 * 因为AbstractAutowireCapableBeanFactory 本身也是一个抽象类，所以它只会实现属于
 * 自己的抽象方法，其他抽象方法由继承 AbstractAutowireCapableBeanFactory 的
 * 类实现。这里就体现了类实现过程中的各司其职，你只需要关心属于你的内容，不
 * 是你的内容，不要参与。
 *
 * 在 AbstractAutowireCapableBeanFactory 类中实现了 Bean 的实例化操作
 * newInstance，其实这块会埋下一个坑，有构造函数入参的对象怎么处理？可以
 * 提前思考
 * - 在处理完 Bean 对象的实例化后，直接调用 addSingleton 方法存放到单例对
 * 象的缓存中去。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;

        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

//        此处都是单例的，还未涉及原型模式
        addSingleton(beanName, bean);
        return bean;
    }
}

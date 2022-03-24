package org.mura.springframework.beans.factory;

import org.mura.springframework.beans.BeansException;

/**
 * @author Akutagawa Murasame
 * 首先我们需要定义 BeanFactory 这样一个 Bean 工厂，提供 Bean 的获取方法
 * getBean(String name)，之后这个 Bean 工厂接口由抽象类
 * AbstractBeanFactory 实现。这样使用模板模式的设计方式，可以统一收口通用核
 * 心方法的调用逻辑和标准定义，也就很好地控制了后续的实现者不用关心调用逻
 * 辑，按照统一方式执行。那么类的继承者只需要关心具体方法的逻辑实现即可
 *
 * 为了解决默认构造函数问题，新增了getBean接口，获取bean的同时传入构造函数的参数
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType);
}

package org.mura.springframework.beans.factory.config;

/**
 * @author Akutagawa Murasame
 * 单例注册接口
 * 这个类比较简单主要是定义了一个获取单例对象的接口。
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
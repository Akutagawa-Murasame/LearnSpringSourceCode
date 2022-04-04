package org.mura.springframework.test.common;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValue;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:52
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        工厂bean后置处理器在beanDefinition已经加载但是没有初始化时生效，所以beanDefinition存在
//        此时xml已被解析
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

//        properties中此时有两个company属性，因为properties使用list来存储的，所以遍历赋值时后面的company会覆盖前面的
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
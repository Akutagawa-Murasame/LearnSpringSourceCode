package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.PropertyValues;

/**
 * @author Akutagawa Murasame
 * 在 Bean 定义类中已经把上一章节中的 Object bean 替换为 Class，这样就可以把
 * Bean 的实例化操作放到容器中处理了。如果你有仔细阅读过上一章并做了相应的
 * 测试，那么你会发现 Bean 的实例化操作是放在初始化调用阶段传递给
 * BeanDefinition 构造函数的。
 */
public class BeanDefinition {
    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
//        在构造时就保证PropertyValues不为null，避免了潜在的错误和麻烦
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        if (null != propertyValues) {
            this.propertyValues = propertyValues;
        }
    }
}

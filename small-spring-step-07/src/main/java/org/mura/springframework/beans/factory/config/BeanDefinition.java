package org.mura.springframework.beans.factory.config;

import org.mura.springframework.beans.PropertyValues;

/**
 * @author Akutagawa Murasame
 * BeanDefinition 新增加了两个属性：initMethodName、destroyMethodName，
 * 这两个属性是为了在 spring.xml 配置的 Bean 对象中，可以配置 init-method="initDataMethod" destroy-method="destroyDataMethod"
 * 操作，最终实现接口的效果是一样的。只不过一个是接口方法的直接调用，另外是一个在配置文件中读取到方法反射调用
 */
public class BeanDefinition {
    private Class<?> beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
//        在构造时就保证PropertyValues不为null，避免了潜在的错误和麻烦
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}

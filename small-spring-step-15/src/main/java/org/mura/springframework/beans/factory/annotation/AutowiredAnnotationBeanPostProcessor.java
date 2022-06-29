package org.mura.springframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.BeanFactory;
import org.mura.springframework.beans.factory.BeanFactoryAware;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.mura.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.mura.springframework.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * @author Akutagawa Murasame
 * @date 2022/6/16 19:23、
 *
 * 用于处理类中的注解的后置处理器
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
//      1. 处理注解 @Value
        Class<?> clazz = bean.getClass();

//        这里需要注意一点因为我们在 AbstractAutowireCapableBeanFactory 类中使用的
//        是 CglibSubclassingInstantiationStrategy 进行类的创建，所以在
//        AutowiredAnnotationBeanPostProcessor#postProcessPropertyValues 中需要判断是
//        否为 CGlib 创建对象，否则是不能正确拿到类信息的。
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);

//            如果属性定义了value注解，就不会拿到null值
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

//      2. 处理注解 @Autowired
        for (Field field : declaredFields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);

            if (null != autowiredAnnotation) {
//                Autowired默认按照类型注入,所以这里先获取待注入的字段的类型
                Class<?> fieldType = field.getType();

                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;

                if (null != qualifierAnnotation) {
                    dependentBeanName = qualifierAnnotation.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }

                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

//        目前看来,propertyValue这个参数只是用来return的
        return propertyValues;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
}

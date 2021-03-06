package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.PropertyValue;
import org.mura.springframework.beans.PropertyValues;
import org.mura.springframework.beans.factory.config.BeanDefinition;
import org.mura.springframework.beans.factory.config.BeanReference;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;

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
 *
 * 现在通过定义bean的实例化策略来避免构造函数有参数的问题
 * 我们选择了Cglib增强代理，这个在spring中其实是可选的，以后再说
 *
 * step-04改动的类主要是 AbstractAutowireCapableBeanFactory，在
 * createBean 中补全属性填充部分。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object... args) throws BeansException {
        Object bean;

        try {
            bean = createBeanInstance(beanDefinition, beanName, args);

            //        给bean填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

//        此处都是单例的，还未涉及原型模式
        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object... args) throws BeansException {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

//        获取bean的所有构造方法
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

//        这里只比较了参数的数量，而实际 Spring 源码中还需要比对入参
//类型，否则相同数量不同入参类型的情况，就会抛异常了。
        for (Constructor<?> constructor : declaredConstructors) {
            if (null != args && constructor.getParameterTypes().length == args.length) {
                constructorToUse = constructor;
                break;
            }
        }

        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    /**
     * bean属性填充
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

//                如果是引用类型，循环依赖问题，当前版本其实没用到这个，这个内容较多，后续版本再写
                if (value instanceof BeanReference) {
//                    A 依赖 B，获取 B 的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

//                属性填充
//                小傅这里引入了hutool依赖，但是pdf里没说。。。
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting properties values:" + beanName);
        }
    }
}

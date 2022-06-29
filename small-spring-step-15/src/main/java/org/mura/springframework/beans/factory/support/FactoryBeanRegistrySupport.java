package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/24 21:59
 *
 * 这个类用来注册FactoryBean产生的Bean的
 *
 * 这其实和数据库的数据缓存到redis很像
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * 缓存被FactoryBeans创建的单例，缓存的是FactoryBean对象
     * FactoryBean name -> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 获取缓存好了的FactoryBean单例，NULL_OBJECT是一个没有赋任何值的new Object()
     */
    protected  Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);

        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 从指定的FactoryBean中获取对象（单例和原型）
     */
    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) throws BeansException {
//        如果FactoryBean是单例的，就在缓存中看看是否存在目标FactoryBean
        if (factoryBean.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);

//            如果缓存中没有，就直接调用传入的FactoryBean的getObject方法，相当于new一个对象，并且缓存起来
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }

            return (object != NULL_OBJECT ? object : null);
        } else {
//            如果FactoryBean不是单例的，就直接调用传入的FactoryBean的getObject方法，相当于new一个对象
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    /**
     * 从传入的FactoryBean中寻找目标bean，找不到报错
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) throws BeansException {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
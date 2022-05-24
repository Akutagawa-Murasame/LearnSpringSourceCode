package org.mura.springframework.beans.factory.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.DisposableBean;
import org.mura.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Akutagawa Murasame
 * 在 DefaultSingletonBeanRegistry 中主要实现 getSingleton 方法，同时实现了一个
 * 受保护的 addSingleton 方法，这个方法可以被继承此类的其他类调用。包括：
 * AbstractBeanFactory 以及继承的 DefaultListableBeanFactory 调用。
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() throws BeansException {
        Iterator<Map.Entry<String, DisposableBean>> disposableBeanIterator = disposableBeans.entrySet().iterator();
        while (disposableBeanIterator.hasNext()) {
            Map.Entry<String, DisposableBean> entry = disposableBeanIterator.next();

            try {
                entry.getValue().destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + entry.getKey() + "' threw an exception", e);
            }

            disposableBeanIterator.remove();
        }
    }
}

package org.mura.springframework.context.event;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.BeanFactory;
import org.mura.springframework.beans.factory.BeanFactoryAware;
import org.mura.springframework.context.ApplicationEvent;
import org.mura.springframework.context.ApplicationListener;
import org.mura.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Akutagawa Murasame
 * @date 2022/5/1 15:11
 *
 * 多播器主要用来统一管理监听器
 */
public abstract class AbstractApplicationEventMulticaster
        implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * 获取指定事件ApplicationEvent支持的监听器
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) throws BeansException {
        LinkedList<ApplicationListener> listeners = new LinkedList<>();

        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                listeners.add(listener);
            }
        }

        return listeners;
    }

    /**
     * 监听器是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) throws BeansException {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

//      按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class（获取原始class，Cglib代理类的原始class就是代理类的super类）
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

//        获取监听器实现的接口
        Type genericInterface = targetClass.getGenericInterfaces()[0];

//        获取监听器的接口的泛型
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;

        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }

//        判定此 eventClassName 对象所表示的类或接口与指定的 event.getClass() 参数所表
//        示的类或接口是否相同，或是否是其超类或超接口。
//        也就是说，event继承了监听器的接口的泛型，就满足监听的条件
//        方便最后确认是否为子类和父类的关系，以此证明此事件归这个符合的类处理
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
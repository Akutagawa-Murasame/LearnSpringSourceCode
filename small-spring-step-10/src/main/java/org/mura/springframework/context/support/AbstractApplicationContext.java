package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.context.ApplicationEvent;
import org.mura.springframework.context.ApplicationListener;
import org.mura.springframework.context.ConfigurableApplicationContext;
import org.mura.springframework.context.event.*;
import org.mura.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:50
 *
 * 抽象应用上下文，提供refresh方法
 *
 * beanFactoryPostProcessor和beanPostProcessor的区别
 * https://blog.csdn.net/caihaijiang/article/details/35552859
 *
 * small-spring-step-10在这个类的refresh方法中添加了关于事件监听的代码
 * 事件监听包括应用上下文refresh监听，应用上下文关闭监听和事件发布
 */
public abstract class AbstractApplicationContext
        extends DefaultResourceLoader
        implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    public ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
//        1. 创建 BeanFactory，并加载 BeanDefinition（初始化一个bean工厂）
        refreshBeanFactory();

//        2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

//        3.添加ApplicationContextAwareProcessor，让继承
//        自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

//        4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
//         (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

//        5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

//        6. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();

//        7. 初始化事件发布者
        initApplicationEventMulticaster();

//        8. 注册事件监听器
        registerListeners();

//        9. 发布容器刷新完成事件
        finishRefresh();

//        监听器注册在bean初始化之后，因此目前不能监听bean的创建
    }

    private void initApplicationEventMulticaster() throws BeansException {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        AbstractApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() throws BeansException {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() throws BeansException {
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationEvent event) throws BeansException {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void close() throws BeansException {
//        发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

//        执行销毁单例 bean 的销毁方法
        getBeanFactory().destroySingletons();
    }

    /**
     * 创建bean工厂
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取bean工厂
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        BeanFactoryPostProcessor也是bean，需要先定义在容器中（这个注释与此处代码无关）
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        在可配置可列表bean工厂中获得所有BeanPostProcessor类型的bean，并且将他们注册到这个工厂中
//        注意：只是注册，没有调用
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);

        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                this.close();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() throws BeansException {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }
}
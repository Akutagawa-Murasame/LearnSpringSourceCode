package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.context.ConfigurableApplicationContext;
import org.mura.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:50
 *
 * 抽象应用上下文，提供refresh方法
 *
 * beanFactoryPostProcessor和beanPostProcessor的区别
 * https://blog.csdn.net/caihaijiang/article/details/35552859
 */
public abstract class AbstractApplicationContext
        extends DefaultResourceLoader
        implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
//        1. 创建 BeanFactory，并加载 BeanDefinition（初始化一个bean工厂）
        refreshBeanFactory();

//        2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

//         3. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor
//         (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

//          4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

//         5. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();
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
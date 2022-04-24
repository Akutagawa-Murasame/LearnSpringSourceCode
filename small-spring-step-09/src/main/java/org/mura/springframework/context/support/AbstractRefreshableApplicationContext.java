package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.mura.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Akutagawa Murasame
 * @date 2022/3/30 13:50
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * loadBeanDefinitions(beanFactory)，在加载完成后即可完成对
     * spring.xml 配置文件中 Bean 对象的定义和注册，同时也包括实现了接口
     * BeanFactoryPostProcessor、BeanPostProcessor 的配置 Bean 信息。
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}

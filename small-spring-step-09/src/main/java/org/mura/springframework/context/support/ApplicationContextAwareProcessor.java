package org.mura.springframework.context.support;

import org.mura.springframework.beans.BeansException;
import org.mura.springframework.beans.factory.config.BeanPostProcessor;
import org.mura.springframework.context.ApplicationContext;
import org.mura.springframework.context.ApplicationContextAware;

/**
 * @author Akutagawa Murasame
 * @date 2022/4/20 18:44
 *
 * 包装处理器(ApplicationContextAwareProcessor)
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 由于 ApplicationContext 的获取并不能直接在创建 Bean 时候就可以拿到，所以
     * 需要在 refresh 操作时，把 ApplicationContext 写入到一个包装的
     * BeanPostProcessor 中去，再由AbstractAutowireCapableBeanFactory
     * .applyBeanPostProcessorsBeforeInitialization方法调用。
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
